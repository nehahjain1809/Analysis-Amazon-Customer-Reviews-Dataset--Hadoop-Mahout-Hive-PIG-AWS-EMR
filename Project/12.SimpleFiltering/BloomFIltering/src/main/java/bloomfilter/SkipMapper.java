package bloomfilter;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.StringTokenizer;

import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;


public class SkipMapper extends Mapper<LongWritable, Text, Text, Text> {

	private Text product = new Text();
	private Text reviewTxt = new Text();

	public static String[] stopwords = {"B007FHX9OK"};
	
	public static Set<String> stopWordList = new HashSet<String>(Arrays.asList(stopwords));
	
//	public static String[] mystopwords = { "a", "as", "able", "about", "above", "according", "accordingly", "across", "actually", 
//			"after", "afterwards", "again", "against", "aint", "all", "allow", "allows", "almost", "alone", "along", "already", 
//			"also", "although", "always", "am", "among", "amongst", "an", "and", "another", "any", "anybody", "anyhow", "anyone",
//			"anything", "anyway", "anyways", "anywhere", "apart", "appear", "appreciate", "appropriate", "are", "arent", "around",
//			"as", "aside", "ask", "asking", "associated", "at", "available", "away", "awfully", "be", "became", "because", "become",
//			"becomes", "becoming", "been", "before", "beforehand", "behind", "being", "believe", "below", "beside", "besides", 
//			"best", "better", "between", "beyond", "both", "brief", "but", "by", "cmon", "cs", "came", "can", "cant", "cannot", 
//			"cant", "cause", "causes", "certain", "certainly", "changes", "clearly", "co", "com", "come", "comes", "concerning",
//			"consequently", "consider", "considering", "contain", "containing", "contains", "corresponding", "could", "couldnt", 
//			"course", "currently", "definitely", "described", "despite", "did", "didnt", "different", "do", "does", "doesnt", "doing", 
//			"dont", "done", "down", "downwards", "during", "each", "edu", "eg", "eight", "either", "else", "elsewhere", "enough", "entirely",
//			"especially", "et", "etc", "even", "ever", "every", "everybody", "everyone", "everything", "everywhere", "ex", "exactly", "example",
//			"except", "far", "few", "ff", "fifth", "first", "five", "followed", "following", "follows", "for", "former", "formerly", "forth", 
//			"four", "from", "further", "furthermore", "get", "gets", "getting", "given", "gives", "go", "goes", "going", "gone", "got", "gotten", 
//			"greetings", "had", "hadnt", "happens", "hardly", "has", "hasnt", "have", "havent", "having", "he", "hes", "hello", "help", "hence", "her", 
//			"here", "heres", "hereafter", "hereby", "herein", "hereupon", "hers", "herself", "hi", "him", "himself", "his", "hither", "hopefully", "how",
//			"howbeit", "however", "i", "id", "ill", "im", "ive", "ie", "if", "ignored", "immediate", "in", "inasmuch", "inc", "indeed", "indicate", "indicated", 
//			"indicates", "inner", "insofar", "instead", "into", "inward", "is", "isnt", "it", "itd", "itll", "its", "its", "itself", "just", "keep", "keeps", 
//			"kept", "know", "knows", "known", "last", "lately", "later", "latter", "latterly", "least", "less", "lest", "let", "lets", "like", "liked", "likely", 
//			"little", "look", "looking", "looks", "ltd", "mainly", "many", "may", "maybe", "me", "mean", "meanwhile", "merely", "might", "more", "moreover", "most", 
//			"mostly", "much", "must", "my", "myself", "name", "namely", "nd", "near", "nearly", "necessary", "need", "needs", "neither", "never", "nevertheless", 
//			"new", "next", "nine", "no", "nobody", "non", "none", "noone", "nor", "normally", "not", "nothing", "novel", "now", "nowhere", "obviously", "of", "off", 
//			"often", "oh", "ok", "okay", "old", "on", "once", "one", "ones", "only", "onto", "or", "other", "others", "otherwise", "ought", "our", "ours", "ourselves",
//			"out", "outside", "over", "overall", "own", "particular", "particularly", "per", "perhaps", "placed", "please", "plus", "possible", "presumably", "probably", 
//			"provides", "que", "quite", "qv", "rather", "rd", "re", "really", "reasonably", "regarding", "regardless", "regards", "relatively", "respectively", "right", 
//			"said", "same", "saw", "say", "saying", "says", "second", "secondly", "see", "seeing", "seem", "seemed", "seeming", "seems", "seen", "self", "selves", "sensible", 
//			"sent", "serious", "seriously", "seven", "several", "shall", "she", "should", "shouldnt", "since", "six", "so", "some", "somebody", "somehow", "someone", 
//			"something", "sometime", "sometimes", "somewhat", "somewhere", "soon", "sorry", "specified", "specify", "specifying", "still", "sub", "such", "sup", "sure", "ts", 
//			"take", "taken", "tell", "tends", "th", "than", "thank", "thanks", "thanx", "that", "thats", "thats", "the", "their", "theirs", "them", "themselves", "then", "thence",
//			"there", "theres", "thereafter", "thereby", "therefore", "therein", "theres", "thereupon", "these", "they", "theyd", "theyll", "theyre", "theyve", "think", "third",
//			"this", "thorough", "thoroughly", "those", "though", "three", "through", "throughout", "thru", "thus", "to", "together", "too", "took", "toward", "towards", "tried", 
//			"tries", "truly", "try", "trying", "twice", "two", "un", "under", "unfortunately", "unless", "unlikely", "until", "unto", "up", "upon", "us", "use", "used", "useful",
//			"uses", "using", "usually", "value", "various", "very", "via", "viz", "vs", "want", "wants", "was", "wasnt", "way", "we", "wed", "well", "were", "weve", "welcome", 
//			"well", "went", "were", "werent", "what", "whats", "whatever", "when", "whence", "whenever", "where", "wheres", "whereafter", "whereas", "whereby", "wherein",
//			"whereupon", "wherever", "whether", "which", "while", "whither", "who", "whos", "whoever", "whole", "whom", "whose", "why", "will", "willing", "wish",
//			"with", "within", "without", "wont", "wonder", "would", "would", "wouldnt", "yes", "yet", "you", "youd", "youll", "youre", "youve", "your", "yours", 
//			"yourself", "yourselves", "zero"};
//	
//	public static Set<String> mystopWordList = new HashSet<String>(Arrays.asList(mystopwords));



	
	public void map(LongWritable key, Text value, Context context)
			throws IOException, InterruptedException {
		String[] line = value.toString().split("\t");
		
		String review = line[13];
		String productId =  line[3];
		
		if(!line[0].equals("marketplace") && review !=null && productId != null) {
			
//			if (stopWordList.contains(productId)) {
//			
//				 StringBuffer sb = new StringBuffer();
//				 StringTokenizer tokenizer = new StringTokenizer(review);
//				 
//					while (tokenizer.hasMoreTokens()) {
//						String token = tokenizer.nextToken();
//						if (mystopWordList.contains(token.toLowerCase())) {
//							context.getCounter(StopWordSkipper.COUNTERS.STOPWORDS)
//									.increment(1L);
//						} else {
//							context.getCounter(StopWordSkipper.COUNTERS.GOODWORDS)
//									.increment(1L);
//							sb.append(token +" ");
//						}
//					}
//				product.set(productId);
//				reviewTxt.set(sb.toString());
//				context.write(product, reviewTxt);
//			} 
			
			if(stopWordList.contains(productId)) {
//				StringBuffer sb = new StringBuffer();
					String s = review.toLowerCase().replaceAll("\\W\\s", " ");
				   String s1 = s.replaceAll("\\p{Punct}|\\d", " ");
				   String review1 = Stopwords.removeStemmedStopWords(s1);
				   String reviewNew = Stopwords.removeStopWords(review1).toLowerCase();
				   
//				   System.out.println(review+ " --> " + s);
//				   StringTokenizer tokenizer = new StringTokenizer(review);
//					while (tokenizer.hasMoreTokens()) {
//						String token = tokenizer.nextToken();
//						if (stopWordList.contains(token)) {
//							context.getCounter(StopWordSkipper.COUNTERS.STOPWORDS)
//									.increment(1L);
//						} else {
//							context.getCounter(StopWordSkipper.COUNTERS.GOODWORDS)
//									.increment(1L);
//							sb.append(token +" ");
//			//				word.set(customerId + "\t" + productId + "\t" + token);
//			//				context.write(word, null);
//						}
//					}
//					word.set(customerId + ":" + productId + ":" + rating+ ":" +sb.toString());
					
				    product.set(productId);
					reviewTxt.set(reviewNew);
				
					context.write(product, reviewTxt);
			}
		    
		}
	}
}
