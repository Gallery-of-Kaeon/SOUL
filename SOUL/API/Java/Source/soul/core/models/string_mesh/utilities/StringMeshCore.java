package soul.core.models.string_mesh.utilities;

import soul.core.models.string_mesh.utilities.components.classifier.Classifier;
import soul.core.models.string_mesh.utilities.components.corpus.Corpus;

public class StringMeshCore {
	
	private Corpus corpus;
	private Classifier classifier;
	
	public StringMeshCore() {
		corpus = new Corpus();
		classifier = new Classifier();
	}
	
	public String compute(String input) {
		return classifier.compute(corpus, input);
	}
	
	public void enforce(String input, String output, double feedback) {
		corpus.enforce(input, output, feedback);
	}
	
	public void load(String data) {
		corpus.load(data);
	}
	
	public String save() {
		return corpus.save();
	}
}