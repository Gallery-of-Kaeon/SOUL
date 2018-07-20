package soul.core.models.string_mesh;

import soul.core.models.string_mesh.utilities.StringMeshCore;
import soul.core.utilities.Model;

public class StringMeshModel extends Model {
	
	public StringMeshCore core;
	
	public StringMeshModel() {
		core = new StringMeshCore();
	}
	
	public String getName() {
		return "String Mesh";
	}
	
	public void train(String input, String output, double correlation) {
		core.enforce(input, output, correlation);
	}
	
	public String process(String input) {
		return core.compute(input);
	}
	
	public void load(String corpus) {
		core.load(corpus);
	}
	
	public String write() {
		return core.save();
	}
}