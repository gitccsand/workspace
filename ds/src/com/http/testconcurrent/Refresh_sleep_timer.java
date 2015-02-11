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
				Thread.sleep(interval);//ÿ��ʱ����ˢ��һ��
				System.out.println("======wakeup");
//				int acquire = semaphore.drainPermits();//����ѷ���������
//				semaphore.release(acquire);//�ͷ��ѷ��������
				semaphore.release(pool_size);
				
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
//		super.run();
	}
}