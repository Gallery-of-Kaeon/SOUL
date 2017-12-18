package soul.core.models.neural_network;

import java.util.ArrayList;

import soul.core.models.neural_network.neural_network.NeuralNetwork;
import soul.core.models.neural_network.neural_network.utilities.NeuralNetworkUtilities;
import soul.core.utilities.Model;

public class NeuralNetworkModel extends Model {
	
	public NeuralNetwork neuralNetwork = new NeuralNetwork();
	
	public void train(String input, String output, double correlation) {
		NeuralNetworkUtilities.train(neuralNetwork, stringToList(input), stringToList(output), correlation);
	}
	
	public String process(String input) {
		return listToString(NeuralNetworkUtilities.process(neuralNetwork, stringToList(input)));
	}
	
	public void load(String corpus) {
		neuralNetwork = NeuralNetworkUtilities.loadNeuralNetwork(corpus);
	}
	
	public String write() {
		return NeuralNetworkUtilities.saveNeuralNetwork(neuralNetwork);
	}
	
	public ArrayList<Double> stringToList(String string) {
		
		ArrayList<Double> list = new ArrayList<Double>(string.length());
		
		for(int i = 0; i < string.length(); i++)
			list.add(sigmoid(string.charAt(i)));
		
		return list;
	}
	
	public String listToString(ArrayList<Double> list) {
		
		String string = "";
		
		for(int i = 0; i < list.size(); i++)
			string += sigmoidInverse(list.get(i));
		
		return string;
	}
	
	public double sigmoid(char character) {
		return 1.0 / (1.0 + Math.pow(Math.E, -character));
	}
	
	public char sigmoidInverse(double number) {
		return (char) (int) -Math.log((1.0 / number) - 1);
	}
}