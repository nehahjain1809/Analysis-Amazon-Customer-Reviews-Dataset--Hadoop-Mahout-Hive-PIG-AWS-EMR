package bloomfilter;
import java.util.ArrayList;
import java.util.List;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.filecache.DistributedCache;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Counters;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.input.TextInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.mapreduce.lib.output.TextOutputFormat;
import org.apache.hadoop.util.GenericOptionsParser;

@SuppressWarnings("deprecation")
public class StopWordSkipper {
	
	public enum COUNTERS {
		  STOPWORDS,
		  GOODWORDS
		 }
	public static void main(String[] args) throws Exception {
		
		Configuration conf = new Configuration();

		Job job = new Job(conf, "StopWordSkipper");
		job.setOutputKeyClass(Text.class);
		job.setOutputValueClass(Text.class);
		job.setJarByClass(StopWordSkipper.class);
		job.setMapperClass(SkipMapper.class);
		job.setReducerClass(SkipReducer.class);
		job.setNumReduceTasks(1);
		job.setInputFormatClass(TextInputFormat.class);
		job.setOutputFormatClass(TextOutputFormat.class);


		FileInputFormat.setInputPaths(job, new Path(args[0]));
		FileOutputFormat.setOutputPath(job, new Path(args[1]));
		job.waitForCompletion(true);
		Counters counters = job.getCounters();
		System.out.printf("Good Words: %d, Stop Words: %d\n",
			      counters.findCounter(COUNTERS.GOODWORDS).getValue(),
			      counters.findCounter(COUNTERS.STOPWORDS).getValue());
			 }
		}
	