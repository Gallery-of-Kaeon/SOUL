package soul.core;

import java.util.ArrayList;

import one.Element;
import one.ElementUtilities;
import one_plus.ONEPlus;
import soul.core.models.neural_network.NeuralNetworkModel;
import soul.core.models.string_mesh.StringMeshModel;
import soul.core.utilities.CoreSet;
import soul.core.utilities.Model;

public class Core {
	
	public ArrayList<CoreSet> sets = new ArrayList<CoreSet>();
	public ArrayList<Model> models = new ArrayList<Model>();
	
	public Model currentModel;
	
	public Core() {
		
		models.add(new NeuralNetworkModel());
		models.add(new StringMeshModel());
		
		currentModel = models.get(0);
	}
	
	public void train(String input, String output, double correlation) {
		
		CoreSet set = new CoreSet();
		
		set.input = input;
		set.output = output;
		
		set.correlation = correlation;
		
		sets.add(set);
		
		for(int i = 0; i < models.size(); i++)
			models.get(i).train(input, output, correlation);
	}
	
	public String process(String input) {
		return currentModel.process(input);
	}
	
	public void load(String corpus) {
		
		Element load = ONEPlus.parseONEPlus(corpus);
		
		Element corpusSets = ElementUtilities.getChild(load, "Corpus");
		Element modelStates = ElementUtilities.getChild(load, "Models");
		
		sets = new ArrayList<CoreSet>();
		
		for(Element set : corpusSets.children) {
			
			CoreSet coreSet = new CoreSet();
			
			coreSet.input = ElementUtilities.getChild(set, "Input").children.get(0).content;
			coreSet.output = ElementUtilities.getChild(set, "Output").children.get(0).content;
			
			coreSet.correlation =
					Double.parseDouble(
							ElementUtilities.getChild(set, "Correlation").children.get(0).content);
			
			sets.add(coreSet);
		}
		
		for(Element model : modelStates.children) {

			for(int i = 0; i < models.size(); i++) {
				
				if(models.get(i).getName().equalsIgnoreCase(model.content)) {
					
					try {
						models.get(i).load(model.children.get(0).content);
					}
					
					catch(Exception exception) {
						
					}
					
					break;
				}
			}
		}
	}
	
	public String write() {
		
		Element write = new Element();
		
		Element corpus = new Element();
		corpus.content = "Corpus";
		
		for(int i = 0; i < sets.size(); i++) {
			
			Element set = new Element();
			set.content = "Set";
			
			Element input = new Element();
			input.content = "Input";
			
			Element inputData = new Element();
			inputData.content = sets.get(i).input;
			
			ElementUtilities.addChild(input, inputData);
			
			Element output = new Element();
			output.content = "Output";
			
			Element outputData = new Element();
			outputData.content = sets.get(i).output;
			
			ElementUtilities.addChild(output, outputData);
			
			Element correlation = new Element();
			correlation.content = "Correlation";
			
			Element correlationData = new Element();
			correlationData.content = "" + sets.get(i).correlation;
			
			ElementUtilities.addChild(input, correlationData);

			ElementUtilities.addChild(set, input);
			ElementUtilities.addChild(set, output);
			ElementUtilities.addChild(set, correlation);
		}
		
		Element modelsElement = new Element();
		modelsElement.content = "Models";
		
		for(int i = 0; i < models.size(); i++) {
			
			Element model = new Element();
			model.content = models.get(i).getName();
			
			Element state = new Element();
			state.content = models.get(i).write();
			
			ElementUtilities.addChild(model, state);
			
			ElementUtilities.addChild(modelsElement, model);
		}
		
		ElementUtilities.addChild(write, corpus);
		ElementUtilities.addChild(write, modelsElement);
		
		return "" + write;
	}
	
	public void setModel(String model) {
		
		model = model.trim();
		
		for(int i = 0; i < model.length(); i++) {
			
			if(model.charAt(i) == ' ') {
				
				model = model.substring(0, i) + model.substring(i + 1);
				
				i--;
			}
		}
		
		for(int i = 0; i < models.size(); i++) {
			
			if(models.get(i).getName().equalsIgnoreCase(model)) {
				
				currentModel = models.get(i);
				
				break;
			}
		}
	}
	
	public void callModel(ArrayList<Object> objects) {
		currentModel.onCall(objects);
	}
}