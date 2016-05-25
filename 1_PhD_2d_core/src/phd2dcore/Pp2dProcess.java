package phd2dcore;

import java.util.ArrayList;
import phd2dpartitioner.MapmzOutKey;

import phd2dcore.PointWeighted;

public class Pp2dProcess {
	
	public static ArrayList<PointWeighted> process(String scNumber, String scLevel, String mzString, String intensityString, String RT) {

	    float[] mzData = null;
	    float[] intensityData = null;
				
		//decode the Base64 strings and populate float arrays with the data	
		try {
			mzData = Pp2dUtils.get64BitStringToFloat(mzString);				
		} catch (Exception e) {
			e.printStackTrace();
		}
 
		try {
			intensityData = Pp2dUtils.get32BitStringToFloat(intensityString);				
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		//Populate arraylist with profile data
		ArrayList<PointWeighted> WeightedPointsTemp = Pp2dWeightedPeaks.WeightedPeaks(mzData, intensityData);
		ArrayList<PointWeighted> outputPoints = new ArrayList<PointWeighted>();
		
		//Isotopic peak detection 
		if (Integer.parseInt(scLevel) == 1) {
			ArrayList<PointWeighted> WeightedPoints = Pp2dISOPeaks.ISOPeaks(WeightedPointsTemp);
			//ArrayList<PointWeighted> WeightedPoints = WeightedPointsTemp;

		//De-duplicate ISO array and Output to Reducer
		int dupeFlag = 0; //Flag to show which rows are duplicates

		for(int i=0; i < WeightedPoints.size(); i++) 
		{	
			if ( dupeFlag == i && i != 0 )  
			{
				//This point is a duplicate so reset the flag and move to next row
				dupeFlag = 0;
			}
			else //Output data for this point
				{
				if (WeightedPoints.get(i).getCharge() > 1) 
				{	
					//Get output key to decide which reducer we are sent to.
					String[] returnKeys = MapmzOutKey.getKey(WeightedPoints.get(i).getWpm());
					WeightedPoints.get(i).setoutKey(Integer.parseInt(returnKeys[0]));
					WeightedPoints.get(i).setoutKey2(Integer.parseInt(returnKeys[1]));
					
					outputPoints.add(WeightedPoints.get(i));
				}
					//check for duplicates in the next 3 points and set dupeFlag if found
					try{
						if(WeightedPoints.get(i).getWpm()==WeightedPoints.get(i+1).getWpm() &
						   WeightedPoints.get(i).getSumI()==WeightedPoints.get(i+1).getSumI() &
						   WeightedPoints.get(i).getCharge()==WeightedPoints.get(i+1).getCharge() &
						   WeightedPoints.get(i).getCurveID()==WeightedPoints.get(i+1).getCurveID()) {
							   dupeFlag = i+1;
						   } else
						   try{
							   if(WeightedPoints.get(i).getWpm()==WeightedPoints.get(i+2).getWpm() &
							   WeightedPoints.get(i).getSumI()==WeightedPoints.get(i+2).getSumI() &
							   WeightedPoints.get(i).getCharge()==WeightedPoints.get(i+2).getCharge() &
							   WeightedPoints.get(i).getCurveID()==WeightedPoints.get(i+2).getCurveID()) {
								   dupeFlag = i+2;}
								   else try{
									   if(WeightedPoints.get(i).getWpm()==WeightedPoints.get(i+3).getWpm() &
									   WeightedPoints.get(i).getSumI()==WeightedPoints.get(i+3).getSumI() &
									   WeightedPoints.get(i).getCharge()==WeightedPoints.get(i+3).getCharge() &
									   WeightedPoints.get(i).getCurveID()==WeightedPoints.get(i+3).getCurveID()) {
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

