package phdmapreduce;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.io.ImmutableBytesWritable;
import org.apache.hadoop.hbase.mapreduce.TableReducer;
import org.apache.hadoop.hbase.util.Bytes;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;

import phd3dcore.*;

public class ReduceHbase extends TableReducer<IntWritable, Text, ImmutableBytesWritable> {
       public static String inputFile = new String();

	public void reduce(IntWritable key, Iterable<Text> values, Context context) throws IOException {
		    ArrayList<PointMountain> mountainPoints = new ArrayList<PointMountain>();		    
		    Set<PointMountain> inputPoints = new HashSet<PointMountain>();	
		   	//Cache reducer values in an array for processing - must fit in memory, depends on number of reducers
			//Read all the weighted points into an in-memory object array
		   	for (Text line : values) {
	   			String[] tempStr;	   
	   		    tempStr = line.toString().split("\t");
	   		    	inputPoints.add (
   		    		new PointMountain(	
	    				Double.parseDouble(tempStr[4]),
						Double.parseDouble(tempStr[5]),
						Double.parseDouble(tempStr[5]),
						Double.parseDouble(tempStr[2]),
						Integer.parseInt(tempStr[0]),
						0,
						Integer.parseInt(tempStr[6]),
						-1,1,0,0,0));
		   	}
		    mountainPoints.addAll(inputPoints);
		   	if (mountainPoints.size() > 5) {
		   		
		   		//scan through the points and flag the first and last point in each RT
				//if the 3d peak includes these it has touched the edge of the partition and will not
				//be included in the output
					int checked = 0;
					double currRT = 0.0;			
					currRT = mountainPoints.get(0).getRetentionTime();
					mountainPoints.get(0).setBoundary(1);
					int kk=1;
					while (kk < mountainPoints.size()) 
					{  
						mountainPoints.get(kk).setBoundary(0);
						if (checked == 1){
							mountainPoints.get(kk-2).setBoundary(1);
							checked = 0;
						}
						
						if (currRT != mountainPoints.get(kk).getRetentionTime()){
							mountainPoints.get(kk).setBoundary(1);
							checked = 1;
						} 
						
						currRT = mountainPoints.get(kk).getRetentionTime();
						kk++;
					}
					mountainPoints.get(kk-1).setBoundary(1);
		   		
				//Now we can sort on by Retention Time followed by WPM using a custom compare from MountainPoint   		   
			   	Collections.sort(mountainPoints, new MountainPointCompare());		  		   
			   	//Create Arraylist of 3D peaks
			   	Pp3d3DPeaks run3dPeaks = new Pp3d3DPeaks();			
			   	ArrayList<PointMountain> outputPoints = run3dPeaks.ThreeDPeaks(mountainPoints);
			   	//Smooth Intensities of the 3D peaks
			   	Pp3dSmoothIntesity.smoothIntensity(outputPoints);	
				//Recalculate 3D peaks taking into account Overlapping Peaks		
			   	Pp3dOverlaps.calcOverlaps(outputPoints);   	
			   	//Create final 3D peaks and set Mountain ID
			   	ArrayList<PointThreeD> threedDpoints = Pp3dFinalPeaks.calcMountains(outputPoints);	  
				//ISO Peaks
			   	ArrayList<PointISO> MonoISO = Pp3dISOPeaks.calcISO(threedDpoints, outputPoints);	   	
			   	//Write out Final Peaks
			   for (int k = 0; k<MonoISO.size();k++){
				   Put put = new Put(Bytes.toBytes(key.toString()));
				   put.add(Bytes.toBytes("peaks"), Bytes.toBytes("charge"), Bytes.toBytes(Integer.toString(MonoISO.get(k).getCharge())));
				   put.add(Bytes.toBytes("peaks"), Bytes.toBytes("wpm"), Bytes.toBytes(Double.toString(MonoISO.get(k).getWpm())));
				   put.add(Bytes.toBytes("peaks"), Bytes.toBytes("sumi"), Bytes.toBytes(Double.toString(MonoISO.get(k).getSumI())));
				   put.add(Bytes.toBytes("peaks"), Bytes.toBytes("rt"), Bytes.toBytes(Double.toString(MonoISO.get(k).getWpRT())));
					try {
						context.write(new ImmutableBytesWritable(Integer.toString(k).getBytes()), put);
					} catch (InterruptedException e) {
						System.out.println("Error Writing Reducer Ouput");
						e.printStackTrace();
					} 
					
			   }
  
 	   }

   } 

}