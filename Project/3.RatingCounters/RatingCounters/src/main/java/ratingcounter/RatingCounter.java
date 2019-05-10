package ratingcounter;



import java.io.IOException;
import java.util.Arrays;
import java.util.HashSet;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Counter;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;


public class RatingCounter {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException, InterruptedException, ClassNotFoundException {
        // TODO code application logic here
        
        Configuration conf = new Configuration();
        Job job = Job.getInstance(conf, "Counting With Counters");
        job.setJarByClass(RatingCounter.class);
        job.setMapperClass(CounterMapper.class);
        job.setMapOutputKeyClass(Text.class);
        job.setMapOutputValueClass(Text.class);
        
        FileInputFormat.addInputPath(job, new Path(args[0]));
        FileOutputFormat.setOutputPath(job, new Path(args[1]));
        
        Path out = new Path(args[1]);
        FileSystem.get(conf).delete(out, true);

        int code = job.waitForCompletion(true) ? 0 : 1;
        
       if(code==0){
            for(Counter counter : job.getCounters().getGroup(CounterMapper.STATE_COUNTER_GROUP)){
                {
                     System.out.println("Number of Products with Rating "+counter.getDisplayName()+" = "+counter.getValue()+" products.");
                }
            }
        }
        System.exit(code);
    }
    
      public static class CounterMapper extends Mapper<LongWritable, Text, Text, Text>{
        
        public static final String STATE_COUNTER_GROUP = "State";
        public static final String UNKNOWN_COUNTER = "Unknown";
        public static final String NULL_OR_EMPTY_COUNTER = "Null or empty";
        
        private String[] statesArray = new String[] {"1", "2", "3", "4", "5"};
        
        private HashSet<String> states = new HashSet<String>(Arrays.asList(statesArray));
        
        public void map (LongWritable key, Text value, Context context) throws IOException, InterruptedException{
            
            if(key.get()==0){
                return;
            }
            
            
            String[] token = value.toString().split("\t");
//            System.out.println(value.toString());
            String stateName = token[7].trim();
            
            if(stateName != null && !stateName.isEmpty()){
                String[] stateTokens = stateName.toUpperCase().split("\\s");
                
                boolean unknown = true;
                for(String state: stateTokens){
                    if(state.contains(state)){
                        
                        context.getCounter(STATE_COUNTER_GROUP, state).increment(1);
                        unknown = false;
                        break;
                    }
                }
            
            if(unknown){
                context.getCounter(STATE_COUNTER_GROUP, UNKNOWN_COUNTER).increment(1);
            }
            } else{
                context.getCounter(STATE_COUNTER_GROUP, NULL_OR_EMPTY_COUNTER).increment(1);
            }
        }
    }    
}