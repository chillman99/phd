package phdflinkbatch;

import org.apache.flink.api.java.utils.ParameterTool;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.util.serialization.SimpleStringSchema;
import org.apache.flink.streaming.connectors.kafka.FlinkKafkaConsumer082;

public class PeakPickFlinkStream {

   	public static void main(String[] args) throws Exception{
   		
   		StreamExecutionEnvironment env =
   			  StreamExecutionEnvironment.getExecutionEnvironment();
   			 
   			ParameterTool parameterTool = ParameterTool.fromArgs(args);
   			 
   			DataStream<String> messageStream = env
   			  .addSource(new FlinkKafkaConsumer082<>(
   			    parameterTool.getRequired("topic"),
   			    new SimpleStringSchema(),
   			    parameterTool.getProperties()));
   				
   		//Job job = Job.getInstance();

   		/*	
   		messageStream.flatMap(null)	;
   		DataSet<Tuple8<Integer, Double, Integer, Double, Integer, Integer, Double, Integer>> mapResult = 
   				messageStream.flatMap(new TestMap());
   		*/
   		//DataSet<Tuple2<IntWritable, Text>> ReduceResult = mapResult
   		//		                           .groupBy(0)     
   		//		                           .reduceGroup(new TestReduce()); 
   		
   		// Set up the Hadoop TextOutputFormat.
   		/*
   		HadoopOutputFormat<IntWritable, Text> hadoopOF = 
		  new HadoopOutputFormat<IntWritable, Text>(
		    new TextOutputFormat<IntWritable, Text>(), job
		  );
		  
   		hadoopOF.getConfiguration().set("mapreduce.output.textoutputformat.separator", " ");
   		TextOutputFormat.setOutputPath(job, new Path(args[1]));   
   		
   		// Emit data using the Hadoop TextOutputFormat.
   		//ReduceResult.output(hadoopOF).setParallelism(1);
   		*/
   			/*
   			String inputLine = messageStream.toString();
   			String[] tempStr = inputLine.split("\t");		
   			int scNumber = Integer.parseInt(tempStr[1]);
   			int scLevel = Integer.parseInt(tempStr[2]);
   			String mzString = tempStr[7];
   			String intensityString = tempStr[8];
   			double RT = Double.parseDouble(tempStr[3]);
   			*/
   			/*
   			mapResult <Tuple5<Integer, Integer, String, String, Double>> = new <Tuple5<Integer, Integer, String, String, Double>> 
   				(scNumber, scLevel, mzString, intensityString, RT);   			   							
   			mapResult.writeAsCsv("mapoutput.csv");
   			*/
   			//System.out.println(messageStream.toString());
   			
   			messageStream.print();   			
   		
   		// Execute Program
   		//env.execute("Hadoop PeakPick");
   		   	       		
   	}   

}
