package videogamedb.scriptfundamentals

import io.gatling.core.Predef._
import io.gatling.http.Predef._

import java.time.LocalDate
import java.time.format.DateTimeFormatter
import scala.util.Random

class VideoGameTest extends Simulation{
  val httpProtocal = http.baseUrl(url = "https://www.videogamedb.uk/api")
    .acceptHeader(value = "application/json")
    .contentTypeHeader(value = "application/json")

  def authenticate() = {
    exec(http(requestName = "Authenticate")
      .post("/authenticate")
      .body(StringBody("{\n  \"password\": \"admin\",\n  \"username\": \"admin\"\n}"))
      .check(jsonPath(path = "$.token").saveAs("token")))
  }

  var idNumbers = (1 to 200).iterator
  val rnd = new Random()
  val pattern = DateTimeFormatter.ofPattern("yyyy-MM-dd")
  val now = LocalDate.now()

  def randomString(length: Int) = {
    rnd.alphanumeric.filter(_.isLetter).take(length).mkString
  }

  def getRandomDate(startDate: LocalDate, random: Random): String = {
    startDate.minusDays(random.nextInt(30)).format(pattern)
  }

  val customFeeder = Iterator.continually(Map(
    "gameId" -> idNumbers.next(),
    "name" -> ("Game-" + randomString(5)),
    "releaseDate" -> getRandomDate(now, rnd),
    "reviewScore" -> rnd.nextInt(100),
    "category" -> ("Category-" + randomString(6)),
    "rating" -> ("Rating-" + randomString(4))
  ))



  def createNewGame() = {
      feed(customFeeder)
        .exec(http(requestName = "Create a new game - #{name}")
          .post("/videogame")
          .header("authorization", "Bearer #{token}")
          .body(ElFileBody("bodies/newGameTemplates.json")).asJson
          .check(bodyString.saveAs("responseBody"))
        )
        .exec {
          session => println(session("responseBody").as[String]); session
        }
        .pause(1)
  }


  def deleteGame()={
    exec(http(requestName="Delete a game")
      .delete("/videogame/1")
      .header(name = "Authorization", value = "Bearer #{token}")
      .check(status.is(200))
      .check(bodyString.is("Video game deleted")))
  }

  val scn = scenario("Video Game Create and Delete")
    .exec(authenticate())
    .exec(createNewGame())
    .exec(deleteGame())


  // Before and After hooks
  before {
    println("Starting the Video Game Create and Delete Simulation")
  }

  after {
    println("Completed the Video Game Create and Delete Simulation")
  }

  // Load Simulation
  // Load Scenario
  setUp(scn.inject(
    nothingFor(5),
    constantUsersPerSec(10).during(10),
    rampUsersPerSec(1).to(5).during(20)
  ).protocols(httpProtocal))
}


