package customeranalytics;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

import org.apache.hadoop.io.Writable;

public class CustomerAnalysisWritable implements Writable{
	
	private long totalVotes;
	private long totalHelpfulVotes;
	private long totalReviews;
	private double avgReviewLength;
	private double helpfullnessRatio;
	
	
	public CustomerAnalysisWritable(long totalVotes, long totalHelpfulVotes, long totalReviews, double avgReviewLength, double helpfullnessRatio) {
		super();
		this.totalVotes = totalVotes;
		this.totalHelpfulVotes = totalHelpfulVotes;
		this.totalReviews = totalReviews;
		this.avgReviewLength = avgReviewLength;
		this.helpfullnessRatio = helpfullnessRatio;
	}

	public CustomerAnalysisWritable() {
		super();
	}


	
	public long getTotalVotes() {
		return totalVotes;
	}

	public void setTotalVotes(long totalVotes) {
		this.totalVotes = totalVotes;
	}

	public long getTotalHelpfulVotes() {
		return totalHelpfulVotes;
	}

	public void setTotalHelpfulVotes(long totalHelpfulVotes) {
		this.totalHelpfulVotes = totalHelpfulVotes;
	}

	public long getTotalReviews() {
		return totalReviews;
	}

	public void setTotalReviews(long totalReviews) {
		this.totalReviews = totalReviews;
	}

	public double getAvgReviewLength() {
		return avgReviewLength;
	}

	public void setAvgReviewLength(double avgReviewLength) {
		this.avgReviewLength = avgReviewLength;
	}

	
	
	public double getHelpfullnessRatio() {
		return helpfullnessRatio;
	}

	public void setHelpfullnessRatio(double helpfullnessRatio) {
		this.helpfullnessRatio = helpfullnessRatio;
	}

	public void readFields(DataInput in) throws IOException {
		// TODO Auto-generated method stub
//		productRatingAvg = in.readDouble();
//		count = in.readInt();
		totalVotes = in.readLong();
		totalHelpfulVotes = in.readLong();
		totalReviews = in.readLong();
		avgReviewLength = in.readDouble();
		helpfullnessRatio = in.readDouble();
	}

	public void write(DataOutput out) throws IOException {
		// TODO Auto-generated method stub
		out.writeDouble(avgReviewLength);
		out.writeLong(totalReviews);
		out.writeLong(totalHelpfulVotes);
		out.writeLong(totalVotes);
		out.writeDouble(helpfullnessRatio);
		
	}

	@Override
	public String toString() {
		return "TotalVotes=" + totalVotes+ "\t" +"TotalHelpfulVotes=" + totalHelpfulVotes + "\t" + "HelpfulnessRatio=" + helpfullnessRatio +
				"\t" + "TotalReviews=" + totalReviews + "\t"+ "AverageLengthOfReviews=" + avgReviewLength;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		long temp;
		temp = Double.doubleToLongBits(avgReviewLength);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(helpfullnessRatio);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + (int) (totalHelpfulVotes ^ (totalHelpfulVotes >>> 32));
		result = prime * result + (int) (totalReviews ^ (totalReviews >>> 32));
		result = prime * result + (int) (totalVotes ^ (totalVotes >>> 32));
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
		CustomerAnalysisWritable other = (CustomerAnalysisWritable) obj;
		if (Double.doubleToLongBits(avgReviewLength) != Double.doubleToLongBits(other.avgReviewLength))
			return false;
		if (Double.doubleToLongBits(helpfullnessRatio) != Double.doubleToLongBits(other.helpfullnessRatio))
			return false;
		if (totalHelpfulVotes != other.totalHelpfulVotes)
			return false;
		if (totalReviews != other.totalReviews)
			return false;
		if (totalVotes != other.totalVotes)
			return false;
		return true;
	}

	
	
	
}
