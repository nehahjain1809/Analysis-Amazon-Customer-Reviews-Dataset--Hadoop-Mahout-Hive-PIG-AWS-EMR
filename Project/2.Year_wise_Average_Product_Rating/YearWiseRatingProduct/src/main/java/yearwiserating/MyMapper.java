package yearwiserating;


import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

public class MyMapper extends Mapper <Object, Text, CompositeKeyWritable, CountAverageTuple>{
 
	private CountAverageTuple ratingCountAverage = new CountAverageTuple();
	@Override
	public void map(Object key, Text value, Mapper<Object, Text, CompositeKeyWritable, CountAverageTuple>.Context context)
			throws IOException, InterruptedException {
		// TODO Auto-generated method stub
		
		String values[] = value.toString().split("\t");
	
		String productId = null;
		int year = 0;
		double rating = 0.0D;
		if(values[5] != null && values[7] != null && !values[0].equals("marketplace")) {
			try {
//				System.out.println("1");
		    	productId = values[3];
		    	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		    	Date reviewDate = sdf.parse(values[14]);
		        year = Integer.parseInt(reviewDate.toString().split(" ")[5]);
//		        System.out.println(year);
		        rating = Double.parseDouble(values[7]);
		    	
			}catch(Exception e) {
				e.printStackTrace();
			}
//			System.out.println("2");
			if(year != 0 && productId != null) {
//				System.out.println("3");
				ratingCountAverage.setCount(1);
				ratingCountAverage.setProductRatingAvg(rating);
				CompositeKeyWritable cw = new CompositeKeyWritable(year, productId);
				
				try {
//					System.out.println(cw.toString() +  " "+ ratingCountAverage.toString());
					context.write(cw, ratingCountAverage);
				}catch (Exception e) {
					// TODO: handle exception
					e.printStackTrace();
				}
			}
		}
	}

}
