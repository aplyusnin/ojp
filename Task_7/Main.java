package ru.nsu.fit.ojp.plyusnin.task_7;

import org.graalvm.polyglot.*;

import java.io.File;
import java.util.HashMap;

public class Main {

	public static void main(String[] args) throws Exception
	{
		Context context = Context.newBuilder().allowAllAccess(true).build();
		File file = new File("cpuinfo.bc");
		Source source = Source.newBuilder("llvm", file).build();
		Value t = context.eval(source);
		Value fn = t.getMember("cpuInfo");

		var res = fn.execute().as(HashMap.class);
		res.forEach((key, val) -> System.out.println(key + " : " + val + "\n"));
	}
}
