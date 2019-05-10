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

/**
 *
 * @author Tanisha_Jain
 */
public class CategoryBinning {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args)throws IOException, InterruptedException, ClassNotFoundException {
        // TODO code application logic here
        Configuration conf = new Configuration();
        Job job = Job.getInstance(conf, "Binning Hour");
        job.setJarByClass(CategoryBinning.class);
           
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
    
    protected void map(LongWritable key, Text valuee, Context context) throws IOException, InterruptedException{
        
           if(key.get()==0){
                return;
            }
                 String values[] = valuee.toString().split("\t");
                 String category = values[6].trim();
//                 String productId = values[3];
                        Text value = new Text();
                        value.set(category + "\t" +values[3]+ "\t" + values[7]);
                        if(category.equals("Wireless")){
                            mos.write("bins", value, NullWritable.get(), "Wirless");
                        }
                         if(category.equals("Watches")){
                            mos.write("bins", value, NullWritable.get(), "Watches");
                        }
                          if(category.equals("Video_Games")){
                            mos.write("bins", value, NullWritable.get(), "Video Games");
                        }
                           if(category.equals("Toys")){
                            mos.write("bins", value, NullWritable.get(), "Toys");
                        }
                           if(category.equals("Sports")){
                            mos.write("bins", value, NullWritable.get(), "Sports");
                        }
                           if(category.equals("Video_DVD")){
                               mos.write("bins", value, NullWritable.get(), "Video_DVD");
                           }
                           if(category.equals("Software")){
                               mos.write("bins", value, NullWritable.get(), "Software");
                           }
                           if(category.equals("Shoes")){
                               mos.write("bins", value, NullWritable.get(), "Shoes");
                           }
                           if(category.equals("Kitchen")){
                               mos.write("bins", value, NullWritable.get(), "Kitchen");
                           }
                           if(category.equals("Luggage")){
                               mos.write("bins", value, NullWritable.get(), "Luggage");
                           }
                           if(category.equals("Sports")){
                               mos.write("bins", value, NullWritable.get(), "Sports");
                           }
                           if(category.equals("Pet_Products")){
                               mos.write("bins", value, NullWritable.get(), "Pet_Products");
                           }
                           if(category.equals("Musical")){
                               mos.write("bins", value, NullWritable.get(), "Musical");
                           }
                           if(category.equals("Books")){
                               mos.write("bins", value, NullWritable.get(), "Books");
                           }
                          
               
    }         
                    protected void cleanup(Context context) throws IOException, InterruptedException{
                        mos.close();
                    }
        
    }
    
}
