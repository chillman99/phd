package phdflinkstream;

import org.apache.flink.api.java.DataSet;
import org.apache.flink.api.java.ExecutionEnvironment;
import org.apache.flink.api.java.hadoop.mapreduce.HadoopInputFormat;
import org.apache.flink.api.java.hadoop.mapreduce.HadoopOutputFormat;
import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.hadoopcompatibility.mapred.HadoopMapFunction;
import org.apache.flink.hadoopcompatibility.mapred.HadoopReduceFunction;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;

import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.mapreduce.lib.input.TextInputFormat;
import org.apache.hadoop.mapreduce.lib.output.TextOutputFormat;
   	
public class PeakPickFlink_MR {

   	public static void main(String[] args) throws Exception{
   		
   		final ExecutionEnvironment env = ExecutionEnvironment.getExecutionEnvironment();
   		
   		// Set up the Hadoop TextInputFormat.
   		Job job = Job.getInstance();
   		HadoopInputFormat<LongWritable, Text> hadoopIF = 
   		  new HadoopInputFormat<LongWritable, Text>(
   		    new TextInputFormat(), LongWritable.class, Text.class, job
   		  );
   		TextInputFormat.addInputPath(job, new Path(args[0]));
   				
   		// Read data using the Hadoop TextInputFormat.
   		DataSet<Tuple2<LongWritable, Text>> text = env.createInput(hadoopIF);
   		DataSet<Tuple2<IntWritable, Text>> result = text
   				 
   		  // use Hadoop Mapper as MapFunction
   		  .flatMap(new HadoopMapFunction<LongWritable, Text, IntWritable, Text>(
   		    new MapHDFS()
   		  ))
   		  .groupBy(0)
   		  // use Hadoop Reducer
   		  .reduceGroup(new HadoopReduceFunction<IntWritable, Text, IntWritable, Text>(
   		    new ReduceHDFS()
   		  ));
 
   		// Set up the Hadoop TextOutputFormat.
		  HadoopOutputFormat<IntWritable, Text> hadoopOF = 
		  new HadoopOutputFormat<IntWritable, Text>(
		    new TextOutputFormat<IntWritable, Text>(), job
		  );
		  
   		hadoopOF.getConfiguration().set("mapreduce.output.textoutputformat.separator", " ");
   		TextOutputFormat.setOutputPath(job, new Path(args[1]));   
   		
   		// Emit data using the Hadoop TextOutputFormat.
   		result.output(hadoopOF).setParallelism(1);
   		
   		// Execute Program
   		env.execute("Hadoop PeakPick");
   		   	       		
   	}
   	
}
