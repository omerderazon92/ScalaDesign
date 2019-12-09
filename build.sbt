name := "ConfigFetcher"

version := "0.1"

scalaVersion := "2.12.7"

libraryDependencies += "com.typesafe.akka" %% "akka-http" % "10.1.10"

libraryDependencies += "com.typesafe.akka" %% "akka-stream" % "2.5.23"

libraryDependencies += "org.scalaj" %% "scalaj-http" % "2.4.2"

libraryDependencies += "org.json4s" %% "json4s-jackson" % "3.7.0-M1"

libraryDependencies += "org.json4s" %% "json4s-native" % "3.7.0-M1"

val circeVersion = "0.7.0"
libraryDependencies ++= Seq(
  "io.circe"  %% "circe-core"     % circeVersion,
  "io.circe"  %% "circe-generic"  % circeVersion,
  "io.circe"  %% "circe-parser"   % circeVersion
)

libraryDependencies += "com.typesafe.scala-logging" %% "scala-logging" % "3.9.2"

