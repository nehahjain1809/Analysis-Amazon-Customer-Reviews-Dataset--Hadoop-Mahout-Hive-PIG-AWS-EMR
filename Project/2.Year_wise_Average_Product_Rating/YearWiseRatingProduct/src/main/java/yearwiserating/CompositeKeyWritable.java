package yearwiserating;


import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Locale;

import org.apache.hadoop.io.Writable;
import org.apache.hadoop.io.WritableComparable;

public class CompositeKeyWritable implements Writable, WritableComparable<CompositeKeyWritable>{
	
	private String productId;
	private int year;
	
	public CompositeKeyWritable() {
		super();
	}

	public CompositeKeyWritable(int year, String productId) {
		super();
		
		this.year = year;
		this.productId = productId;
	}
	
	public String getProductId() {
		return productId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}

	public int getYear() {
		return year;
	}

	public void setYear(int year) {
		this.year = year;
	}

	@Override
	public String toString() {
		return "ProductId=" + productId + ",\t"+ "Year=" + year + "\t";
	}
	
	public int compareTo(CompositeKeyWritable o) {
		// TODO Auto-generated method stub
		
		int result = -1;
		
		if(year > o.year) {
			result = 1;
		} 
		else if (year == o.year) {
			result = 0;
		}
		else {
			result = -1;
		}
		

		if(result == 0) {
			result = productId.compareTo(o.productId);
		}
		return result;
	}
	

	public void write(DataOutput out) throws IOException {
		// TODO Auto-generated method stub
		out.writeInt(year);
		out.writeUTF(productId);
		
	}

	public void readFields(DataInput in) throws IOException {
		// TODO Auto-generated method stub
		year = in.readInt();
		productId = in.readUTF();
	}
	
	
}
