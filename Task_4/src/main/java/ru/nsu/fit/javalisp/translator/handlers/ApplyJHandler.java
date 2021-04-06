package ru.nsu.fit.javalisp.translator.handlers;

import ru.nsu.fit.javalisp.Node;
import ru.nsu.fit.javalisp.Pair;
import ru.nsu.fit.javalisp.translator.Context;
import ru.nsu.fit.javalisp.translator.FunctionDescriptor;
import ru.nsu.fit.javalisp.translator.JavaInvoker;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Handler that applies java function to arguments
 */
public class ApplyJHandler extends BasicHandler {

	/**
	 * Create handler
	 * @param contexts - list of contexts
	 * @param nameToDesc - defined functions
	 * @param nameToDummy - declared functions
	 */
	public ApplyJHandler(List<Context> contexts, HashMap<String, FunctionDescriptor> nameToDesc, HashMap<String, FunctionDescriptor> nameToDummy){
		super(contexts, nameToDesc, nameToDummy);
	}

	@Override
	protected Pair<Boolean, Pair<String, Integer>> generateSource(Node node, int created, String resVar) throws Exception
	{
		if (node.getType() != Node.Type.COMPLEX){
			return new Pair<>(Boolean.FALSE, null);
		}
		Node node1 = node.getSubNodes().get(0);
		if (node1.getType() != Node.Type.JAVACALL){
			return new Pair<>(Boolean.FALSE, null);
		}
		int args = node.getSubNodes().size() - 1;

		var t = JavaInvoker.getFunction(node1.getResult(), args);

		if (t == null) throw new Exception("Unknown java call: " + node1.getResult());

		String type = t.first;
		String fName = JavaInvoker.normalizeName(node1.getResult());
		List<String> src = new ArrayList<>();
		List<String> vars = new ArrayList<>();

		int cnt = 0;

		for (int i = 1; i < node.getSubNodes().size(); i++){
			String name = "_LOCAL_VAR_" + (created + cnt);
			cnt++;
			vars.add(name);
			var t1 = startingHandler.evalNode(node.getSubNodes().get(i), created + cnt, name);
			cnt += t1.second;
			src.add(t1.first);
		}

		StringBuilder builder = new StringBuilder();

		boolean isNew = false;

		for (int i = 0; i < src.size(); i++) {
			builder.append(src.get(i));
		}
		if (t.first.equals("double") || t.first.equals("float")) {
			builder.append("{ ").append(resVar).append(" = new Double(").append(fName).append("(");
			isNew = true;
		}
		else if (t.first.equals("int") || t.first.equals("long")){
			builder.append("{ ").append(resVar).append(" = new Integer(").append(fName).append("(");
			isNew = true;
		}
		else {
			builder.append("{ ").append(resVar).append(" = new Object();\n").append(resVar).append(" = ").append(fName).append("(");
		}

		//builder.append("{ ").append(resVar).append(" = ").append(fName).append("(");
		for (int i = 0; i < vars.size(); i++) {
			if (t.second.get(i).equals("double")){
				builder.append("((Double)");
				builder.append(vars.get(i));
				builder.append(").doubleValue()");
			}
			else if(t.second.get(i).equals("int")){
				builder.append("((Integer)");
				builder.append(vars.get(i));
				builder.append(").intValue()");
			}
			else if (t.second.get(i).equals("boolean")){
				builder.append("((Boolean)");
				builder.append(vars.get(i));
				builder.append(").booleanValue()");
			}
			else{
				builder.append("(").append(vars.get(i)).append(")");
				builder.append(vars.get(i));
			}
			if (i + 1 < vars.size()) builder.append(", ");
		}
		if (isNew) builder.append(")");
		builder.append(");}\n");

		return new Pair<>(Boolean.TRUE, new Pair<>(builder.toString(), cnt));
	}

}
