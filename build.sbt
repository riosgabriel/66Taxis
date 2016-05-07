name := "66Taxis"

version := "1.0-SNAPSHOT"

lazy val root = project.in(file(".")).enablePlugins(PlayScala)

libraryDependencies ++= Seq(
  "org.scalatest" % "scalatest_2.9.2" % "1.8" % "test" withSources()
    withJavadoc()
)

fork in run := true