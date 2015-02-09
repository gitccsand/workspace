package com.http.testconcurrent;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.concurrent.Semaphore;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
@WebServlet("/Concurrent.do")
public class Concurrent extends HttpServlet {
	
	private Semaphore semaphore ;

	@Override
	public void init() throws ServletException {
		this.semaphore = new Semaphore(1);
//		super.init();
	}

	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("text/html; charset=gb2312");
		PrintWriter output = response.getWriter();
		if(semaphore.getQueueLength()>0){
			output.write("没有并发服务许可了，请重试");
			return;
		}
		
		try {
			semaphore.acquire();
			Thread.sleep(15000);
			output.write("服务完成");
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		semaphore.release();
			
//		super.service(request, response);
	}
	
	

}
