package top10;


import java.io.IOException;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

public class MyReducer extends Reducer<LongWritable, IntWritable, LongWritable, IntWritable> {
 private IntWritable result = new IntWritable();
 @Override
 protected void reduce(LongWritable key, Iterable<IntWritable> values, Context context) throws
IOException, InterruptedException {

		 int count = 0;
		 for(IntWritable val : values){
				 count += val.get();
			 }
		 result.set(count);
		 context.write(key, result);
		 }
}