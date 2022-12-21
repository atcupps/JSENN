package neural;

import java.util.ArrayList;
import java.util.Collection;

/**
 * The Node object is the base unit of the Network; Nodes contain data
 * and Edges which connect a Node to other Nodes in the next layer.
 * 
 * Copyright (C) 2022 Andrew Cupps, CC BY-SA
 * 
 * @author Andrew Cupps
 * @version 20 Dec 2022
 */
public class Node {
	
	/*
	 * The data stored in the current Node.
	 */
	private double data;
	
	/*
	 * A collection of all the edges between the current Node and all Nodes
	 * of the next Layer in the Network.
	 */
	private Collection<Edge> edges;
	
	/*
	 * The name of the current Node object; used for individual Node identification
	 */
	private String name;
	
	/**
	 * The default constructor for a new Node; by default, Nodes have random
	 * data from -1 to 1, no Edges, and no name.
	 */
	public Node() {
		data = Math.random() * 2 - 1;
	}
	
	/**
	 * If edge is not instantiated, it instantiates it as a new ArrayList.
	 * This method then adds a new Edge with nextNode from the current Node.
	 * @param nextNode - the Node to be linked to with a new Edge
	 * @throws IllegalArgumentException if nextNode is null
	 */
	public void createEdge(Node nextNode) {
		if (nextNode == null) {
			throw new IllegalArgumentException("Cannot create an edge to a null Node object.");
		}
		
		if (edges == null) {
			edges = new ArrayList<Edge>();
		}
		
		edges.add(new Edge(nextNode));
	}
	
	/**
	 * Sets the name of the current Node object to the name parameter.
	 * @param name - the name to be assigned to this Node
	 */
	public void setName(String name) {
		this.name = name;
	}
	
	/**
	 * Checks whether the parameter name matches the name of the current
	 * Node object.
	 * @param name - the name to be commpared to this.name
	 * @return true if name matches this.name, false otherwise (including if
	 * the current Node object name is null)
	 */
	public boolean hasName(String name) {
		
		if (this.name == null) {
			return false;
		}
		
		return this.name.equals(name);
	}
}
