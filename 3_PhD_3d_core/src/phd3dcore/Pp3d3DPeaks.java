package phd3dcore;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Pp3d3DPeaks {
	Set<PointMountain> outputPoints = new HashSet<PointMountain>();
	int mountainID = 0;
   	int i = 0; //Outer Loop Counter
   	int j = 0; //Inner Loop Counter
   	int iterations = 0;
   	double currRT = 0.0;
   	int peakFound = 0;  	
   	int matchpos1 = 0;
   	int boundaryCheck = 0;
   	List<Integer> pointList = new ArrayList<Integer>(); //store the found peaks in a list and skip them next time
   	
	@SuppressWarnings({ "unused" })
	public ArrayList<PointMountain> ThreeDPeaks(ArrayList<PointMountain> mountainPoints) {		
		int currScan =0;
		int prevScan =-1;
		int nextRTIndex = 0;
	    int holdpos = 0;
		double mountainRT = 0;
	    int loopNum = 0;	    	
	    //Outer Loop
		while (i <= mountainPoints.size()-2) //need to check next two scans, -2 to avoid array out of bounds error
	    {  			 
		 	mountainID++;
			if(mountainPoints.get(i).getBoundary() == 1){
				boundaryCheck =1;
			}
			
			//flag this point as having been checked for matches
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
				} nextRTIndex = j;
			}

			currRT = mountainPoints.get(j).getRetentionTime();
			//check this RT is within 30 seconds window from first point of 3D curve	
			//Only check next 120 scans = approximately 30 seconds
			if (scanCount < 120) {	
				
				//Call MatchPeaks to look for a matching peak 				
				MatchPeaks(mountainPoints);
				
				//if we didn't find a match in this RT, check the next one
				if (peakFound==0){										
					currRT = mountainPoints.get(j).getRetentionTime();
					MatchPeaks(mountainPoints);	
				}
							
				scanCount++;			
			}
				    				    		   					
			if (peakFound > 0) {
				//pointList.add(matchpos1);
				peakFound = 0;
				
				//carry on looping from the matched point	
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
				if(mountainPoints.get(i).getBoundary() == 1){
					boundaryCheck =1;
				} else boundaryCheck = 0;

				while (i < mountainPoints.size()){
					if (pointList.contains(holdpos)){
						//this point is already in a peak so skip to next
						holdpos++;
						//iterations++;
					} else {
						//CIH Added
						//mountainID++;
						break;
					}
					i++;
				}
				i=holdpos;
				mountainID++;     		
			}
			prevScan = currScan;	   
			mountainID++; 
			loopNum++;
			
		} //Outer Loop
		ArrayList<PointMountain> outputPointsAL = new ArrayList<PointMountain>();
		
		for (PointMountain objLoop : outputPoints ) {    		    		
			if (objLoop.getMountainID() != -1) outputPointsAL.add(objLoop);			
		}
		
		//outputPointsAL.addAll(outputPoints);
		Collections.sort(outputPointsAL, new MountainPointCompare());
		return outputPointsAL;
	}
	
	@SuppressWarnings("unused")
	public void MatchPeaks(ArrayList<PointMountain> mountainPoints) {
		int k = 0;
		//Loop through the points for this RT and look for match
		int inLoopNum = 0;
		
		while (peakFound == 0 & j <= mountainPoints.size()-2 
			   && mountainPoints.get(j).getRetentionTime() == currRT
			   ) {
			 iterations++;
		     if (mountainPoints.get(i).matches(mountainPoints.get(j)) && peakFound == 0) {	
				//this peak matches in a 3D peak
		    	matchpos1 = j;	
    		 	//keep looping through next 4 points to see if other points match in this RT 
    		 	j++;
    		 	k=j+4;
    		 	while (j <= mountainPoints.size()-2 
    		 		   && j<=k && mountainPoints.get(j).getRetentionTime() == currRT
    		 		   ) {
    		 		
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
    		 	} 
   		 			if(mountainPoints.get(i).getBoundary() == 1) boundaryCheck =1; 
   		 			//Change here to remove all boundary peaks from the output...
   		 			if(boundaryCheck != 1){
	   		 			mountainPoints.get(i).setMountainID(mountainID);				    		 		 	
		    		 	mountainPoints.get(j).setMountainID(mountainID);
   		 			}else {
	   		 			mountainPoints.get(i).setNewMountainID(9999);
	    		 		mountainPoints.get(j).setNewMountainID(9999);	    		 	
   		 			}	    		 	

   		 		peakFound = 1;
		    	outputPoints.add (mountainPoints.get(i));			
		    	pointList.add(i);

		    	outputPoints.add (mountainPoints.get(j));			
		    	pointList.add(j);
		    	
			}
		    //Added to test peakFound speedup
			j++;
			inLoopNum++;			
		}
		
	}
	
}

	
	