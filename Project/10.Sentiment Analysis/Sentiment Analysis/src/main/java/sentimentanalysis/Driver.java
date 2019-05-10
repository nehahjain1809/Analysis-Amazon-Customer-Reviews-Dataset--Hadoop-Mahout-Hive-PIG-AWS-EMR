package sentimentanalysis;

import java.net.URI;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.conf.Configured;
import org.apache.hadoop.filecache.DistributedCache;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.input.TextInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.mapreduce.lib.output.TextOutputFormat;
import org.apache.hadoop.util.ToolRunner;
import org.apache.hadoop.util.Tool;

public class Driver extends Configured implements Tool{
	public static void main(String[] args) throws Exception {
		ToolRunner.run(new Driver(), args);
	}
	
	public int run(String[] args) throws Exception{
		// Create configuration
		Configuration conf = new Configuration(true);
		//Creating Distributed Cache
		DistributedCache.addCacheFile(new URI("/usr/local/bin/hadoop-2.7.3/logs/Dataset/SentimentAnalysis/inputlib/Affin.txt"),conf);

		// Create job & Submitting job
		Job job = new Job(conf, "SentimentAnalysis");
		job.setJarByClass(Driver.class);

		// Setup MapReduce
		job.setMapperClass(Mapper1.class);
		job.setReducerClass(Reducer1.class);

		// Specify key / value
		job.setOutputKeyClass(Text.class);
		job.setOutputValueClass(IntWritable.class);

		// Input
		FileInputFormat.addInputPath(job, new Path(args[0]));
		job.setInputFormatClass(TextInputFormat.class);

		// Output
		FileOutputFormat.setOutputPath(job, new Path(args[1]));
		job.setOutputFormatClass(TextOutputFormat.class);

		//Second JOB
		boolean complete = job.waitForCompletion(true);

//		Configuration conf2 = new Configuration();
//		Job job2 = Job.getInstance(conf2, "chaining");
//
//		//For Mapper2 and Reducer2, second job
//		if (complete) {
//				job2.setJarByClass(Driver.class);
//				
//				job2.setMapperClass(Mapper2.class);
//				job2.setMapOutputKeyClass(Text.class);
//				job2.setMapOutputValueClass(IntWritable.class);
//				
//				job2.setReducerClass(Reducer2.class);
//				job2.setOutputKeyClass(Text.class);
//				job2.setOutputValueClass(IntWritable.class);
//				
//
//				FileInputFormat.addInputPath(job2, new Path(args[1]));
//				FileOutputFormat.setOutputPath(job2, new Path(args[2]));
//
//				System.exit(job2.waitForCompletion(true) ? 0 : 1);
//				}
			return 0;
}

}