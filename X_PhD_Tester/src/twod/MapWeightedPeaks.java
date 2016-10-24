package twod;

import java.util.ArrayList;

public class MapWeightedPeaks {
	
	public static ArrayList<PointWeighted> WeightedPeaks(float[] mzData, float[] intensityData) {
	//**************** Start Main Section of Code for 2D Peak Picking *********************			
	int curveCount=0;				//Temp Counter while processing curvearray
	int count=0;					//Temp Counter while construction curvearray
	int badCurve = 0;				//flag to ignore bad curves
	double currIntensity=0.0;		//Current intensity value
	double lastIntensity=0.0;		//Intensity value of previous input row
	double PI=0;					//Intensity value of previous row in current curve
	double PI2=0;					//Intensity value of previous row but 1 in current curve
	int overlapFlag = 0;			//indicates that the current curve has overlapping peaks
	int prevOverlapFlag = 0;		//indicates that the Previous curve had overlapping peaks
	double overlapIntensity=0;		//temp holder for first intensity of overlapping peak
	double minMZ=0;					//minimum MZ for current curve
	double maxMZ=0;					//maximum MZ for current curve
	double maxIntensity=0;			//maximum intensity for current curve
	double minIntensity=0;			//minimum intensity for current curve
	double sumIntensity=0;			//sum of intensity for current curve
	double sumMZ=0;			        //sum of MZ for current curve
	double sumMZByIntensity=0;		//sum of (MZ * intensity) for current curve
	double intensityThreshold=0;	//intensity threshold for current curve - default 10% maxMZ 
	double[][] curveArray = new double[2][150];						//array to hold current curve
	ArrayList<PointWeighted> WeightedPointsTemp = new ArrayList<PointWeighted>();	//temp array to hold point objects
	int curveID=0;  				//Counter for current curve
			
	int masterCount = 1;
	while ( masterCount < intensityData.length)
	try {	
		maxIntensity=0;
	
		//Initialize Temp Array
    	for (int i=0; i <= 50; i++){
    		curveArray[0][i]=0;
    		curveArray[1][i]=0;
    	}
    	
		if (overlapFlag== 1 || prevOverlapFlag == 1){
    		count = 1;
    	} else {
    		count = 0;
    	}			
			    	
    	maxIntensity=0;		    	
    	currIntensity=intensityData[masterCount];
    	
		//Find start of Curve, lastintensity is 0 
    	//or previous lastintensity is higher than lastintensity - overlapping peaks (double peak curve)	    	
    	if ((double) currIntensity > (double) 00 && lastIntensity == 0 || overlapFlag==1){
			//Populate Temp Array with Curve points and find maxIntensity to derive threshold    		
			while ((double) currIntensity > (double) 0){															
				if(maxIntensity < currIntensity)
						maxIntensity=currIntensity;
				
				if (overlapFlag==1){
					overlapFlag=0;
					prevOverlapFlag=1;
					PI = overlapIntensity;
					currIntensity=intensityData[masterCount];											
				}
				//Need to add back in the mz and intensity from the end of the previous overlapping peak
				if (prevOverlapFlag==1){
					prevOverlapFlag=0;
					curveArray[0][count-1]=mzData[masterCount-1];
					curveArray[1][count-1]=intensityData[masterCount-1];
					PI = overlapIntensity;
					currIntensity=intensityData[masterCount];					
				}		
				
				curveArray[0][count]=mzData[masterCount];
				curveArray[1][count]=intensityData[masterCount];					
				count++;	

		    	if (masterCount < mzData.length){
		    		masterCount ++;
		    	}

				PI2 = PI;
				PI = currIntensity;
				currIntensity=intensityData[masterCount];					
									
				if (currIntensity > PI && PI2 > PI){
					//Overlapping Peak found, store Intensity and start new Curve for next Iteration
					overlapFlag=1;
					overlapIntensity=intensityData[masterCount];
					masterCount--;
					curveArray[1][count-1]=0;	
					//System.out.println("Overlap " + mzData[masterCount] + "\t" + intensityData[masterCount]);
					break;
				}	
			}
			//Reset flags
			currIntensity =0;
			PI=0;
			PI2=0;
			//Initialize Temporary Variables
			intensityThreshold = (double) (maxIntensity*0.10000000);
			//intensityThreshold = (double) 0;
			curveCount=0;
			maxMZ=0;
			minMZ=0;
			minIntensity= 0;						
			sumIntensity= 0;
			sumMZByIntensity= 0;
			sumMZ=0;				
			
			//Process Temp Array to create intermediate metrics
			if(curveArray[1][curveCount] > 0) {
				while (curveArray[1][curveCount] > 0){							
					//Handle cases where peaks do not have a good start point
					if (curveCount == 0) 
						{if (curveArray[1][curveCount] > curveArray[1][curveCount+1]) badCurve = 1;								
						}					
					
					if (curveArray[1][curveCount] > intensityThreshold){
						if (maxMZ < curveArray[0][curveCount]){
							maxMZ=curveArray[0][curveCount];
						}
						if (minIntensity > curveArray[1][curveCount] || minIntensity == 0){
							minIntensity=curveArray[1][curveCount];
						}
						if (minMZ > curveArray[0][curveCount] || minMZ == 0){
							minMZ=curveArray[0][curveCount];
						}
						sumIntensity=sumIntensity+curveArray[1][curveCount];
						sumMZ=sumMZ+curveArray[0][curveCount];
						sumMZByIntensity=sumMZByIntensity+(curveArray[0][curveCount]*curveArray[1][curveCount]);

					}
					curveCount++;
				}
				//Add Results to WeightedArray for use by later ISO process
				//No Single point curves
				if (curveCount > 1 && badCurve != 1){
				//if (curveCount > 3 && badCurve != 1){
					WeightedPointsTemp.add (new PointWeighted(curveID, sumMZByIntensity/sumIntensity, sumIntensity, maxIntensity, 0, 0, -1));
				} 
				badCurve =0;
				curveID++;
			}
    	}	//else  System.out.println("Zero Intensity " + mzData[masterCount] + "\t" + intensityData[masterCount]); 																																		
    	masterCount ++;		    																															
	} catch (Exception e) {e.printStackTrace();}
	
	return WeightedPointsTemp;
	
  }
}