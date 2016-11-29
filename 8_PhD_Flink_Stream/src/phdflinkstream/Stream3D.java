package phdflinkstream;


import java.util.ArrayList;
import java.util.Collections;

import org.apache.flink.streaming.api.functions.WindowMapFunction;
import org.apache.flink.util.Collector;
import phd3dcore.*;


@SuppressWarnings("serial")
public class Stream3D implements WindowMapFunction<String, String>{

	public void mapWindow(Iterable<String> inWindow, Collector<String> outCollect) throws Exception {

		int cntr2D =0;
		int cntr3D =0;
		int minScan = 1000000;
		int maxScan = 0;
		ArrayList<PointMountain> mountainPoints = new ArrayList<PointMountain>();
		
		//Loop over input window to get to individual 2D ISO Peaks
		for (String record : inWindow.toString().split(",")){		    
			cntr2D++;
			record = record.replace("[", "");record = record.replace("]", "");
			String ISOrecord[] = record.split("\t");
			    mountainPoints.add (
		    		new PointMountain(Double.parseDouble(ISOrecord[0]),
		    						  Double.parseDouble(ISOrecord[5]),
		    						  Double.parseDouble(ISOrecord[5]),
		    						  Double.parseDouble(ISOrecord[2]),
		    						  Integer.parseInt(ISOrecord[4]),
		    						  0,
		    						  Integer.parseInt(ISOrecord[6]),
		    						  -1,1,0,0,0));	   		
			    if (minScan > Integer.parseInt(ISOrecord[4])) minScan =Integer.parseInt(ISOrecord[4]);
			    if (maxScan < Integer.parseInt(ISOrecord[4])) maxScan =Integer.parseInt(ISOrecord[4]);
			    
		}
		
		Collections.sort(mountainPoints, new MountainPointCompare());
		
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
		   	if (outputPoints.size() > 5){		   	
			   	//Smooth Intensities of the 3D peaks
			   	Pp3dSmoothIntesity.smoothIntensity(outputPoints);	
				//Recalculate 3D peaks taking into account Overlapping Peaks		
			   	Pp3dOverlaps.calcOverlaps(outputPoints);   	
			   	//Create final 3D peaks and set Mountain ID
			   	ArrayList<PointThreeD> threedDpoints = Pp3dFinalPeaks.calcMountains(outputPoints);	  
				//ISO Peaks
			   	ArrayList<phd3dcore.PointISO> MonoISO = Pp3dISOPeaks.calcISO(threedDpoints, outputPoints);	   	
			   	cntr3D = MonoISO.size();
			   	//Write out Final Peaks
			   	for (int k = 0; k<MonoISO.size();k++){
					String outText = MonoISO.get(k).getCharge() + "\t" +
					MonoISO.get(k).getWpm() + "\t" +
					MonoISO.get(k).getSumI() + "\t" +
					MonoISO.get(k).getWpRT();
				//out.collect(new Tuple2<IntWritable, Text>(new IntWritable(0), new Text(outText)));			
					outCollect.collect(outText);
			   	}
				
		   	}
		
	   	}		
		System.out.println("2D ISO peak Count: " + cntr2D + " 3D ISO peak Count: " + cntr3D + 
							" Min Scan No: " + minScan + " Max Scan No: " + maxScan);	
	}

}
