package phdmapreduce;

import java.io.*;
import java.nio.ByteBuffer;
import java.util.*;

import org.apache.cassandra.db.Cell;
import org.apache.cassandra.utils.ByteBufferUtil;
import org.apache.hadoop.io.*;
import org.apache.hadoop.mapreduce.*;
import org.apache.hadoop.mapreduce.Mapper.Context;

import phd2dcore.PointWeighted;
import phd2dcore.Pp2dProcess;


//public class MapCassandra extends Mapper<ByteBuffer, SortedMap<ByteBuffer, Cell>, IntWritable, Text> {
@SuppressWarnings("unused")
public class MapCassandra extends Mapper<ByteBuffer, SortedMap<ByteBuffer, Cell>, Text, IntWritable> {
	
	public void map(ByteBuffer key, SortedMap<ByteBuffer, Cell> columns, Context context) throws IOException, InterruptedException {
		Text peakOut = new Text();		//Map Task Output
		String scNumber = "1";
		String fileName = "0";
	    String scLevel = "0";
	    String RT = "0";
	    String mzString = "0";
	    String intensityString = "0";
		
	    scNumber = String.valueOf(key.getInt());
		for (Cell cell : columns.values())
        {
            String name  = ByteBufferUtil.string(cell.name().toByteBuffer());
       
            if (name.contains("scan")) scNumber = String.valueOf(ByteBufferUtil.toInt(cell.value()));
            if (name.contains("filename")) fileName = ByteBufferUtil.string(cell.value());
            if (name.contains("mslvl")) scLevel = String.valueOf(ByteBufferUtil.toInt(cell.value()));
            if (name.contains("rettime")) RT = String.valueOf(ByteBufferUtil.toDouble(cell.value()));
            if (name.contains("mzarray")) mzString = ByteBufferUtil.string(cell.value());
            if (name.contains("intensityarray")) intensityString = ByteBufferUtil.string(cell.value()); 
        }

		//Populate outPoints with the istopic centroided peaks
		if (mzString != "0"){			

		ArrayList<PointWeighted> outputPoints = new ArrayList<PointWeighted>();
			outputPoints = Pp2dProcess.process(scNumber, scLevel, mzString, intensityString, RT);
			
			//Write out to HDFS
			if (outputPoints.size() > 0){
				for (int i=0; i<outputPoints.size(); i++){
					String mzStringOut = scNumber + "\t" + scLevel + "\t" + RT  + "\t" + 
				    		Integer.toString(outputPoints.get(i).getCurveID()) + "\t"  +									
				    		Double.toString(outputPoints.get(i).getWpm()) + "\t"  +
				    		Double.toString(outputPoints.get(i).getSumI()) + "\t"  +						
				    		Integer.toString(outputPoints.get(i).getCharge()) + "\t"  +
				    		fileName;	
					peakOut.set(new Text(mzStringOut));						    		
					try {										
						//context.write(new IntWritable(outputPoints.get(i).getoutKey()), peakOut);
						context.write(peakOut, new IntWritable(outputPoints.get(i).getoutKey()));
					} catch (InterruptedException e) {
						System.out.println();
						e.printStackTrace();
					}	
					
				}	
				
			}
			
		}
			//move to next scan 
    }	 
	
}

