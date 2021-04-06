package ru.nsu.fit.ojp.Task_4.translator.handlers;

import javassist.CtClass;
import ru.nsu.fit.ojp.Task_4.Node;
import ru.nsu.fit.ojp.Task_4.Pair;
import ru.nsu.fit.ojp.Task_4.translator.Context;
import ru.nsu.fit.ojp.Task_4.translator.FunctionDescriptor;

import java.util.HashMap;
import java.util.List;

public class LambdaHandler extends BasicHandler{

	private static int lambdas = 0;
	private static CtClass cc;

	public LambdaHandler(List<Context> contexts, HashMap<String, FunctionDescriptor> nameToDesc, HashMap<String, FunctionDescriptor> nameToDummy, CtClass cc){
		super(contexts, nameToDesc, nameToDummy);
		keyWord = "fn";
		this.cc = cc;
	}

	@Override
	protected Pair<Boolean, Pair<String, Integer>> generateSource(Node node, int created, String resVar) throws Exception
	{
		if (node.getType() != Node.Type.COMPLEX) return new Pair<>(Boolean.FALSE, null);
		Node st = node.getSubNodes().get(0);
		if (st.getType() != Node.Type.VARIABLE) return new Pair<>(Boolean.FALSE, null);

		if (!st.getResult().equals(keyWord)) return new Pair<>(Boolean.FALSE, null);

		//Context context = new Context();

		//parseArgs()
		/*for (int i = 1; i < contexts.size(); i++) {
			for (var x : contexts.get(i).getArgs().entrySet()){
				context.add
			}
		}*/

		return null;
	}

}
