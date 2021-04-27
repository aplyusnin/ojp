package ru.nsu.fit.ojp.plyusnin.task_6;

import java.util.Map;

public class MyJNIClass {
	static {
		System.loadLibrary("cpu");
	}

	static native Map<String,String> getCpuInfo();

}
