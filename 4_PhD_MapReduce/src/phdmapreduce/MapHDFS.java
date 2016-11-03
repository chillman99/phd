package phdmapreduce;

import java.io.*;
import java.util.ArrayList;
import org.apache.hadoop.io.*;
import org.apache.hadoop.mapred.*;
import org.apache.hadoop.mapreduce.Mapper;
import phd2dcore.*;

public class MapHDFS extends Mapper<LongWritable, Text, IntWritable, Text> {

	public static String inputFile = new String();
	public void configure (JobConf job) {
		  inputFile = job.get("map.input.file");
	}
	
	public void map(LongWritable key, Text value, Context context) throws IOException {
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
				mzStringOut = scNumber + "\t" + scLevel + "\t" + RT  + "\t" + 
			    		Integer.toString(outputPoints.get(i).getCurveID()) + "\t"  +									
			    		Double.toString(outputPoints.get(i).getWpm()) + "\t"  +
			    		Double.toString(outputPoints.get(i).getSumI()) + "\t"  +						
			    		Integer.toString(outputPoints.get(i).getCharge());
			    		//removed inputfile for performance
			    		//+ "\t" + inputFile;	
				peakOut.set(new Text(mzStringOut));						    		
				try {
					context.write(new IntWritable(outputPoints.get(i).getoutKey()), peakOut);
					if (outputPoints.get(i).getoutKey2() != -1){
						context.write(new IntWritable(outputPoints.get(i).getoutKey2()), peakOut);											
					}
				} catch (InterruptedException e) {
					System.out.println("Error Writing Mapper Output");
					e.printStackTrace();
				}
				
			}
			
		}
			//move to next scan 
	}
			
}

