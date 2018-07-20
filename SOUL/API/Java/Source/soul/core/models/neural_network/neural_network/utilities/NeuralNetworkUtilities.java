package soul.core.models.neural_network.neural_network.utilities;

import java.util.ArrayList;

import one.Element;
import one.ElementUtilities;
import one_plus.ONEPlus;
import soul.core.models.neural_network.neural_network.NeuralNetwork;
import soul.core.models.neural_network.neural_network.NeuralNetworkConnection;
import soul.core.models.neural_network.neural_network.NeuralNetworkLayer;
import soul.core.models.neural_network.neural_network.Neuron;

public class NeuralNetworkUtilities {
	
	public static NeuralNetwork getNewNeuralNetwork() {
		
		NeuralNetwork neuralNetwork = new NeuralNetwork();
		
		int numLayers = 4;
		
		for(int i = 0; i < numLayers; i++) {
			
			NeuralNetworkLayer layer = new NeuralNetworkLayer();
			
			int numNeurons = i == 0 || i == numLayers - 1 ? 1 : 3;
			
			for(int j = 0; j < numNeurons; j++) {
				
				NeuralNetworkConnection connection = new NeuralNetworkConnection();
				connection.neuron = new Neuron();
				
				layer.connections.add(connection);
			}
			
			neuralNetwork.layers.add(layer);
		}
		
		for(int i = 0; i < neuralNetwork.layers.size() - 1; i++) {
			
			for(int j = 0; j < neuralNetwork.layers.get(i).connections.size(); j++) {
				
				for(int k = 0; k < neuralNetwork.layers.get(i + 1).connections.size(); k++)
					neuralNetwork.layers.get(i).connections.get(j).weights.add(Math.random());
			}
		}
		
		return neuralNetwork;
	}
	
	public static NeuralNetwork loadNeuralNetwork(String data) {
		
		NeuralNetwork neuralNetwork = new NeuralNetwork();
		
		Element element = ONEPlus.parseONEPlus(data);
		
		for(Element layerElement : element.children) {
			
			NeuralNetworkLayer layer = new NeuralNetworkLayer();
			
			for(Element neuronElement : layerElement.children) {
				
				NeuralNetworkConnection connection = new NeuralNetworkConnection();
				
				Neuron neuron = new Neuron();
				connection.neuron = neuron;

				Element bias = ElementUtilities.getChild(neuronElement, "Bias");
				Element connections = ElementUtilities.getChild(neuronElement, "Connections");
				
				if(bias != null)
					neuron.bias = Double.parseDouble(bias.children.get(0).content);
				
				if(connections != null) {
					
					for(Element connectionElement : connections.children)
						connection.weights.add(Double.parseDouble(connectionElement.content));
				}
				
				layer.connections.add(connection);
			}
			
			neuralNetwork.layers.add(layer);
		}
		
		return neuralNetwork;
	}
	
	public static String saveNeuralNetwork(NeuralNetwork neuralNetwork) {
		
		Element data = new Element();
		
		for(int i = 0; i < neuralNetwork.layers.size(); i++) {
			
			Element layer = new Element();
			layer.content = "Layer";
			
			for(int j = 0; j < neuralNetwork.layers.get(i).connections.size(); j++) {
				
				Element neuron = new Element();
				neuron.content = "Neuron";
				
				NeuralNetworkConnection connection =
						neuralNetwork.layers.get(i).connections.get(j);
				
				if(i > 0) {
					
					Element bias = new Element();
					bias.content = "Bias";
					
					Element biasNumber = new Element();
					biasNumber.content = "" + connection.neuron.bias;
					
					ElementUtilities.addChild(bias, biasNumber);
					ElementUtilities.addChild(neuron, bias);
				}
				
				Element connections = new Element();
				connections.content = "Connections";
				
				for(int k = 0; k < connection.weights.size(); k++) {
					
					Element weight = new Element();
					weight.content = "" + connection.weights.get(k);
					
					ElementUtilities.addChild(connections, weight);
				}
				
				if(connections.children.size() > 0)
					ElementUtilities.addChild(neuron, connections);
				
				ElementUtilities.addChild(layer, neuron);
			}
			
			ElementUtilities.addChild(data, layer);
		}
		
		return "" + data;
	}
	
	public static ArrayList<Double> process(NeuralNetwork neuralNetwork, ArrayList<Double> input) {
		
		try {
			
			for(int i = input.size(); i < neuralNetwork.layers.get(0).connections.size(); i++)
				input.add(0.0);
			
			while(input.size() > neuralNetwork.layers.get(0).connections.size())
				input.remove(input.size() - 1);
			
			ArrayList<Double> output = new ArrayList<Double>(input);
			
			for(int i = 0; i < neuralNetwork.layers.size() - 1; i++)
				output = process(neuralNetwork.layers.get(i), neuralNetwork.layers.get(i + 1), output);
			
			return output;
		}
		
		catch(Exception exception) {
			return new ArrayList<Double>();
		}
	}
	
	public static ArrayList<Double> process(
			NeuralNetworkLayer neuralNetworkLayer,
			NeuralNetworkLayer nextNeuralNetworkLayer,
			ArrayList<Double> input) {
		
		ArrayList<Double> output = new ArrayList<Double>();
		
		for(int i = 0; i < nextNeuralNetworkLayer.connections.size(); i++) {
			
			double sum = nextNeuralNetworkLayer.connections.get(i).neuron.bias;
			
			for(int j = 0; j < neuralNetworkLayer.connections.size(); j++)
				sum += input.get(j) * neuralNetworkLayer.connections.get(j).weights.get(i);
			
			sum = nextNeuralNetworkLayer.connections.get(i).neuron.activation.activation(sum);
			
			output.add(sum);
		}
		
		return output;
	}
	
	public static void train(
			NeuralNetwork neuralNetwork,
			ArrayList<Double> input,
			ArrayList<Double> output,
			double correlation) {
		
	}
}