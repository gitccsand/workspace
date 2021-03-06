package com.http.testcookie;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.http.testcookie.*;
@WebServlet("/SetCookies.do")
public class SetCookies extends HttpServlet {
	private static final long serialVersionUID = 6849802930688070121L;
	
	private String sortPrarams(Map<String,String[]> params){
		Set<String> keySet = params.keySet();
		TreeSet<String> sortSet = new TreeSet<String>();
		sortSet.addAll(keySet);
		String keyValueStr="";
		Iterator<String> it = sortSet.iterator();
		while(it.hasNext()){
			String key = it.next();
			String[] value = params.get(key);
			keyValueStr+=key+value[0];
		}
		return keyValueStr;
		
	}

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		System.out.println(sortPrarams(request.getParameterMap()));
		String output = null;
		String username = request.getParameter("username");
		if (!StringUtils.validateNull(username)) {
			Cookie cookie1 = new Cookie("username",
					StringUtils.filterHtml(username));
			// cookie的有效期为1个月
			cookie1.setMaxAge(24 * 60 * 60 * 30);
			//httponly 设置为true
			cookie1.setHttpOnly(true);
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			Cookie cookie2 = new Cookie("lastTime", sdf.format(new Date()));
			cookie2.setMaxAge(24 * 60 * 60 * 30);
			response.addCookie(cookie1);
			response.addCookie(cookie2);
			output = "本次登录时间与用户名已经写到Cookie中。<br><a href=\"/ds/GetCookies.do\">查看Cookies</a>"
					+"<br>本次登录时间与用户名已经写到Cookie中。用户名HTTPONLY<br><a href=\"/ds/TestHttpOnly.do\">测试HTTPONLY</a>";
		} else {
			output = "用户名为空，请重新输入。<br><a href=\"/ds/testcookie/CookieInput.html\">输入用户名</a>";
		}
		response.setContentType("text/html;charset=UTF-8");
		PrintWriter out = response.getWriter();
		out.println("<html>");
		out.println("<head><title>set cookies </title></head>");
		out.println("<body>");
		out.println("<h2>" + output + "</h2>");
		out.println("</body>");
		out.println("</html>");
		out.flush();
		out.close();
		// response.
	}

}
