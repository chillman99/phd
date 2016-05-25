package phdflinkstream;

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
    		new PointMountain(t.f1,t.f6,t.f6,t.f3,t.f5,0,t.f7,-1,1,0,0));	   		    	   
    }
    Collections.sort(mountainPoints, new MountainPointCompare());		  		   
   	/*
   	for (int k = 0; k<mountainPoints.size();k++){
		   outText = "000" + "\t" + 
				   mountainPoints.get(k).getCharge() + "\t" +
				   mountainPoints.get(k).getWpm() + "\t" +
				   mountainPoints.get(k).getSumI() + "\t" +
				   mountainPoints.get(k).getSmoothI() + "\t" +		   		   
		   		   mountainPoints.get(k).getRetentionTime() + "\t" +		   		  
				   mountainPoints.get(k).getScanNumber();
				   		   		
		   out.collect(new Tuple2<IntWritable, Text>(new IntWritable(0), new Text(outText)));
	  }
   	*/
	   	if (mountainPoints.size() > 5) {
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
   	
	/*
   	ArrayList<PointMountain> outputPoints = Reduce3DPeaks.ThreeDPeaks(mountainPoints);
	     	   
   	for (int k = 0; k<outputPoints.size();k++){
    		outText = "001" + "\t" + outputPoints.get(k).getWpm() + "\t" +
		   		   outputPoints.get(k).getCharge() + "\t" +						   
				   outputPoints.get(k).getSumI() + "\t" +
				   outputPoints.get(k).getSmoothI() + "\t" +
				   outputPoints.get(k).getRetentionTime() + "\t" +
				   outputPoints.get(k).getScanNumber();
		out.collect(new Tuple2<IntWritable, Text>(new IntWritable(0), new Text(outText)));		    			   
	   }
   	*/
   }

}