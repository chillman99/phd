package phd3dcore;

import java.io.IOException;
import java.util.Iterator;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapred.MapReduceBase;
import org.apache.hadoop.mapred.OutputCollector;
import org.apache.hadoop.mapred.Reducer;
import org.apache.hadoop.mapred.Reporter;

//Debugging / info class to count peaks by WPM values

public class Pp3dWpmCount extends MapReduceBase implements Reducer<IntWritable, Text, IntWritable, Text> {
    public void reduce(IntWritable key, Iterator<Text> values, OutputCollector<IntWritable, Text> output, Reporter reporter) throws IOException {
      int sum = 0;
      String count = new String();
      while (values.hasNext()) {
       count = values.next().toString();
       sum = sum + Integer.parseInt(count); 
       
      }
      output.collect(key, new Text(Integer.toString(sum))); 
   }
    
}