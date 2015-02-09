package com.http.testconcurrent;

import java.util.TimerTask;
import java.util.concurrent.Semaphore;

public class Refresh_sleep_timer extends Thread {
	
	private Semaphore semaphore;
	private int poolsize;
	private long interval;
	
	public Refresh_sleep_timer(Semaphore semaphore,long interval,int poolsize) {
//		super();
		this.semaphore = semaphore;
		this.poolsize = poolsize;
		this.interval = interval;
	}

	@Override
	public void run() {
		while (true) {
			try {
//				System.out.println("======wakeup");
//				int acquire = semaphore.drainPermits();//����ѷ���������
//				semaphore.release(acquire);//�ͷ��ѷ��������
				semaphore.release(poolsize);
				Thread.sleep(interval);//ÿ��ʱ����ˢ��һ��
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
//		super.run();
	}

}
