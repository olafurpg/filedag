inThisBuild(List(
  scalaVersion := "2.12.10"
))

lazy val filedag = project
  .settings(
    libraryDependencies ++= List(
      "org.scalameta" % "semanticdb-scalac-core" % "4.2.5" cross CrossVersion.full,
      "org.scalameta" % "metac" % "4.2.5" cross CrossVersion.full,
    )
  )
