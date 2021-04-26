package ru.nsu.fit.ojp.plyusnin.task_6;
public class Main {

	public static void main(String[] args){
		var t = MyJNIClass.getCpuInfo();

		for (var x : t.entrySet()){
			System.out.print(x.getKey() + " : " + x.getValue());
		}
	}
}
