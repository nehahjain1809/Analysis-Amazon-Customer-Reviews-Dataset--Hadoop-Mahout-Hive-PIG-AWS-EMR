package top10;

import java.io.IOException;
import java.util.Iterator;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TreeMap;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

public class MyReducer2 extends Reducer<NullWritable, TreeMap<Integer, Text>, NullWritable, Text>{
 private TreeMap<Integer,Text> sortedCustomerList = new TreeMap<Integer,Text>();
 @Override
 protected void reduce(NullWritable key, Iterable<TreeMap<Integer, Text>> values, Context context) throws
IOException, InterruptedException {

	 Iterator itr = values.iterator();
	 while(itr.hasNext()) {
		 context.write(NullWritable.get(), new Text(itr.next().toString()));
	 }

 }
 }