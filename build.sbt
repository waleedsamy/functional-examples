import Dependencies._

lazy val root = (project in file(".")).settings(
  inThisBuild(
    List(
      organization := "io.github.waleedsamy.examples",
      scalaVersion := "2.13.7",
      version := "0.1.0-SNAPSHOT"
    )),
  name := "FunctionalProgrammingTutorial",
  libraryDependencies ++= Seq(
    catsCore,
    scalaTest % Test
  )
)

scalafmtOnCompile := true
