package ru.nsu.fit.javalisp.translator.handlers;

import ru.nsu.fit.javalisp.Node;
import ru.nsu.fit.javalisp.Pair;
import ru.nsu.fit.javalisp.translator.Context;
import ru.nsu.fit.javalisp.translator.FunctionDescriptor;

import java.util.HashMap;
import java.util.List;


/**
 * Handler that evaluates let construction
 */
public class LetHandler extends BasicHandler {

	/**
	 * Create handler
	 * @param contexts - list of contexts
	 * @param nameToDesc - defined functions
	 * @param nameToDummy - declared functions
	 */

	public LetHandler(List<Context> contexts, HashMap<String, FunctionDescriptor> nameToDesc, HashMap<String, FunctionDescriptor> nameToDummy){
		super(contexts, nameToDesc, nameToDummy);
		keyWord = "let";
	}

	@Override
	protected Pair<Boolean, Pair<String, Integer>> generateSource(Node node, int created, String resVar) throws Exception
	{
		if (node.getType() != Node.Type.COMPLEX){
			return new Pair<>(Boolean.FALSE, null);
		}
		if (node.getSubNodes().size() != 3){
			return new Pair<>(Boolean.FALSE, null);
		}
		String name = node.getSubNodes().get(0).getResult();
		if (!name.equals(keyWord)){
			return new Pair<>(Boolean.FALSE, null);
		}

		Node bindings = node.getSubNodes().get(1);

		if (bindings.getType() != Node.Type.COMPLEX){
			return new Pair<>(Boolean.FALSE, null);
		}

		Context context = new Context();

		contexts.add(context);
		int cnt = 0;
		StringBuilder src = new StringBuilder();
		for (var x : bindings.getSubNodes()){
			if (x.getType() != Node.Type.COMPLEX){
				contexts.remove(contexts.size() - 1);
				return new Pair<>(Boolean.FALSE, null);
			}
			if (x.getSubNodes().size() != 2){
				contexts.remove(contexts.size() - 1);
				return new Pair<>(Boolean.FALSE, null);
			}
			Node bind = x.getSubNodes().get(0);
			Node value = x.getSubNodes().get(1);
			if (bind.getType() != Node.Type.VARIABLE){
				contexts.remove(contexts.size() - 1);
				return new Pair<>(Boolean.FALSE, null);
			}
			String bindName = "_LOCAL_VAR_" + (created + cnt);
			if (context.containsVar(bind.getResult())){
				contexts.remove(contexts.size() - 1);
				return new Pair<>(Boolean.FALSE, null);
			}
			cnt++;
			var t = startingHandler.evalNode(value, created + cnt, bindName);
			src.append(t.first);
			cnt += t.second;
			context.add(bind.getResult(), bindName);
		}

		Node function = node.getSubNodes().get(2);

		var t = startingHandler.evalNode(function, created + cnt, resVar);

		cnt += t.second;
		src.append(t.first);
		contexts.remove(contexts.size() - 1);
		return new Pair<>(Boolean.TRUE, new Pair<>(src.toString(), cnt));
	}

}
