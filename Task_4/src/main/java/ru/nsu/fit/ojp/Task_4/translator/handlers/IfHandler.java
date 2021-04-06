package ru.nsu.fit.ojp.Task_4.translator.handlers;

import ru.nsu.fit.ojp.Task_4.Node;
import ru.nsu.fit.ojp.Task_4.Pair;
import ru.nsu.fit.ojp.Task_4.translator.Context;
import ru.nsu.fit.ojp.Task_4.translator.FunctionDescriptor;

import java.util.HashMap;
import java.util.List;

/**
 * Handler that evaluates if-then and if-then-else constructions
 */
public class IfHandler extends BasicHandler{

	/**
	 * Create handler
	 * @param contexts - list of contexts
	 * @param nameToDesc - defined functions
	 * @param nameToDummy - declared functions
	 */

	public IfHandler(List<Context> contexts, HashMap<String, FunctionDescriptor> nameToDesc, HashMap<String, FunctionDescriptor> nameToDummy){
		super(contexts, nameToDesc, nameToDummy);
		keyWord = "if";
	}

	@Override
	protected Pair<Boolean, Pair<String, Integer>> generateSource(Node node, int created, String resVar) throws Exception
	{
		if (node.getType() != Node.Type.COMPLEX){
			return new Pair<>(Boolean.FALSE, null);
		}
		Node node1 = node.getSubNodes().get(0);
		if (node1.getType() != Node.Type.VARIABLE) return new Pair<>(Boolean.FALSE, null);
		if (!node1.getResult().equals(keyWord)) return new Pair<>(Boolean.FALSE, null);

		if (node.getSubNodes().size() < 3 || node.getSubNodes().size() > 4) {
			return new Pair<>(Boolean.FALSE, null);
		}

		int cnt = 0;
		String condVar = "_LOCAL_VAR_" + created;
		cnt++;

		StringBuilder builder = new StringBuilder();

		var t = evalNode(node.getSubNodes().get(1), created + cnt, condVar);

		builder.append(t.first);

		builder.append("if (((Boolean)").append(condVar).append(").booleanValue()){\n");

		cnt += t.second;

		var t1 = startingHandler.evalNode(node.getSubNodes().get(2),created + cnt, resVar);
		cnt += t1.second;
		builder.append(t1.first);
		builder.append("}\nelse {\n");
		if (node.getSubNodes().size() == 4) {
			var t2 = startingHandler.evalNode(node.getSubNodes().get(3), created + cnt, resVar);
			builder.append(t2.first);
			cnt += t2.second;
		}
		else {
			builder.append(resVar).append(" = null;\n");
		}
		builder.append("}\n");
		return new Pair<>(Boolean.TRUE, new Pair<>(builder.toString(), cnt));
	}

}
