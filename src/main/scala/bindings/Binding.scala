package bindings

import java.util.Properties

import org.apache.kafka.clients.consumer.KafkaConsumer
import org.apache.kafka.clients.producer.KafkaProducer
import scaldi.Module

object Binding {

  implicit lazy val appInjector = {
    new ProducerModule :: new ConsumerModule
  }

  class ProducerModule extends Module {
    val producerProps = new Properties()
    producerProps.put("zk.connect","localhost:2181")
    producerProps.put("serializer.class", "kafka.serializer.StringEncoder")
    producerProps.put("bootstrap.servers", "localhost:9092")
    producerProps.put("key.serializer", "org.apache.kafka.common.serialization.IntegerSerializer")
    producerProps.put("value.serializer", "org.apache.kafka.common.serialization.ByteArraySerializer")
    bind[KafkaProducer[Integer,Array[Byte]]] toProvider  new KafkaProducer[Integer, Array[Byte]](producerProps)
  }

  class ConsumerModule extends Module {
    val consumerProps = new Properties
    consumerProps.put("bootstrap.servers", "localhost:9092")
    consumerProps.setProperty("key.deserializer", "org.apache.kafka.common.serialization.IntegerDeserializer")
    consumerProps.setProperty("value.deserializer", "org.apache.kafka.common.serialization.ByteArrayDeserializer")
    consumerProps.setProperty("group.id", "group0")
    consumerProps.put("auto.offset.reset", "earliest")
    bind[KafkaConsumer[Integer,Array[Byte]]] toProvider new KafkaConsumer[Integer, Array[Byte]](consumerProps)
  }

}
