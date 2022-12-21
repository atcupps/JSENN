package neural;

import functions.Function;

/**
 * Edges connect Nodes to other Nodes; an Edge stores the destination of the
 * edge in the next field, as well as the weight and bias of the Edge; Edges
 * also transfer data through sigmoid processing between Nodes.
 * 
 * Copyright (C) 2022 Andrew Cupps, CC BY-SA
 * 
 * @author Andrew Cupps
 * @version 20 Dec 2022
 */
public class Edge {

	/*
	 * The Node to which this Edge links
	 */
	private Node next;
	
	/*
	 * The weight and bias of the current Node
	 */
	private double weight, bias;
	
	/**
	 * The default constructor for creating a new Edge to a Node; by default, 
	 * weight and bias are random numbers between -1 and 1. 
	 * @param nextNode - the destination Node of the Edge
	 */
	public Edge(Node nextNode) {
		next = nextNode;
		weight = Math.random() * 2 - 1;
		bias = Math.random() * 2 - 1;
	}
	
	/**
	 * Genetic algorithmic inheritance constructor for an Edge in a
	 * Network being inherited. Data is copied from toInherit, with a maximum
	 * variation of plus or minus maxVariance.
	 * @param nextNode - the destination Node of the Edge
	 * @param toInherit - the Edge to be inherited
	 * @param maxVariance - the maximum variance of inherited Edge data
	 */
	public Edge(Node nextNode, Edge toInherit, double maxVariance) {
		next = nextNode;
		weight = toInherit.weight + Math.random() * maxVariance * 2 - maxVariance;
		bias = toInherit.bias + Math.random() * maxVariance * 2 - maxVariance;
	}
	
	/**
	 * Adds the parameter data to the current data of the destination Node of
	 * this edge after processing it through the sigmoid function with the
	 * Edge's weight and bias.
	 * @param data - the data to be transferred to the next Node.
	 */
	public void addData(double data) {
		next.addData(Function.sigmoid(data, weight, 1, bias, 0));
	}
}
