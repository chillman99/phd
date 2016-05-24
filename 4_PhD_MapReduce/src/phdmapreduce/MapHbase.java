package phdmapreduce;

import java.io.*;
import java.util.ArrayList;

import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.io.ImmutableBytesWritable;
import org.apache.hadoop.hbase.mapreduce.TableMapper;
import org.apache.hadoop.hbase.util.Bytes;
import org.apache.hadoop.io.*;
import org.apache.hadoop.mapred.*;

import phd2dcore.PointWeighted;
import phd2dcore.Pp2dProcess;

public class MapHbase extends TableMapper<IntWritable, Text> {

	public static String inputFile = new String();
	public void configure (JobConf job) {
		  inputFile = job.get("map.input.file");
	}
	
	public void map(ImmutableBytesWritable row, Result value, Context context) throws IOException {
		String scNumber = new String();
		String scLevel = new String();
		String mzString = new String();
		String intensityString = new String();	
		String RT = "0";
	    String mzStringOut = new String();
	    Text peakOut = new Text();		//Map Task Output
	    
	    //Parse input Row from HBase
	    scNumber = new String(value.getValue(Bytes.toBytes("meta"), Bytes.toBytes("scan")));
	    scLevel = new String(value.getValue(Bytes.toBytes("meta"), Bytes.toBytes("mslvl")));
	    RT = new String(value.getValue(Bytes.toBytes("meta"), Bytes.toBytes("rettime")));
	    mzString = new String(value.getValue(Bytes.toBytes("array"), Bytes.toBytes("mz")));
		intensityString  = new String(value.getValue(Bytes.toBytes("array"), Bytes.toBytes("intensity")));
	
		//Populate outputPoints with the isotopic centroided peaks
		ArrayList<PointWeighted> outputPoints = Pp2dProcess.process(scNumber, scLevel, mzString, intensityString, RT);
		
		if (outputPoints.size()>0) {
			//Write out to HDFS
			for (int i=0; i<outputPoints.size(); i++){
				mzStringOut = scNumber + "\t" + scLevel + "\t" + RT  + "\t" + 
			    		Integer.toString(outputPoints.get(i).getCurveID()) + "\t"  +									
			    		Double.toString(outputPoints.get(i).getWpm()) + "\t"  +
			    		Double.toString(outputPoints.get(i).getSumI()) + "\t"  +						
			    		Integer.toString(outputPoints.get(i).getCharge());
			    		//removed inputfile outputPoints ouput for performance
			    		//+ "\t" + inputFile;	
				peakOut.set(new Text(mzStringOut));						    		
				try {
					context.write(new IntWritable(outputPoints.get(i).getoutKey()), peakOut);
				} catch (InterruptedException e) {
					System.out.println("Error Writing Mapper Output");
					e.printStackTrace();
				}
				
			}
			
		}
			//move to next scan 
	}
			
}

