package productwiseratings;

import java.io.IOException;

import org.apache.hadoop.mapreduce.*;

public class AverageReducer extends Reducer<ProductWritable, CountAverageTuple, ProductWritable, CountAverageTuple> {
	
	private CountAverageTuple result = new CountAverageTuple();

	public void reduce(ProductWritable key, Iterable<CountAverageTuple> values, Context context) throws IOException, InterruptedException {
		double sum = 0;
		int count = 0;

		for(CountAverageTuple val: values) {
			sum += val.getCount() * val.getProductRatingAvg();
			count += val.getCount();

		}
	
		result.setCount(count);
		result.setProductRatingAvg(sum/count);

		context.write(key, result);
	}
}
