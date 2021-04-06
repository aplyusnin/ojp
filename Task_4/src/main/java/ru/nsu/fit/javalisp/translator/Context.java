package ru.nsu.fit.javalisp.translator;

import java.util.HashMap;

/**
 * Context class for variables
 */
public class Context {
	private HashMap<String, String> args;
	private HashMap<String, String> inverse;

	public Context(){
		args = new HashMap<>();
		inverse = new HashMap<>();
	}

	/**
	 * Add variable to context
	 * @param argName in-source name
	 * @param localName in-bytecode name
	 */
	public void add(String argName, String localName){
		args.put(argName, localName);
		inverse.put(localName, argName);
	}

	/**
	 * Get variable in-bytecode name
 	 * @param arg in-source name
	 * @return in-bytecode name
	 */
	public String getVar(String arg){
		if (args.containsKey(arg)) return args.get(arg);
		return arg;
	}

	/**
	 * Check if variable exists
	 * @param arg - variable name
	 * @return true, if variable exists
	 */
	public boolean containsVar(String arg){
		return args.containsKey(arg);
	}

	/**
	 * Get number of variables in context
	 * @return number of variables in context
	 */
	public int size(){
		return args.size();
	}

	public String getInverse(String name){
		return inverse.get(name);
	}
	public HashMap<String, String> getArgs() {
		return args;
	}
}
