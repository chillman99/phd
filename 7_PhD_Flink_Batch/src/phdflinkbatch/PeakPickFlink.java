package phdflinkbatch;

import org.apache.flink.api.java.DataSet;
import org.apache.flink.api.java.ExecutionEnvironment;
import org.apache.flink.api.java.hadoop.mapreduce.HadoopOutputFormat;
import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.api.java.tuple.Tuple8;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.mapreduce.lib.input.TextInputFormat;
import org.apache.hadoop.mapreduce.lib.output.TextOutputFormat;
   	
public class PeakPickFlink {

   	public static void main(String[] args) throws Exception{
   		
   		final ExecutionEnvironment env = ExecutionEnvironment.getExecutionEnvironment();
   		Job job = Job.getInstance();
   		
   		DataSet<Tuple2<LongWritable, Text>> input =
   			    env.readHadoopFile(new TextInputFormat(), LongWritable.class, Text.class, args[0]);
   		   		
   		DataSet<Tuple8<Integer, Double, Integer, Double, Integer, Integer, Double, Integer>> mapResult =
   		//DataSet<Tuple2<IntWritable, Text>> mapResult =
   				input.flatMap(new TestMap());
   		
   		DataSet<Tuple2<IntWritable, Text>> ReduceResult = mapResult
   				                           .groupBy(0)     
   				                           .reduceGroup(new TestReduce()); 
   		
   		// Set up the Hadoop TextOutputFormat.
   		HadoopOutputFormat<IntWritable, Text> hadoopOF = 
		  new HadoopOutputFormat<IntWritable, Text>(
		    new TextOutputFormat<IntWritable, Text>(), job
		  );
		  
   		hadoopOF.getConfiguration().set("mapreduce.output.textoutputformat.separator", " ");
   		TextOutputFormat.setOutputPath(job, new Path(args[1]));   
   		
   		// Emit data using the Hadoop TextOutputFormat.
   		ReduceResult.output(hadoopOF).setParallelism(1);
   		//mapResult.output(hadoopOF).setParallelism(1);
   		
   		// Execute Program
   		env.execute("Hadoop PeakPick");
   		   	       		
   	}   
   	
}

