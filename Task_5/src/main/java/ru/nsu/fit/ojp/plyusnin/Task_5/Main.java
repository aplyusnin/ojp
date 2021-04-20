package ru.nsu.fit.ojp.plyusnin.Task_5;

public class Main {

	public static void main(String[] args){
		Object o1 = new Object();
		Object o2 = new Object();

		synchronized (o1) {
			new Thread(() -> {
				try {
					synchronized (o2) {
						Thread.sleep(1000);
						synchronized (o1) {
							System.out.print("Hi from thead");
						}
					}
				}
				catch (InterruptedException ignored) {}
			}).start();
			try{
				Thread.sleep(100);
			}
			catch (InterruptedException ignored){}
			synchronized (o2){
				System.out.print("Hi from main");
			}
		}
	}
}
