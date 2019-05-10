package distinctcustomer;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.text.ParseException;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.conf.*;
import org.apache.hadoop.io.*;
import org.apache.hadoop.mapreduce.*;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.input.TextInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.mapreduce.lib.output.TextOutputFormat;

public class uniqueReducer extends Reducer<Text,NullWritable, Text, NullWritable> {

    public void reduce(Text key, Iterable<NullWritable> values, Context context) 
      throws IOException, InterruptedException {
       
        context.write(key,NullWritable.get());
    }

}
