import Dependencies._

lazy val root = (project in file(".")).settings(
  inThisBuild(
    List(
      organization := "io.github.waleedsamy.examples",
      scalaVersion := "2.13.7",
      version := "0.1.0-SNAPSHOT",
      semanticdbEnabled := true,
      semanticdbVersion := scalafixSemanticdb.revision,
      scalafixScalaBinaryVersion := "2.13",
      scalafixDependencies ++= Seq(
        "com.github.liancheng" %% "organize-imports" % "0.6.0"
      )
    )
  ),
  name := "FunctionalProgrammingTutorial",
  libraryDependencies ++= Seq(
    scalaLogging,
    logback,
    catsCore,
    catsEffect,
    zio,
    scalaTest % Test,
    scalaCheck % Test
  )
)

scalafmtOnCompile := true
addCompilerPlugin("org.typelevel" % "kind-projector" % "0.13.2" cross CrossVersion.full)
scalacOptions ++= Seq("-Wunused")
