package ru.nsu.fit.ojp.Task_4.translator;

import java.util.ArrayList;
import java.util.List;

public class TranslationResult {
	public enum Value {
		SOURCE,
		FUNCTIONCALL
	}
	private Value value;
	private boolean success;
	private String var;
	private String src;
	private String funcName;
	private int paramsNumber;
	private List<String> params;

	private TranslationResult(){

	}


	public static class Builder{
		private Value value;
		private boolean success;
		private String var;
		private String src;
		private String funcName;
		private int paramsNumber;
		private List<String> params;

		public Builder(){
			params = new ArrayList<>();
		}
		public Builder setVar(String var){
			this.var = var;
			return this;
		}
		public Builder setSuccess(boolean success){
			this.success = success;
			return this;
		}

		public Builder setSrc(String src){
			this.src = src;
			return this;
		}

		public Builder setName(String name){
			this.funcName = name;
			return this;
		}

		public Builder setNumber(int number){
			this.paramsNumber = number;
			return this;
		}

		public Builder addParam(String param){
			this.params.add(param);
			return this;
		}

		public Builder setValue(Value value){
			this.value = value;
			return this;
		}

		public TranslationResult build(){
			TranslationResult result = new TranslationResult();
			result.value = value;
			result.success = success;
			result.var = var;
			result.src = src;
			result.funcName = funcName;
			result.paramsNumber = paramsNumber;
			result.params = params;
			return result;
		}
	}

	public int getParamsNumber() {
		return paramsNumber;
	}

	public List<String> getParams() {
		return params;
	}

	public String getFuncName() {
		return funcName;
	}

	public String getSrc() {
		return src;
	}

	public String getVar() {
		return var;
	}

	public Value getValue() {
		return value;
	}

}
