package ru.nsu.fit.javalisp.parser;

import ru.nsu.fit.javalisp.Node;

import java.io.FileReader;
import java.util.List;

/**
 * Abstract class for parsing
 */
public abstract class Parser {
	protected StateMachine machine;
	protected int line = 1;
	protected int position = 0;
	protected int level = 0;
	protected FileReader reader;
	public Parser(){
		machine = new StateMachine();
	}

	protected int nextChar() throws Exception{
		int value = reader.read();
		if (value == '\n'){
			line++;
			position = 0;
		}
		else {
			position++;
		}
		return value;
	}

	public abstract List<Node> parse() throws Exception;
}
