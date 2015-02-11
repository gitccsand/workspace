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
//				int acquire = semaphore.drainPermits();//����ѷ���������
//				System.out.println(acquire);
				semaphore.release(poolsize);//�ͷ��ѷ��������
				System.out.println("refresh: queued ?: " + semaphore.hasQueuedThreads());
//		super.run();
	}
	
	

	
	

}
