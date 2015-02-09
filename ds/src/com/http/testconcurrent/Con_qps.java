package com.http.testconcurrent;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import java.util.Timer;
import java.util.concurrent.Semaphore;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
@WebServlet("/Con_qps.do")
public class Con_qps extends HttpServlet {
	
	//ʵ������
	private Semaphore pool;//���Ƴ� �ź���ʵ��
//	private Timer pool_timer;//��ʱ��
	private Refresh_task refresh_task;//ˢ������
	
	

	@Override
	public void init() throws ServletException {
		pool=new Semaphore(3);//��ʼ�����Ƴ� 3������
		
		refresh_task = new Refresh_task(pool,3);
		
		//��ʼ��ʱˢ�����Ƴ� 10��ˢ��һ��
		Timer pool_timer = new Timer();		
		pool_timer.schedule(refresh_task, 5, 20000);
		
//		super.init();
	}



	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		response.setContentType("text/html; charset=gb2312");		
		PrintWriter output = response.getWriter();
		
//		System.out.println("queued ?: " + pool.hasQueuedThreads());
		if(pool.availablePermits()==0 ){//���ƺľ�������
			output.write("�������þ������Ժ�����");
			return;
		}
		
		try {
			pool.acquire();//��ȡ����
			output.write("�������");//����
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
//		super.service(request, response);
	}


}

//	Refresh pool_refresh_timer;

//	@Override
//	public void init() throws ServletException {
//		this.semaphore = new Semaphore(3);//��ʼ�����Ƴأ���3������
//		this.pool_refresh_timer = new Refresh(semaphore,10000);//��ʱ����ʼ��ÿ10��ˢ�����Ƴ�
//		pool_refresh_timer.start();
////		super.init();
//	}