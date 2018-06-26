import Dependencies._

lazy val root = (project in file(".")).
  settings(
    inThisBuild(List(
      organization := "io.github.waleedsamy.examples",
      scalaVersion := "2.12.6",
      version      := "0.1.0-SNAPSHOT"
    )),
    name := "FunctionalProgrammingTutorial",
    libraryDependencies ++= Seq(
      catsCore,
      scalaTest % Test
    )
  )

scalacOptions += "-Ypartial-unification"
