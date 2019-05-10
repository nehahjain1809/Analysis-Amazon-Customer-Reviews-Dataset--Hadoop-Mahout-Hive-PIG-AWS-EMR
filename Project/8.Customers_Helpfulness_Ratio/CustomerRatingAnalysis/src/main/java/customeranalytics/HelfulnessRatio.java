package customeranalytics;

import java.io.IOException;
import java.text.DecimalFormat;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.FloatWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.input.TextInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

public class HelfulnessRatio {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException, InterruptedException, ClassNotFoundException {
        
        
        Configuration conf = new Configuration();
        Job job = Job.getInstance(conf,"ProductId and Ratings");
        
        job.setJarByClass(HelfulnessRatio.class);
        job.setMapperClass(FoodMapper.class);
        job.setMapOutputKeyClass(Text.class);
        job.setMapOutputValueClass(FloatWritable.class);
        job.setReducerClass(FoodReducer.class);
        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(FloatWritable.class);

        FileInputFormat.addInputPath(job, new Path(args[0]));
        FileOutputFormat.setOutputPath(job, new Path(args[1]));
        
        System.exit(job.waitForCompletion(true) ? 0 : 1);
      
    }
    
    public static class FoodMapper extends Mapper<LongWritable, Text, Text, FloatWritable>{
        
        private Text text = new Text();
        private FloatWritable score = new FloatWritable();
        
        @Override
        protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException{
            
            if(key.get()==0){
            return;
            }
            
            else{
            
//            String[] line = value.toString().split(",");
//            String productId = line[1].trim();
//            int helpnum = Integer.parseInt(line[4].trim());
//            int helpden = Integer.parseInt(line[5].trim());
//            float ratio;
            	String[] values = value.toString().split("\t");
            	String customerId = values[1];
            	 int helpnum = Integer.parseInt(values[8].toString());
            	 int helpden = Integer.parseInt(values[9].toString());
            	 float ratio;
            			 
            	
            if(helpden!=0){
            ratio = helpnum/helpden;
            }
            else {
            ratio=(float) 0.0;
            }
            
            text.set(customerId);
            score.set(ratio);
            
            context.write(text, score);
            
            }
        }
    }
    
    public static class FoodReducer extends Reducer<Text, FloatWritable, FloatWritable, Text >{
        
        private FloatWritable result = new FloatWritable();
        
        @Override
        protected void reduce(Text key, Iterable<FloatWritable> values, Context context) throws IOException, InterruptedException{
           
            float sum = 0;
            int count = 0;
            for(FloatWritable val:values){
                sum+=val.get();
                count = count+1;
            }
            
            float average = (sum/count)*100;
            
            DecimalFormat df = new DecimalFormat();
            df.setMaximumFractionDigits(2);
            float avg = Float.parseFloat(df.format(average));
            
            key.set("%"+"               "+key);
            result.set(avg);
            context.write(result, key);
        }
    }
}