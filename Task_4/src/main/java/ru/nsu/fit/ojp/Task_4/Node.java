package ru.nsu.fit.ojp.Task_4;

import java.util.LinkedList;
import java.util.List;

/**
 * Node class in syntax forest
 */
public class Node {
	private StringBuilder value;
	private String result;
	private List<Node> subNodes;
	public enum Type {
		VARIABLE,
		STRING,
		INT,
		FLOAT,
		JAVACALL,
		BOOL,
		COMPLEX
	}

	private Type type;
	private boolean processed = false;

	/**
	 * Create node
	 */
	public Node(){
		value = new StringBuilder();
		subNodes = new LinkedList<>();
	}

	/**
	 * Finalize node
	 */
	public void process(){
		result = value.toString();
		processed = true;

	}

	/**
	 * Add subtree to this node 
	 * @param node node to add to current
	 */
	public void addNode(Node node){subNodes.add(node);}

	/**
	 * Get list of child nodes
	 * @return list of child nodes
	 */
	public List<Node> getSubNodes() {
		return subNodes;
	}

	/**
	 * Add char to current node
	 * @param ch char to add
	 */
	public void addChar(char ch){
		value.append(ch);
	}

	/**
	 * Set node type
	 * @param type - node type
	 */
	public void setType(Type type) {
		this.type = type;
	}

	/**
	 * Get node type
	 * @return node type
	 */
	public Type getType() {
		return type;
	}

	/**
	 * Get node value
	 * @return node value, is applied process dunction
	 */
	public String getResult() {
		if (!processed) return "";
		return result;
	}


	public String getInfo(){
		StringBuilder info = new StringBuilder();
		if (type != Type.COMPLEX){
			info.append(result);
		}
		else {
			for (var t : subNodes){
				info.append("(").append(t.getInfo()).append(" ");
			}
		}
		return info.append(")").toString();
	}
}
