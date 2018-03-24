import java.nio.charset.StandardCharsets
import java.util

import org.apache.kafka.clients.consumer.KafkaConsumer
import org.apache.kafka.clients.producer.KafkaProducer
import scaldi.Injectable

object Main extends App with Injectable{

  import bindings.Binding.appInjector

  private val topicName = "test"

  val producer = inject[KafkaProducer[Integer,Array[Byte]]]
  val data = ProducerRecordTest(topicName, "This is a message")
  producer.send(data)
  producer.close()

  val consumer = inject[KafkaConsumer[Integer,Array[Byte]]]
  consumer.subscribe(util.Arrays.asList(topicName))
  val records = consumer.poll(5000)
  println(records.count)
  val recordIterator = records.iterator
  while ( recordIterator.hasNext) {
    val record = recordIterator.next
    val output = new String(record.value, StandardCharsets.UTF_8)
    println(output)
  }
}
