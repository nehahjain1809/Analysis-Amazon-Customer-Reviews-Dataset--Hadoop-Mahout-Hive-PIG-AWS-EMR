package productwiseratings;


import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import org.apache.hadoop.io.Writable;
import org.apache.hadoop.io.WritableComparable;

public class ProductWritable implements Writable, WritableComparable<ProductWritable>{
	
	private String productTitle;
	private float rating;
	
	public ProductWritable() {
		super();
	}

	public ProductWritable(float rating, String ProductSymbol) {
		super();
		
		this.rating = rating;
		this.productTitle = ProductSymbol;
	}
	
	
		
	public String getProductSymbol() {
		return productTitle;
	}

	public void setProductSymbol(String ProductSymbol) {
		this.productTitle = ProductSymbol;
	}

	public float getrating() {
		return rating;
	}

	public void setrating(float rating) {
		this.rating = rating;
	}

	@Override
	public String toString() {
		return "Product=" + productTitle + "\t";
	}
	
	


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((productTitle == null) ? 0 : productTitle.hashCode());
		result = prime * result + Float.floatToIntBits(rating);
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ProductWritable other = (ProductWritable) obj;
		if (productTitle == null) {
			if (other.productTitle != null)
				return false;
		} else if (!productTitle.equals(other.productTitle))
			return false;
		if (Float.floatToIntBits(rating) != Float.floatToIntBits(other.rating))
			return false;
		return true;
	}

	public void write(DataOutput out) throws IOException {
		// TODO Auto-generated method stub
		out.writeFloat(rating);
		out.writeUTF(productTitle);
		
	}

	public void readFields(DataInput in) throws IOException {
		// TODO Auto-generated method stub
		rating = in.readInt();
		productTitle = in.readUTF();
	}

	public int compareTo(ProductWritable o) {
		// TODO Auto-generated method stub
		int result = -1;
		 if(rating >  o.rating) {
			 result = 1;
		 }
		 if(rating == o.rating) {
			 result = 0;
		 }
		 if(result == 0) {
			result  = productTitle.compareTo(o.productTitle);
		 }
		return result;
	}
}
	