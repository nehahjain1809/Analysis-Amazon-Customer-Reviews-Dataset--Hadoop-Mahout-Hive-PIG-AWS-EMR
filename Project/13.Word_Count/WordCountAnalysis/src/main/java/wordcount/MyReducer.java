package wordcount;


import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

public class MyReducer extends Reducer<Text, WordFrequency, Text,WordFrequency>{
	
	 @Override
	 protected void reduce(Text key, Iterable<WordFrequency> values, Context context) throws
	IOException, InterruptedException {
	
	  Map<String, Integer> frequencyMap = new TreeMap<String,Integer>();
 
	     
	    	 for(WordFrequency wf : values) {
	    		if(!frequencyMap.containsKey(wf.getWord())) {
	    		 frequencyMap.put(wf.getWord(), wf.getFrequency());
	    		}
	    		else {
	    			 int freq = frequencyMap.get(wf.getWord());
	    			 frequencyMap.put(wf.getWord(), freq + 1);
	    		}
	    	 }
	     
//	 System.out.println(frequencyMap.size());
	//     List<WordFrequency> frequencyList = new ArrayList<WordFrequency>();
	     
	     for(Entry<String, Integer> entry : frequencyMap.entrySet()) {
	    	 
//	    	 System.out.println(entry.getKey() + " " +entry.getValue());
	    	 WordFrequency wf1 = new WordFrequency();
	    	 wf1.setFrequency(entry.getValue());
	    	 wf1.setWord(entry.getKey());
	//    	 frequencyList.add(wf1);
	    	 context.write(key, wf1);
	    	
		 }
     
     
		 
	 }
		
}