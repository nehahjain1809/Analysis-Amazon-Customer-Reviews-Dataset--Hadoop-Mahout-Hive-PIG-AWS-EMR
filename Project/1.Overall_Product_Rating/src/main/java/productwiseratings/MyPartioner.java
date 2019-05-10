
package productwiseratings;


import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.mapreduce.Partitioner;

public class MyPartioner extends Partitioner <ProductWritable, NullWritable>{

	@Override
	public int getPartition(ProductWritable arg0, NullWritable arg1, int numOfReducerTasks) {
		// TODO Auto-generated method stub
		int hash = arg0.hashCode();
		int partition = hash % numOfReducerTasks;
		return partition;
	}

}

