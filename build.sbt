name := "Kafka"

version := "0.1"

scalaVersion := "2.11.0"

libraryDependencies ++= Seq(
  // The excludes of jms, jmxtools and jmxri are required as per https://issues.apache.org/jira/browse/KAFKA-974.
  // The exclude of slf4j-simple is because it overlaps with our use of logback with slf4j facade;  without the exclude
  // we get slf4j warnings and logback's configuration is not picked up.
  "org.apache.kafka" % "kafka_2.11" % "0.11.0.0"
    exclude("javax.jms", "jms")
    exclude("com.sun.jdmk", "jmxtools")
    exclude("com.sun.jmx", "jmxri")
    exclude("org.slf4j", "slf4j-simple"),
  // Logback with slf4j facade
  "ch.qos.logback" % "logback-classic" % "1.0.13",
  "ch.qos.logback" % "logback-core" % "1.0.13",
  "org.slf4j" % "slf4j-api" % "1.7.5",
  "org.scalatest" % "scalatest_2.10" % "2.0" % "test"
)