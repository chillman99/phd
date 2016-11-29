package phdmapreduce;

import java.nio.ByteBuffer;
import phd2dcore.*;
import java.util.List;

import org.apache.cassandra.dht.Murmur3Partitioner;
import org.apache.cassandra.hadoop.ColumnFamilyInputFormat;
import org.apache.cassandra.hadoop.ColumnFamilyOutputFormat;
import org.apache.cassandra.hadoop.ConfigHelper;
import org.apache.cassandra.hadoop.cql3.CqlConfigHelper;
import org.apache.cassandra.thrift.SlicePredicate;
import org.apache.cassandra.thrift.SliceRange;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.conf.Configured;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.client.Scan;
import org.apache.hadoop.hbase.mapreduce.TableMapReduceUtil;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.input.TextInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.mapreduce.lib.output.TextOutputFormat;
import org.apache.hadoop.util.Tool;
import org.apache.hadoop.util.ToolRunner;

public class PeakPickMR extends Configured
  implements Tool
{
  //private static final String PRIMARY_KEY = "row_key";
  static final String KEYSPACE = "wordcount";
  static final String OUTPUT_COLUMN_FAMILY = "output_words";
  //private static final String CONF_COLUMN_NAME = "columnname";

  @SuppressWarnings({ "deprecation", "unused" })
public int run(String[] args)
    throws Exception
  {
	
    if (args[0].equals("hdfsmap"))
    {
      Job job = new Job(getConf(), "peakpick");
      job.setJarByClass(PeakPickMR.class);
      job.setNumReduceTasks(0);
      job.setOutputKeyClass(IntWritable.class);
      job.setOutputValueClass(Text.class);
      job.setMapperClass(MapHDFS.class);
      job.setInputFormatClass(TextInputFormat.class);
      job.setOutputFormatClass(TextOutputFormat.class);
      job.setPartitionerClass(MZPartitioner.class);
      FileInputFormat.setInputPaths(job, new Path[] { new Path(args[1]) });
      FileOutputFormat.setOutputPath(job, new Path(args[2]));

      job.waitForCompletion(true);
      return job.isSuccessful() ? 0 : 1;
    }
    
    if (args[0].equals("hdfs"))
    {
      Job job = new Job(getConf(), "peakpick");
      job.setJarByClass(PeakPickMR.class); 
      //long milliSeconds = 1000*60*60; 
      //job.setLong("mapred.task.timeout", milliSeconds);
      
      //job.setNumReduceTasks(2);
      //job.setNumReduceTasks(52);
      job.setNumReduceTasks(42);
      job.setOutputKeyClass(IntWritable.class);
      job.setOutputValueClass(Text.class);
      job.setMapperClass(MapHDFS.class);
      job.setReducerClass(ReduceHDFS.class);
      job.setInputFormatClass(TextInputFormat.class);
      job.setOutputFormatClass(TextOutputFormat.class);
      job.setPartitionerClass(MZPartitioner.class);
      FileInputFormat.setInputPaths(job, new Path[] { new Path(args[1]) });
      FileOutputFormat.setOutputPath(job, new Path(args[2]));
      
      job.waitForCompletion(true);
      return job.isSuccessful() ? 0 : 1;
    }

    if (args[0].equals("cassandra"))
    {
      Job job = new Job(getConf(), "peakpick");
      job.setJarByClass(getClass());
      job.setNumReduceTasks(42);

      job.setMapOutputKeyClass(Text.class);
      job.setMapOutputValueClass(IntWritable.class);

      job.setMapperClass(MapCassandra.class);
      job.setInputFormatClass(ColumnFamilyInputFormat.class);
      ConfigHelper.setInputRpcPort(job.getConfiguration(), "9160");
      ConfigHelper.setInputInitialAddress(job.getConfiguration(), args[1]);
      ConfigHelper.setInputPartitioner(job.getConfiguration(), Murmur3Partitioner.class.getName());
      ConfigHelper.setInputColumnFamily(job.getConfiguration(), "phd", "scandata");
      CqlConfigHelper.setInputCQLPageRowSize(job.getConfiguration(), "3");

      SlicePredicate predicate = new SlicePredicate();
      SliceRange sliceRange = new SliceRange();
      sliceRange.setStart(new byte[0]);
      sliceRange.setFinish(new byte[0]);
      predicate.setSlice_range(sliceRange);
      ConfigHelper.setInputSlicePredicate(job.getConfiguration(), predicate);

      job.setReducerClass(ReduceCassandra.class);
      job.setOutputKeyClass(ByteBuffer.class);
      job.setOutputValueClass(List.class);
      job.setOutputFormatClass(ColumnFamilyOutputFormat.class);

      ConfigHelper.setOutputColumnFamily(job.getConfiguration(), "wordcount", "output_words");
      ConfigHelper.setOutputPartitioner(job.getConfiguration(), "Murmur3Partitioner");
      ConfigHelper.setOutputInitialAddress(job.getConfiguration(), "192.168.100.10");
      ConfigHelper.setOutputRpcPort(job.getConfiguration(), "9160");
      job.getConfiguration().set("columnname", "sum");

      job.setNumReduceTasks(42);

      FileOutputFormat.setOutputPath(job, new Path(args[2]));

      job.waitForCompletion(true);
      return job.isSuccessful() ? 0 : 1;
    }

    if (args[0].equals("cassandramap"))
    {
      Job job = new Job(getConf(), "peakpick");
      job.setJarByClass(getClass());
      job.setNumReduceTasks(104);

      job.setMapOutputKeyClass(Text.class);
      job.setMapOutputValueClass(IntWritable.class);

      job.setMapperClass(MapCassandra.class);
      job.setInputFormatClass(ColumnFamilyInputFormat.class);
      ConfigHelper.setInputRpcPort(job.getConfiguration(), "9160");
      ConfigHelper.setInputInitialAddress(job.getConfiguration(), args[1]);
      ConfigHelper.setInputPartitioner(job.getConfiguration(), Murmur3Partitioner.class.getName());
      ConfigHelper.setInputColumnFamily(job.getConfiguration(), "phd", "scandata");
      CqlConfigHelper.setInputCQLPageRowSize(job.getConfiguration(), "3");

      SlicePredicate predicate = new SlicePredicate();
      SliceRange sliceRange = new SliceRange();
      sliceRange.setStart(new byte[0]);
      sliceRange.setFinish(new byte[0]);
      predicate.setSlice_range(sliceRange);
      ConfigHelper.setInputSlicePredicate(job.getConfiguration(), predicate);
      job.setNumReduceTasks(0);
      /*
      job.setReducerClass(ReduceHDFS.class);
      job.setOutputFormatClass(TextOutputFormat.class);
      job.setOutputKeyClass(IntWritable.class);
      job.setOutputValueClass(Text.class);
      job.setNumReduceTasks(104);
      */
      FileOutputFormat.setOutputPath(job, new Path(args[2]));

      job.waitForCompletion(true);
      return job.isSuccessful() ? 0 : 1;
    }
    
    if (args[0].equals("hbase")) {
      Configuration config = HBaseConfiguration.create();

      Job job = new Job(config);
      job.setJarByClass(getClass());
      job.setJobName("peakPickMap");
      config.set("hbase.zookeeper.quorum", args[1]);
      Scan scan = new Scan();
      scan.setCaching(500);
      scan.setCacheBlocks(false);

      String tableName = "scandata";
      String targetTable = "peakout";

      TableMapReduceUtil.initTableMapperJob(
        tableName, 
        scan, 
        MapHbase.class, 
        IntWritable.class, 
        Text.class, 
        job);

      job.setNumReduceTasks(42);
      TableMapReduceUtil.initTableReducerJob(
        targetTable, 
        ReduceHbase.class, 
        job);

      job.waitForCompletion(true);
      return job.isSuccessful() ? 0 : 1;
    }
    
    if (args[0].equals("hbasemap")) {
      Configuration config = HBaseConfiguration.create();
      config.set("hbase.zookeeper.quorum", args[1]);
      
      Job job = new Job(config);
      job.setJarByClass(getClass());
      job.setJobName("peakPickMap");
      Scan scan = new Scan();
      scan.setCaching(500);
      scan.setCacheBlocks(false);

      String tableName = "scandata";
      String targetTable = "peakout";

      TableMapReduceUtil.initTableMapperJob(
        tableName, 
        scan, 
        MapHbase.class, 
        IntWritable.class, 
        Text.class, 
        job);
      
      /*
      job.setReducerClass(ReduceHDFS.class);
      job.setOutputFormatClass(TextOutputFormat.class);
      job.setOutputKeyClass(IntWritable.class);
      job.setOutputValueClass(Text.class);
      */
      
      job.setNumReduceTasks(0);
      FileOutputFormat.setOutputPath(job, new Path(args[2]));
      job.waitForCompletion(true);
      return job.isSuccessful() ? 0 : 1;
    }

    return 0;
  }

  public static void main(String[] args) throws Exception
  {
    int res = ToolRunner.run(new Configuration(), new PeakPickMR(), args);
    System.exit(res);
  }
}