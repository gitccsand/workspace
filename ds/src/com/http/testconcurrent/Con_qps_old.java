package com.http.testconcurrent;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.concurrent.Semaphore;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
@WebServlet("/Con_qps_old.do")
public class Con_qps_old extends HttpServlet {
	
	//ʵ������
	private Semaphore pool;//���Ƴ� �ź���ʵ��
	private Refresh_sleep_timer pool_refresh;//��ʱ��

	@Override
	public void init() throws ServletException {
		//��ʼ�����Ƴ� 3������
		pool=new Semaphore(3);
		
		//��ʼ��ʱˢ�����Ƴ� 10��ˢ��һ��
		pool_refresh = new Refresh_sleep_timer(pool, 10000, 3);
		pool_refresh.start();
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
