package soul.core.models.string_mesh.utilities.components.corpus.data;

import java.util.ArrayList;
import java.util.Collections;

public class Input implements Comparable<Input> {
	
	private String input;
	private ArrayList<Output> outputs;
	
	public Input(String input) {
		
		this.input = input;
		
		outputs = new ArrayList<Output>();
	}
	
	public void addOutput(String output, double feedback) {
		
		for(int i = 0; i < outputs.size(); i++) {
			
			if(outputs.get(i).getOutput().equals(output)) {
				
				outputs.get(i).setFeedback(feedback);
				
				return;
			}
		}
		
		outputs.add(new Output(output, feedback));
		
		Collections.sort(outputs);
	}
	
	public String getInput() {
		return input;
	}
	
	public ArrayList<Output> getOutputs() {
		return outputs;
	}
	
	public int compareTo(Input input) {
		return this.input.compareTo(input.getInput());
	}
}