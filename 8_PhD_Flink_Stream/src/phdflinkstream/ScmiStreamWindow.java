package phdflinkstream;
import java.util.HashSet;
import java.util.Properties;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.apache.flink.api.common.functions.FlatMapFunction;
import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.datastream.IterativeDataStream;
import org.apache.flink.streaming.api.datastream.WindowedDataStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.scala.windowing.Time;
import org.apache.flink.streaming.api.windowing.StreamWindow;
import org.apache.flink.streaming.connectors.kafka.*;
import org.apache.flink.streaming.util.serialization.SimpleStringSchema;
import org.apache.hadoop.hdfs.server.protocol.DatanodeStorage.State;

import phd3dcore.PointMountain;

@SuppressWarnings("unused")
public class ScmiStreamWindow {
	//static Set<String> windowOutput = new HashSet<String>();

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
			.window(Time.of(40, TimeUnit.SECONDS)).every(Time.of(1, TimeUnit.SECONDS))
			.mapWindow(new Stream3D());
			//.getDiscretizedStream()
			//.print()

		env.execute("Scmi Stream Processing");
		
	}
	
}




