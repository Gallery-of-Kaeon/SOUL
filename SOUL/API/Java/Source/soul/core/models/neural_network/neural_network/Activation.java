package soul.core.models.neural_network.neural_network;

public class Activation {
	
	public double activation(double input) {
		return 1.0 / (1.0 + Math.pow(Math.E, -input));
	}
}