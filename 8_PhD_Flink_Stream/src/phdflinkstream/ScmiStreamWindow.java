package phdflinkstream;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.windowing.helper.Time;
import org.apache.flink.streaming.connectors.kafka.FlinkKafkaConsumer082;
import org.apache.flink.streaming.util.serialization.SimpleStringSchema;

@SuppressWarnings("deprecation")
public class ScmiStreamWindow {

	public static void main(String[] args) throws Exception {
		StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();

		Properties props = new Properties();
		props.setProperty("zookeeper.connect", "localhost:2181"); // Zookeeper host:port
		props.setProperty("bootstrap.servers", "localhost:9092"); // Broker host:port
		props.setProperty("group.id", "myGroup");                 // Consumer group ID

		DataStream<String> scmiScan = env.addSource(
		  new FlinkKafkaConsumer082<String>(
		    "scmiStream",										  //Kafka Topic name
		    new SimpleStringSchema(), props)
		  );
		
		scmiScan.flatMap(new StreamMap())
			.window(Time.of(10, TimeUnit.SECONDS)).every(Time.of(1, TimeUnit.SECONDS))
			 	.reduceWindow(new StreamReduce());
		
//		scmiScan.flatMap(new StreamMapWindow()).addSink(new KafkaSink("localhost:9092", "peak2DStream", new SimpleStringSchema()));		
//		env.execute("Scmi Stream Processing");
//		
	}
	
}

/*
 * Start Stream using these commands in 4 seperate command windows
/Users/localadmin/Documents/working/flink/flink-1.0.0/bin/start-zookeeper-quorum.sh /Users/localadmin/Documents/working/flink/flink-1.0.0/conf/zoo.cfg

/Users/localadmin/Documents/working/kafka/kafka_2.10-0.8.2.1/bin/kafka-server-start.sh /Users/localadmin/Documents/working/kafka/kafka_2.10-0.8.2.1/config/server.properties &

java -cp  /Users/localadmin/Documents/Box\ Sync/UoD/PhD/code/kafkacode/StreamScmiFile.jar StreamScmiFile 127.0.0.1:9092 127.0.0.1:2181 /Users/localadmin/Documents/Box\ Sync/UoD/PhD/data/100312_100.scmi scmiStream 500

/Users/localadmin/Documents/working/kafka/kafka_2.10-0.8.2.1/bin/kafka-console-consumer.sh   --zookeeper localhost:2181   --topic scmiStream
*/




