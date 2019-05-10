package productwiseratings;

import java.io.IOException;
import org.apache.hadoop.io.*;
import org.apache.hadoop.mapreduce.Mapper;

public class AverageMapper extends Mapper<Object, Text, ProductWritable, CountAverageTuple> {
	private ProductWritable productWritable = new ProductWritable();
	private CountAverageTuple ratingCountAverage = new CountAverageTuple();
//	private final static SimpleDateFormat frmt = new SimpleDateFormat("yyyy-MM-dd");

	public void map(Object key, Text value, Context context) throws IOException, InterruptedException {

		String[] values = value.toString().split("\t");

		if(values[5] != null && values[7] != null && !values[0].equals("marketplace")) {
			String productTitle = values[3];
			float productRating = Float.parseFloat(values[7]);
			
			
			
			productWritable.setProductSymbol(productTitle);
			productWritable.setrating(productRating);
			
			
			ratingCountAverage.setCount(1);
			ratingCountAverage.setProductRatingAvg(productRating);
			context.write(productWritable, ratingCountAverage);
		}

	}
}
