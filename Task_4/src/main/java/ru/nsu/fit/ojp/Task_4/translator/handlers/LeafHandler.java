package ru.nsu.fit.ojp.Task_4.translator.handlers;

import ru.nsu.fit.ojp.Task_4.Node;
import ru.nsu.fit.ojp.Task_4.Pair;
import ru.nsu.fit.ojp.Task_4.translator.Context;
import ru.nsu.fit.ojp.Task_4.translator.FunctionDescriptor;
import ru.nsu.fit.ojp.Task_4.translator.JavaInvoker;

import java.util.HashMap;
import java.util.List;

/**
 * Handler that evaluates value of single word
 */
public class LeafHandler extends BasicHandler {

	/**
	 * Create handler
	 * @param contexts - list of contexts
	 * @param nameToDesc - defined functions
	 * @param nameToDummy - declared functions
	 */

	public LeafHandler(List<Context> contexts, HashMap<String, FunctionDescriptor> nameToDesc, HashMap<String, FunctionDescriptor> nameToDummy){
		super(contexts, nameToDesc, nameToDummy);
	}

	@Override
	public Pair<Boolean, Pair<String, Integer>> generateSource(Node node, int created, String resVar) throws Exception
	{
		if (node.getType() == Node.Type.COMPLEX){
			return new Pair<>(Boolean.FALSE, null);
		}
		StringBuilder builder = new StringBuilder();
		int cnt = 0;
		switch (node.getType())
		{
			case INT -> {
				builder.append("").append(resVar).append(" = new Integer(").append(node.getResult()).append(");\n");
			}
			case FLOAT -> {
				builder.append("").append(resVar).append(" = new Double(").append(node.getResult()).append(");\n");
			}
			case STRING -> {
				builder.append("").append(resVar).append(" = new String(\"").append(node.getResult()).append("\");\n");
			}
			case BOOL -> {
				builder.append("").append(resVar).append(" = new Boolean(").append(node.getResult()).append(");\n");
			}
			case VARIABLE -> {
				boolean found = false;
				for (int i = contexts.size() - 1; !found && i >= 0; i--) {
					if (contexts.get(i).containsVar(node.getResult())) {
						builder.append("").append(resVar).append(" = ").append(contexts.get(i).getVar(node.getResult())).append(";\n");
						found = true;
					}
				}
				if (!found) {
					if (nameToDesc.containsKey(node.getResult())) {
						FunctionDescriptor desc = nameToDesc.get(node.getResult());
						if (desc.getArgsCount() == 0) {
							builder.append("").append(resVar).append(" = ").append(desc.getName()).append("();\n");
							found = true;
						}
					}
					else if (nameToDummy.containsKey(node.getResult())) {
						FunctionDescriptor desc = nameToDummy.get(node.getResult());
						if (desc.getArgsCount() == 0) {
							builder.append("").append(resVar).append(" = ").append(desc.getName()).append("();\n");
							found = true;
						}
					}
				}
				if (!found)
					return new Pair<>(Boolean.FALSE, null);
//					throw new Exception("Unknown symbol " + node.getResult());
			}
			case JAVACALL -> {
				var t = JavaInvoker.getFunction(node.getResult(), 0);
				String fName = JavaInvoker.normalizeName(node.getResult());
				builder.append("").append(resVar).append(" = ").append(fName).append("();\n");
			}
		}
	return new Pair<>(Boolean.TRUE, new Pair<>(builder.toString(), cnt));
	}

}
