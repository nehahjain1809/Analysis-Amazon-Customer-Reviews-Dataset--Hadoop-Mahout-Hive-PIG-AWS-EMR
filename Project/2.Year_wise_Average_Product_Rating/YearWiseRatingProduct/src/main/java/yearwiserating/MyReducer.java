package yearwiserating;


import java.io.IOException;

import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.mapreduce.Reducer;


public class MyReducer extends Reducer <CompositeKeyWritable, CountAverageTuple, CompositeKeyWritable, CountAverageTuple>{

	private CountAverageTuple result = new CountAverageTuple();
	@Override
	protected void reduce(CompositeKeyWritable arg0, Iterable<CountAverageTuple> arg1,
			Reducer<CompositeKeyWritable, CountAverageTuple, CompositeKeyWritable, CountAverageTuple>.Context arg2)
			throws IOException, InterruptedException {
		
		double sum = 0;
		int count = 0;

		for(CountAverageTuple val: arg1) {
			sum += val.getCount() * val.getProductRatingAvg();
			count += val.getCount();

		}
	
		result.setCount(count);
		result.setProductRatingAvg(sum/count);

		arg2.write(arg0, result);
	}

}
