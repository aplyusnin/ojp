package ru.nsu.fit.ojp.Task_4.translator;

/**
 * Description of declared function
 */
public class FunctionDescriptor {
	private final String name;
	private final int argsCount;

	public FunctionDescriptor(String name, int argsCount){
		this.name = name;
		this.argsCount = argsCount;
	}

	public String getName() {
		return name;
	}

	public int getArgsCount() {
		return argsCount;
	}

}
