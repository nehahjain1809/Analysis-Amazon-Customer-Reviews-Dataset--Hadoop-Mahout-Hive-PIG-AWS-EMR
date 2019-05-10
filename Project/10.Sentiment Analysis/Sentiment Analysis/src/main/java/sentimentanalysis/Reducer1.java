package sentimentanalysis;

import java.io.IOException;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

public class Reducer1 extends Reducer<Text, IntWritable, Text, Text>{
	@Override
	protected void reduce(Text key, Iterable<IntWritable> value, Context context) throws IOException, InterruptedException{
		Text sentiment = new Text();
		int valuee = 0;
		for(IntWritable val : value) {
			valuee = valuee + val.get();
		}
		if(valuee == 0)
		{
			sentiment.set("Neutral");
			context.write(key, sentiment);
		}
		if(valuee < 0)
		{
			sentiment.set("Negative");
			context.write(key, sentiment);
		}
		else {
			sentiment.set("Positive");
			context.write(key, sentiment);
		}
//		context.write(key, value);
		

	}

}