package twod;

import java.util.ArrayList;
import java.util.Collections;

public class MapISOPeaks {
	
	public static ArrayList<PointWeighted> ISOPeaks(ArrayList<PointWeighted> WeightedPointsISO) {
		
		int WeightedArrayPos=0;
		int innerPos=1;
		double WeightedArrayWPM=0.0;
		@SuppressWarnings("unused")
		double WeightedArrayMass=0.0;
		double innerWPM=0.0;
		double WeightedArrayMAXI=0.0;
		double innerMAXI=0.0;
		double WeightedArraySUMI=0.0;
		double innerSUMI=0.0;
		int charge = 0;
		double wpmDiff = 0.00;		
		ArrayList<PointWeighted> WeightedPoints = new ArrayList<PointWeighted>();
		
		while (WeightedArrayPos <= WeightedPointsISO.size()-1){
			
		   WeightedArrayWPM=WeightedPointsISO.get(WeightedArrayPos).getWpm();
		   WeightedArrayMAXI=WeightedPointsISO.get(WeightedArrayPos).getMaxI();
		   WeightedArraySUMI=WeightedPointsISO.get(WeightedArrayPos).getSumI();
		   //inner curveid is always less than outer curveid
		   innerPos=WeightedArrayPos-1;
		   
		   while (innerPos >= 0){				   
			   innerWPM=WeightedPointsISO.get(innerPos).getWpm();
			   innerMAXI=WeightedPointsISO.get(innerPos).getMaxI();
			   innerSUMI=WeightedPointsISO.get(innerPos).getSumI();
			   if (WeightedArrayPos != innerPos
				   && WeightedArrayWPM-innerWPM >= 0.0 
				   && WeightedArrayWPM-innerWPM <= 1.000
				   //&& WeightedArrayRT==innerRT
				   //Changed to 50% to match Spectracus code
				   //&& (innerMAXI > (0.66667*WeightedArrayMAXI))) {
				   && (innerMAXI > (0.5*WeightedArrayMAXI))) {
					   wpmDiff = WeightedArrayWPM-innerWPM;
					  					  
					   if ((wpmDiff > 1.0-0.0109135) && (wpmDiff < 1.0 + 0.0109135)){
						     charge = 1;}
						   else if ((wpmDiff >= 0.5014343200-0.0109135) && (wpmDiff <= 0.5014343200 + 0.0109135)){
						   	 charge = 2;}
						   else if ((wpmDiff >= 0.3342895500-0.0109135) && (wpmDiff <= 0.3342895500 + 0.0109135)){
					   		 charge = 3;}
						   else if ((wpmDiff >= 0.2507171600-0.0109135) && (wpmDiff <= 0.2507171600 + 0.0109135)){
					   		 charge = 4;}
						   else if ((wpmDiff >= 0.2005737300-0.0109135) && (wpmDiff <= 0.2005737300 + 0.0109135)){
					   		 charge = 5;}
						   else if ((wpmDiff >= 0.1671447700-0.0109135) && (wpmDiff <= 0.1671447700 + 0.0109135)){
					   		 charge = 6;}	

					   WeightedArrayMass = charge * WeightedArrayWPM;
					   					
					   if( charge > 1) {
							//Collect the ISO peaks in an array if they have a charge greater than 1
						    WeightedPoints.add (new PointWeighted(WeightedArrayPos,innerWPM,innerSUMI,innerMAXI,charge,0,-1));
						    WeightedPoints.add (new PointWeighted(WeightedArrayPos,WeightedArrayWPM,WeightedArraySUMI,WeightedArrayMAXI,charge,0,-1));
							charge = 0;
					   }
							
			   }
			   innerPos--;					   
		   }			   			   
		   WeightedArrayPos++;
		}

		Collections.sort(WeightedPointsISO, new WeightedPointCompare());

		return WeightedPoints;
		
	}
}
