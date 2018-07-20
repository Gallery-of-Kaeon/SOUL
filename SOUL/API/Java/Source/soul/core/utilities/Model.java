package soul.core.utilities;

import java.util.ArrayList;

public class Model {
	
	public String getName() {
		
		try {
			return getClass().getName().substring(getClass().getName().lastIndexOf('.'));
		}
		
		catch(Exception exception) {
			return getClass().getName();
		}
	}
	
	public void onCall(ArrayList<Object> objects) {
		
	}
	
	public void train(String input, String output, double correlation) {
		
	}
	
	public String process(String input) {
		return "";
	}
	
	public void load(String corpus) {
		
	}
	
	public String write() {
		return "";
	}
}