import java.nio.charset.StandardCharsets
import java.util

import org.apache.kafka.clients.consumer.KafkaConsumer
import org.apache.kafka.clients.producer.{KafkaProducer, ProducerRecord}
import scaldi.Injectable

object Main extends App with Injectable{

  import bindings.Binding.appInjector

  val producer = inject[KafkaProducer[Integer,Array[Byte]]]
  val data = ProducerRecordTest("test", "This is a message")
  producer.send(data)
  producer.close()

  val consumer = inject[KafkaConsumer[Integer,Array[Byte]]]
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
}
