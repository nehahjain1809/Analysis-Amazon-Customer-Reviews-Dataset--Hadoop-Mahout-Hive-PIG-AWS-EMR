package top10;

import java.io.IOException;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

public class MyMapper extends Mapper<Object, Text, LongWritable, IntWritable> {
 private LongWritable outCustomerId = new LongWritable();
 @Override
 protected void map(Object key, Text value, Context context) throws IOException,
InterruptedException {
	 String[] values = value.toString().split("\t");	
		if(values[5] != null && values[7] != null && values[1] != null && !values[0].equals("marketplace")) {
			long data = Long.parseLong(values[1]);	
			outCustomerId.set(data);
			context.write(outCustomerId, new IntWritable(1));
			
		}

 }
}

