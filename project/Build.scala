import sbt._
import Keys._

object SbtTut extends Build {

  lazy val root = Project("sbt-tut", file("."))
    .aggregate(thrift, server, clientJava, clientScala)

  lazy val thrift = Project("sbt-tut-thrift", file("thrift"))

  lazy val server = Project("sbt-tut-server", file("server"))
    .dependsOn(thrift)

  lazy val clientJava = Project("sbt-tut-client-java", file("client-java"))
    .dependsOn(thrift)

  lazy val clientScala = Project("sbt-tut-client-scala", file("client-scala"))
    .dependsOn(thrift)
}
