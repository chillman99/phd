import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Reduce3DPeaks {
	Set<PointMountain> outputPoints = new HashSet<PointMountain>();
	int mountainID = 0;
   	int i = 0; //Outer Loop Counter
   	int j = 0; //Inner Loop Counter
   	int iterations = 0;
   	double currRT = 0.0;
   	int peakFound = 0;  	
   	int matchpos1 = 0;
   	List<Integer> pointList = new ArrayList<Integer>(); //store the found peaks in a list and skip them next time
   	
	public ArrayList<PointMountain> ThreeDPeaks(ArrayList<PointMountain> mountainPoints) {		
		int currScan =0;
		int prevScan =-1;
		int nextRTIndex = 0;
	    int holdpos = 0;
	    int endLoop = mountainPoints.size();
	    int scanCount = 0;
	    
	    //Outer Loop	    
		while (i <= endLoop-2) //need to check next two scans, -2 to avoid array out of bounds error
	    //while (i <= 5000) //need to check next two scans, -2 to avoid array out of bounds error
	    {  			 
			mountainID++;
			//flag this point as having been checked for matches
			mountainPoints.get(i).setChecked(1);
			matchpos1=0;			
			currScan = mountainPoints.get(i).getScanNumber();
			j=i+1;

			//is the next point in the outer loop on the same scan as the previous? if so we know the index of the next RT
			if (currScan == prevScan) j = nextRTIndex;
			else {
			//Fast Forward through Array to find next RT that is higher than the current   					
			while ( mountainPoints.get(j).getScanNumber() <= currScan && j <= endLoop-2 ) {				
    		   	j++;
				} nextRTIndex = j;
			}
			
			//Potential speed up, move j to the WPM just before WPM for i
			while ( mountainPoints.get(j).getWpm() < mountainPoints.get(i).getWpm() 
					&& j <= endLoop-2 
					&& mountainPoints.get(j).getScanNumber() == currScan) {				
    		   	j++;
				} nextRTIndex = j;
			
//System.out.println("Outside: "
//				+ "outerScan: " + mountainPoints.get(i).getScanNumber() 
//				+ " outerWPM : " + mountainPoints.get(i).getWpm() 
//		        + " innerScan: " + mountainPoints.get(j).getScanNumber()
//		        + " innerWPM : " + mountainPoints.get(j).getWpm());

			currRT = mountainPoints.get(j).getRetentionTime();
			//check this RT is within 30 seconds window from first point of 3D curve	
			//Only check next 120 scans = approximately 30 seconds					
			//Call MatchPeaks to look for a matching peak - inner loop
				MatchPeaks(mountainPoints);
				
				//if we didn't find a match in this RT, check the next one
				if (peakFound==0){				
					currRT = mountainPoints.get(j).getRetentionTime();
					MatchPeaks(mountainPoints);	
				}							
				    				    		   					
				if (scanCount < 120 && peakFound > 0) {
					peakFound = 0;				
					//carry on looping from the matched point	
					while (i < endLoop){
						if (mountainPoints.get(matchpos1).getChecked() == 1){
							matchpos1++;
						} else break;
						i++;
					}				
					i=matchpos1;
					scanCount++;
				}    		
				else {
					//check next point is not in a peak
					holdpos++;
					mountainID++;
					
					while (i < endLoop){
						if (pointList.contains(holdpos)){
							//this point is already in a peak so skip to next
							holdpos++;
						} else {
							break;
						}
						i++;
					}
					scanCount=0;
					i=holdpos;
					mountainID++;     		
				}
				prevScan = currScan;
						
			mountainID++;
		} //Outer Loop
		
		//Complete so add hashlist contents to arraylist, sort and return
		ArrayList<PointMountain> outputPointsAL = new ArrayList<PointMountain>();		
		for (PointMountain objLoop : outputPoints ) {    		    		
			if (objLoop.getMountainID() != -1) outputPointsAL.add(objLoop);			
		}
		
		Collections.sort(outputPointsAL, new MountainPointCompare());
		return outputPointsAL;
	}
	
	public void MatchPeaks(ArrayList<PointMountain> mountainPoints) {
		//Loop through the points for this RT and look for match
		int endLoop = mountainPoints.size();
		int k = 0;		
		double iWpm = mountainPoints.get(i).getWpm();
		int iCharge = mountainPoints.get(i).getCharge() ;
		double iMass = iCharge * iWpm;
		int chargeMatch = 0;
		
//System.out.println("Inside1: "
//		+ "outerScan: " + mountainPoints.get(i).getScanNumber() 
//		+ " outerWPM : " + mountainPoints.get(i).getWpm() 
//        + " innerScan: " + mountainPoints.get(j).getScanNumber()
//        + " innerWPM : " + mountainPoints.get(j).getWpm());
		
		while (peakFound == 0 
				&& j <= endLoop-2 
				&& mountainPoints.get(j).getRetentionTime() == currRT  
				&& mountainPoints.get(j).getWpm() < (mountainPoints.get(i).getWpm() + 0.05)
//Investigate potential speed up here 				
				) 
		{
						
			int jCharge = mountainPoints.get(j).getCharge();
			double jMass = jCharge * mountainPoints.get(j).getWpm();	
			
//			if(mountainPoints.get(j).getWpm() > (mountainPoints.get(i).getWpm() + 0.05)){
//				j++;
//				continue;
//			}
			
//System.out.println("Inside2: "
//		+ "outerScan: " + mountainPoints.get(i).getScanNumber() 
//		+ " outerWPM : " + mountainPoints.get(i).getWpm() 
//        + " innerScan: " + mountainPoints.get(j).getScanNumber()
//        + " innerWPM : " + mountainPoints.get(j).getWpm());
//			
			if (iCharge != jCharge && chargeMatch == 1) break;
			else if (iCharge != jCharge
					|| (iMass < (jMass - (jMass * 0.000007)))
					)					
			{
				j++;
				continue;
			}

			chargeMatch = 1;	
	
			//Check mass inside the 7 ppm window and the same charge object "matches"method"	
			if (iMass <= (jMass + (jMass * 0.000007)))
		    {	 
		    	//this peak matches in a 3D peak
		    	matchpos1 = j;	
    		 	//keep looping through next 4 points to see if other points match in this RT     		 	
    		 	k=j+4;
    		 	j++;
    		 	
    		 	while (j <= endLoop-2 && j<=k && mountainPoints.get(j).getRetentionTime() == currRT ) 
    		 	{
    		 		jMass = jCharge * mountainPoints.get(j).getWpm();	
    		 		if (iMass <= (jMass + (jMass * 0.000007)))
    		 		{	 
    		 			//Another peak match found in same RT is it closer to the original point?
			 			if (mountainPoints.get(matchpos1).getWpm() - iWpm > mountainPoints.get(j).getWpm() - iWpm) matchpos1 = j;						    		 		

    		 		}
	    		 	j++;
    		 	}
    		 	j=matchpos1;
    		 	
    		 	//Is this point already part of a peak? if MountainID has been set it must be
	   		 	if (mountainPoints.get(i).getMountainID() > -1){
	   		 	    //mountainPoints.get(i).setMountainID(mountainPoints.get(i).getMountainID());				    		 		 	
	    		 	mountainPoints.get(j).setMountainID(mountainPoints.get(i).getMountainID());
	    		 	} else if (mountainPoints.get(j).getMountainID() > -1){
	       		 	    mountainPoints.get(i).setMountainID(mountainPoints.get(j).getMountainID());				    		 		 	
	        		 	mountainPoints.get(j).setMountainID(mountainPoints.get(j).getMountainID());
	    		 	} else {
	       		 	    mountainPoints.get(i).setMountainID(mountainID);				    		 		 	
	        		 	mountainPoints.get(j).setMountainID(mountainID);    		 		
	    		 	}

   		 		peakFound = 1;
		    	outputPoints.add (mountainPoints.get(i));			
		    	pointList.add(i);
		    	outputPoints.add (mountainPoints.get(j));			
		    	pointList.add(j);	
		    	//Added breakpoint to remove invalid iterations
		    	break;
			}
			j++;			
		}
		//Make sure we are at the end of this scan in case we skipped the main loop when j > i+1
		while (peakFound == 0 
				&& j <= endLoop-2 
				&& mountainPoints.get(j).getRetentionTime() == currRT  
				) {
			j++;
		}
				
	}
	
}
