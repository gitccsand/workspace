package com.http.testconcurrent;

import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

import java.io.*;
@WebServlet("/Concurrent_test.do")
public class Concurrent_Test extends HttpServlet {
	
	PrintWriter output;

	public void service(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String username;
		response.setContentType("text/html; charset=gb2312");
		username = request.getParameter("username");
		output = response.getWriter();
		try {
			Thread.sleep(5000); // Ϊ��ͻ���������⣬��������һ����ʱ
		} catch (InterruptedException e) {
		}
		output.println("�û���:" + username + "<BR>");
	}
}