package neural;

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
	 * @param nextNode
	 */
	public Edge(Node nextNode) {
		next = nextNode;
		weight = Math.random() * 2 - 1;
		bias = Math.random() * 2 - 1;
	}
}
