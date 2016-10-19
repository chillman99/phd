package phd2dcore;

import java.util.ArrayList;
import java.util.Collections;

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
			Collections.sort(WeightedPoints, new WeightedPointCompare());
			
			for(int i=0; i < WeightedPoints.size(); i++) 
			{	

				if (WeightedPoints.get(i).getCharge() > 1) 
				{	
					//Get output key to decide which reducer we are sent to.
					String[] returnKeys = MapmzOutKey.getKey(WeightedPoints.get(i).getWpm());
					WeightedPoints.get(i).setoutKey(Integer.parseInt(returnKeys[0]));
					WeightedPoints.get(i).setoutKey2(Integer.parseInt(returnKeys[1]));
					
					outputPoints.add(WeightedPoints.get(i));
				}

			}
		
		}  //move to next scan 
			return outputPoints;
	}
	
}

