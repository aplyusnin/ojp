package ru.nsu.fit.ojp.Task_4.translator.handlers;

import ru.nsu.fit.ojp.Task_4.Node;
import ru.nsu.fit.ojp.Task_4.Pair;
import ru.nsu.fit.ojp.Task_4.translator.Context;
import ru.nsu.fit.ojp.Task_4.translator.FunctionDescriptor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Handler that applies VARARGS function to arguments
 */
public class ApplyVHandler extends BasicHandler {

	/**
	 * Create handler
	 * @param contexts - list of contexts
	 * @param nameToDesc - defined functions
	 * @param nameToDummy - declared functions
	 */
	public ApplyVHandler(List<Context> contexts, HashMap<String, FunctionDescriptor> nameToDesc, HashMap<String, FunctionDescriptor> nameToDummy){
		super(contexts, nameToDesc, nameToDummy);
	}

	@Override
	protected Pair<Boolean, Pair<String, Integer>> generateSource(Node node, int created, String resVar) throws Exception
	{
		if (node.getType() != Node.Type.COMPLEX){
			return new Pair<>(Boolean.FALSE, null);
		}
		Node s = node.getSubNodes().get(0);
		if (s.getType() != Node.Type.VARIABLE) {
			return new Pair<>(Boolean.FALSE, null);
		}

		FunctionDescriptor func;

		if (nameToDesc.containsKey(s.getResult()))
			func = nameToDesc.get(s.getResult());
		else
			return new Pair<>(Boolean.FALSE, null);

		if (func.getArgsCount() != -1) return new Pair<>(Boolean.FALSE, null);

		StringBuilder builder = new StringBuilder();

		int cnt = 0;

		List<String> vars = new ArrayList<>();
		List<String> src = new ArrayList<>();
		for (int i = 1; i < node.getSubNodes().size(); i++) {
			String name = "_LOCAL_VAR_" + (created + cnt);
			vars.add(name);
			cnt++;
			try {
				var t = startingHandler.evalNode(node.getSubNodes().get(i), created + cnt, name);
				cnt += t.second;
				src.add(t.first);
			}
			catch (Exception e){
				return new Pair<>(Boolean.FALSE, null);
			}
		}

		String acc = "_LOCAL_VAR_" + (created + cnt);


		for (int i = 0; i < src.size(); i++) {
			builder.append(src.get(i));
		}

		builder.append(acc).append(" = new Object[").append(vars.size()).append("];\n");
		for (int i = 0; i < vars.size(); i++){
			builder.append("((Object[])").append(acc).append(")[").append(i).append("] = ").append(vars.get(i)).append(";\n");
		}
		cnt++;

		builder.append("{ ").append(resVar).append(" = ").append(func.getName()).append("((Object[])").append(acc).append(");}\n");
		/*for (int i = 0; i < vars.size(); i++) {
			builder.append(vars.get(i));
			if (i + 1 < vars.size()) builder.append(", ");
		}*/
		//builder.append(");}\n");

		return new Pair<>(Boolean.TRUE, new Pair<>(builder.toString(), cnt));
	}

}
