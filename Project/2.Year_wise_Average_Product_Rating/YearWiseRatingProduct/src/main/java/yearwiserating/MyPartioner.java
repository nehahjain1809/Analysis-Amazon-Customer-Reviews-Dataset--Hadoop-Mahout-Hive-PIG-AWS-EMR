package yearwiserating;


import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.mapreduce.Partitioner;

public class MyPartioner extends Partitioner <CompositeKeyWritable, NullWritable>{

	@Override
	public int getPartition(CompositeKeyWritable arg0, NullWritable arg1, int numOfReducerTasks) {
		// TODO Auto-generated method stub
		int hash = arg0.getProductId().hashCode();
		int partition = hash % numOfReducerTasks;
		return partition;
	}

}

