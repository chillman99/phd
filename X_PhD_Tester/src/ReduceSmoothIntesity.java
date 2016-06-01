import java.util.ArrayList;

public class ReduceSmoothIntesity {
	
	public static void smoothIntensity (ArrayList<PointMountain> outputPoints) {
	
		int currMountainID = 0;
		//Smooth Intensities
		try {
		for(int k=1; k <= outputPoints.size()-2; k++)    		    
		{									
			if (currMountainID != outputPoints.get(k).getMountainID()){
				//first point for a curve					
				try{
					currMountainID = outputPoints.get(k).getMountainID();
					outputPoints.get(k).setSmoothI((outputPoints.get(k).getSumI()
													+outputPoints.get(k+1).getSumI())/2);
				} catch(IndexOutOfBoundsException IoE) {
				//reached end of object array
					break;
				}
			} else if (currMountainID != outputPoints.get(k+1).getMountainID()){
				//last point for a curve
				try{
				outputPoints.get(k).setSmoothI((outputPoints.get(k).getSumI()
                        +outputPoints.get(k-1).getSumI())/2);
				} catch(IndexOutOfBoundsException IoE) {
				//reached end of object array
					break;
				}
			} else {
				try{
	 				outputPoints.get(k).setSmoothI((outputPoints.get(k-1).getSumI()
	 												+outputPoints.get(k).getSumI()
	 												+outputPoints.get(k+1).getSumI())/3);		 				
	    			
				}	catch(IndexOutOfBoundsException IoE) {
				//reached end of object array					
					break;
				}
			}
		} 
		} catch (Exception e2) {
				//end of array
		}	

	}
	
}
