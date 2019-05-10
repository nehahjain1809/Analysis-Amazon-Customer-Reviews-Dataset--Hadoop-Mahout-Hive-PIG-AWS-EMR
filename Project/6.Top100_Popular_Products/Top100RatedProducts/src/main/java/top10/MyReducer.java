package top10;


import java.io.IOException;
import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;


import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;
import org.mortbay.jetty.servlet.PathMap.Entry;

public class MyReducer extends Reducer<NullWritable, Text, NullWritable, Text>{
	 private TreeMap<Integer,Text> sortedNeighborhoodList = new TreeMap<Integer,Text>();
	 @Override
	 protected void reduce(NullWritable key, Iterable<Text> values, Context context) throws
	IOException, InterruptedException {
	 for(Text value : values){
		 String valuestr[] = value.toString().split("\t");
		   String product = valuestr[0].toString().split("=")[1];
		   String ratedby = valuestr[2].toString().split("=")[1];
		   String rating = valuestr[3].toString().split("=")[1];
		 int count = Integer.parseInt(ratedby);
		 sortedNeighborhoodList.put(count, new Text(value));

	 if(sortedNeighborhoodList.size() > 100){
	 sortedNeighborhoodList.remove(sortedNeighborhoodList.firstKey());
	 }
	 }

	 for(Text t : sortedNeighborhoodList.descendingMap().values()){
	 context.write(NullWritable.get(),t);
	 }
	 }
 
		 	
		
}