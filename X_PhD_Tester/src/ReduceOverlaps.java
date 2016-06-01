import java.util.ArrayList;
public class ReduceOverlaps {	
	@SuppressWarnings("unused")
	public static void calcOverlaps (ArrayList<PointMountain> outputPoints) {
		double olCurrSumI = 0.00;
		double checkSumI_L = 0.00;
		double checkSumI_R = 0.00;
		int newMountainID = 0;
		int currMountainID = 0;
		int prevMountainID = 0;
		int startPeakFlag = 0;
		int mBreak = 0;
		int currSlopeInd = 0;
		int prevSlopeInd = 0;
		
		
		try {
			for(int k=1; k <= outputPoints.size()-1; k++)    		    
			{					
				try{
					currMountainID = outputPoints.get(k).getMountainID();						
				} catch(IndexOutOfBoundsException IoE) {
				//reached end of object array
					break;
				}	
				
				if (prevMountainID != currMountainID){
					//Start of new Peak so auto increment
					newMountainID++;
					startPeakFlag = 1;	
					prevSlopeInd = 0;
					if (outputPoints.get(k).getSmoothI() < outputPoints.get(k+1).getSmoothI() || 
					    outputPoints.get(k).getSmoothI() == outputPoints.get(k+1).getSmoothI()){
						currSlopeInd = 1;
					} else {currSlopeInd = -1;}
					
//if (currMountainID == 1317) System.out.println("Start Peak:" + "\t" + currMountainID + "\t" + newMountainID + "\t" + checkSumI_L  + "\t" + olCurrSumI  + "\t" + checkSumI_R);					
				} else {
					olCurrSumI = outputPoints.get(k).getSmoothI();	
					checkSumI_L = outputPoints.get(k-1).getSmoothI()*(1/1.3);
				    checkSumI_R = outputPoints.get(k+1).getSmoothI()*(1/1.3);												

					if (checkSumI_L < checkSumI_R || checkSumI_R==checkSumI_L){
						currSlopeInd = 1;
						if(olCurrSumI < checkSumI_L ) {
							newMountainID++;
							mBreak = -1;
//if (currMountainID == 1317) System.out.println("Break L:" + "\t" + currMountainID + "\t" + newMountainID + "\t" + checkSumI_L  + "\t" + olCurrSumI  + "\t" + checkSumI_R);							
						} else {
//if (currMountainID == 1317) System.out.println("No Break:" + "\t" + currMountainID + "\t" + newMountainID + "\t" + checkSumI_L  + "\t" + olCurrSumI  + "\t" + checkSumI_R);						
						}
					} else if (checkSumI_R < checkSumI_L){
						currSlopeInd = -1;
						if(olCurrSumI < checkSumI_R){
							newMountainID++;
							mBreak = 1;
//if (currMountainID == 1317) System.out.println("Break R:" + "\t" + currMountainID + "\t" + newMountainID + "\t" + checkSumI_L  + "\t" + olCurrSumI  + "\t" + checkSumI_R);							
						} else {
//if (currMountainID == 1317) System.out.println("No Break:" + "\t" + currMountainID + "\t" + newMountainID + "\t" + checkSumI_L  + "\t" + olCurrSumI  + "\t" + checkSumI_R);						
						}
					}
				}
				
				if(prevSlopeInd == -1 && currSlopeInd ==1){
					outputPoints.get(k).setNewMountainID(newMountainID);
					newMountainID++;
					mBreak = 1;
//if (currMountainID == 1317) System.out.println("Break New:" + "\t" + currMountainID + "\t" + newMountainID + "\t" + checkSumI_L  + "\t" + olCurrSumI  + "\t" + checkSumI_R);
				}
				
					prevSlopeInd = currSlopeInd;
					outputPoints.get(k + mBreak).setNewMountainID(newMountainID);
					prevMountainID = currMountainID;		
					startPeakFlag = 0;
					mBreak = 0;
			}

		} catch (Exception e3) {
				//end of array
		}		

	}
	
}
