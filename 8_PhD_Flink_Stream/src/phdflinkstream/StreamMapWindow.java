package phdflinkstream;

import java.io.*;
import java.util.ArrayList;

import org.apache.flink.api.common.functions.FlatMapFunction;
import org.apache.flink.util.Collector;

import phd2dcore.PointWeighted;
import phd2dcore.Pp2dProcess;

@SuppressWarnings("serial")
public class StreamMapWindow implements FlatMapFunction<String, String>  {

public void flatMap(String inputLine, String out) throws IOException {
		
		String[] tempStr = inputLine.split("\t");		
		String scNumber = tempStr[1];
		String scLevel = tempStr[2];
		String mzString = tempStr[7];
		String intensityString = tempStr[8];
		String RT = tempStr[3];
	    		
		//Populate outputPoints with the isotopic centroided peaks
		ArrayList<PointWeighted> outputPoints = Pp2dProcess.process(scNumber, scLevel, mzString, intensityString, RT);
		
		if (outputPoints.size()>0) {			
			for (int i=0; i<outputPoints.size(); i++){
				
//Modified from MapReduce function to output results as a concatenated string				
				out = new String(outputPoints.get(i).getoutKey() + '\t' + 
			     		   Double.toString(outputPoints.get(i).getWpm()) + '\t' + 
			     		   scLevel + '\t' +
			     		   RT + '\t' +
			     		   outputPoints.get(i).getCurveID() + '\t' +
			     		   scNumber + '\t' + 
			     		   outputPoints.get(i).getSumI() + '\t' + 
			     		   Integer.toString(outputPoints.get(i).getCharge()));			
			}	
			
		}	
		
	}

@Override
public void flatMap(String arg0, Collector<String> arg1) throws Exception {
	// TODO Auto-generated method stub
	
}	

}

