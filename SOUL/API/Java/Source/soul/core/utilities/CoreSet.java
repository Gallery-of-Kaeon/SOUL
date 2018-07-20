package soul.core.utilities;

public class CoreSet {
	
	public String input;
	public String output;
	
	public double correlation;
	
	public boolean equals(Object object) {
		
		if(!(object instanceof CoreSet))
			return false;
		
		CoreSet set = (CoreSet) object;
		
		return
				input.equals(set.input) &&
				output.equals(set.output) &&
				correlation == correlation;
	}
}