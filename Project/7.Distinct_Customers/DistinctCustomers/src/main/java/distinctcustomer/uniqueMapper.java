package distinctcustomer;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.text.ParseException;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.conf.*;
import org.apache.hadoop.io.*;
import org.apache.hadoop.mapreduce.*;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.input.TextInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.mapreduce.lib.output.TextOutputFormat;

public class uniqueMapper extends Mapper<Object, Text, Text, NullWritable>{
	private Text outCustomerId = new Text();

	public void map(Object key, Text value, Context context) throws IOException, InterruptedException{
		String[] values = value.toString().split("\t");	
		if(values[5] != null && values[7] != null && values[1] != null && !values[0].equals("marketplace")) {
			String data = values[1];	
			outCustomerId.set(data);
			context.write(outCustomerId, NullWritable.get());
			
		}

}

}