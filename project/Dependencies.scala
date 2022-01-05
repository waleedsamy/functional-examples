import sbt._

object Dependencies {
  lazy val scalaLogging = "com.typesafe.scala-logging" %% "scala-logging" % "3.9.4"
  lazy val logback = "ch.qos.logback" % "logback-classic" % "1.2.10"
  lazy val catsCore = "org.typelevel" %% "cats-core" % "2.3.0"
  lazy val catsEffect = "org.typelevel" %% "cats-effect" % "3.3.3"
  lazy val scalaTest = "org.scalatest" %% "scalatest" % "3.2.10"
  lazy val scalaCheck = "org.scalacheck" %% "scalacheck" % "1.14.1"
}
