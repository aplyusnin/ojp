package ru.nsu.fit.ojp.plyusnin.Task_3;

import java.util.TreeMap;

public class Register {

	private static Register instance = null;
	private TreeMap<Integer, Long> values;
	private Register(){
		values = new TreeMap<>();
	}
	public static Register getInstance(){
		if (instance == null) instance = new Register();
		return instance;
	}

	public void addValue(int id, long value){
		values.put(id, value);
	}

	public Long getMin(){
		Long ans = 0L;
		boolean init = false;
		for (var x : values.entrySet()){
			if (!init || ans > x.getValue()){
				init = true;
				ans = x.getValue();
			}
		}
		return ans;
	}

	public Long getMax(){
		Long ans = 0L;
		boolean init = false;
		for (var x : values.entrySet()){
			if (!init || ans < x.getValue()){
				init = true;
				ans = x.getValue();
			}
		}
		return ans;
	}


	public Double getAverage(){
		Double sum = 0.0;
		boolean init = false;
		for (var x : values.entrySet()){
			sum += x.getValue();
		}
		return sum / values.size();
	}
}
