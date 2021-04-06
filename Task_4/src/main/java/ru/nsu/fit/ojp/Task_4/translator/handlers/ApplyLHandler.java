package ru.nsu.fit.ojp.Task_4.translator.handlers;

import ru.nsu.fit.ojp.Task_4.Node;
import ru.nsu.fit.ojp.Task_4.Pair;
import ru.nsu.fit.ojp.Task_4.translator.Context;
import ru.nsu.fit.ojp.Task_4.translator.FunctionDescriptor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ApplyLHandler extends BasicHandler {

	public ApplyLHandler(List<Context> contexts, HashMap<String, FunctionDescriptor> nameToDesc, HashMap<String, FunctionDescriptor> nameToDummy) {
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
		else if (nameToDummy.containsKey(s.getResult()))
			func = nameToDummy.get(s.getResult());
		else
			return new Pair<>(Boolean.FALSE, null);

		StringBuilder builder = new StringBuilder();

		int cnt = 0;

		List<String> vars = new ArrayList<>();
		List<String> src = new ArrayList<>();
		for (int i = 1; i < node.getSubNodes().size(); i++) {
			if (node.getSubNodes().get(i).getType() == Node.Type.VARIABLE){
				String name;
				if (nameToDesc.containsKey(node.getSubNodes().get(i).getResult())){
					name = nameToDesc.get(node.getSubNodes().get(i).getResult()).getName();
					vars.add("(\"" + name + "\")");
					continue;
				}
				else if (nameToDummy.containsKey(node.getSubNodes().get(i).getResult())){
					name = nameToDummy.get(node.getSubNodes().get(i).getResult()).getName();
					vars.add("(\"" + name + "\")");
					continue;
				}
			}

			String name = "_LOCAL_VAR_" + (created + cnt);
			vars.add(name);
			cnt++;
			var t = startingHandler.evalNode(node.getSubNodes().get(i), created + cnt, name);
			cnt += t.second;
			src.add(t.first);
		}

		if (func.getArgsCount() == -1) return new Pair<>(Boolean.FALSE, null);

		if (vars.size() != func.getArgsCount()) throw new Exception("Waited for: " + func.getArgsCount() + "arguments, found: " + vars.size() + " arguments");

		for (int i = 0; i < src.size(); i++) {
			builder.append(src.get(i));
		}

		builder.append("").append(resVar).append(" = ").append(func.getName()).append("(");
		for (int i = 0; i < vars.size(); i++) {
			builder.append(vars.get(i));
			if (i + 1 < vars.size()) builder.append(", ");
		}
		builder.append(");\n");

		return new Pair<>(Boolean.TRUE, new Pair<>(builder.toString(), cnt));
	}

}
