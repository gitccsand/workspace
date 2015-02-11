package com.http.testconcurrent;

import java.util.concurrent.Semaphore;

public class Refresh_sleep_timer extends Thread {
	private long interval;
	private int pool_size;
	private Semaphore semaphore;

	public Refresh_sleep_timer(Semaphore semaphore,long interval,int pool_size) {
//		super();
		this.semaphore = semaphore;
		this.interval = interval;
		this.pool_size = pool_size;
	}



	@Override
	public void run() {
		while (true) {
			try {
				Thread.sleep(interval);//每个时间间隔刷新一次
				System.out.println("======wakeup");
//				int acquire = semaphore.drainPermits();//获得已分配令牌数
//				semaphore.release(acquire);//释放已分配的令牌
				semaphore.release(pool_size);
				
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
//		super.run();
	}
}
