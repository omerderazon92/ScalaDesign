name := "ConfigFetcher"

version := "0.1"

scalaVersion := "2.11.8"

libraryDependencies += "com.typesafe.akka" %% "akka-http" % "10.1.10"

libraryDependencies += "com.typesafe.akka" %% "akka-stream" % "2.5.23"

libraryDependencies += "org.scalaj" %% "scalaj-http" % "2.4.2"

libraryDependencies += "com.typesafe.scala-logging" %% "scala-logging" % "3.9.2"

libraryDependencies += "ch.qos.logback" % "logback-classic" % "1.2.3"

//libraryDependencies += "org.scalatest" %% "scalatest" % "3.2.0-M2" % Test

libraryDependencies += "org.scalactic" %% "scalactic" % "3.2.0-M2"
libraryDependencies += "org.scalatest" %% "scalatest" % "3.2.0-M2" % "test"

val circeVersion = "0.7.0"
libraryDependencies ++= Seq(
"io.circe" %% "circe-core" % circeVersion,
"io.circe" %% "circe-generic" % circeVersion,
"io.circe" %% "circe-parser" % circeVersion
)





