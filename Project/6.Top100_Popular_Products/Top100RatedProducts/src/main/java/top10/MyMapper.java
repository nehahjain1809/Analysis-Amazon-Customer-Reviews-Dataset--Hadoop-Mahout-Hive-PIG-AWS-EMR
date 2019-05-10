package top10;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Mapper.Context;

public class MyMapper extends Mapper<Object,Text,NullWritable,Text>{
	
	 private TreeMap<Integer, Text> sortedBoroughList = new TreeMap<Integer,Text>();
	 @Override
	 protected void map(Object key, Text value, Context context) throws IOException,
	InterruptedException {


	   String values[] = value.toString().split("\t");
//	   System.out.println(value.toString() + " "+ values[0] + " "+ values[2] + " " + values[3]);
	   String product = values[0].toString().split("=")[1];
	   String ratedby = values[2].toString().split("=")[1];
	   String rating = values[3].toString().split("=")[1];

       int count = Integer.parseInt(ratedby);

		 sortedBoroughList.put(count,new Text(value));
	
		 if(sortedBoroughList.size() > 100){
			 sortedBoroughList.remove(sortedBoroughList.firstKey());
		 }
		 }
		 @Override
		 protected void cleanup(Context context) throws IOException, InterruptedException {
		 for(Text t : sortedBoroughList.values()){
			 context.write(NullWritable.get(),t);
		 }
		 }
}


