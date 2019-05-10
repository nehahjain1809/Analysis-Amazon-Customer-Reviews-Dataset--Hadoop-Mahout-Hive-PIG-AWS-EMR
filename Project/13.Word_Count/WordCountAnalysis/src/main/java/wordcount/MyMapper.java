package wordcount;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

public class MyMapper extends Mapper<Object,Text,Text,WordFrequency>{
	
	

	 @Override
	 protected void map(Object key, Text value, Context context) throws IOException,
	InterruptedException {

//	   List<WordFrequency> wordList = new ArrayList<WordFrequency>();
	   
	   String values[] = value.toString().split("\t");
	   if(values.length == 2) {
	   String product = values[0].toString();	  
	   String review = values[1].toString();

       Text prod = new Text();
       prod.set(product);
       
       
	   	for(String str : review.split(" ")) {
	   		WordFrequency wf = new WordFrequency(1, str);
//	   		wordList.add(wf);
//	   		System.out.println(wf);
	   		context.write(prod,wf);
	   		
	   	}
	   }
	   	
	   	
	   	
	 }

}

