package ru.nsu.fit.javalisp.translator.handlers;

import ru.nsu.fit.javalisp.Node;
import ru.nsu.fit.javalisp.Pair;
import ru.nsu.fit.javalisp.translator.Context;
import ru.nsu.fit.javalisp.translator.FunctionDescriptor;

import java.util.HashMap;
import java.util.List;

/**
 * Handler that unwraps nodes. ((x)) -> (x)
 */
public class WrapHandler extends BasicHandler{

	/**
	 * Create handler
	 * @param contexts - list of contexts
	 * @param nameToDesc - defined functions
	 * @param nameToDummy - declared functions
	 */
	public WrapHandler(List<Context> contexts, HashMap<String, FunctionDescriptor> nameToDesc, HashMap<String, FunctionDescriptor> nameToDummy){
		super(contexts, nameToDesc, nameToDummy);
	}
	@Override
	protected Pair<Boolean, Pair<String, Integer>> generateSource(Node node, int created, String resVar) throws Exception
	{
		if (node.getType() != Node.Type.COMPLEX) return new Pair<>(Boolean.FALSE, null);
		if (node.getSubNodes().size() != 1) return new Pair<>(Boolean.FALSE, null);
		return new Pair<>(Boolean.TRUE, startingHandler.evalNode(node.getSubNodes().get(0), created, resVar));
	}

}
