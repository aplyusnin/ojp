package ru.nsu.fit.ojp.Task_4.parser;

import ru.nsu.fit.ojp.Task_4.Node;

import java.io.File;
import java.io.FileReader;
import java.util.LinkedList;
import java.util.List;
import java.util.Stack;

/**
 * Class for parsing Lisp source and generating a syntax forest of it.
 */
public class LispParser extends Parser {

	private Stack<Node> stack;
	private Stack<Character> parenthesis;

	private int nextChar;
	private LinkedList<Node> nodes;

	/**
	 * Instantiate parser
	 * @param filename name of Lisp source
	 * @throws Exception Can't open script
	 */
	public LispParser(String filename) throws Exception{
		super();
		File file = new File(filename);
		if (!file.canRead()) throw new Exception("Can't open script");
		reader = new FileReader(file);
		stack= new Stack<>();
		parenthesis = new Stack<>();
		machine.createState("Empty");
		machine.createState("Word");
		machine.createState("StringBody");
		machine.createState("StringEnd");
		machine.createState("Integer");
		machine.createState("Float");
		machine.createState("JavaCall");

		for (int i = 0; i <= 32; i++){
			machine.addTransition((char)i, "StringEnd", "Empty", this, LispParser.class.getDeclaredMethod("complete"));
			machine.addTransition((char)i, "Word", "Empty", this, LispParser.class.getDeclaredMethod("complete"));
			machine.addTransition((char)i, "Float", "Empty", this, LispParser.class.getDeclaredMethod("complete"));
			machine.addTransition((char)i, "Integer", "Empty", this, LispParser.class.getDeclaredMethod("complete"));
			machine.addTransition((char)i, "JavaCall", "Empty", this, LispParser.class.getDeclaredMethod("complete"));
			machine.addTransition((char)i, "StringBody", "StringBody", this, LispParser.class.getDeclaredMethod("addChar"));
			machine.addTransition((char)i, "Empty", "Empty");
		}
		for (int i = 0; i < 26; i++){
			machine.addTransition((char)(i + 'a'), "StringBody", "StringBody", this, LispParser.class.getDeclaredMethod("addChar"));
			machine.addTransition((char)(i + 'A'), "StringBody", "StringBody", this, LispParser.class.getDeclaredMethod("addChar"));
			machine.addTransition((char)(i + 'a'), "Empty", "Word", this, LispParser.class.getDeclaredMethod("newWordToken"));
			machine.addTransition((char)(i + 'A'), "Empty", "Word", this, LispParser.class.getDeclaredMethod("newWordToken"));
			machine.addTransition((char)(i + 'a'), "Word", "Word", this, LispParser.class.getDeclaredMethod("addChar"));
			machine.addTransition((char)(i + 'A'), "Word", "Word", this, LispParser.class.getDeclaredMethod("addChar"));
			machine.addTransition((char)(i + 'a'), "JavaCall", "JavaCall", this, LispParser.class.getDeclaredMethod("addChar"));
			machine.addTransition((char)(i + 'A'), "JavaCall", "JavaCall", this, LispParser.class.getDeclaredMethod("addChar"));
		}
		machine.addTransition('-', "Empty", "Integer", this, LispParser.class.getDeclaredMethod("newIntToken"));
		for (int i = 0; i < 10; i++){
			machine.addTransition((char)(i + '0'), "Empty", "Integer", this, LispParser.class.getDeclaredMethod("newIntToken"));
			machine.addTransition((char)(i + '0'), "Integer", "Integer", this, LispParser.class.getDeclaredMethod("addChar"));
			machine.addTransition((char)(i + '0'), "Float", "Float", this, LispParser.class.getDeclaredMethod("addChar"));
			machine.addTransition((char)(i + '0'), "StringBody", "StringBody", this, LispParser.class.getDeclaredMethod("addChar"));
			machine.addTransition((char)(i + '0'), "Word", "Word", this, LispParser.class.getDeclaredMethod("addChar"));
			machine.addTransition((char)(i + '0'), "JavaCall", "JavaCall", this, LispParser.class.getDeclaredMethod("addChar"));
		}
		machine.addTransitions("[(", "Empty", "Empty", this, LispParser.class.getDeclaredMethod("newLevel"));
		machine.addTransitions("[(", "StringBody", "StringBody", this, LispParser.class.getDeclaredMethod("addChar"));

		machine.addTransitions(")]", "Empty", "Empty", this, LispParser.class.getDeclaredMethod("complete"));
		machine.addTransitions(")]", "StringBody", "StringBody", this, LispParser.class.getDeclaredMethod("addChar"));
		machine.addTransitions(")]", "Word", "Empty", this, LispParser.class.getDeclaredMethod("complete"));
		machine.addTransitions(")]", "Integer", "Empty", this, LispParser.class.getDeclaredMethod("complete"));
		machine.addTransitions(")]", "StringEnd", "Empty", this, LispParser.class.getDeclaredMethod("complete"));
		machine.addTransitions(")]", "Float", "Empty", this, LispParser.class.getDeclaredMethod("complete"));
		machine.addTransitions(")]", "JavaCall", "Empty", this, LispParser.class.getDeclaredMethod("complete"));

		machine.addTransitions(".,", "Integer", "Float", this, LispParser.class.getDeclaredMethod("toFloat"));
		machine.addTransitions(".,", "Empty", "Float", this, LispParser.class.getDeclaredMethod("newFloatToken"));
		machine.addTransitions(".", "Word", "Word", this, LispParser.class.getDeclaredMethod("addChar"));
		machine.addTransitions(".,", "StringBody", "StringBody", this, LispParser.class.getDeclaredMethod("addChar"));
		machine.addTransitions("./", "JavaCall", "JavaCall", this, LispParser.class.getDeclaredMethod("addChar"));

		machine.addTransitions("\"", "Empty", "StringBody", this, LispParser.class.getDeclaredMethod("newString"));
		machine.addTransitions("\"", "StringBody", "StringEnd");
		String aux = "!:#$%^&*-+=_<>=?|~/`@";
		machine.addTransitions(aux.substring(0, 20), "Word", "Word", this, LispParser.class.getDeclaredMethod("addChar"));
		machine.addTransitions(aux.substring(0, 20), "Empty", "Word", this, LispParser.class.getDeclaredMethod("newWordToken"));
		machine.addTransitions("@", "Empty", "JavaCall", this, LispParser.class.getDeclaredMethod("newJavaCall"));
		machine.addTransitions(aux, "StringBody", "StringBody", this, LispParser.class.getDeclaredMethod("addChar"));
		machine.setErrorTransitionFunction(this, LispParser.class.getDeclaredMethod("error"));
		machine.setStartState("Empty");
	}

	private void newIntToken(){
		Node node = new Node();
		node.setType(Node.Type.INT);
		stack.push(node);
		node.addChar((char)nextChar);
	}

	private void newFloatToken(){
		Node node = new Node();
		node.setType(Node.Type.FLOAT);
		stack.push(node);
		node.addChar((char)nextChar);
	}

	private void newJavaCall(){
		Node node = new Node();
		node.setType(Node.Type.JAVACALL);
		stack.push(node);
	}


	private void addChar(){
		stack.peek().addChar((char)nextChar);
	}

	private void error() throws Exception{
		throw  new Exception("Found invalid symbol at line: " + line + ", position: " + position);
	}

	private void newWordToken(){
		Node node = new Node();
		node.setType(Node.Type.VARIABLE);
		node.addChar((char)nextChar);
		stack.push(node);
	}

	private void verify(Node node) throws Exception{
		if (node.getType() == Node.Type.VARIABLE){
			try {
				Integer.parseInt(node.getResult());
				node.setType(Node.Type.INT);
			}
			catch (Exception e) {

				try {
					Double.parseDouble(node.getResult());
					node.setType(Node.Type.FLOAT);
				}
				catch (Exception e1) {

				}
			}
			if (node.getResult().equals("true") || node.getResult().equals("false")){
				node.setType(Node.Type.BOOL);
			}
		}
		if (node.getType() == Node.Type.JAVACALL){
			long t = node.getResult().chars().filter(e -> e == '/').count();
			if (t != 1)
				throw new Exception("Invalid java call: " + node.getResult());
		}
	}

	private void complete() throws Exception{
		if (nextChar == ')' || nextChar == ']'){
			if (nextChar == ')' && parenthesis.peek() != '(') throw new Exception("Invalid close bracket at line: " + line + ", position: " + position);
			if (nextChar == ']' && parenthesis.peek() != '[') throw new Exception("Invalid close bracket at line: " + line + ", position: " + position);
			parenthesis.pop();
			level --;
			Node node = stack.pop();
			//close brackets
			if (node.getType() == Node.Type.COMPLEX){
				if (stack.size() > 0) {
					stack.peek().addNode(node);
				}
				else{
					nodes.add(node);
				}
			}
			else{
				node.process();
				verify(node);
				if (stack.size() > 0)
				{
					Node node1 = stack.pop();
					node1.addNode(node);
					if (stack.size() == 0){
						nodes.add(node1);
					}
					else{
						stack.peek().addNode(node1);
					}
				}
				else{
					throw new Exception("Error, there is no brackets on line: " + line + ", position: " + position);
				}
			}
		}
		else{
			Node node = stack.pop();
			node.process();
			verify(node);
			if (stack.size() > 0){
				stack.peek().addNode(node);
			}
			else{
				throw new Exception("Error, there is no brackets on line: " + line + ", position: " + position);
			}
		}
	}

	private void toFloat(){
		stack.peek().setType(Node.Type.FLOAT);
		stack.peek().addChar((char)nextChar);
	}

	private void newLevel(){
		level++;
		Node node = new Node();
		node.setType(Node.Type.COMPLEX);
		parenthesis.push((char)nextChar);
		stack.push(node);
	}

	private void newString(){
		Node node = new Node();
		node.setType(Node.Type.STRING);
		stack.push(node);
	}

	/**
	 * Parse source
	 * @return syntax forest
	 */
	public List<Node> parse() throws Exception{
		nodes = new LinkedList<>();

		while ((nextChar = nextChar()) != -1){
			machine.feedChar((char)nextChar);
		}
		if (level != 0) throw new Exception("Incorrect source");
		return nodes;
	}

}
