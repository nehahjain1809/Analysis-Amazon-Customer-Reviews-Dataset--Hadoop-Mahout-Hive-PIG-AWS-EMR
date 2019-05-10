package yearwiserating;


import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

import org.apache.hadoop.io.Writable;


public class CountAverageTuple implements Writable{

	public double productRatingAvg;
	public int count;
	
	public CountAverageTuple(double productRatingAvg, int count) {
		super();
		this.productRatingAvg = productRatingAvg;
		this.count = count;
	}

	public CountAverageTuple() {
		super();
	}

	
	public double getProductRatingAvg() {
		return productRatingAvg;
	}

	public void setProductRatingAvg(double productRatingAvg) {
		this.productRatingAvg = productRatingAvg;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}


	public void write(DataOutput out) throws IOException {
		// TODO Auto-generated method stub
		out.writeDouble(productRatingAvg);
		out.writeInt(count);
	}

	public void readFields(DataInput in) throws IOException {
		// TODO Auto-generated method stub
		productRatingAvg = in.readDouble();
		count = in.readInt();
	}

	@Override
	public String toString() {
		return "RatedBy=" + count+  "\t"+",AverageRatingForYear=" + productRatingAvg;
	}
}
