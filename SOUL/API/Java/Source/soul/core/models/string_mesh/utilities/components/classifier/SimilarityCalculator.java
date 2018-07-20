package soul.core.models.string_mesh.utilities.components.classifier;

import java.util.ArrayList;

public class SimilarityCalculator {
	
	public static ArrayList<Double> getSimilarityVector(String alpha, String beta) {
		
		String longer = alpha.length() >= beta.length() ? alpha : beta;
		String shorter = alpha.length() >= beta.length() ? beta : alpha;
		
		return getFractalSimilarityVector(shorter, longer);
	}
	
	private static ArrayList<Double> getFractalSimilarityVector(String shorter, String longer) {
		
		if(shorter.length() == 0) {

			ArrayList<Double> similarity = new ArrayList<Double>();
			similarity.add(0.0);
			
			return similarity;
		}
			
		if(shorter.length() == 1) {
			
			double instances = 0;
			
			for(int i = 0; i < longer.length(); i++) {
				
				if(shorter.charAt(0) == longer.charAt(i))
					instances++;
			}
			
			ArrayList<Double> similarity = new ArrayList<Double>();
			similarity.add(instances / longer.length());
			
			return similarity;
		}
		
		ArrayList<Double> alphaSimilarity =
				getFractalSimilarityVector(
						shorter.substring(0, shorter.length() / 2),
						longer.substring(0, longer.length() / 2));
		
		ArrayList<Double> betaSimilarity =
				getFractalSimilarityVector(
						shorter.substring(shorter.length() / 2),
						longer.substring(longer.length() / 2));
		
		alphaSimilarity.addAll(betaSimilarity);
		
		return alphaSimilarity;
	}
}