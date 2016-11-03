package phdflinkbatch;

import java.io.*;
import java.util.ArrayList;

import org.apache.flink.api.common.functions.FlatMapFunction;
import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.api.java.tuple.Tuple8;
import org.apache.flink.util.Collector;
import org.apache.hadoop.io.*;

import phd2dcore.*;

@SuppressWarnings("serial")
public class TestMap implements FlatMapFunction<Tuple2<LongWritable, Text>, Tuple8<Integer, Double, Integer,Double,Integer,Integer,Double,Integer>>  {
//public class TestMap implements FlatMapFunction<Tuple2<LongWritable, Text>, Tuple2<IntWritable, Text>>  {

public void flatMap(Tuple2<LongWritable, Text> value, Collector<Tuple8<Integer, Double, Integer,Double,Integer,Integer,Double,Integer>> out) throws IOException {
    //public void flatMap(Tuple2<LongWritable, Text> value, Collector<Tuple2<IntWritable, Text>> out) throws IOException {
		
		String inputLine = value.f1.toString();	
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
				
				out.collect(new Tuple8<Integer, Double, Integer,Double,Integer,Integer,Double,Integer>
				           (outputPoints.get(i).getoutKey(), outputPoints.get(i).getWpm(),Integer.parseInt(scLevel), Double.parseDouble(RT),
				       	   outputPoints.get(i).getCurveID(),
				        	   Integer.parseInt(scNumber), outputPoints.get(i).getSumI(), outputPoints.get(i).getCharge()));
				
				if (outputPoints.get(i).getoutKey2() != -1){
					out.collect(new Tuple8<Integer, Double, Integer,Double,Integer,Integer,Double,Integer>
			           (outputPoints.get(i).getoutKey2(), outputPoints.get(i).getWpm(),Integer.parseInt(scLevel), Double.parseDouble(RT),
			       	   outputPoints.get(i).getCurveID(),
			        	   Integer.parseInt(scNumber), outputPoints.get(i).getSumI(), outputPoints.get(i).getCharge()));
				}
				
				/*
				out.collect(new Tuple2<IntWritable, Text>
		           (new IntWritable(outputPoints.get(i).getoutKey()), 
		        		   new Text(Double.toString(outputPoints.get(i).getWpm()) + '\t' + 
		        		   scLevel + '\t' +
		        		   RT + '\t' +
		        		   outputPoints.get(i).getCurveID() + '\t' +
		        		   scNumber + '\t' + 
		        		   outputPoints.get(i).getSumI() + '\t' + 
		        		   Integer.toString(outputPoints.get(i).getCharge()))));
		        		   */
			}
			
		}
		
	}
			
}

