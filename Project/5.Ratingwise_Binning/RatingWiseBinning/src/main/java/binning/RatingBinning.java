package binning;


import java.io.IOException;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.lib.input.TextInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.mapreduce.lib.output.MultipleOutputs;
import org.apache.hadoop.mapreduce.lib.output.TextOutputFormat;


public class RatingBinning {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args)throws IOException, InterruptedException, ClassNotFoundException {
        // TODO code application logic here
        Configuration conf = new Configuration();
        Job job = Job.getInstance(conf, "Binning Rating");
        job.setJarByClass(RatingBinning.class);
           
        //Setting Mapper Class and the output key and value
        job.setMapperClass(BinningMapper.class);
        job.setMapOutputKeyClass(Text.class);
        job.setMapOutputValueClass(NullWritable.class);
           
        //No combiner, partitioner or reducer is used in this pattern!
        job.setNumReduceTasks(0);
        
        TextInputFormat.addInputPath(job, new Path(args[0]));
        FileOutputFormat.setOutputPath(job, new Path(args[1]));
            
        MultipleOutputs.addNamedOutput(job, "bins", TextOutputFormat.class, Text.class, NullWritable.class);
        MultipleOutputs.setCountersEnabled(job, true);
        
        System.exit(job.waitForCompletion(true) ? 0 : 1);
    }
    
    public static class BinningMapper extends Mapper<LongWritable, Text, Text, NullWritable>{
    
    private MultipleOutputs<Text, NullWritable> mos = null;
    
    protected void setup(Context context){
        mos = new MultipleOutputs(context);
        
    }
    
    protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException{
        
           if(key.get()==0){
                return;
            }
                 String values[] = value.toString().split("\t");
                 
//                 System.out.println(value.toString() +   " " + values.length + " " +  values[0]+  " " + values[2] + " " + values[3]);
                 
                 String product = values[0].toString().split("=")[1];
                 String ratedby = values[2].toString().split("=")[1];
                 String rating = values[3].toString().split("=")[1];
//                        Text value = new Text();
//                        value.set(product);
//                System.out.println(rating + " " +  rating.equals("1.0"));
                        
                        if(rating.equals("1.0")){
                            mos.write("bins", value, NullWritable.get(), "1.0");
                        }
                         if(rating.equals("1.5")){
                            mos.write("bins", value, NullWritable.get(), "1.5");
                        }
                          if(rating.equals("2.0")){
                            mos.write("bins", value, NullWritable.get(), "2.0");
                        }
                           if(rating.equals("2.5")){
                            mos.write("bins", value, NullWritable.get(), "2.5");
                        }
                           if(rating.equals("3.0")){
                            mos.write("bins", value, NullWritable.get(), "3.0");
                        }
                           if(rating.equals("3.5")){
                               mos.write("bins", value, NullWritable.get(), "3.5");
                           }
                           if(rating.equals("4.0")){
                               mos.write("bins", value, NullWritable.get(), "4.0");
                           }
                           if(rating.equals("4.5")){
                               mos.write("bins", value, NullWritable.get(), "4.5");
                           }
                           if(rating.equals("5.0")){
                               mos.write("bins", value, NullWritable.get(), "5.0");
                           }
                           else {
                        	   mos.write("bins", value, NullWritable.get(), "other");
                           }
                           
                          
               
    }         
                    protected void cleanup(Context context) throws IOException, InterruptedException{
                        mos.close();
                    }
        
    }
    
}
