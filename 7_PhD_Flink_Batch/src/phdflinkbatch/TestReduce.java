package phdflinkbatch;
import java.util.ArrayList;
import java.util.Collections;

import org.apache.flink.api.common.functions.GroupReduceFunction;
import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.api.java.tuple.Tuple8;
import org.apache.flink.util.Collector;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import phd3dcore.*;

@SuppressWarnings("serial")
public class TestReduce
implements GroupReduceFunction<Tuple8<Integer, Double, Integer,Double,Integer,Integer,Double,Integer>, Tuple2<IntWritable, Text>> {

@Override
public void reduce(Iterable<Tuple8<Integer, Double, Integer,Double,Integer,Integer,Double,Integer>> in, Collector<Tuple2<IntWritable, Text>> out) {

	ArrayList<PointMountain> mountainPoints = new ArrayList<PointMountain>();
	String outText = null;
	
    for (Tuple8<Integer, Double, Integer,Double,Integer,Integer,Double,Integer> t : in) {
	    mountainPoints.add (
    		new PointMountain(t.f1,t.f6,t.f6,t.f3,t.f5,0,t.f7,-1,1,0,0,0));	   		    	   
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
		   	//Smooth Intensities of the 3D peaks
		   	Pp3dSmoothIntesity.smoothIntensity(outputPoints);	
			//Recalculate 3D peaks taking into account Overlapping Peaks		
		   	Pp3dOverlaps.calcOverlaps(outputPoints);   	
		   	//Create final 3D peaks and set Mountain ID
		   	ArrayList<PointThreeD> threedDpoints = Pp3dFinalPeaks.calcMountains(outputPoints);	  
			//ISO Peaks
		   	ArrayList<phd3dcore.PointISO> MonoISO = Pp3dISOPeaks.calcISO(threedDpoints, outputPoints);	   	
		   	//Write out Final Peaks
		   	for (int k = 0; k<MonoISO.size();k++){
				outText = MonoISO.get(k).getCharge() + "\t" +
				MonoISO.get(k).getWpm() + "\t" +
				MonoISO.get(k).getSumI() + "\t" +
				MonoISO.get(k).getWpRT();
				out.collect(new Tuple2<IntWritable, Text>(new IntWritable(0), new Text(outText)));				
		   }
		
	   	}

	}

}

