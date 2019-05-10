package top10;

import java.io.IOException;
import java.util.TreeMap;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

public class MyMapper2 extends Mapper<Object,Text,NullWritable,Text>{
 private TreeMap<Integer, Text> sortedCustomerList = new TreeMap<Integer,Text>();

 @Override
 protected void map(Object key, Text value, Context context) throws IOException,
InterruptedException {

 System.out.println(value);
 String line = value.toString();
 String[] lines = line.split("\t");

 String customer = lines[0];
 int count = Integer.parseInt(lines[1]);

 System.out.println(count + " " + customer);
 sortedCustomerList.put(count,new Text(customer));

 if(sortedCustomerList.size() > 10){
	 sortedCustomerList.remove(sortedCustomerList.firstKey());
 }
 
// context.write(NullWritable.get(), sortedCustomerList);
 }
 @Override
 protected void cleanup(Context context) throws IOException, InterruptedException {
	 for(Text t : sortedCustomerList.values()){
		 context.write(NullWritable.get(),t);
	 }
 }




}