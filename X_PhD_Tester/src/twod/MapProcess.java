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
				
		//Populate arraylist with profile data
		ArrayList<PointWeighted> WeightedPointsTemp = MapWeightedPeaks.WeightedPeaks(mzData, intensityData);
		ArrayList<PointWeighted> outputPoints = new ArrayList<PointWeighted>();
		
		//Isotopic peak detection 
		if (Integer.parseInt(scLevel) == 1) {
			ArrayList<PointWeighted> WeightedPoints = MapISOPeaks.ISOPeaks(WeightedPointsTemp);	
	    Collections.sort(WeightedPoints, new WeightedPointCompare());
			
		//De-duplicate ISO array and Output to Reducer
		int dupeFlag = 0; //Flag to show which rows are duplicates	
		for(int i=0; i <= WeightedPoints.size()-1; i++) 
		{	
			if ( dupeFlag == i && i != 0 )  
			{   //This point is a duplicate so reset the flag and move to next row
				dupeFlag = 0;
			}
			else //Output data for this point
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
					//check for duplicates in the next 3 points and set dupeFlag if found
					try{
						if(WeightedPoints.get(i).equals(WeightedPoints.get(i+1))) {
							   dupeFlag = i+1;
						   } else
						   try{
							   if(WeightedPoints.get(i).equals(WeightedPoints.get(i+2))){
								   dupeFlag = i+2;}
								   else try{
									   if(WeightedPoints.get(i).equals(WeightedPoints.get(i+3))) {
										   dupeFlag = i+3;
									   }
							   		} catch (Exception e){
							   			//almost the last object
							   		}
							   } catch (Exception e){
								   //nearly the end of the line
							   }
					} catch (Exception e) {
						//the actual end of the line						
					}
						
				}

			}
		
		}  //move to next scan 
		 
		return outputPoints;
	}
	
}

