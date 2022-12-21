package neural;

import java.util.Collection;
import java.util.Iterator;

/**
 * The Layer class contains all the Nodes of a given layer in a neural network.
 * The size of the layer is predetermined and given in the constructor
 * parameters.
 * 
 * Copyright (C) 2022 Andrew Cupps, CC BY 4.0
 * 
 * @author Andrew Cupps
 * @version 21 Dec 2022
 */
public class Layer {

	/*
	 * The size of the Layer
	 */
	private int size;
	
	/*
	 * The next layer in the Network
	 */
	private Layer next;
	
	/*
	 * Boolean to determine whether this layer is an output layer or not.
	 */
	private boolean isOutput;
	
	/*
	 * All the nodes in the layer in array form
	 */
	private Node[] nodes;
	
	/**
	 * Constructor for an output layer
	 */
	public Layer(int size) {
		isOutput = true;
		
		this.size = size;
		nodes = new Node[size];
		
		for (int i = 0; i < nodes.length; i++) {
			nodes[i] = new Node();
		}
	}

	/**
	 * Genetic algorithmic inheritance constructor for an output Layer. Copies the
	 * data from the parameter Layer with slight variations at a maximum of
	 * maxVariance.
	 * @param toInherit - the Layer to be copied
	 * @param maxVariance - the maximum variance of Layer data
	 */
	public Layer(Layer toInherit, double maxVariance) {
		isOutput = true;
		
		this.size = toInherit.size;
		nodes = new Node[size];
		
		for (int i = 0; i < nodes.length; i++) {
			nodes[i] = new Node(toInherit.nodes[i], maxVariance);
		}
	}
	
	/**
	 * Constructor for a non-output layer with unspecified Node identifications
	 */
	public Layer(int size, Layer nextLayer) {
		isOutput = false;
		
		this.size = size;
		nodes = new Node[size];
		
		this.next = nextLayer;
		
		for (int i = 0; i < nodes.length; i++) {
			nodes[i] = new Node();
			
			/*
			 * Creating edges between each Node of the current Layer and each
			 * of the next Layer.
			 */
			Node[] nextLayerNodes = nextLayer.nodes;
			for (int j = 0; j < nextLayerNodes.length; j++) {
				nodes[i].createEdge(nextLayerNodes[j]);
			}
		}
	}
	
	/**
	 * Genetic algorithmic inheritence constructor for a new non-output Layer
	 * copied from a corresponding non-output Layer in another Network. Data
	 * is copied with maxVariance amount of variance (+/- maxVariance).
	 * @param toInherit - the Layer to be inherited
	 * @param next - the next Layer in the Network
	 * @param maxVariance - the maximum variance of copied data
	 */
	public Layer(Layer toInherit, Layer next, double maxVariance) {
		isOutput = false;
		
		this.size = toInherit.size;
		nodes = new Node[size];
		
		this.next = next;
		
		for (int i = 0; i < nodes.length; i++) {
			nodes[i] = new Node(toInherit.nodes[i], next.nodes, maxVariance);
		}
	}

	/**
	 * Returns the node at the given parameter index.
	 * @param index - the index of the Node to be returned.
	 * @return nodes[index]
	 */
	public Node get(int index) {
		return nodes[index];
	}
	
	/**
	 * Iterates through the Nodes in the current layer and returns the first
	 * Node with the given parameter name; if none is present, then this method
	 * returns null.
	 * @param name - the name of the Node to be returned.
	 * @return the first Node with the given name in the nodes field
	 */
	public Node get(String name) {
		for (Node n : nodes) {
			if (n.hasName(name)) {
				return n;
			}
		}
		
		return null;
	}
	
	/**
	 * Resets all the Nodes of the current Layer to have their default data values.
	 */
	public void resetLayer() {
		for (Node n : nodes) {
			n.resetData();
		}
	}
	
	/**
	 * Returns false if this Layer is an output layer; otherwise, this method
	 * will call all Nodes to transfer their data, then returns true.
	 * @return false if isOutput, true if not
	 */
	public boolean transferData() {
		if (isOutput) {
			return false;
		}
		
		for (Node n : nodes) {
			n.transferData();
		}
		
		return true;
	}
	
}
