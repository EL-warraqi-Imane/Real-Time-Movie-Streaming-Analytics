import scala.collection.Seq

ThisBuild / version := "0.1.0-SNAPSHOT"

ThisBuild / scalaVersion := "2.13.12"  // Scala 2.13.12 pour Spark 3.5

lazy val root = (project in file("."))
  .settings(
    name := "SCALA",
    libraryDependencies ++= Seq(
      // Apache Spark 3.5.1 (compatible Java 17)
      "org.apache.spark" %% "spark-core" % "3.5.1",
      "org.apache.spark" %% "spark-sql" % "3.5.1",
      "org.apache.spark" %% "spark-streaming" % "3.5.1",
      "org.apache.spark" %% "spark-sql-kafka-0-10" % "3.5.1",
      "org.apache.spark" %% "spark-streaming-kafka-0-10" % "3.5.1",

      // Kafka clients
      "org.apache.kafka" % "kafka-clients" % "3.6.1",

      // JSON4S - VERSION COMPATIBLE AVEC SPARK 3.5.1
      "org.json4s" %% "json4s-jackson" % "3.7.0-M11",

      // MongoDB
      // Dans build.sbt
      "org.mongodb" % "mongodb-driver-sync" % "4.11.1"
    )
  )