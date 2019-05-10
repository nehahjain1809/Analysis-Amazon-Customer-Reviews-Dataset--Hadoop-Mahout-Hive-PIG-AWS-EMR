package customeranalytics;

import java.io.IOException;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;


public class MyReducer extends Reducer<Text, CustomerAnalysisWritable, LongWritable, CustomerAnalysisWritable> {
	
	private CustomerAnalysisWritable result = new CustomerAnalysisWritable();

	public void reduce(LongWritable key, Iterable<CustomerAnalysisWritable> values, Context context) throws IOException, InterruptedException {
		long totalVotes = 0;
		long helpfulVotes = 0;
		long totalReviews = 0;
		double sum = 0;
		double count = 0;
		
		try {
			for(CustomerAnalysisWritable val: values) {

				totalVotes = totalVotes + val.getTotalVotes();
				helpfulVotes = helpfulVotes +  val.getTotalHelpfulVotes();
				totalReviews = totalReviews + val.getTotalReviews();
				sum = sum + val.getAvgReviewLength();
				count++;
				
			
			}
//			System.out.println(totalVotes +  "," +  helpfulVotes);
		
			result.setTotalVotes(totalVotes);
			result.setTotalHelpfulVotes(helpfulVotes);
			result.setTotalReviews(totalReviews);
			if(totalReviews != 0 && sum !=0) {
				result.setAvgReviewLength(sum/count);
			}
			else result.setAvgReviewLength(0);
			
			if(helpfulVotes != 0) {
				result.setHelpfullnessRatio(helpfulVotes/totalReviews);
			}
			else {
				result.setHelpfullnessRatio(0);
			}	
//			System.out.println(result);
	
			context.write(key, result);
		}
		catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
}
}


