package phdflinkstream;

import java.io.IOException;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapred.MapReduceBase;
import org.apache.hadoop.mapred.OutputCollector;
import org.apache.hadoop.mapred.Reducer;
import org.apache.hadoop.mapred.Reporter;

import phd3dcore.*;


public class ReduceHDFS extends MapReduceBase implements Reducer<IntWritable, Text, IntWritable, Text> {
    public static String inputFile = new String();

	public void reduce(IntWritable key, Iterator<Text> values, OutputCollector<IntWritable, Text> output, Reporter reporter) throws IOException {
		    ArrayList<PointMountain> mountainPoints = new ArrayList<PointMountain>();		    
		    String outText = null;
		   	
		   	//Cache reducer values in an array for processing - must fit in memory, depends on number of reducers
			//Read all the weighted points into an in-memory object array
		    while (values.hasNext()) {
	   			String[] tempStr;	   
	   		    tempStr = values.next().toString().split("\t");
	   		    
 	   		    mountainPoints.add (
   		    		new PointMountain(	
	    				Double.parseDouble(tempStr[0]),
						Double.parseDouble(tempStr[5]),
						Double.parseDouble(tempStr[5]),
						Double.parseDouble(tempStr[2]),
						Integer.parseInt(tempStr[4]),
						//0,
						Integer.parseInt(key.toString()),
						//
						Integer.parseInt(tempStr[6]),
						-1,1,0,0));
 	   		    
		   	}		   			    
		    
		   //	if (mountainPoints.size() > 5) {
			//Now we can sort on by Retention Time followed by WPM using a custom compare from MountainPoint   		   
		   	Collections.sort(mountainPoints, new MountainPointCompare());		  		   
		   	
		   	for (int k = 0; k<mountainPoints.size();k++){
				   outText = "000" + "\t" + mountainPoints.get(k).getCharge() + "\t" +
						   mountainPoints.get(k).getWpm() + "\t" +
						   mountainPoints.get(k).getSumI() + "\t" +
						   mountainPoints.get(k).getSmoothI() + "\t" +
						   mountainPoints.get(k).getRetentionTime() + "\t" +
						   mountainPoints.get(k).getScanNumber();
				   		
				   //output.collect(new IntWritable(0), new Text(outText));
				   output.collect(new IntWritable(mountainPoints.get(k).getScanOrder()), new Text(outText));
				   
			  }
	   	
		   	//Create Arraylist of 3D peaks
		   	Pp3d3DPeaks run3dPeaks = new Pp3d3DPeaks();
		   	ArrayList<PointMountain> outputPoints = run3dPeaks.ThreeDPeaks(mountainPoints);
		 
		   outText = "001_1" + "\t" + outputPoints.size(); 

		   //output.collect(new IntWritable(0), new Text(outText));
		   output.collect(new IntWritable(outputPoints.get(1).getScanOrder()), new Text(outText));
				   
		   	for (int k = 0; k<outputPoints.size();k++){
		   		/*
				   outText = "001" + "\t" + outputPoints.get(k).getCharge() + "\t" +
						   outputPoints.get(k).getWpm() + "\t" +
						   outputPoints.get(k).getSumI() + "\t" +
						   outputPoints.get(k).getSmoothI() + "\t" +
						   outputPoints.get(k).getRetentionTime() + "\t" +
						   outputPoints.get(k).getScanNumber();						   
				   		*/
		   		outText = "001" + "\t" + outputPoints.get(k).getWpm() + "\t" +
				   		   outputPoints.get(k).getCharge() + "\t" +						   
						   outputPoints.get(k).getSumI() + "\t" +
						   outputPoints.get(k).getSmoothI() + "\t" +
						   outputPoints.get(k).getRetentionTime() + "\t" +
						   outputPoints.get(k).getScanNumber();
				   //output.collect(new IntWritable(0), new Text(outText));
				   output.collect(new IntWritable(outputPoints.get(k).getScanOrder()), new Text(outText));
				   
			   }
			   
		   	/*
		   	//Smooth Intensities of the 3D peaks
		   	ReduceSmoothIntesity.smoothIntensity(outputPoints);
		   	
		   	for (int k = 0; k<outputPoints.size();k++){
				   outText = "002" + "\t" + outputPoints.get(k).getCharge() + "\t" +
						   outputPoints.get(k).getWpm() + "\t" +
						   outputPoints.get(k).getSumI() + "\t" +
						   outputPoints.get(k).getSmoothI() + "\t" +
						   outputPoints.get(k).getRetentionTime() + "\t" +
						   outputPoints.get(k).getScanNumber();
				   		
				   //output.collect(new IntWritable(0), new Text(outText));
				   output.collect(new IntWritable(outputPoints.get(k).getScanOrder()), new Text(outText));
				   
			   }
		   	
			
		   	//Recalculate 3D peaks taking into account Overlapping Peaks		
		   	ReduceOverlaps.calcOverlaps(outputPoints); 
		   	
		   	for (int k = 0; k<outputPoints.size();k++){
				   outText = "003" + "\t" + outputPoints.get(k).getCharge() + "\t" +
						   outputPoints.get(k).getWpm() + "\t" +
						   outputPoints.get(k).getSumI() + "\t" +
						   outputPoints.get(k).getSmoothI() + "\t" +
						   outputPoints.get(k).getRetentionTime() + "\t" +
						   outputPoints.get(k).getScanNumber();
				   		
				   //output.collect(new IntWritable(0), new Text(outText));
				   output.collect(new IntWritable(outputPoints.get(k).getScanOrder()), new Text(outText));
				   
			   }
		   	
		   	//Create final 3D peaks and set Mountain ID
		   	ArrayList<PointThreeD> threedDpoints = ReduceFinalPeaks.calcMountains(outputPoints);
		   	
		   	for (int k = 0; k<threedDpoints.size();k++){
				   outText = "004" + "\t" + threedDpoints.get(k).getCharge() + "\t" +
						   threedDpoints.get(k).getWpm() + "\t" +
						   threedDpoints.get(k).getSumI();
						   
				   //output.collect(new IntWritable(0), new Text(outText));
				   output.collect(new IntWritable(outputPoints.get(k).getScanOrder()), new Text(outText));
				   
			   }
		   	
		   	
			//ISO Peaks
		   	ArrayList<PointISO> MonoISO = ReduceISOPeaks.calcISO(threedDpoints, outputPoints);	   	
		   	//Write out Final Peaks
		   for (int k = 0; k<MonoISO.size();k++){
			   outText = "005" + "\t" + MonoISO.get(k).getCharge() + "\t" +
					   MonoISO.get(k).getWpm() + "\t" +
					   MonoISO.get(k).getSumI() + "\t" +
					   MonoISO.get(k).getWpRT();
			   		
			   output.collect(new IntWritable(0), new Text(outText));
			   
		   }		   
	  
 	   }
*/	 
    } 

}