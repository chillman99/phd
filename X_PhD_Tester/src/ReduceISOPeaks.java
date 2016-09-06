import java.util.ArrayList;

public class ReduceISOPeaks {
	@SuppressWarnings("unused")
	public static ArrayList<PointISO> calcISO (ArrayList<PointThreeD> threedDpoints, ArrayList<PointMountain> outputPoints) {
   	
	//set up variables for isotopic peak detection - need to join the weighted array back to itself
	//curveID = WeightedArrayPos;
	double outerWPM=0.0;
	double innerWPM=0.0;
	double outerSUMI=0.0;
	double innerSUMI=0.0;
	double outerMAXRT=0.0;
	double outerMINRT=0.0;
	double innerMAXRT=0.0;
	double innerMINRT=0.0;
	double innerRT = 0.0;
	double outerCharge=0.0;
	double innerCharge=0.0;
	int innerCurve = 0;
	int outerCurve = 0;
	int match = 0;
	double wpmDiff = 0.00;
	int level = 1;
	int envelope = 0;
	int isoIndex = 0;
	int holdIndex = 0;
	
	ArrayList<PointISO> ISOpoints = new ArrayList<PointISO>();
	ArrayList<PointISO> MonoISO = new ArrayList<PointISO>();
	//int oSize = outputPoints.size()-1;
	int oSize = threedDpoints.size()-1;
	for(int outerPos=0; outerPos <= oSize; outerPos++){		
		   outerWPM=threedDpoints.get(outerPos).getWpm();
		   outerSUMI=threedDpoints.get(outerPos).getSumI();
		   outerMAXRT=threedDpoints.get(outerPos).getMaxRT();
		   outerMINRT=threedDpoints.get(outerPos).getMinRT();
		   outerCharge=threedDpoints.get(outerPos).getCharge();

	   for(int innerPos=0; innerPos < oSize; innerPos++){	
			   innerWPM=threedDpoints.get(innerPos).getWpm();
			   innerSUMI=threedDpoints.get(innerPos).getSumI();
			   innerRT=threedDpoints.get(innerPos).getWpRT();
			   innerCharge=threedDpoints.get(innerPos).getCharge();
				   		   		 
		   
		   if (outerPos != innerPos
			//Match on WPM
			   && (outerWPM-innerWPM >= 0.0 & outerWPM-innerWPM <= 1.000)
			//Match on RT Window
			   && (outerMAXRT >= innerRT &  outerMINRT <= innerRT)
			   //& (innerMINRT <= outerMAXRT & innerMAXRT >= outerMINRT)
			//Match on Charge
			   && outerCharge == innerCharge
			//Match on Intensity			
			   && (innerSUMI > (0.66667*outerSUMI))) {
		   
			 //Enhanced method similar using Standard Deviation of weighted 3d peak
				   wpmDiff = Math.sqrt(
						   Math.pow(0.0109135, 2) + 
						   Math.pow(threedDpoints.get(innerPos).getWeightedSD(), 2) + 
						   Math.pow(threedDpoints.get(outerPos).getWeightedSD(), 2) 
						   );

				   if 	   ((outerCharge == 2) && (outerWPM-innerWPM)-0.50 <= wpmDiff ){match = 1;}
				   else if ((outerCharge == 3) && (outerWPM-innerWPM)-0.33 <= wpmDiff ){match = 1;}
				   else if ((outerCharge == 4) && (outerWPM-innerWPM)-0.25 <= wpmDiff ){match = 1;}
				   else if ((outerCharge == 5) && (outerWPM-innerWPM)-0.20 <= wpmDiff ){match = 1;}
				   else if ((outerCharge == 6) && (outerWPM-innerWPM)-0.17 <= wpmDiff ){match = 1;}	
				   
				   if( match >= 1) {
					   //drill down to individual points to calculate correlation coefficient
					   ArrayList<PointCheck> checkPoints = new ArrayList<PointCheck>();
					   int tempCntr = 0;
					   
					   innerCurve = threedDpoints.get(innerPos).getCurveID();
					   outerCurve = threedDpoints.get(outerPos).getCurveID();
					   
					   //Populate checkPoints Arraylist with the data from the peak pairs
//need to check osize variable here					   
					   for(int aPos=0; aPos <= oSize; aPos++){	
						   if (outputPoints.get(aPos).getNewMountainID() == innerCurve){									   
							    checkPoints.add(new PointCheck(
							        outputPoints.get(aPos).getWpm(),
							        outputPoints.get(aPos).getSmoothI(),
							        outputPoints.get(aPos).getCharge(),
							        outputPoints.get(aPos).getNewMountainID(),
							        outputPoints.get(aPos).getNormI(),
							        0.0,0.0,0,0,0.0
						        ));						
						   }						   
					   }
					   	
					   int cSize = checkPoints.size();
					   for(int aPos=0; aPos <= cSize; aPos++){	
						   if (outputPoints.get(aPos).getNewMountainID() == outerCurve){
							        checkPoints.get(tempCntr).setWpmB(outputPoints.get(aPos).getWpm());
							        checkPoints.get(tempCntr).setSmoothB(outputPoints.get(aPos).getSmoothI());
							        checkPoints.get(tempCntr).setChargeB(outputPoints.get(aPos).getCharge());
							        checkPoints.get(tempCntr).setNewMountainIDB(outputPoints.get(aPos).getNewMountainID());
							        checkPoints.get(tempCntr).setNormB(outputPoints.get(aPos).getNormI());  
							    tempCntr++;
						   }							  
					   }
					   
					   double normAnormB = 0.00;
					   double normAnormA = 0.00;
					   double normBnormB = 0.00;
					   double corrCoeff = 0.00;
					   
					   for(int aPos=0; aPos <= checkPoints.size()-1; aPos++){	
						   normAnormB = normAnormB + (checkPoints.get(aPos).getNormA() * checkPoints.get(aPos).getNormB());
						   normAnormA = normAnormA + (checkPoints.get(aPos).getNormA() * checkPoints.get(aPos).getNormA());
						   normBnormB = normBnormB + (checkPoints.get(aPos).getNormB() * checkPoints.get(aPos).getNormB());							   
					   }
					   
					   corrCoeff = normAnormB / Math.sqrt((normAnormA*normBnormB));
					   
						//Write out matched points if correlation coefficient is greater than 0.6
					   if (corrCoeff >= 0.6) {
						   PointISO tempPointa =  new PointISO(	
								   envelope,
								   level,
								   threedDpoints.get(innerPos).getCharge(),
								   threedDpoints.get(innerPos).getWpm() ,
								   threedDpoints.get(innerPos).getSumI() ,
								   threedDpoints.get(innerPos).getWpRT());
						   
						   PointISO tempPointb =  new PointISO(	
								   envelope,
								   level,
								   threedDpoints.get(outerPos).getCharge(),
								   threedDpoints.get(outerPos).getWpm() ,
								   threedDpoints.get(outerPos).getSumI() ,
								   threedDpoints.get(outerPos).getWpRT());
						   
						   //This point doesn't exist in the array so
						   //add points and set new envelopeID and Level back to 1
						   //set holdIndex to start of envelope
						   if (!(ISOpoints.contains(tempPointa))) {
							   //if not the first envelope then we can go back to holdIndex
							   //and calculate the mono ISO peak for the previous envelope
							  if (envelope > 0) {
								  double sumSumI = 0.0;
								  double level1Wpm = ISOpoints.get(holdIndex).getWpm();
								  double level1Rt = ISOpoints.get(holdIndex).getWpRT();
								  int level1Charge = ISOpoints.get(holdIndex).getCharge();
								  
									  for (int i = holdIndex;i<ISOpoints.size();i++){
										  sumSumI = sumSumI + ISOpoints.get(i).getSumI();
									  }
								  
								//Add mono peak 
								  MonoISO.add(new PointISO(
										0,
										0,
										level1Charge,
										level1Wpm,
										sumSumI,
										level1Rt
							        ));
							  }
							  
							   holdIndex = isoIndex;
							   envelope++;
							   level = 1;
							   ISOpoints.add (tempPointa);	
							   ISOpoints.get(isoIndex).setEnvelopeID(envelope);
							   ISOpoints.get(isoIndex).setLevelID(level);
							   isoIndex++;
							   level++;
							   ISOpoints.add (tempPointb);	
							   ISOpoints.get(isoIndex).setEnvelopeID(envelope);
							   ISOpoints.get(isoIndex).setLevelID(level);
							   isoIndex++;
			    			} else {
			    			//point already exists so check b value and add as new point 
			    			//if necessary keep envelopeID the same but set level++
			    				if (!(ISOpoints.contains(tempPointb))) {
								   level++;
								   ISOpoints.add (tempPointb);	
								   ISOpoints.get(isoIndex).setEnvelopeID(envelope);
								   ISOpoints.get(isoIndex).setLevelID(level);
								   isoIndex++;    			    			
			    				}
			    				
			    			}

					   }
				   
				   }
						
			   }	
			   match = 0;		   
		   }			
		   //System.out.println("Outer Loop:" + "\t" + System.currentTimeMillis()  + "\t" + outerWPM  + "\t" + outerMAXRT  + "\t" + outerCharge);
		}
	return MonoISO;
	}

}
