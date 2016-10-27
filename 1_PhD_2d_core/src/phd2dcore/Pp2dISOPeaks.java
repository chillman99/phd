package phd2dcore;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class Pp2dISOPeaks {
	
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
		Set<PointWeighted> WeightedPoints = new HashSet<PointWeighted>();	
		
		while (WeightedArrayPos <= WeightedPointsISO.size()-1){
			
		   WeightedArrayWPM=WeightedPointsISO.get(WeightedArrayPos).getWpm();
		   WeightedArrayMAXI=WeightedPointsISO.get(WeightedArrayPos).getMaxI();
		   WeightedArraySUMI=WeightedPointsISO.get(WeightedArrayPos).getSumI();
		   innerPos=WeightedArrayPos-1;
		   
		   while (innerPos >= 0){				   
			   innerWPM=WeightedPointsISO.get(innerPos).getWpm();
			   innerMAXI=WeightedPointsISO.get(innerPos).getMaxI();
			   innerSUMI=WeightedPointsISO.get(innerPos).getSumI();
			   if (WeightedArrayPos != innerPos
				   && WeightedArrayWPM-innerWPM >= 0.0 
				   && WeightedArrayWPM-innerWPM <= 1.000
				   && (innerMAXI > (0.66667*WeightedArrayMAXI))) {							

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
					   					
					   if( charge > 0) {
					   //Collect the ISO peaks in an array if they have a charge greater than 1
					   //CHANGED to include the charge 1 peaks
							WeightedPoints.add (new PointWeighted(0,innerWPM,innerSUMI,innerMAXI,charge,0,-1));
							WeightedPoints.add (new PointWeighted(0,WeightedArrayWPM,WeightedArraySUMI,WeightedArrayMAXI,charge,0,-1));						    
							charge = 0;
					   }
							
			   }
			   innerPos--;					   
		   }			   			   
		   WeightedArrayPos++;
		}
		
		//Complete so de-duplicate and add hashlist contents to arraylist, sort and return
		//Added 50% of max intensity threshold to reduce false positives
		ArrayList<PointWeighted> outputPoints = new ArrayList<PointWeighted>();		
		double minI = 100000000.0;
		double maxI = 0.0;
		for (PointWeighted objLoop : WeightedPoints ) {    		    		
			if (objLoop.getSumI() < minI ) minI = objLoop.getSumI();
			if (objLoop.getSumI() > maxI ) maxI = objLoop.getSumI();	
			}
			
		for (PointWeighted objLoop : WeightedPoints ) {    		    		
			if (!outputPoints.contains(objLoop) 
				&& objLoop.getSumI()>(maxI/99)
				) 
			{
				outputPoints.add(objLoop);			
			}
		
		}		
		return outputPoints;		
	}
	
}
