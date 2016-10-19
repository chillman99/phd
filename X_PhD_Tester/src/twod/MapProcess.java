package twod;

import java.util.ArrayList;

import java.util.Collections;

//import org.apache.hadoop.io.*;
//import java.util.Collections;
//import org.apache.hadoop.mapred.*;
import twod.PointWeighted;

public class MapProcess {
	
	public static ArrayList<PointWeighted> process(String scNumber, String scLevel, String mzString, String intensityString, String RT) throws Exception {
		//decode the Base64 strings and populate float arrays with the data	
		float[] mzData = MapUtils.get64BitStringToFloat(mzString);				
		float[] intensityData = MapUtils.get32BitStringToFloat(intensityString);		

//Write out raw data for QC		
//for (int i=0; i<mzData.length;i++){
//	
//		System.out.println(scNumber + "\t" +
// 		  mzData[i] + "\t" +
// 		 intensityData[i] 
// 		);
//}		
		//Populate arraylist with profile data
		ArrayList<PointWeighted> WeightedPointsTemp = MapWeightedPeaks.WeightedPeaks(mzData, intensityData);		
		ArrayList<PointWeighted> outputPoints = new ArrayList<PointWeighted>();

//Write out weighted peaks data for QC		
//for (int i=0; i<WeightedPointsTemp.size();i++){
//	
//				System.out.println(
//						WeightedPointsTemp.get(i).getWpm()  + "\t" +
//						WeightedPointsTemp.get(i).getSumI()
//					    
//		 		);
//		}		
//		
		//Isotopic peak detection 
		if (Integer.parseInt(scLevel) == 1) {
			ArrayList<PointWeighted> WeightedPoints = MapISOPeaks.ISOPeaks(WeightedPointsTemp);	
	        Collections.sort(WeightedPoints, new WeightedPointCompare());
			
			for(int i=0; i <= WeightedPoints.size()-1; i++) 
			{	
				WeightedPoints.get(i).setoutKey(MapmzOutKey.getKey(WeightedPoints.get(i).getWpm()));
				//if( (WeightedPoints[i].getWpm() >=372.00 ) && (WeightedPoints[i].getWpm() < 374.00)){outKey = 0;}				
				if (WeightedPoints.get(i).getCharge() > 1					
					//Debugging, force single reducer with small range of points	
					//&& (WeightedPoints[i].getWpm() >=372.00 ) && (WeightedPoints[i].getWpm() < 374.00)
					) 
				{	
					outputPoints.add(WeightedPoints.get(i));										    			
				}

			}	
		
		}  //move to next scan 
		return outputPoints;
	}
	
}

