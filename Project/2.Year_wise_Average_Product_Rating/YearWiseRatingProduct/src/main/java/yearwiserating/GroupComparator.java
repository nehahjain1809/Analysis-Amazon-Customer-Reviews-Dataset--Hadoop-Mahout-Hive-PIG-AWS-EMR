package yearwiserating;



import org.apache.hadoop.io.WritableComparable;
import org.apache.hadoop.io.WritableComparator;

public class GroupComparator extends WritableComparator{

	protected GroupComparator() {
		super(CompositeKeyWritable.class, true);
	}

	@Override
	public int compare(WritableComparable a, WritableComparable b) {
		
		CompositeKeyWritable cw1 = (CompositeKeyWritable) a;
		CompositeKeyWritable cw2 = (CompositeKeyWritable) b;
//		boolean isLaterDate = false;

//		if(cw1.getYear() > cw2.getYear()) {
//			isLaterDate = true;
//		}
//
//		int comResult = 0;
//		if(isLaterDate) {
//			comResult = 1;
//		}
		
				
		return cw1.getProductId().compareTo(cw2.getProductId());
	}
}
