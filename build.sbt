name := "Kafka-SparkStreaming"


version := "0.1"

scalaVersion := "2.11.12"

val sparkVersion = "2.2.0"

//Unit тесты для Scala
libraryDependencies ++=Seq(
  "org.scalatest" %% "scalatest" % "3.0.4"
)



resolvers ++= Seq(
  "apache-snapshots" at "http://repository.apache.org/snapshots/"
)


libraryDependencies ++= Seq(
  "org.apache.spark" %% "spark-core" % sparkVersion,
  "org.apache.spark" %% "spark-sql" % sparkVersion,
  "org.apache.spark" %% "spark-mllib" % sparkVersion,
  "org.apache.spark" %% "spark-streaming" % sparkVersion,
  "org.apache.spark" %% "spark-hive" % sparkVersion,
  "org.apache.spark" % "spark-streaming_2.11" % sparkVersion,
  "org.apache.spark" % "spark-streaming-kafka-0-10_2.11" % sparkVersion,

  "org.apache.kafka" % "kafka-clients" % sparkVersion,
  "org.apache.kafka" %% "kafka-streams-scala" % sparkVersion //kafka




  //Дополнительные библиотеки
  //"com.typesafe.scala-logging" %% "scala-logging" % "3.8.0",
  //"org.apache.kafka" %% "kafka" % "0.10.0.1",
  //"org.apache.spark" %% "spark-sql-kafka-0-10" % sparkVersion

  //"org.apache.spark" % "spark-streaming-flume_2.11" % sparkVersion, //flume
  //"org.apache.spark" % "spark-streaming-kinesis-asl_2.11" % sparkVersion //kinesis
)

//Чтобы устранить конфликт версий Jackson
libraryDependencies += "com.fasterxml.jackson.module" %% "jackson-module-scala" % "2.9.8"

//MySQL connector JDBC
libraryDependencies += "mysql" % "mysql-connector-java" % "5.1.44" //5.1.16 //6.0.6






