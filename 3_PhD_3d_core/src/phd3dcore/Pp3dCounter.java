package phd3dcore;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapred.MapReduceBase;
import org.apache.hadoop.mapred.OutputCollector;
import org.apache.hadoop.mapred.Reducer;
import org.apache.hadoop.mapred.Reporter;

//Debugging / info class to count how many peaks reach each reducer

public class Pp3dCounter extends MapReduceBase implements Reducer<IntWritable, Text, IntWritable, Text> {
       public static String inputFile = new String();
	   @SuppressWarnings("unused")
	public void reduce(IntWritable key, Iterator<Text> values, OutputCollector<IntWritable, Text> output, Reporter reporter) throws IOException {
		    ArrayList<PointMountain> mountainPoints = new ArrayList<PointMountain>();
		    ArrayList<PointMountain> outputPoints = new ArrayList<PointMountain>();
		    String outText = null;
		   	

   		    double maxMz = 0.0;
   		    double minMz = 3000;
   		    int peakCnt = 0;
   		    
		   	while (values.hasNext()) {
	   			String[] tempStr;	   
	   		    tempStr = values.next().toString().split("\t");

	   		    
	   		    try{	   		    	
	   		    	if (Double.parseDouble(tempStr[4]) > maxMz){
	   		    		maxMz = Double.parseDouble(tempStr[4]);	   		    		
	   		    	}
	   		    	
	   		    	if (Double.parseDouble(tempStr[4]) < minMz){
	   		    		minMz = Double.parseDouble(tempStr[4]);	   		    		
	   		    	}
	   		    	
	   		    peakCnt++;
 	   		    
	   		    } catch (Exception e){
	   		    	//need to add some exception detail
	   		    }

		   	}
		   	
				outText = minMz + "\t"  + 
						maxMz 	+ "\t"  +
						peakCnt;
			 	output.collect(key, new Text(outText));
			
	   }
	   
  }