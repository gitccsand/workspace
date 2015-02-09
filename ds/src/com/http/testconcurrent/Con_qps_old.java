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
	
	//实例变量
	private Semaphore pool;//令牌池 信号量实现
	private Refresh_sleep_timer pool_refresh;//定时器

	@Override
	public void init() throws ServletException {
		//初始化令牌池 3个令牌
		pool=new Semaphore(3);
		
		//开始定时刷新令牌池 10秒刷新一次
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
		if(pool.availablePermits()==0 ){//令牌耗尽，返回
			output.write("令牌已用尽，请稍候重试");
			return;
		}
		
		try {
			pool.acquire();//获取令牌
			output.write("服务完成");//服务
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
//		super.service(request, response);
	}


}
