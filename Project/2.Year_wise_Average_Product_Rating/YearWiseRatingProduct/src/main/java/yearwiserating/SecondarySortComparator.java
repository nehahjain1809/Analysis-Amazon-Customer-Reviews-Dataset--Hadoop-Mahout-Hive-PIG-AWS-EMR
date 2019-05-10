package yearwiserating;


import org.apache.hadoop.io.WritableComparable;
import org.apache.hadoop.io.WritableComparator;

public class SecondarySortComparator extends WritableComparator{

	protected SecondarySortComparator() {
		super(CompositeKeyWritable.class, true);
	}

	@Override
	public int compare(WritableComparable a, WritableComparable b) {
		// TODO Auto-generated method stub
		
		CompositeKeyWritable cw1 = (CompositeKeyWritable) a;
		CompositeKeyWritable cw2 = (CompositeKeyWritable) b;
		
		int result = 0;
		
		if(cw1.getYear() > cw2.getYear()) {
			result = 1;
		} 
		else if (cw1.getYear() == cw2.getYear()) {
			result = 0;
		}
		else {
			result = -1;
		}
		
		if(result == 0) {
			result = cw1.getProductId().compareTo(cw2.getProductId());
		}
		return result*(-1);
	}

}
