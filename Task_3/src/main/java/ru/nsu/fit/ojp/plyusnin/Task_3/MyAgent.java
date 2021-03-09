package ru.nsu.fit.ojp.plyusnin.Task_3;

import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtMethod;

import java.io.ByteArrayInputStream;
import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.IllegalClassFormatException;
import java.lang.instrument.Instrumentation;
import java.security.ProtectionDomain;


//-javaagent:build/libs/Task_3-1.0-SNAPSHOT.jar
public class MyAgent {
	public static void premain(String agentArgs, Instrumentation inst){
		//System.out.println("Hello from Java Agent!");
		inst.addTransformer(new MyTransformer());
		Runtime.getRuntime().addShutdownHook(new Thread(() -> System.out.println(inst.getAllLoadedClasses().length + " min: "
		                                                                        + Register.getInstance().getMin() + " max: "
				                                                                + Register.getInstance().getMax() + " average: "
		                                                                        + Register.getInstance().getAverage())));
	}


	public static class MyTransformer implements ClassFileTransformer{

		@Override
		public byte[] transform(ClassLoader loader, String className, Class<?> classBeingRedefined,
		                        ProtectionDomain protectionDomain, byte[] classfileBuffer) throws IllegalClassFormatException {
			if (className.equals("ru/nsu/fit/ojp/plyusnin/Task_3/TransactionProcessor")){
				try {
					ClassPool cp = ClassPool.getDefault();
					CtClass ctClass = cp.makeClass(new ByteArrayInputStream(classfileBuffer));
					CtMethod method = ctClass.getDeclaredMethod("processTransaction");
					method.insertBefore("{$1 += 99;}");

					method.addLocalVariable("initTime", CtClass.longType);
					method.insertBefore("{initTime = System.currentTimeMillis();}");
					method.insertAfter("{ru.nsu.fit.ojp.plyusnin.Task_3.Register.getInstance().addValue($1, System.currentTimeMillis() - initTime);}");


					byte[] modifiedClassfileBuffer = ctClass.toBytecode();
					ctClass.detach();
					return modifiedClassfileBuffer;
				}
				catch (Exception e){
					e.printStackTrace();
					//System.out.println(e.getStackTrace());
				}
			}
			return classfileBuffer;
		}
	}
	public static class DataCollector{

	}
}
