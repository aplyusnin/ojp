package ru.nsu.fit.ojp.Task_4.translator.handlers;

import ru.nsu.fit.ojp.Task_4.Node;
import ru.nsu.fit.ojp.Task_4.Pair;
import ru.nsu.fit.ojp.Task_4.translator.Context;
import ru.nsu.fit.ojp.Task_4.translator.FunctionDescriptor;

import java.util.HashMap;
import java.util.List;

/**
 * Class basis for handler for chain of handlers;
 */
public abstract class BasicHandler {

	protected List<Context> contexts;
	protected HashMap<String, FunctionDescriptor> nameToDesc;
	protected HashMap<String, FunctionDescriptor> nameToDummy;
	protected BasicHandler nextHandler;
	protected BasicHandler startingHandler;
	protected String keyWord;

	/**
	 * Constructor
	 * @param contexts - List of contexts. Context with higher index has higher priority
	 * @param nameToDesc - Table of defined methods. Being looked up after contexts
	 * @param nameToDummy - Table of declared methods, Being looked up after declared methods
	 */
	public BasicHandler(List<Context> contexts, HashMap<String, FunctionDescriptor> nameToDesc, HashMap<String, FunctionDescriptor> nameToDummy){
		this.nameToDesc = nameToDesc;
		this.contexts = contexts;
		this.nameToDummy = nameToDummy;
	}

	/**
	 * Generate source evaluating node
	 * @param node node to generate source for
	 * @param created index of first local variable to declare
	 * @param resVar - variable name to store computation result
	 * @return Pair of generated source and number of used variables
	 * @throws Exception - unable to create source
	 */
	public Pair<String, Integer> evalNode(Node node, int created, String resVar) throws Exception {
		var t = generateSource(node, created, resVar);
		if (t.first) return t.second;
		try {
			return nextHandler.evalNode(node, created, resVar);
		}
		catch (Exception e){
			String res = node.getInfo();
			throw new Exception("Cannot compile: " + res);
		}
	}

	/**
	 * Try to generate source for given type of node
	 * @param node node to generate source for
	 * @param created index of first local variable to declare
	 * @param resVar - variable name to store computation result
	 * @return Pair of boolean and pair of generated source and number of used variables.
	 * If generate source will return (true, (source, cnt)), otherwise (false, null)
	 * @throws Exception - unable to build source
	 */
	protected abstract Pair<Boolean, Pair<String, Integer>> generateSource(Node node, int created, String resVar) throws Exception;

	/**
	 * Set next handler in chain
	 * @param nextHandler - next handler
	 */
	public void setNextHandler(BasicHandler nextHandler) {
		this.nextHandler = nextHandler;
	}

	/**
	 * Set starting handler in chain to evaluate sub-nodes
	 * @param startingHandler - starting handler
	 */
	public void setStartingHandler(BasicHandler startingHandler) {
		this.startingHandler = startingHandler;
	}


}
