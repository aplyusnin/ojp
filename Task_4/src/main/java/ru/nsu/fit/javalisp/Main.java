package ru.nsu.fit.javalisp;

import ru.nsu.fit.javalisp.parser.LispParser;
import ru.nsu.fit.javalisp.parser.Parser;
import ru.nsu.fit.javalisp.translator.LispTransformer;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;

public class Main {

	public static void hui(){
		System.out.print("HUI");
	}


	public static class Ahahah{
		public Ahahah(){

		}

		void eblan(){
			hui();
		}

	}

	public static void main(String[] args) throws Exception
	{
		Parser parser = new LispParser(args[0]);

		HashMap<Object, Object> vars = new HashMap<>();

		Object a = new Object(), b = new Object(), c = new Object();

		vars.put(a, b);
		vars.put(a, c);

		Ahahah ahahah = new Ahahah();
		ahahah.eblan();


		List<Node> nodeList = parser.parse();

		LispTransformer transformer = new LispTransformer();

		Class clazz = transformer.generate(nodeList);
		Object source = clazz.newInstance();
		Method method1 = clazz.getDeclaredMethod("initGlobals");
		method1.invoke(source);
		Method method2 = clazz.getDeclaredMethod("evaluate");
		method2.invoke(source);
	}
}
