Gatling plugin for Maven - Scala demo project
=============================================

A simple showcase of a Maven project using the Gatling plugin for Maven. Refer to the plugin documentation
[on the Gatling website](https://gatling.io/docs/current/extensions/maven_plugin/) for usage.

This project is written in Scala, others are available for [Java](https://github.com/gatling/gatling-maven-plugin-demo-java)
and [Kotlin](https://github.com/gatling/gatling-maven-plugin-demo-kotlin).

It includes:

* [Maven Wrapper](https://maven.apache.org/wrapper/), so that you can immediately run Maven with `./mvnw` without having
  to install it on your computer
* minimal `pom.xml`
* latest version of `io.gatling:gatling-maven-plugin` applied
* sample [Simulation](https://gatling.io/docs/gatling/reference/current/general/concepts/#simulation) class,
  demonstrating sufficient Gatling functionality
* proper source file layout


Step to run: 
1. Download Maven
2. Install IntelliJ with Java 8 or later
3. Install Scala plugin from IntelliJ
4. Setup SDK Scala for the project
5. Run the Engine.scala file
6. Select VideoGameTest option while selecting simulation number
7. Select run description (optional)