package phd3dcore;

import java.io.IOException;
import java.util.Iterator;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapred.MapReduceBase;
import org.apache.hadoop.mapred.OutputCollector;
import org.apache.hadoop.mapred.Reducer;
import org.apache.hadoop.mapred.Reporter;

//Debugging / info class to count how many peaks reach each reducer

public class Pp3dWriteMapPeaks extends MapReduceBase implements Reducer<IntWritable, Text, IntWritable, Text> {
       public static String inputFile = new String();
	   @SuppressWarnings("unused")
	public void reduce(IntWritable key, Iterator<Text> values, OutputCollector<IntWritable, Text> output, Reporter reporter) throws IOException {	   
		    String outText = null;

		   	int inputCount = 0;
		   	while (values.hasNext()) {
	   			String[] tempStr;	   
	   		    tempStr = values.next().toString().split("\t");

	   		    
				outText =	Double.parseDouble(tempStr[4]) + "\t" +
						    Double.parseDouble(tempStr[5]) + "\t" +
						    Double.parseDouble(tempStr[5]) + "\t" +
						    Double.parseDouble(tempStr[2]) + "\t" +
						    Integer.parseInt(tempStr[0]) + "\t" +
						    Integer.parseInt(tempStr[6]);
				output.collect(new IntWritable(0), new Text(outText)); 

		   	}		   	
			
	   }
	   
  }