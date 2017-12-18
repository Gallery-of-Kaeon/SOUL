package soul.core.models.string_mesh.utilities.components.corpus;

import java.util.ArrayList;
import java.util.Collections;

import one.Element;
import one.ElementUtilities;
import one_plus.ONEPlus;
import soul.core.models.string_mesh.utilities.components.corpus.data.Input;

public class Corpus {

	private ArrayList<Input> inputs;
	
	public Corpus() {
		inputs = new ArrayList<Input>();
	}
	
	public void enforce(String input, String output, double feedback) {
		
		Input exisitngInput = getInput(input);
		
		if(exisitngInput != null)
			exisitngInput.addOutput(output, feedback);
		
		else {
			
			Input newInput = new Input(input);
			newInput.addOutput(output, feedback);
			
			inputs.add(newInput);
			
			Collections.sort(inputs);
		}
	}
	
	private Input getInput(String input) {
		
		int index = inputs.size() / 2;
		
		for(int divide = 4; divide < inputs.size() / 2; divide *= 2) {
			
			if(inputs.get(index).getInput().equals(input))
				return inputs.get(index);
			
			if(inputs.get(index).getInput().compareTo(input) < 0)
				index += inputs.size() / divide;
			
			else
				index -= inputs.size() / divide;
		}
		
		return null;
	}
	
	public ArrayList<Input> getInputs() {
		return inputs;
	}
	
	public void load(String data) {
		
		try {
			
			Element one = ONEPlus.parseONEPlus(data);
			
			ArrayList<Input> newInputs = new ArrayList<Input>();
			
			for(int i = 0; i < one.children.size(); i++) {
				
				Element inputElement = one.children.get(i);
				
				Input input = new Input(inputElement.content);
				
				for(int j = 0; j < one.children.size(); j++) {
	
					Element outputElement = inputElement.children.get(i);
					
					input.addOutput(
							outputElement.content, 
							Double.parseDouble(
									outputElement.children.get(0).content));
				}
				
				newInputs.add(input);
			}
			
			inputs = newInputs;
		}
		
		catch(Exception exception) {
			
		}
	}
	
	public String save() {
		
		Element corpus = new Element();
		
		for(int i = 0; i < inputs.size(); i++) {
			
			Element input = new Element();
			input.content = inputs.get(i).getInput();
			
			for(int j = 0; j < inputs.get(i).getOutputs().size(); j++) {
				
				Element output = new Element();
				output.content = inputs.get(i).getOutputs().get(j).getOutput();
				
				Element feedback = new Element();
				feedback.content = "" + inputs.get(i).getOutputs().get(j).getFeedback();
				
				ElementUtilities.addChild(output, feedback);
				
				ElementUtilities.addChild(input, output);
			}
			
			ElementUtilities.addChild(corpus, input);
		}
		
		return corpus.toString();
	}
}