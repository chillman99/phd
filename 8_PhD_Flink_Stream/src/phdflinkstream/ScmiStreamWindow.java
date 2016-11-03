package phdflinkstream;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.windowing.helper.Time;
import org.apache.flink.streaming.connectors.kafka.FlinkKafkaConsumer082;
import org.apache.flink.streaming.util.serialization.SimpleStringSchema;

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
			//.window(Time.of(40, TimeUnit.SECONDS)).every(Time.of(1, TimeUnit.SECONDS))
			.window(Time.of(2, TimeUnit.SECONDS)).every(Time.of(1, TimeUnit.SECONDS))
			.reduceWindow(new StreamReduce());
		
		env.execute("Scmi Stream Processing");
		
	}
	
}




