package soul.core.models.string_mesh.utilities.components.corpus.data;

public class Output implements Comparable<Output> {
	
	private String output;
	private double feedback;
	
	public Output(String newOutput, double newFeedback) {
		output = newOutput;
		feedback = newFeedback;
	}
	
	public void setOutput(String newOutput) {
		output = newOutput;
	}
	
	public void setFeedback(double newFeedback) {
		feedback = newFeedback;
	}
	
	public String getOutput() {
		return output;
	}
	
	public double getFeedback() {
		return feedback;
	}
	
	public int compareTo(Output o) {
		return feedback == o.getFeedback() ? 0 : (feedback > o.getFeedback() ? 1 : -1);
	}
}