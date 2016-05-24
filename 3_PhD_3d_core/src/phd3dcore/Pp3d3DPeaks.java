package phd3dcore;

import java.util.ArrayList;
import java.util.List;

public class Pp3d3DPeaks {
	static ArrayList<PointMountain> outputPoints = new ArrayList<PointMountain>();
	static int mountainID = 0;
   	static int i = 0; //Outer Loop Counter
   	static int j = 0; //Inner Loop Counter
   	static int iterations = 0;
   	static double currRT = 0.0;
   	static int peakFound = 0;  	
   	static int matchpos1 = 0;
   	static List<Integer> pointList = new ArrayList<Integer>(); //store the found peaks in a list and skip them next time
   	
   	public static ArrayList<PointMountain> ThreeDPeaks(ArrayList<PointMountain> mountainPoints) {
		int currScan =0;
		int prevScan =-1;
		int nextRTIndex = 0;
	    int holdpos = 0;
	    @SuppressWarnings("unused")
		double mountainRT = 0;
	    //Outer Loop
		while (i <= mountainPoints.size()-2) //need to check next two scans, -2 to avoid array out of bounds error
	    {  			 
		    mountainID++;
			mountainPoints.get(i).setChecked(1); 			
			matchpos1=0;
			int scanCount = 0;
			currScan = mountainPoints.get(i).getScanNumber();
			mountainRT = mountainPoints.get(i).getRetentionTime();
			j=i+1;
			//is the next point in the outer loop on the same scan as the previous? if so we know the index of the next RT
			if (currScan == prevScan) j = nextRTIndex;
			else {
			//Fast Forward through Array to find next RT that is higher than the current   					
			while ( mountainPoints.get(j).getScanNumber() <= currScan && j <= mountainPoints.size()-2 ) {				
    		   	j++;
    		    //Handle duplicates in the input
    		   	//iterations++;
				} nextRTIndex = j;
			}
			currRT = mountainPoints.get(j).getRetentionTime();
			if (scanCount < 40) {					
			//Call MatchPeaks to look for a matching peak 
				MatchPeaks(mountainPoints);				
				//if we didn't find a match in this RT, check the next one
				if (peakFound==0){				
					currRT = mountainPoints.get(j).getRetentionTime();
					MatchPeaks(mountainPoints);	
				}
				scanCount++;			
			}
			else {			
			}
				    				    		   					
			if (peakFound > 0) {
				peakFound = 0;
				while (i < mountainPoints.size()){
					if (mountainPoints.get(matchpos1).getChecked() == 1){
						matchpos1++;
					} else break;
					i++;
				}
				
				i=matchpos1;
			}    		
			else {				
				//check next point is not in a peak
				holdpos++;
				mountainID++;
				//ppf = 0;
				while (i < mountainPoints.size()){
					if (pointList.contains(holdpos)){
						//this point is already in a peak so skip to next
						holdpos++;
					} else {
						break;
					}
					i++;
				}
				i=holdpos;
				mountainID++;     		
			}
			prevScan = currScan;
			mountainID++; 
			
		} //Outer Loop
		return outputPoints;
	}
	
	public static void MatchPeaks(ArrayList<PointMountain> mountainPoints) {
		int k = 0;
		//Loop through the points for this RT and look for match
		while (peakFound == 0 & j <= mountainPoints.size()-2 
			   && mountainPoints.get(j).getRetentionTime() == currRT
			   ) {			
			iterations++;	
			//Check mass inside the 7 ppm window and the same charge object "matches"method"	
		     if (mountainPoints.get(i).matches(mountainPoints.get(j)) && peakFound == 0) {	
				//this peak matches in a 3D peak
		    	matchpos1 = j;	
    		 	//keep looping through next 4 points to see if other points match in this RT 
    		 	j++;
    		 	k=j+4;
    		 	while (j <= mountainPoints.size()-2 
    		 		   && j<=k && mountainPoints.get(j).getRetentionTime() == currRT
    		 		   ) {
    		 		//iterations++;
    		 		if (mountainPoints.get(i).matches(mountainPoints.get(j))) {
    		 			//Another peak match found in same RT is it closer to the original point?
	    		 		if (mountainPoints.get(matchpos1).getWpm() - mountainPoints.get(i).getWpm() > mountainPoints.get(j).getWpm() - mountainPoints.get(i).getWpm()){
			    		 		matchpos1 = j;						    		 		
	    		 		} 
    		 		}
	    		 	j++;
    		   		if (j > 0 && mountainPoints.get(j).getWpm() == mountainPoints.get(j-1).getWpm()){
    		   			j++;
    		   		}
    		 	}
    		 	j=matchpos1;    		 	
    		 	//Is this point already part of a peak?
    		 	//if MountainID has not been set it must be
   		 	if (mountainPoints.get(i).getMountainID() > -1){
    		 		mountainID = mountainPoints.get(i).getMountainID();
    		 	} else if (mountainPoints.get(j).getMountainID() > -1){
    		 		mountainID = mountainPoints.get(j).getMountainID();    		 		
    		 	} //else {
	    		 	mountainPoints.get(i).setMountainID(mountainID);				    		 		 	
	    		 	mountainPoints.get(j).setMountainID(mountainID);
    		 //	}
	    		peakFound = 1;
		    	if (!(outputPoints.contains(mountainPoints.get(i)))) {
		    		outputPoints.add (mountainPoints.get(i));			
		    		pointList.add(i);	
    			}
		    	
		    	if (!(outputPoints.contains(mountainPoints.get(j)))) {
		    		outputPoints.add (mountainPoints.get(j));			
		    		pointList.add(j);
    			}	
		    	
			}
			j++;		
		}
		
	}
	
}
