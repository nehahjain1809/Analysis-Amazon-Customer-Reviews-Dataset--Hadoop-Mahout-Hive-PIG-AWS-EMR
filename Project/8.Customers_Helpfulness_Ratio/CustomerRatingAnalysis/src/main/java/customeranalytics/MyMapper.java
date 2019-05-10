package customeranalytics;

import java.io.IOException;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Mapper.Context;

//marketplace       - 2 letter country code of the marketplace where the review was written.
//customer_id       - Random identifier that can be used to aggregate reviews written by a single author.
//review_id         - The unique ID of the review.
//product_id        - The unique Product ID the review pertains to. In the multilingual dataset the reviews
//                    for the same product in different countries can be grouped by the same product_id.
//product_parent    - Random identifier that can be used to aggregate reviews for the same product.
//product_title     - Title of the product.
//product_category  - Broad product category that can be used to group reviews 
//                    (also used to group the dataset into coherent parts).
//star_rating       - The 1-5 star rating of the review.
//helpful_votes     - Number of helpful votes.
//total_votes       - Number of total votes the review received.
//vine              - Review was written as part of the Vine program.
//verified_purchase - The review is on a verified purchase.
//review_headline   - The title of the review.
//review_body       - The review text.
//review_date       - The date the review was written.

public class MyMapper extends Mapper<Object, Text, LongWritable, CustomerAnalysisWritable> {
	
	private CustomerAnalysisWritable customerAnalysis = new CustomerAnalysisWritable();
	private LongWritable customer = new LongWritable();
//	private final static SimpleDateFormat frmt = new SimpleDateFormat("yyyy-MM-dd");

	public void map(Object key, Text value, Context context) throws IOException, InterruptedException {

		String[] values = value.toString().split("\t");
//		System.out.println(value.toString());
		if(values[8] != null && values[9] != null && values[13] != null && !values[0].equals("marketplace")) {
			long customerId = Long.parseLong(values[1]);
			
			
			long helpfulVotes = Long.parseLong(values[8].toString());
			
			long totalvotes = Long.parseLong(values[9].toString());
			
			long reviewCount = 1;
			String reviewBody = values[13].toString();
			
			long lenghtofReview = (long)reviewBody.length();
			
//			System.out.println(customerId + " " +  totalvotes + " " +  helpfulVotes + " " +  reviewCount + " " + lenghtofReview);
			
			customer.set(customerId);
			customerAnalysis.setTotalVotes(totalvotes);
			customerAnalysis.setTotalHelpfulVotes(helpfulVotes);
			customerAnalysis.setTotalReviews(reviewCount);
			
			if(lenghtofReview < 1000) {
				customerAnalysis.setAvgReviewLength(lenghtofReview);
			}
			else {
				customerAnalysis.setAvgReviewLength(1000);
			}
//			System.out.println(customerAnalysis.getTotalVotes());
			
			context.write(customer, customerAnalysis);
		}

	}


}
