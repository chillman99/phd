package phdflinkstream;

import java.io.*;
import java.util.ArrayList;

import org.apache.hadoop.io.*;
import org.apache.hadoop.mapred.*;

import phd2dcore.PointWeighted;
import phd2dcore.Pp2dProcess;

public class MapHDFS extends MapReduceBase implements Mapper<LongWritable, Text, IntWritable, Text>  {
	public static String inputFile = new String();
	public void configure (JobConf job) {
		  inputFile = job.get("map.input.file");
	}

	public void map(LongWritable key, Text value, OutputCollector<IntWritable, Text> output, Reporter reporter) throws IOException {
	
		String inputLine = value.toString();			
		String scNumber = new String();
		String scLevel = new String();
		String mzString = new String();
		String intensityString = new String();	
		String RT = "0";
	    String[] tempStr;
	    String mzStringOut = new String();
	    Text peakOut = new Text();		//Map Task Output
	    
	    //Parse input String from HDFS
	    tempStr = inputLine.split("\t");
	    scNumber = tempStr[1];
	    scLevel = tempStr[2];
	    RT = tempStr[3];
	    mzString = tempStr[7];
		intensityString  = tempStr[8];		
				
		//Populate outputPoints with the isotopic centroided peaks
		ArrayList<PointWeighted> outputPoints = Pp2dProcess.process(scNumber, scLevel, mzString, intensityString, RT);
		
		if (outputPoints.size()>0) {
			//Write out to HDFS
			for (int i=0; i<outputPoints.size(); i++){
				mzStringOut = Double.toString(outputPoints.get(i).getWpm()) + "\t"  + scLevel + "\t" + RT  + "\t" + 
			    		Integer.toString(outputPoints.get(i).getCurveID()) + "\t"  +									
			    		scNumber + "\t" +
			    		Double.toString(outputPoints.get(i).getSumI()) + "\t"  +						
			    		Integer.toString(outputPoints.get(i).getCharge());
			    		//removed inputfile outputPoints ouput for performance
			    		//+ "\t" + inputFile;	
				peakOut.set(new Text(mzStringOut));						    		
					output.collect(new IntWritable(outputPoints.get(i).getoutKey()), peakOut);
			
			}
			
		}
			//move to next scan 
	}
			
}

