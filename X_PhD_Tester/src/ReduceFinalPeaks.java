import java.util.ArrayList;

public class ReduceFinalPeaks {
	@SuppressWarnings("unused")
	public static ArrayList<PointThreeD> calcMountains (ArrayList<PointMountain> outputPoints) {
		
		//Calculate the 3D peaks
		int startPeak = 1; 				//flag for start of 3D peak
		int numPeaks = 0;				//number of 2D peaks in this 3D
		int peakCnt = 0;				//counter for peak
		double minRT = 0.0; 			//minimum RT 
		double maxRT = 0.0; 			//maximum RT
		double weightedPeakMz = 0.0;	//RT*I/sumI
		double weightedPeakRT = 0.0;	//mz*I/sumI
		int curveID = 0;				//counter for final 3D peak ID
		int charge = 0;
		int currMountainID = -1;	    	//holder for current Mountain ID
		double sumIntensity = 0.0;		//sum of Intenstity for this 3D peak
		double weightedMzSD = 0.0;
		double weightedMeanAvg = 0.0;
		double weighted_avg_mz = 0.0;
		double WPMZ = 0.0;
		double maxI = 0.0; //3d_clean
		double normI = 0.0;
		int numPoints = 0;
		int boundaryCheck = 0;			//Only add peaks that dont touch a partition boundary
		
		ArrayList<PointThreeD> threedDpoints = new ArrayList<PointThreeD>();
		for(int i1=0; i1 <= outputPoints.size(); i1++)    		    
		{ 
			try{
				//charge = outputPoints.get(i1).getCharge();
				if (currMountainID != outputPoints.get(i1).getNewMountainID()) {
					
					if (currMountainID > -1 && boundaryCheck == 0) {
						weightedPeakRT = weightedPeakRT / sumIntensity;
						weighted_avg_mz = weightedPeakMz / sumIntensity;
						weightedMzSD = Math.sqrt(weightedMeanAvg/(((numPeaks-1)*sumIntensity)/numPeaks));
						//charge = outputPoints.get(i1).getCharge();
						//build the final array of 3D points before Isotopic envelope detection
						//Don't add mountains with a single peak
						if(minRT != maxRT){
						threedDpoints.add (new PointThreeD(	currMountainID, //curveID,
															weighted_avg_mz,
															sumIntensity,
															weightedPeakRT,
															minRT,
															maxRT,
															charge,
															numPeaks,
															weightedMzSD,
															normI));  			
						}
					}
					//reset counters
					numPeaks = 0;
					minRT = 0.0;
					maxRT = 0.0;				
					weightedPeakMz = 0.0;
					sumIntensity = 0.0;
					weightedPeakRT = 0.0;
					startPeak = 1;			
					curveID++;
					currMountainID = outputPoints.get(i1).getNewMountainID();
					charge = outputPoints.get(i1).getCharge();
					weightedMzSD = 0.0;
					weightedMeanAvg = 0.0;
					weighted_avg_mz = 0.0;
					boundaryCheck = 0;
				}
			} catch(IndexOutOfBoundsException IoB) {
				break;
			}
			try{
			if(startPeak == 1){
				//Calculate WPM for peak
				minRT = outputPoints.get(i1).getRetentionTime();
				peakCnt = i1;
				WPMZ = 0;
				maxI = 0.0; //3d_clean			
				charge = outputPoints.get(i1).getCharge();
				
				//Work out the Weighted mz for this peak first
				while (peakCnt < outputPoints.size() && currMountainID == outputPoints.get(peakCnt).getNewMountainID()) {
					WPMZ = WPMZ + (outputPoints.get(peakCnt).getWpm() * outputPoints.get(peakCnt).getSmoothI());
					sumIntensity = sumIntensity + outputPoints.get(peakCnt).getSmoothI();
					if (outputPoints.get(peakCnt).getSmoothI() > maxI) maxI = outputPoints.get(peakCnt).getSmoothI();
					//if(outputPoints.get(peakCnt).getBoundary() == 1) boundaryCheck = 1; 
					peakCnt = peakCnt + 1;							
				}
				
				WPMZ = WPMZ/sumIntensity;
				sumIntensity = 0;
				startPeak = 0;
			}	
			} catch (Exception ePeak){
				//zero size array?
			}	
			
			try {
				sumIntensity = sumIntensity + outputPoints.get(i1).getSmoothI();				
				numPeaks ++;
				maxRT = outputPoints.get(i1).getRetentionTime();
				weightedPeakMz = weightedPeakMz + (outputPoints.get(i1).getWpm() * outputPoints.get(i1).getSmoothI());
				weightedPeakRT = weightedPeakRT + (outputPoints.get(i1).getRetentionTime()* outputPoints.get(i1).getSmoothI());
				weighted_avg_mz = weighted_avg_mz + (outputPoints.get(i1).getWpm() * outputPoints.get(i1).getSmoothI());
				weightedMeanAvg = weightedMeanAvg + (outputPoints.get(i1).getSmoothI() * Math.pow((outputPoints.get(i1).getWpm()-WPMZ),2));
				//add norm calc here sum / max 	
				outputPoints.get(i1).setNormI((outputPoints.get(i1).getSmoothI() / maxI));
				if(outputPoints.get(i1).getBoundary() == 1) boundaryCheck = 1;
			} catch (Exception eCalcs){
				//zero size Array?					
			}
		}
		
		//add final 3d point to array
		try {
			weightedPeakRT = weightedPeakRT / sumIntensity;
			weighted_avg_mz = weightedPeakMz / sumIntensity;
			weightedMzSD = Math.sqrt(weightedMeanAvg/(((numPeaks-1)*sumIntensity)/numPeaks));
			//Don't add mountains with a single peak or boundary peaks
			if(minRT != maxRT && boundaryCheck == 0){
			threedDpoints.add (new PointThreeD(	currMountainID, //curveID,
					weighted_avg_mz,
					sumIntensity,
					weightedPeakRT,
					minRT,
					maxRT,
					charge,
					numPeaks,
					weightedMzSD,
					normI));
			}
		} catch (Exception eadd) {
			//zero size array
		}
		return threedDpoints;
	}
		
}
