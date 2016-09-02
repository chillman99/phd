///Users/localadmin/Documents/Box\ Sync/UoD/PhD/data/hashtest/peakout2d_pt244_h240_120-126.txt
///Users/localadmin/Documents/Box\ Sync/UoD/phd/DataQC/output/3dpeaksout_pt244_hash240_120-126.txt

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.FileReader;
import java.io.BufferedReader;

@SuppressWarnings("unused")
public class run3dTest  {
       public static String inputFile = new String();
       private String filepath;
       private static String outputFile;
       
       //Start section to read input file
       public run3dTest (String file_path) {
	    	filepath = file_path;
	    }       
	    public String[] openFile() throws IOException{
	    	FileReader fr  = new FileReader(filepath);
	    	BufferedReader textReader = new BufferedReader(fr);	    
	    	int numberOfLines = readLines();
	    	String[] textData = new String[numberOfLines];
	    	int i;
	    	for (i=0;i<numberOfLines;i++){
	    		textData[i] = textReader.readLine();	    	
	    	}
	    	textReader.close();
	    	return textData;	    	
	    }	    
	    
		int readLines() throws IOException{
	    	FileReader inputFile = new FileReader(filepath);
	    	BufferedReader bf  = new BufferedReader(inputFile);
	    	String aLine;
	    	int numberOfLines = 0;
	    	while ( (aLine = bf.readLine() ) != null){
	    		numberOfLines++;
	    	}
	    	bf.close();
	    	return numberOfLines;
	    }
		
	    //Complete section to read input file
	    	    
	   public static void main(String[] args) {
		    Set<PointMountain> inputPoints = new HashSet<PointMountain>();
		    ArrayList<PointMountain> mountainPoints = new ArrayList<PointMountain>();
		    String inputFile = args[0];
		    String outputFile = args[1];

System.out.println("Start:" + "\t" + System.currentTimeMillis());			
		    //Open File and populate array
		    run3dTest file = new run3dTest(inputFile);
		    
		    String[] fileLines;			
				try {
					fileLines = file.openFile();

		   	int i;
		   	if (fileLines.length > 0) {
		   	//Parse String Array into ArrayList of Objects to match the MapReduce code	
			   	for (i=0;i<fileLines.length;i++)
			   	 {
		   			String[] tempStr;	   
		   			tempStr = fileLines[i].split("\t");
		   			//System.out.println(tempStr[4]);
	 	   		    inputPoints.add (
	 	   		    		new PointMountain(	
	 	   		    		    Double.parseDouble(tempStr[3]),	 	   		    			
	 							Double.parseDouble(tempStr[4]),
	 							Double.parseDouble(tempStr[5]),
	 							Double.parseDouble(tempStr[2]),
	 							Integer.parseInt(tempStr[0]),
	 							0,
	 							Integer.parseInt(tempStr[5]),
	 							-1,1,0,0,0));			 	   		    
	 	   		 }
			   	mountainPoints.addAll(inputPoints);
//System.out.println("DeDupe Peak Count:" + "\t" + System.currentTimeMillis() + "\t" + mountainPoints.size());
		   	 }
/*
		   	new PointMountain(	
	    				Double.parseDouble(tempStr[5]),
						Double.parseDouble(tempStr[6]),
						Double.parseDouble(tempStr[6]),
						Double.parseDouble(tempStr[3]),
						Integer.parseInt(tempStr[1]),
						0,
						Integer.parseInt(tempStr[7]),
						-1,1,0,0));	
						*/
		   	BufferedWriter writer = null;{
				try
				{
					writer = new BufferedWriter( new FileWriter( outputFile ));			
				} catch ( IOException e){
					}
			}

System.out.println("Start Sort:" + "\t" + System.currentTimeMillis());		   	
			//Now we can sort on Retention Time followed by WPM using a custom compare from MountainPoint
			//and de-duplicate 2d peaks
		   	Collections.sort(mountainPoints, new MountainPointCompare());	
		   	   	
System.out.println("Done Sort:" + "\t" + System.currentTimeMillis());		   	
		   	//Create Arraylist of 3D peaks  
		   	//call the processing method - this is the time consuming part
System.out.println("Start 3d peaks:" + "\t" + System.currentTimeMillis());
		
		//scan through the points and flag the first and last point in each RT
		//if the 3d peak includes these it has touched the edge of the partition and will not
		//be included in the output
			int checked = 0;
			double currRT = 0.0;			
			currRT = mountainPoints.get(0).getRetentionTime();
			mountainPoints.get(0).setBoundary(1);
			int kk=1;
			while (kk < mountainPoints.size()) 
			{  
				mountainPoints.get(kk).setBoundary(0);
				if (checked == 1){
					mountainPoints.get(kk-2).setBoundary(1);
					checked = 0;
				}
				
				if (currRT != mountainPoints.get(kk).getRetentionTime()){
					mountainPoints.get(kk).setBoundary(1);
					checked = 1;
				} 
				
				currRT = mountainPoints.get(kk).getRetentionTime();
				kk++;
			}
			mountainPoints.get(kk-1).setBoundary(1);
//			writer.newLine();			
//			writer.write("*************************Boundary Flags******************************");
//			writer.newLine();
//			for (int k = 0; k<mountainPoints.size();k++){
//				try {					
//					writer.write( 
//						  mountainPoints.get(k).getBoundary() + "\t" +
//						  mountainPoints.get(k).getWpm() + "\t" + 
//						  mountainPoints.get(k).getScanOrder() + "\t" +
//						  mountainPoints.get(k).getScanNumber() + "\t" +							  
//						  mountainPoints.get(k).getRetentionTime() + "\t" +
//						  mountainPoints.get(k).getSumI() + "\t" +
//						  mountainPoints.get(k).getSmoothI() + "\t" +					   		  
//						  mountainPoints.get(k).getCharge()  + "\t" +
//						  mountainPoints.get(k).getMountainID() + "\t" +
//						  mountainPoints.get(k).getNewMountainID() + "\t" +
//						  mountainPoints.get(k).getMass());					 
//				 writer.newLine();
//				 writer.flush();			 
//				}catch (IOException e) {
//					e.printStackTrace();
//				}
//			}
//			writer.write("*************************Boundary Flags******************************");
			
			
			//Instantiate Reduce3Dpeaks object and run the method
			Reduce3DPeaks run3dPeaks = new Reduce3DPeaks();			
		   	ArrayList<PointMountain> outputPoints = run3dPeaks.ThreeDPeaks(mountainPoints);
System.out.println("Done 3d peaks:" + "\t" + System.currentTimeMillis());		

//	writer.write("************************Start 3d Peaks******************************");
//	writer.newLine();
//	for (int k = 0; k<outputPoints.size();k++){
//		try {					
//			writer.write( 
//				  outputPoints.get(k).getBoundary() + "\t" +
//				  outputPoints.get(k).getWpm() + "\t" + 
//				  outputPoints.get(k).getScanOrder() + "\t" +
//				  outputPoints.get(k).getScanNumber() + "\t" +							  
//				  outputPoints.get(k).getRetentionTime() + "\t" +
//				  outputPoints.get(k).getSumI() + "\t" +
//				  outputPoints.get(k).getSmoothI() + "\t" +					   		  
//		   		  outputPoints.get(k).getCharge()  + "\t" +
//		 		  outputPoints.get(k).getMountainID() + "\t" +
//		 		  outputPoints.get(k).getNewMountainID() + "\t" +
//		 		  outputPoints.get(k).getMass());					 
//		 writer.newLine();
//		 writer.flush();			 
//		}catch (IOException e) {
//			e.printStackTrace();
//		}
//	}
//	writer.write("*************************Done 3d Peaks******************************");
//	writer.newLine();
	
		   	//Smooth Intensities of the 3D peaks
System.out.println("Smooth3d peaks:" + "\t" + System.currentTimeMillis());
		   	ReduceSmoothIntesity.smoothIntensity(outputPoints);
System.out.println("Done 3d peaks:" + "\t" + System.currentTimeMillis());

//		   	writer.write("******************************SmoothIntensity******************************");
//		   	writer.newLine();
//		   	for (int k = 0; k<outputPoints.size();k++){
//		   		try {					
//		   			writer.write( 								
//							  outputPoints.get(k).getWpm() + "\t" + 
//							  outputPoints.get(k).getScanOrder() + "\t" +
//							  outputPoints.get(k).getScanNumber() + "\t" +							  
//							  outputPoints.get(k).getRetentionTime() + "\t" +
//							  outputPoints.get(k).getSumI() + "\t" +
//							  outputPoints.get(k).getSmoothI() + "\t" +					   		  
//					   		  outputPoints.get(k).getCharge()  + "\t" +
//					 		  outputPoints.get(k).getMountainID() + "\t" +
//					 		  outputPoints.get(k).getNewMountainID() + "\t" +
//					 		  outputPoints.get(k).getMass());					 
//					 writer.newLine();
//					 writer.flush();			 
//			    }catch (IOException e) {
//					e.printStackTrace();
//				}
//		   	}
		   		   
			//Recalculate 3D peaks taking into account Overlapping Peaks	
System.out.println("Start Overlaps:" + "\t" + System.currentTimeMillis());
		   	ReduceOverlaps.calcOverlaps(outputPoints);	   	
System.out.println("Done Overlaps:" + "\t" + System.currentTimeMillis());

		   	writer.write("******************************CalcOverlaps******************************");
		   	writer.newLine();
		   	for (int k = 0; k<outputPoints.size();k++){
		   		try {					
		   			writer.write( 
		   					  outputPoints.get(k).getBoundary() + "\t" +
							  outputPoints.get(k).getWpm() + "\t" + 
							  outputPoints.get(k).getScanOrder() + "\t" +
							  outputPoints.get(k).getScanNumber() + "\t" +							  
							  outputPoints.get(k).getRetentionTime() + "\t" +
							  outputPoints.get(k).getSumI() + "\t" +
							  outputPoints.get(k).getSmoothI() + "\t" +					   		  
					   		  outputPoints.get(k).getCharge()  + "\t" +
					 		  outputPoints.get(k).getMountainID() + "\t" +
					 		  outputPoints.get(k).getNewMountainID() + "\t" +
					 		  outputPoints.get(k).getMass());					 
					 writer.newLine();
					 writer.flush();			 
		    }catch (IOException e) {
				e.printStackTrace();
			}
	
		   	}
		   	writer.write("******************************CalcOverlaps******************************");
		   	writer.newLine();
		   	
		   	//Create final 3D peaks and set Mountain ID		   	
System.out.println("Start final 3d:" + "\t" + System.currentTimeMillis());
		   	ArrayList<PointThreeD> threedDpoints = ReduceFinalPeaks.calcMountains(outputPoints);
System.out.println("Done final 3d:" + "\t" + System.currentTimeMillis());
//System.out.println("3d Peak Count:" + "\t" + System.currentTimeMillis() + "\t" + outputPoints.size());

	writer.write("******************************mono 3DPeaks******************************");
	writer.newLine();
	for (int k1 = 0; k1<threedDpoints.size();k1++){
		try {					
			 writer.write( threedDpoints.get(k1).getCurveID() + "\t" +
					 threedDpoints.get(k1).getCharge() + "\t" +
					 threedDpoints.get(k1).getWpm() + "\t" +
					 threedDpoints.get(k1).getSumI() + "\t" +
					 threedDpoints.get(k1).getWpRT() + "\t" +
					 threedDpoints.get(k1).getMaxRT() + "\t" +
					 threedDpoints.get(k1).getMinRT() );					 
			 writer.newLine();
			 writer.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
		}
	writer.write("******************************mono 3DPeaks******************************");
	writer.newLine();
	
			//ISO Peaks
Collections.sort(threedDpoints, new ThreeDPointCompare());
System.out.println("Start ISOPeaks:" + "\t" + System.currentTimeMillis());
		   	ArrayList<PointISO> MonoISO = ReduceISOPeaks.calcISO(threedDpoints, outputPoints);
System.out.println("Done ISOPeaks:" + "\t" + System.currentTimeMillis());
		   	//Write out Peaks
writer.write("******************************ISOPeaks******************************");
writer.newLine();
for (int k1 = 0; k1<MonoISO.size();k1++){
	try {					
		 writer.write( (MonoISO.get(k1).getEnvelopeID() + "\t" +						
					MonoISO.get(k1).getLevelID() + "\t" +
					MonoISO.get(k1).getCharge() + "\t" +
					MonoISO.get(k1).getSumI() + "\t" +
					MonoISO.get(k1).getWpm() + "\t" +
					MonoISO.get(k1).getWpRT()));					 
		 writer.newLine();
		 writer.flush();
	} catch (IOException e) {
		e.printStackTrace();
	}
	}
writer.write("******************************ISOPeaks******************************");
writer.newLine();

//		   for (int k1 = 0; k1<MonoISO.size();k1++){
		   //for (int k = 0; k<outputPoints.size();k++){
//		   	writer.write("******************************3DPeaks******************************");
//		   	writer.newLine();
//		   	for (int k = 0; k<threedDpoints.size();k++){
//
//				try {					
//					 writer.write( threedDpoints.get(k).getCurveID() + "\t" +
//							 threedDpoints.get(k).getCharge() + "\t" +
//							 threedDpoints.get(k).getWpm() + "\t" +
//							 threedDpoints.get(k).getSumI() + "\t" +
//							 threedDpoints.get(k).getWpRT() + "\t" +
//							 threedDpoints.get(k).getMaxRT() + "\t" +
//							 threedDpoints.get(k).getMinRT() );					 
//					 writer.newLine();
//					 writer.flush();
//				} catch (IOException e) {
//					e.printStackTrace();
//				}
//			   	 	
//		   	}
				/* 
				System.out.println( threedDpoints.get(k).getCurveID() + "\t" +
						 threedDpoints.get(k).getCharge() + "\t" +
						 threedDpoints.get(k).getWpm() + "\t" +
						 threedDpoints.get(k).getSumI() + "\t" +
						 threedDpoints.get(k).getWpRT() + "\t" +
						 threedDpoints.get(k).getMaxRT() + "\t" +
						 threedDpoints.get(k).getMinRT()
		   		);
				
			
				try {			
					
					 writer.write( 
							  outputPoints.get(k).getWpm() + "\t" + 
							  outputPoints.get(k).getScanOrder() + "\t" +
							  outputPoints.get(k).getScanNumber() + "\t" +							  
							  outputPoints.get(k).getRetentionTime() + "\t" +
							  outputPoints.get(k).getSumI() + "\t" +
							  outputPoints.get(k).getSmoothI() + "\t" +					   		  
					   		  outputPoints.get(k).getCharge()  + "\t" +
					 		  outputPoints.get(k).getMountainID() + "\t" +
					 		  outputPoints.get(k).getNewMountainID() + "\t" +
					 		  outputPoints.get(k).getMass());					 
					 writer.newLine();
					 writer.flush();
					
				System.out.println( 
						outputPoints.get(k).getWpm() + "\t" + 
								  outputPoints.get(k).getScanOrder() + "\t" +
								  outputPoints.get(k).getScanNumber() + "\t" +							  
								  outputPoints.get(k).getRetentionTime() + "\t" +
								  outputPoints.get(k).getSumI() + "\t" +
								  outputPoints.get(k).getSmoothI() + "\t" +					   		  
						   		  outputPoints.get(k).getCharge()  + "\t" +
						 		  outputPoints.get(k).getMountainID() + "\t" +
						 		  outputPoints.get(k).getNewMountainID() + "\t" +
						 		  outputPoints.get(k).getMass());	
				
		   		
				System.out.println( threedDpoints.get(k).getCurveID() + "\t" +
						threedDpoints.get(k).getWeightedSD() + "\t" +
						threedDpoints.get(k).getWpm() + "\t" +
						threedDpoints.get(k).getSumI() + "\t" +
						threedDpoints.get(k).getMaxI() + "\t" +
						threedDpoints.get(k).getWpRT() + "\t" +
						threedDpoints.get(k).getCharge() 
		   		);
		   		
		   		*/
//				try {					
//					 writer.write( (MonoISO.get(k1).getEnvelopeID() + "\t" +						
//								MonoISO.get(k1).getLevelID() + "\t" +
//								MonoISO.get(k1).getCharge() + "\t" +
//								MonoISO.get(k1).getSumI() + "\t" +
//								MonoISO.get(k1).getWpm() + "\t" +
//								MonoISO.get(k1).getWpRT()));					 
//					 writer.newLine();
//					 writer.flush();
					 
//				System.out.println( MonoISO.get(k).getEnvelopeID() + "\t" +						
//						MonoISO.get(k).getLevelID() + "\t" +
//						MonoISO.get(k).getCharge() + "\t" +
//						MonoISO.get(k).getSumI() + "\t" +
//						MonoISO.get(k).getWpm() + "\t" +
//						MonoISO.get(k).getWpRT() 
//		   		);

//				} catch (IOException e) {
//					e.printStackTrace();
//				}
			      	
//		    }		   
		
			} catch (IOException e) {
				e.printStackTrace();
			}
		   	 	
      }
		   	
} 
				
