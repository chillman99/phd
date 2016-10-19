package twod;

import java.util.ArrayList;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.FileReader;
import java.io.BufferedReader;

import twod.MapProcess;
import twod.PointWeighted;

public class run2dTest  {
       public static String inputFile = new String();
       private String filepath;
       @SuppressWarnings("unused")
       private static String outputFile;
       
       //Start section to read input file
       public run2dTest (String file_path) {
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
	    @SuppressWarnings("unused")
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
	    	    
	   public static void main(String[] args) throws Exception {		   
		    String inputFile = args[0];
		    String outputFile = args[1];
			
		    //Open File and populate array
		    run2dTest file = new run2dTest(inputFile);
		    
		    String[] fileLines;			
				try {
					fileLines = file.openFile();
		   	int i;
		   	BufferedWriter writer = null;{
				try
				{
					writer = new BufferedWriter( new FileWriter( outputFile ));			
				} catch ( IOException e){
					}
			}
		   	
		   	String scNumber = new String();
			String scLevel = new String();
			String mzString = new String();
			String intensityString = new String();	
			
		   	if (fileLines.length > 0) {
		   	//Parse String Array into ArrayList of Objects to match the MapReduce code	
			   	for (i=0;i<fileLines.length;i++)
			   	 {

					String RT = "0";
				    String[] tempStr;
				  
				    //Parse input String from HDFS
				    tempStr = fileLines[i].split("\t");
				    scNumber = tempStr[1];
				    scLevel = tempStr[2];
				    RT = tempStr[3];
				    mzString = tempStr[7];
					intensityString  = tempStr[8];		
		   				
		   			ArrayList<PointWeighted> outputPoints = MapProcess.process(scNumber, scLevel, mzString, intensityString, RT);
		   			
					for (int k = 0; k<outputPoints.size();k++){
					   try {
						   writer.write( (scNumber + "\t" +
								   		  scLevel + "\t" +
								   		  RT + "\t" +
								   		  outputPoints.get(k).getCurveID() + "\t" +
								   		  outputPoints.get(k).getWpm() + "\t" +								   		  
										  outputPoints.get(k).getSumI() + "\t" +
								   		  outputPoints.get(k).getCharge() 
								   		));
							writer.newLine();							
							writer.flush();
//Write out iso peaks to console							
							System.out.println(
							   		  outputPoints.get(k).getWpm() + "\t" +								   		  
									  outputPoints.get(k).getSumI() + "\t" +
							   		  outputPoints.get(k).getCharge() 
							   		);
							
						} catch (IOException e) {
							e.printStackTrace();
						}
					}
			   	}
		   	}
			} catch (IOException e) {
				e.printStackTrace();
			}   	   
				
	   }	
} 

