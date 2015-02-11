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
	
	

	
	

}
