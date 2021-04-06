package ru.nsu.fit.javalisp.translator.handlers;

import ru.nsu.fit.javalisp.Node;
import ru.nsu.fit.javalisp.Pair;
import ru.nsu.fit.javalisp.translator.Context;
import ru.nsu.fit.javalisp.translator.FunctionDescriptor;

import java.util.HashMap;
import java.util.List;

/**
 * Handler that evaluates Do constuctions
 */
public class DoHandler extends BasicHandler {

	public DoHandler(List<Context> contexts, HashMap<String, FunctionDescriptor> nameToDesc, HashMap<String, FunctionDescriptor> nameToDummy){
		super(contexts, nameToDesc, nameToDummy);
		keyWord = "do";
	}

	@Override
	protected Pair<Boolean, Pair<String, Integer>> generateSource(Node node, int created, String resVar) throws Exception
	{
		if (node.getType() != Node.Type.COMPLEX) return new Pair<>(Boolean.FALSE, null);
		if (node.getSubNodes().get(0).getType() != Node.Type.VARIABLE) return new Pair<>(Boolean.FALSE, null);
		if (!node.getSubNodes().get(0).getResult().equals(keyWord)) return new Pair<>(Boolean.FALSE, null);

		int cnt = 0;
		String result = "_LOCAL_VAR_" + (created + cnt);
		cnt++;

		StringBuilder builder = new StringBuilder();
		builder.append("{\n");

		for (int i = 1; i < node.getSubNodes().size(); i++){
			var t = startingHandler.evalNode(node.getSubNodes().get(i), created + cnt, result);
			builder.append(t.first);
			cnt += t.second;
		}
		builder.append("}\n");

		return new Pair<>(Boolean.TRUE, new Pair<>(builder.toString(), cnt));
	}

}
