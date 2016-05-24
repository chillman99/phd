package phdmapreduce;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Arrays;
//import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.apache.cassandra.thrift.Column;
import org.apache.cassandra.thrift.ColumnOrSuperColumn;
import org.apache.cassandra.thrift.Mutation;
import org.apache.cassandra.utils.ByteBufferUtil;
import org.apache.hadoop.io.IntWritable;
//import org.apache.cassandra.utils.ByteBufferUtil;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.Reducer.Context;

@SuppressWarnings("unused")
public class ReduceCassandra extends Reducer<Text, IntWritable, ByteBuffer, List<Mutation>>
{
    private ByteBuffer outputKey;
    private static final String CONF_COLUMN_NAME = "columnname";
    protected void setup(@SuppressWarnings("rawtypes") org.apache.hadoop.mapreduce.Reducer.Context context)
    throws IOException, InterruptedException
    {
        outputKey = ByteBufferUtil.bytes(context.getConfiguration().get(CONF_COLUMN_NAME));
    }

    public void reduce(Text word, Iterable<IntWritable> values, Context context) throws IOException, InterruptedException
    {
        int sum = 0;
        for (IntWritable val : values)
            sum += val.get();
        context.write(outputKey, Collections.singletonList(getMutation(word, sum)));
    }

    private static Mutation getMutation(Text word, int sum)
    {
        org.apache.cassandra.thrift.Column c = new org.apache.cassandra.thrift.Column();
        c.setName(Arrays.copyOf(word.getBytes(), word.getLength()));
        c.setValue(ByteBufferUtil.bytes(sum));
        c.setTimestamp(System.currentTimeMillis());

        Mutation m = new Mutation();
        m.setColumn_or_supercolumn(new ColumnOrSuperColumn());
        m.column_or_supercolumn.setColumn(c);
        return m;
    }
    
}


/*
public class ReduceCassandra extends Reducer<IntWritable, Text, ByteBuffer, List<Mutation>> {
//public class ReduceCassandra extends Reducer<IntWritable, Text, Map<String, ByteBuffer>, List<ByteBuffer>> {
//public class ReduceCassandra extends Reducer<Text, Text, Map<String, ByteBuffer>, List<ByteBuffer>> {
//public class ReduceCassandra extends Reducer<IntWritable, Text, ByteBuffer, List<ByteBuffer>> {
//public class ReduceCassandra extends Reducer<IntWritable, Text,  Map<String, ByteBuffer>, List<ByteBuffer>> {
//public class ReduceCassandra extends Reducer<IntWritable, Text,  ByteBuffer, List<ByteBuffer>> {
		
	//private static final String CONF_COLUMN_NAME = "columnName";	
/*
    private Map<String, ByteBuffer> keys;
    //private ByteBuffer key;
    protected void setup(@SuppressWarnings("rawtypes") org.apache.hadoop.mapreduce.Reducer.Context context)
    throws IOException, InterruptedException
    {
        keys = new LinkedHashMap<String, ByteBuffer>();
    }

*/
//    private ByteBuffer outputKey;
/*
    protected void setup(@SuppressWarnings("rawtypes") org.apache.hadoop.mapreduce.Reducer.Context context)
    throws IOException, InterruptedException
    {
        outputKey = ByteBufferUtil.bytes(context.getConfiguration().get("columnname"));
    	//outputKey = ByteBufferUtil.bytes(4);
    }
 */
    
/*   
	public void reduce(IntWritable word, Iterable<Text> values, Context context) throws IOException, InterruptedException {	
	    ArrayList<PointMountain> mountainPoints = new ArrayList<PointMountain>();		    
	    //String outText = null;
	   	
	   	//Cache reducer values in an array for processing - must fit in memory, depends on number of reducers
		//Read all the weighted points into an in-memory object array
	   	for (Text line : values) {
   			String[] tempStr;	   
   		    tempStr = line.toString().split("\t");
	   		    mountainPoints.add (
		    		new PointMountain(	
    				Double.parseDouble(tempStr[4]),
					Double.parseDouble(tempStr[5]),
					Double.parseDouble(tempStr[5]),
					Double.parseDouble(tempStr[2]),
					Integer.parseInt(tempStr[0]),
					0,
					Integer.parseInt(tempStr[6]),
					-1,1,0,0));
	   	}
		    
		   	if (mountainPoints.size() > 5) {
			//Now we can sort on by Retention Time followed by WPM using a custom compare from MountainPoint   		   
		   	Collections.sort(mountainPoints, new MountainPointCompare());		  		   
		   	//Create Arraylist of 3D peaks
		   	//ArrayList<PointMountain> outputPoints = Reduce3DPeaks.ThreeDPeaks(mountainPoints);
		   	//Smooth Intensities of the 3D peaks
		   	//ReduceSmoothIntesity.smoothIntensity(outputPoints);	
			//Recalculate 3D peaks taking into account Overlapping Peaks		
		   	//ReduceOverlaps.calcOverlaps(outputPoints);   	
		   	//Create final 3D peaks and set Mountain ID
		   	//ArrayList<PointThreeD> threedDpoints = ReduceFinalPeaks.calcMountains(outputPoints);	  
			//ISO Peaks
		   	//ArrayList<PointISO> MonoISO = ReduceISOPeaks.calcISO(threedDpoints, outputPoints);	  
/*		   	
		   	Double t = (Math.random()*100);
            Integer sum = t.intValue();
            Text word1 = new Text();
            word1.set("plum"+Integer.toString(sum));
            
            
            byte[] keyBytes = word1.getBytes();
            outputKey = ByteBuffer.wrap(Arrays.copyOf(keyBytes, keyBytes.length));        
            
            context.write(outputKey, Collections.singletonList(getMutation(word1, sum)));
*/		   	
		   	//Write out Final Peaks
		   //for (int k = 0; k < MonoISO.size(); k++){
		   	
			   //outText = MonoISO.get(k).getCharge() + "\t" +
				//	   MonoISO.get(k).getWpm() + "\t" +
				//	   MonoISO.get(k).getSumI() + "\t" +
				//	   MonoISO.get(k).getWpRT();
		   	//outText = "Plum";
		   	/*
				try {
	
					word.set("Plum");
					//int sum = 100;
					
					List<Mutation> columnsToAdd = new ArrayList<Mutation>();
			        Integer wordCount = 9999;
			        
			        Column countCol = new Column(ByteBuffer.wrap("count_num".getBytes()));
			        countCol.setValue(intToByteArray(wordCount));
			        countCol.setTimestamp(new Date().getTime());

			        ColumnOrSuperColumn wordCosc = new ColumnOrSuperColumn();
			        wordCosc.setColumn(countCol);
			 
			        Mutation countMut = new Mutation();
			        countMut.column_or_supercolumn = wordCosc;
			 
			        columnsToAdd.add(countMut);
			        context.write(ByteBuffer.wrap(word.toString().getBytes()), columnsToAdd);
			        System.out.println(word);
					
					
		            //keys.put("word", ByteBufferUtil.bytes(word.toString()));
		            //context.write(keys, getBindVariables(word, sum));
		            //context.write(keys, getBindVariables(word, sum));

					//keys.put("word", ByteBufferUtil.bytes(word.toString()));
					//context.write(keys, getBindVariables(word, sum));

					//context.write(outputKey, Collections.singletonList(getMutation(word, sum)));
					
					//context.write(ByteBufferUtil.bytes("0"), getBindVariables("0", outText));
					
					
				} catch (InterruptedException e) {
					System.out.println("Error Writing Reducer Ouput");
					e.printStackTrace();
				}
				*/
		   	//}
/*           

	   	}
		   	

	} 
/*
    private byte[] intToByteArray (final Integer intValue) throws IOException {   
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        DataOutputStream dos = new DataOutputStream(bos);
        dos.writeInt(intValue);
        dos.flush();
        return bos.toByteArray();
    }
*/
/*
    private List<ByteBuffer> getBindVariables(Text word, int sum)
    {
        List<ByteBuffer> variables = new ArrayList<ByteBuffer>();
        variables.add(ByteBufferUtil.bytes(word.toString()));
        variables.add(ByteBufferUtil.bytes(String.valueOf(sum)));         
        return variables;
    }
    
    private static Mutation getMutation(Text word, int sum)
   {
       org.apache.cassandra.thrift.Column c = new org.apache.cassandra.thrift.Column();
       c.setName(Arrays.copyOf(word.getBytes(), word.getLength()));
       c.setValue(ByteBufferUtil.bytes(sum));
       c.setTimestamp(System.currentTimeMillis());

       Mutation m = new Mutation();
       m.setColumn_or_supercolumn(new ColumnOrSuperColumn());
       m.column_or_supercolumn.setColumn(c);
       return m;
   }
*/
	/*
	private static Mutation getMutation(Text word, int sum)
    {
        org.apache.cassandra.thrift.Column c = new org.apache.cassandra.thrift.Column();
        c.setName(Arrays.copyOf(word.getBytes(), word.getLength()));
        c.setValue(ByteBufferUtil.bytes(sum));
        c.setTimestamp(System.currentTimeMillis());

        Mutation m = new Mutation();
        m.setColumn_or_supercolumn(new ColumnOrSuperColumn());
        m.column_or_supercolumn.setColumn(c);
        return m;
    }
}
*/