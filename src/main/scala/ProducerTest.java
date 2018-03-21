import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Iterator;
import java.util.Properties;

public class ProducerTest {
    public static void main(String[] args) throws Exception{
        Properties producerProps = new Properties();
        producerProps.put("zk.connect","localhost:2181");
        producerProps.put("serializer.class", "kafka.serializer.StringEncoder");
        producerProps.put("bootstrap.servers", "localhost:9092");
        producerProps.put("key.serializer","org.apache.kafka.common.serialization.IntegerSerializer");
        producerProps.put("value.serializer", "org.apache.kafka.common.serialization.ByteArraySerializer");

        KafkaProducer<Integer, byte[]> producer = new KafkaProducer<Integer, byte[]>(producerProps);

        ProducerRecord<Integer, byte[]> data = new ProducerRecord<Integer, byte[]>("test","This is a message19".getBytes(StandardCharsets.UTF_8));
        producer.send(data);
        producer.close();

        Properties consumerProps = new Properties();
        consumerProps.put("bootstrap.servers", "localhost:9092");
        consumerProps.setProperty("key.deserializer","org.apache.kafka.common.serialization.IntegerDeserializer");
        consumerProps.setProperty("value.deserializer", "org.apache.kafka.common.serialization.ByteArrayDeserializer");
        consumerProps.setProperty("group.id", "group0");
        consumerProps.put("auto.offset.reset", "earliest");
        KafkaConsumer<Integer, byte[]> consumer = new KafkaConsumer<>(consumerProps);
        consumer.subscribe(Arrays.asList("test"));
        ConsumerRecords<Integer,byte[]> records = consumer.poll(5000);
        System.out.println(records.count());
        int count = records.count();
        Iterator<ConsumerRecord<Integer, byte[]>> recordIterator = records.iterator();
        for (int i=0;i<count;i++) {
            ConsumerRecord<Integer, byte[]> record = recordIterator.next();
            String output = new String(record.value(), StandardCharsets.UTF_8);
            System.out.println(output);
        }
    }
}
