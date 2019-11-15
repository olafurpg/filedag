inThisBuild(
  List(
    scalaVersion := "2.12.10"
  )
)

lazy val example = project
  .settings(
    scalacOptions ++= List(
      "-Yrangepos",
      "-P:semanticdb:synthetics:on"
    ),
    addCompilerPlugin(
      "org.scalameta" % "semanticdb-scalac" % "4.2.5" cross CrossVersion.full
    )
  )
lazy val filedag = project
  .settings(
    libraryDependencies ++= List(
      "com.lihaoyi" %% "pprint" % "0.5.6",
      "org.scalameta" % "semanticdb-scalac-core" % "4.2.5" cross CrossVersion.full,
      "org.scalameta" % "metac" % "4.2.5" cross CrossVersion.full
    )
  )
  .dependsOn(example)
