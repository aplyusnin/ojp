package ru.nsu.fit.javalisp.parser;

import java.lang.reflect.Method;
import java.util.HashMap;

/**
 * Class for state machine
 */
public class StateMachine {
	private HashMap<String, State> states;

	private State current;
	private State error;

	/**
	 * Instantiate state machine
	 */
	public StateMachine(){
		states = new HashMap<>();
		error = new State("Error");
		states.put("Error", error);
	}

	/**
	 * Set function for non-existing transition
	 * @param o - object, whose method will be invoked.
	 * @param m - method to be invoked. If null no method will be invoked.
	 */
	public void setErrorTransitionFunction(Object o, Method m){
		for (var state : states.entrySet()){
			state.getValue().setErrorTransition(o, m);
		}
	}

	/**
	 * Set starting state
	 * @param name state name
	 * @throws Exception There is no such state
	 */
	public void setStartState(String name) throws Exception{
		if (states.containsKey(name)) current = states.get(name);
		else throw new Exception("No such state");
	}

	/**
	 * Create new state
	 * @param name state name
	 * @throws Exception State with given name already exists
	 */
	public void createState(String name) throws Exception {
		if (states.containsKey(name)) throw new Exception("State already exists");
		State state = new State(name);
		states.put(name, state);
		state.setErrorState(error);
	}

	private void addTransition(Character ch, State from, State to, Object o, Method m){
		from.addTransition(ch, to, o, m);
	}

	/**
	 * Add transition from one state to another
	 * @param ch character to make transition
	 * @param from state name, from transition will be made
	 * @param to state name, where transition will be made
	 * @param o object, whose method will be invoked
	 * @param m method that will be invoked during transition
	 * @throws Exception There is no states
	 */
	public void addTransition(Character ch, String from, String to, Object o, Method m) throws Exception {
		if (!states.containsKey(from)) throw new Exception("There is no state: " + from);
		if (!states.containsKey(to)) throw new Exception("There is no state: " + to);
		addTransition(ch, states.get(from), states.get(to), o, m);
	}

	/**
	 * Add transition from one state to another
	 * @param ch character to make transition
	 * @param from state name, from transition will be made
	 * @param to state name, where transition will be made
	 * @throws Exception There is no states
	 */
	public void addTransition(Character ch, String from, String to) throws Exception {
		if (!states.containsKey(from)) throw new Exception("There is no state: " + from);
		if (!states.containsKey(to)) throw new Exception("There is no state: " + to);
		addTransition(ch, states.get(from), states.get(to), null, null);
	}

	/**
	 * Add multiple transition from one state to another
	 * @param s set of characters to make transition
	 * @param from state name, from transition will be made
	 * @param to state name, where transition will be made
	 * @throws Exception There is no states
	 */
	public void addTransitions(String s, String from, String to) throws Exception {
		if (!states.containsKey(from)) throw new Exception("There is no state: " + from);
		if (!states.containsKey(to)) throw new Exception("There is no state: " + to);
		State _from = states.get(from);
		State _to = states.get(to);
		for (int i = 0; i < s.length(); i++){
			addTransition(s.charAt(i), _from, _to, null, null);
		}
	}

	/**
	 * Add multiple transition from one state to another
	 * @param s set of characters to make transition
	 * @param from state name, from transition will be made
	 * @param to state name, where transition will be made
	 * @param o object, whose method will be invoked
	 * @param m method that will be invoked during transition
	 * @throws Exception There is no states
	 */
	public void addTransitions(String s, String from, String to, Object o, Method m) throws Exception {
		if (!states.containsKey(from)) throw new Exception("There is no state: " + from);
		if (!states.containsKey(to)) throw new Exception("There is no state: " + to);
		State _from = states.get(from);
		State _to = states.get(to);
		for (int i = 0; i < s.length(); i++){
			addTransition(s.charAt(i), _from, _to, o, m);
		}
	}


	/**
	 * Perform transition.
	 * @param ch - char to make transition
	 * @return new state
	 * @throws Exception can't make transition or got exception in
	 */
	public State feedChar(char ch) throws Exception {
		return current = current.makeTransition(ch);
	}

	/**
	 * Get current state name
	 * @return current state name
	 */
	public String getCurrentStateName(){
		return current.getName();
	}
}
