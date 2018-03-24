import java.nio.charset.StandardCharsets
import java.util
import java.util.Properties

import org.apache.kafka.clients.consumer.KafkaConsumer
import org.apache.kafka.clients.producer.{KafkaProducer, ProducerRecord}

object Main extends App {

  val producer = createProducer()
  val data = new ProducerRecord[Integer, Array[Byte]]("test", "This is a message19".getBytes(StandardCharsets.UTF_8))
  producer.send(data)
  producer.close()

  val consumer = createConsumer()
  consumer.subscribe(util.Arrays.asList("test"))
  val records = consumer.poll(5000)
  println(records.count)
  val count = records.count
  val recordIterator = records.iterator
  var i = 0
  while ( {
    i < count
  }) {
    val record = recordIterator.next
    val output = new String(record.value, StandardCharsets.UTF_8)
    println(output)

    {
      i += 1; i - 1
    }
  }

  def createProducer(): KafkaProducer[Integer, Array[Byte]] ={
    val producerProps = new Properties()
    producerProps.put("zk.connect","localhost:2181")
    producerProps.put("serializer.class", "kafka.serializer.StringEncoder")
    producerProps.put("bootstrap.servers", "localhost:9092")
    producerProps.put("key.serializer", "org.apache.kafka.common.serialization.IntegerSerializer")
    producerProps.put("value.serializer", "org.apache.kafka.common.serialization.ByteArraySerializer")
    val result = new KafkaProducer[Integer,Array[Byte]](producerProps)
    result
  }
  def createConsumer(): KafkaConsumer[Integer, Array[Byte]] = {
    val consumerProps = new Properties
    consumerProps.put("bootstrap.servers", "localhost:9092")
    consumerProps.setProperty("key.deserializer", "org.apache.kafka.common.serialization.IntegerDeserializer")
    consumerProps.setProperty("value.deserializer", "org.apache.kafka.common.serialization.ByteArrayDeserializer")
    consumerProps.setProperty("group.id", "group0")
    consumerProps.put("auto.offset.reset", "earliest")
    val result = new KafkaConsumer[Integer,Array[Byte]](consumerProps)
    result
  }

}
