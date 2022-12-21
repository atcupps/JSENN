package neural;

/**
 * The NeuralNetworkException indicates that something has gone unexpectedly
 * wrong in some aspect of the NeuralNetwork.
 * 
 * Copyright (C) 2022 Andrew Cupps, CC BY-SA
 * 
 * @author Andrew Cupps
 * @version 20 Dec 2022
 */
public class NeuralNetworkException extends RuntimeException {
	 
	public NeuralNetworkException(String errorMessage) {
		super(errorMessage);
	}
}
