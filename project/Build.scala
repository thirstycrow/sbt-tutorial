import sbt._
import Keys._
import com.twitter.scrooge.ScroogeSBT

object SbtTut extends Build {

  lazy val root = Project("sbt-tut", file("."))
    .aggregate(thrift, server, clientJava, clientScala)

  lazy val thrift = Project("sbt-tut-thrift", file("thrift"))
    .configs(thriftGenJava, thriftGenScala)
    .settings(thriftGenSettings(thriftGenJava, "java"): _*)
    .settings(thriftGenSettings(thriftGenScala, "scala"): _*)
    .settings(
      libraryDependencies ++= Dependencies.scroogeDependencies
    )

  lazy val server = Project("sbt-tut-server", file("server"))
    .dependsOn(thrift)

  lazy val clientJava = Project("sbt-tut-client-java", file("client-java"))
    .dependsOn(thrift)

  lazy val clientScala = Project("sbt-tut-client-scala", file("client-scala"))
    .dependsOn(thrift)

  lazy val thriftGenJava = config("thriftGenJava") extend Compile

  lazy val thriftGenScala = config("thriftGenScala") extend Compile

  def thriftGenSettings(config: Configuration, language: String) = {
    inConfig(config)(
      ScroogeSBT.genThriftSettings ++ Seq(
        ScroogeSBT.scroogeLanguage := language,
        sourceGenerators in Compile <+= ScroogeSBT.scroogeGen
      )
    )
  }

  object Dependencies {

    import Dependency._

    val scroogeDependencies = Seq(
      libthrift, scroogeCore, finagleThrift
    )
  }

  object Dependency {
    object V {
      val Scala = "2.10.4"
      val Scrooge = "3.12.3"
      val Finagle = "6.12.1"
    }

    val libthrift = "org.apache.thrift" % "libthrift" % "0.5.0"
    val scroogeCore = "com.twitter" %% "scrooge-core" % V.Scrooge
    val finagleThrift = "com.twitter" %% "finagle-thrift" % V.Finagle
  }
}
