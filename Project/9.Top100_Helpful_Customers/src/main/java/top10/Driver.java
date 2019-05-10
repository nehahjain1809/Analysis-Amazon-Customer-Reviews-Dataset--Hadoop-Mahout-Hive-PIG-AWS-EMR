package top10;


import java.io.IOException;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.input.MultipleInputs;
import org.apache.hadoop.mapreduce.lib.input.TextInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.mapreduce.lib.output.TextOutputFormat;

public class Driver {
 /**
 * @param args the command line arguments
 */
 public static void main(String[] args) throws IOException, InterruptedException,
ClassNotFoundException {
 // TODO code application logic here
	 Configuration conf=new Configuration();
	 Job job = Job.getInstance(conf, "chaining");
	 job.setJarByClass(Driver.class);
	 job.setMapperClass(MyMapper.class);
	 job.setMapOutputKeyClass(LongWritable.class);
	 job.setMapOutputValueClass(IntWritable.class);
	 job.setCombinerClass(MyReducer.class);
	 job.setReducerClass(MyReducer.class);
	 job.setOutputKeyClass(LongWritable.class);
	 job.setOutputValueClass(IntWritable.class);
	
	 FileInputFormat.addInputPath(job, new Path(args[0]));
	 FileOutputFormat.setOutputPath(job,new Path(args[1]));
	 boolean complete = job.waitForCompletion(true);


//	 Configuration conf2 = new Configuration();
//	 Job job2 = Job.getInstance(conf, "chaining");
//	 FileInputFormat.addInputPath(job2, new Path(args[1]));
//	 FileOutputFormat.setOutputPath(job2, new Path(args[2]));
//	 System.exit(job2.waitForCompletion(true)?0:1);
 }
 
}