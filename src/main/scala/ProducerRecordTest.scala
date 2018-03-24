import java.nio.charset.StandardCharsets

import org.apache.kafka.clients.producer.ProducerRecord

case class ProducerRecordTest(topicName: String, message: String)
  extends ProducerRecord[Integer, Array[Byte]](topicName,message.getBytes(StandardCharsets.UTF_8))
