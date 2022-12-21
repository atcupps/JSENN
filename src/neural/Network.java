package neural;

/**
 * The Network class contains all the layers of a neural network, and sets up
 * the neural network so that all non-output nodes have an edge to each node
 * in the next layer of the Network; it also contains all the methods to input
 * and get data from the Network.
 * 
 * Copyright (C) 2022 Andrew Cupps, CC BY-SA
 * 
 * @author Andrew Cupps
 * 20 Dec 2022
 */
public class Network {

	/*
	 * The number of layers in the neural network
	 */
	private int numLayers;
	
	/*
	 * An array of all the layers in the Network
	 */
	private Layer[] layers;
	
	/**
	 * Creates the current network with numLayers amount of layers, where
	 * each layer has a corresponding value in layerSizes representing the
	 * number of Nodes in that layer. For example, if layerSizes[0] = 8, then
	 * the first layer (since layers are zero-indexed in Network) will have
	 * eight nodes.
	 * 
	 * @param numLayers - the number of layers in the Network
	 * @param layerSizes - an array representing the size in nodes of each layer
	 * @throws IllegalArgumentException if numLayers is less than two, or if the
	 * size of layerSizes is not equal to numLayers, or if any of the
	 * values of layerSizes is less than one.
	 */
	public Network(int numLayers, int[] layerSizes) {
		if (numLayers < 2) {
			throw new IllegalArgumentException("Must have at least 2 layers.");
		}
		
		if (layerSizes.length != numLayers) {
			throw new IllegalArgumentException("Number of layers must match size of layerSizes array.");
		}
		
		this.numLayers = numLayers;
		layers = new Layer[numLayers];
		
		layers[numLayers - 1] = new Layer(layerSizes[numLayers - 1]);
		
		for (int i = numLayers - 2; i >= 0; i++) {
			if (layerSizes[i] < 1) {
				throw new IllegalArgumentException("Layer Sizes must be at least one.");
			}
			
			layers[i] = new Layer(layerSizes[i], layers[i+1]);
		}
	}
	
	/**
	 * Returns the input Layer of the Network.
	 * @return Network.layers[0]
	 */
	public Layer getInputs() {
		return layers[0];
	}
	
	/**
	 * Returns the output layer of the Network.
	 * @return Network.layers[layers.length - 1]
	 */
	public Layer getOutputs() {
		return layers[layers.length - 1];
	}
	
	/**
	 * Returns the layer specified by the int parameter layerNumber.
	 * @param layerNumber - the depth of the Layer to be returned.
	 * @return Network.layers[layerNumber]
	 */
	public Layer getSpecifiedLayer(int layerNumber) {
		return layers[layerNumber];
	}
}
