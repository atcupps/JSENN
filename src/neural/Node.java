package neural;

import java.util.ArrayList;
import java.util.List;

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
	 * The current value of the data stored in the Node, and the default value.
	 */
	private double curData, defaultData;
	
	/*
	 * A list of all the edges between the current Node and all Nodes
	 * of the next Layer in the Network.
	 */
	private List<Edge> edges;
	
	/*
	 * The name of the current Node object; used for individual Node identification
	 */
	private String name;
	
	/**
	 * The default constructor for a new Node; by default, Nodes have data
	 * of 0.0 and no edges or name.
	 */
	public Node() {
		defaultData = 0;
		curData = defaultData;
	}
	
	/**
	 * Constructor for genetically inheriting an output Node; data from the
	 * parameter Node is copied with variation of maximum plus or minus maxVariance.
	 * @param toInherit - the Node to be inherited
	 * @param maxVariance - the maximum variance of inherited Node data
	 */
	public Node(Node toInherit, double maxVariance) {
		defaultData = toInherit.defaultData + Math.random() * maxVariance * 2 - maxVariance;
		curData = defaultData;
		name = toInherit.name;
	}
	
	/**
	 * Constructor for genetically inheriting a non-output Node; data from
	 * the parameter Node is copied with variation of maximum plus or minus
	 * maxVariance.
	 * @param toInherit - the Node to be inherited
	 * @param nextNodes - the Nodes of the next Layer
	 * @param maxVariance - the maximum variance of inherited Node data
	 */
	public Node(Node toInherit, Node[] nextNodes, double maxVariance) {
		defaultData = toInherit.defaultData + Math.random() * maxVariance * 2 - maxVariance;
		curData = defaultData;
		name = toInherit.name;
		
		edges = new ArrayList<Edge>();
		
		for (int i = 0; i < nextNodes.length; i++) {
			edges.add(new Edge(nextNodes[i], toInherit.edges.get(i), maxVariance));
		}
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
	
	/**
	 * Resets the value of curData to defaultData
	 */
	public void resetData() {
		curData = defaultData;
	}
	
	/**
	 * Adds the given parameter data to curData.
	 * @param data - the data to be added
	 */
	public void addData(double data) {
		curData += data;
	}
	
	/**
	 * Calls addData on all of the current Node's Edges with curData as 
	 * the method parameter.
	 */
	public void transferData() {
		for (Edge e : edges) {
			e.addData(curData);
		}
	}
}
