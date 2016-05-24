package phdmapreduce;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

import phd3dcore.*;

public class ReduceHDFS extends Reducer<IntWritable, Text, IntWritable, Text> {
       public static String inputFile = new String();

	public void reduce(IntWritable key, Iterable<Text> values, Context context) throws IOException {
		    ArrayList<PointMountain> mountainPoints = new ArrayList<PointMountain>();		    
		    String outText = null;
		   	
		   	//Cache reducer values in an array for processing - must fit in memory, depends on number of reducers
			//Read all the weighted points into an in-memory object array
		   	for (Text line : values) {
	   			String[] tempStr;	   
	   		    tempStr = line.toString().split("\t");
 	   		    mountainPoints.add (
   		    		new PointMountain(	
	    				Double.parseDouble(tempStr[4]),
						Double.parseDouble(tempStr[5]),
						Double.parseDouble(tempStr[5]),
						Double.parseDouble(tempStr[2]),
						Integer.parseInt(tempStr[0]),
						0,
						Integer.parseInt(tempStr[6]),
						-1,1,0,0));
		   	}
		   			  
		   	if (mountainPoints.size() > 5) {
			//Now we can sort on by Retention Time followed by WPM using a custom compare from MountainPoint   		   
		   	Collections.sort(mountainPoints, new MountainPointCompare());		  		   
		   	//Create Arraylist of 3D peaks
		   	ArrayList<PointMountain> outputPoints = Pp3d3DPeaks.ThreeDPeaks(mountainPoints);
		   	//Smooth Intensities of the 3D peaks
		   	Pp3dSmoothIntesity.smoothIntensity(outputPoints);	
			//Recalculate 3D peaks taking into account Overlapping Peaks		
		   	Pp3dOverlaps.calcOverlaps(outputPoints);   	
		   	//Create final 3D peaks and set Mountain ID
		   	ArrayList<PointThreeD> threedDpoints = Pp3dFinalPeaks.calcMountains(outputPoints);	  
		   	/*
			   for (int k = 0; k<threedDpoints.size();k++){
				   outText = threedDpoints.get(k).getCurveID() + "\t" +
						   threedDpoints.get(k).getWpm() + "\t" +
						   threedDpoints.get(k).getSumI() + "\t" +
						   threedDpoints.get(k).getMinRT() + "\t" +
						   threedDpoints.get(k).getMaxRT() + "\t" +
						   threedDpoints.get(k).getCharge() + "\t" +
						   threedDpoints.get(k).getNumPeaks() + "\t" +
						   threedDpoints.get(k).getMaxI();
					try {
						context.write(new IntWritable(999), new Text(outText));
					} catch (InterruptedException e) {
						System.out.println("Error Writing Reducer Ouput");
						e.printStackTrace();
					}
					
			   }
			 */  
			//ISO Peaks
		   	ArrayList<PointISO> MonoISO = Pp3dISOPeaks.calcISO(threedDpoints, outputPoints);	   	
		   	//Write out Final Peaks
		   for (int k = 0; k<MonoISO.size();k++){
			   outText = MonoISO.get(k).getCharge() + "\t" +
					   MonoISO.get(k).getWpm() + "\t" +
					   MonoISO.get(k).getSumI() + "\t" +
					   MonoISO.get(k).getWpRT();
				try {
					context.write(new IntWritable(0), new Text(outText));
				} catch (InterruptedException e) {
					System.out.println("Error Writing Reducer Ouput");
					e.printStackTrace();
				}
				
		   }
  
 	   }

    } 

}