package ru.nsu.fit.ojp.plyusnin.Task_3;

public class TransactionProcessor {
	public void processTransaction(int txNum) throws Exception{
		System.err.println("Processing tx: " + txNum);
		int sleep = (int) (Math.random() * 1000);
		Thread.sleep(sleep);
		System.err.println(String.format("tx: %d completed", txNum));
	}
	public static void main(String[] args) throws Exception{
		//System.out.println("Hello from main");
		TransactionProcessor tp = new TransactionProcessor();
		for (int i = 0; i < 10; ++i) {
			tp.processTransaction(i);
		}
	}
}
