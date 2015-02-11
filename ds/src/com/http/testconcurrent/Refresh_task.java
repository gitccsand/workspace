package com.http.testconcurrent;

import java.util.TimerTask;
import java.util.concurrent.Semaphore;

public class Refresh_task extends TimerTask {
	
	private Semaphore semaphore;
	private int poolsize;
	
	public Refresh_task(Semaphore semaphore,int poolsize) {
//		super();
		this.semaphore = semaphore;
		this.poolsize = poolsize;
	}


	@Override
	public void run() {
//				int acquire = semaphore.drainPermits();//获得已分配令牌数
//				System.out.println(acquire);
				semaphore.release(poolsize);//释放已分配的令牌
				System.out.println("refresh: queued ?: " + semaphore.hasQueuedThreads());
//		super.run();
	}
	
	
//	private long interval;

//	public Timer(Semaphore semaphore,long interval) {
////		super();
//		this.semaphore = semaphore;
//		this.interval = interval;
//	}



//	@Override
//	public void run() {
//		while (true) {
//			try {
//				Thread.sleep(interval);//每个时间间隔刷新一次
//				System.out.println("======wakeup");
//				int acquire = semaphore.drainPermits();//获得已分配令牌数
//				semaphore.release(acquire);//释放已分配的令牌
//			} catch (InterruptedException e) {
//				e.printStackTrace();
//			}
//		}
////		super.run();
//	}
	
	

}
