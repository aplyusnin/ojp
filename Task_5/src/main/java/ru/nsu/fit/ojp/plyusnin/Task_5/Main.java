package ru.nsu.fit.ojp.plyusnin.Task_5;



public class Main {
	private static class MyBarrier {
		private int permits;
		private int current;
		public MyBarrier(int permits){
			this.permits = permits;
			this.current = permits;
		}
		public synchronized void acquire() throws InterruptedException{
			if (current == 1){
				current = permits;
				notifyAll();
			}
			else{
				current--;
				wait();
			}
		}


	}
	public static void main(String[] args){
		Object o1 = new Object();
		Object o2 = new Object();
		MyBarrier barrier = new MyBarrier(2);

		synchronized (o1) {
			new Thread(() -> {
				try {
					synchronized (o2) {
						barrier.acquire();
						synchronized (o1) {
							System.out.print("Hi from thead");
						}
					}
				}
				catch (InterruptedException ignored) {}
			}).start();
			try{
				barrier.acquire();
			}
			catch (InterruptedException ignored){}
			synchronized (o2){
				System.out.print("Hi from main");
			}
		}
	}
}
