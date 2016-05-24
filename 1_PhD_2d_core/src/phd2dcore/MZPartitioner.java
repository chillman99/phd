package phd2dcore;

import org.apache.hadoop.io.*;
import org.apache.hadoop.mapreduce.Partitioner;

public class MZPartitioner extends Partitioner<IntWritable,Text> {
	@Override
	public int getPartition(IntWritable key, Text value, int numPartitions) {
		int outkey = key.get();
		return outkey;
	}
 
}