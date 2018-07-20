package soul.core.models.string_mesh.utilities.components.classifier;

import java.util.ArrayList;

import soul.core.models.string_mesh.utilities.components.corpus.Corpus;
import soul.core.models.string_mesh.utilities.components.corpus.data.Input;
import soul.core.models.string_mesh.utilities.components.corpus.data.Output;

public class Classifier {
	
	public String compute(Corpus corpus, String input) {
		
		ArrayList<ArrayList<Input>> similarInputs = getSimilarInputs(corpus, input);
		ArrayList<String> approximateOutputs = new ArrayList<String>();
		
		for(int i = 0; i < similarInputs.size(); i++) {
			
			for(int j = 0; j < similarInputs.get(i).size(); j++)
				approximateOutputs.add(approximateOutput(similarInputs.get(i).get(j)));
		}
		
		return amalgamate(approximateOutputs);
	}
	
	private ArrayList<ArrayList<Input>> getSimilarInputs(Corpus corpus, String input) {
		
		ArrayList<Input> inputs = corpus.getInputs();
		ArrayList<ArrayList<Double>> similarityVectors = new ArrayList<ArrayList<Double>>();
		
		for(int i = 0; i < inputs.size(); i++)
			similarityVectors.add(SimilarityCalculator.getSimilarityVector(inputs.get(i).getInput(), input));
		
		ArrayList<ArrayList<Input>> similarInputs = new ArrayList<ArrayList<Input>>();
		
		for(int i = 0; i < input.length(); i++) {
			
			ArrayList<Input> similarInputList = new ArrayList<Input>();
			double maxSimilarity = 0;
			
			for(int j = 0; j < similarityVectors.size(); j++) {
				
				int index = (int) (i * ((double) similarityVectors.get(j).size() / input.length()));
				double similarity = similarityVectors.get(j).get(index);
					
				if(similarity > maxSimilarity) {
					similarInputList = new ArrayList<Input>();
					maxSimilarity = similarity;
				}
				
				similarInputList.add(inputs.get(j));
			}
			
			similarInputs.add(similarInputList);
		}
		
		return similarInputs;
	}
	
	private String approximateOutput(Input input) {

		ArrayList<Output> outputs = input.getOutputs();
		
		if(outputs.get(outputs.size() - 1).getFeedback() == 1)
			return outputs.get(outputs.size() - 1).getOutput();
		
		ArrayList<String> outputData = new ArrayList<String>();
		
		for(int i = 0; i < input.getOutputs().size(); i++)
			outputData.add(input.getOutputs().get(i).getOutput());
		
		int length = 0;
		
		if(outputData.size() > 0) {
			
			length = outputData.get(outputData.size() - 1).length();
			
			int trend = 0;
			
			for(int i = 0; i < outputData.size() - 1; i++)
				trend += outputData.get(i + 1).length() - outputData.get(i).length();
			
			length += trend / outputData.size();
			length = Math.abs(length);
		}
		
		String approximate = "";
		
		for(int i = 0; i < length; i++) {
			
			ArrayList<Character> characters = new ArrayList<Character>();
			ArrayList<Double> instances = new ArrayList<Double>();
			
			for(int j = 0; j < outputs.size(); j++) {
				
				char character = getCharacterAt(outputData.get(j), length, i);
				double feedback = outputs.get(j).getFeedback();
				
				int characterIndex = -1;
				
				for(int k = 0; k < characters.size(); k++) {
					
					if(characters.get(k) == character) {
						characterIndex = k;
						break;
					}
				}
				
				if(characterIndex == -1) {
					characters.add(character);
					instances.add(feedback);
				}
				
				else
					instances.set(characterIndex, instances.get(characterIndex) + feedback);
			}
			
			int maxIndex = 0;
			
			for(int j = 1; j < instances.size(); j++) {
				
				if(instances.get(j) > instances.get(maxIndex))
					maxIndex = j;
			}
			
			approximate += characters.get(maxIndex);
		}
		
		return approximate;
	}
	
	private String amalgamate(ArrayList<String> strings) {
		
		int length = 0;
		
		for(int i = 0; i < strings.size(); i++)
			length += strings.get(i).length();
		
		length /= strings.size();
		
		String amalgamate = "";
		
		for(int i = 0; i < length; i++) {
			
			ArrayList<Character> characters = new ArrayList<Character>();
			ArrayList<Integer> instances = new ArrayList<Integer>();
			
			for(int j = 0; j < strings.size(); j++) {
				
				char character = getCharacterAt(strings.get(j), length, i);
				
				int characterIndex = -1;
				
				for(int k = 0; k < characters.size(); k++) {
					
					if(characters.get(k) == character) {
						characterIndex = k;
						break;
					}
				}
				
				if(characterIndex == -1) {
					characters.add(character);
					instances.add(i);
				}
				
				else
					instances.set(characterIndex, instances.get(characterIndex) + i);
			}
			
			int maxIndex = 0;
			
			for(int j = 1; j < instances.size(); j++) {
				
				if(instances.get(j) > instances.get(maxIndex))
					maxIndex = j;
			}
			
			amalgamate += characters.get(maxIndex);
		}
		
		return amalgamate;
	}
	
	private char getCharacterAt(String string, int length, int index) {
		
		if(string.length() == 0)
			return (char) ((int) Math.random() * 256);
		
		int begin = (int) ((((double) string.length() / (double) length)) * index);
		int end = (int) ((((double) string.length() / (double) length)) * (index + 1));
		
		if(begin == end)
			end++;
		
		String sample = string.substring(begin, end);
		
		ArrayList<Character> characters = new ArrayList<Character>();
		ArrayList<Integer> instances = new ArrayList<Integer>();
		
		for(int i = 0; i < sample.length(); i++) {
			
			char character = sample.charAt(i);
			
			int characterIndex = -1;
			
			for(int j = 0; j < characters.size(); j++) {
				
				if(characters.get(j) == character) {
					characterIndex = j;
					break;
				}
			}
			
			if(characterIndex == -1) {
				characters.add(character);
				instances.add(1);
			}
			
			else
				instances.set(characterIndex, instances.get(characterIndex) + 1);
		}
		
		int maxIndex = 0;
		
		for(int i = 1; i < instances.size(); i++) {
			
			if(instances.get(i) == instances.get(maxIndex))
				maxIndex = i;
		}
		
		return characters.get(maxIndex);
	}
}