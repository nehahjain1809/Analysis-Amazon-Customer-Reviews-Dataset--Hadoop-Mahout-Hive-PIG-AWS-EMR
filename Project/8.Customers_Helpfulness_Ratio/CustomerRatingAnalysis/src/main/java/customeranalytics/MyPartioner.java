package customeranalytics;


import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Partitioner;

public  class MyPartioner extends Partitioner <LongWritable, CustomerAnalysisWritable>{

	@Override
	public int getPartition(LongWritable arg0, CustomerAnalysisWritable arg1, int numOfReducerTasks) {
		// TODO Auto-generated method stub
		int hash = arg0.hashCode();
		int partition = hash % numOfReducerTasks;
		return partition;
	}

}

