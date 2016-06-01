import java.util.ArrayList;
import java.util.Collections;
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
	    	    
	   public void main(String[] args) {
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
	 	   		    mountainPoints.add (
	 	   		    		new PointMountain(	
	 	   		    		    Double.parseDouble(tempStr[3]),	 	   		    			
	 							Double.parseDouble(tempStr[4]),
	 							Double.parseDouble(tempStr[5]),
	 							Double.parseDouble(tempStr[2]),
	 							Integer.parseInt(tempStr[0]),
	 							0,
	 							Integer.parseInt(tempStr[5]),
	 							-1,1,0,0));		
	 	   		    
	 	   		 }    
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
		   	Collections.sort(mountainPoints, new MountainPointCompare());		
		   	//Set<PointMountain> mp = new LinkedHashSet<PointMountain>(mountainPoints);
System.out.println("Done Sort:" + "\t" + System.currentTimeMillis());		   	
		   	//Create Arraylist of 3D peaks  
		   	//call the processing method - this is the time consuming part
System.out.println("Start 3d peaks:" + "\t" + System.currentTimeMillis());
			//Instantiate Reduce3Dpeaks object and run the method
			Reduce3DPeaks run3dPeaks = new Reduce3DPeaks();			
		   	ArrayList<PointMountain> outputPoints = run3dPeaks.ThreeDPeaks(mountainPoints);
System.out.println("Done 3d peaks:" + "\t" + System.currentTimeMillis());		

	writer.write("************************Start 3d Peaks******************************");
	writer.newLine();
	for (int k = 0; k<outputPoints.size();k++){
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
		}catch (IOException e) {
			e.printStackTrace();
		}
	}
	writer.write("*************************Done 3d Peaks******************************");
	
		   	//Smooth Intensities of the 3D peaks
System.out.println("Smooth 3d peaks:" + "\t" + System.currentTimeMillis());
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
//		    }catch (IOException e) {
//				e.printStackTrace();
//			}
//		   	}
//		   		   
			//Recalculate 3D peaks taking into account Overlapping Peaks	
System.out.println("Start Overlaps:" + "\t" + System.currentTimeMillis());
		   	ReduceOverlaps.calcOverlaps(outputPoints);	   	
System.out.println("Done Overlaps:" + "\t" + System.currentTimeMillis());

//		   	writer.write("******************************CalcOverlaps******************************");
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
//		    }catch (IOException e) {
//				e.printStackTrace();
//			}
//	
//		   	}
		   	
		   	//Create final 3D peaks and set Mountain ID		   	
System.out.println("Start final 3d:" + "\t" + System.currentTimeMillis());
		   	ArrayList<PointThreeD> threedDpoints = ReduceFinalPeaks.calcMountains(outputPoints);
System.out.println("Done final 3d:" + "\t" + System.currentTimeMillis());		   	
			//ISO Peaks//System.out.println("Start ISOPeaks:" + "\t" + System.currentTimeMillis());
//		   	ArrayList<PointISO> MonoISO = ReduceISOPeaks.calcISO(threedDpoints, outputPoints);
//System.out.println("Done ISOPeaks:" + "\t" + System.currentTimeMillis());
		   	//Write out Peaks
		
		   //for (int k = 0; k<MonoISO.size();k++){
		   //for (int k = 0; k<outputPoints.size();k++){
		   	writer.write("******************************3DPeaks******************************");
		   	writer.newLine();
		   	for (int k = 0; k<threedDpoints.size();k++){

				try {					
					 writer.write( threedDpoints.get(k).getCurveID() + "\t" +
							 threedDpoints.get(k).getCharge() + "\t" +
							 threedDpoints.get(k).getWpm() + "\t" +
							 threedDpoints.get(k).getSumI() + "\t" +
							 threedDpoints.get(k).getWpRT() + "\t" +
							 threedDpoints.get(k).getMaxRT() + "\t" +
							 threedDpoints.get(k).getMinRT() );					 
					 writer.newLine();
					 writer.flush();
				} catch (IOException e) {
					e.printStackTrace();
				}
			   	 	
		   	}
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
		   		
		   		
				try {					
					 writer.write( (MonoISO.get(k).getEnvelopeID() + "\t" +						
								MonoISO.get(k).getLevelID() + "\t" +
								MonoISO.get(k).getCharge() + "\t" +
								MonoISO.get(k).getSumI() + "\t" +
								MonoISO.get(k).getWpm() + "\t" +
								MonoISO.get(k).getWpRT()));					 
					 writer.newLine();
					 writer.flush();
					 
				System.out.println( MonoISO.get(k).getEnvelopeID() + "\t" +						
						MonoISO.get(k).getLevelID() + "\t" +
						MonoISO.get(k).getCharge() + "\t" +
						MonoISO.get(k).getSumI() + "\t" +
						MonoISO.get(k).getWpm() + "\t" +
						MonoISO.get(k).getWpRT() 
		   		);

				} catch (IOException e) {
					e.printStackTrace();
				}
			     	
		    }		   
	*/			
			} catch (IOException e) {
				e.printStackTrace();
			}
		   	 	
      }
		   	
} 
				
