package com.http.testmemcache;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.danga.MemCached.MemCachedClient;
import com.danga.MemCached.SockIOPool;
import com.http.testmemcache.UserInfo;

@WebServlet("/testmc.do")
public class testmc extends HttpServlet {
	
	private int arrtibute_count = 10;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		this.doPost(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		String userid = request.getParameter("userid");
		if(userid==null) response.sendRedirect("/ds/testmc/nouser.jsp");
		
		
		String[] servers = { "192.168.235.128:11211" };

	       SockIOPool pool = SockIOPool.getInstance();

	       pool.setServers(servers);

	       pool.setFailover(true);

	       pool.setInitConn(10);

	       pool.setMinConn(5);

	       pool.setMaxConn(250);

	       pool.setMaintSleep(30);

	       pool.setNagle(false);

	       pool.setSocketTO(3000);

	       pool.setAliveCheck(true);
	       
	       pool.initialize();
	       
	       //获得缓存中的seesion信息
	       MemCachedClient sessionClient = new MemCachedClient();  
	       
	       String[] attributes=new String[this.arrtibute_count];
	       
	       for(int i=0;i<arrtibute_count;i++){	    	   
	    	   if (sessionClient.get(userid)!=null){
	    		   if(request.getParameter(String.valueOf(i))!=null)
	    			   sessionClient.replace(userid+i, request.getParameter(String.valueOf(i)));	       
	    		   
	    	   }else{
	    		   if(request.getParameter(String.valueOf(i))!=null)
	    			   sessionClient.add(userid+i, request.getParameter(String.valueOf(i)));	       
	    	   }
	    	   attributes[i] = (String) sessionClient.get(userid+i);
	    	   
	    	   request.getSession().setAttribute("attr"+i, attributes[i]);
	       }
	       
	       request.getSession().setAttribute("userid", userid);    
	       response.sendRedirect("/ds/testmc/testmc.jsp");	       
	       
	    }
	
}
	
//	       MemCachedClient sessionClient = new MemCachedClient(true);  
//	       boolean b_add = sessionClient.add("key1", "value1");
//	       if(b_add) System.out.println("memcache add key1 success ="+ b_add);
//	       
//	       String cachedValue = (String) sessionClient.get("key1");
//	       System.out.println("memcached key1 value is = " + cachedValue);
	
//	       boolean b_replace = sessionCachClient.replace("key1", "replaced value");
//	       if(b_replace) System.out.println("memcached key1 replaced success = " + b_replace);	       
//	       System.out.println("memcached key1 = " + (String)sessionCachClient.get("key1"));
//	       
//	       boolean b_delete = sessionCachClient.delete("key1");	       
//	       if(b_delete) System.out.println("memcached key1 deleted sucess = "+b_delete);
//	       System.out.println("deleted key1 value = " + sessionCachClient.get("key1"));
//	       
//	       String userid = request.getParameter("userid");
//		   String buyitem = request.getParameter("buyitem"); 	  
//		   System.out.println("userid is =" + userid);
//	       
//	       
//	       UserInfo cachedSession = (UserInfo)sessionClient.get(userid);//查找缓存中该用户的session信息
//	       
//	       if (cachedSession!=null){//缓存中已有的session
//	    	   cachedSession.setBuyitem(buyitem);
//	    	   System.out.println("cached session userid is :"+cachedSession.getUserid());
//	    	   
//	    	   sessionClient.replace(userid, cachedSession);//更新原缓存的session
//	       }else{//缓存中没有的新session
//	    	   cachedSession = new UserInfo();
//	    	   cachedSession.setUserid(userid);
//	    	   cachedSession.setBuyitem(buyitem);     	   
//	    	   
//	    	   boolean b_add1 = sessionClient.add(userid, cachedSession);//新增缓存session
//	    	   System.out.println("is session added = " + b_add1);
//	    	   
//	       }
//	       
//	       request.getSession().setAttribute("userid", cachedSession.getUserid());	  
//	       request.getSession().setAttribute("buyitem", cachedSession.getBuyitem());
	
//	       request.getSession().setAttribute("userinfo", cachedSession);	  
//	       Object cachedSession = sessionCachClient.get(userid);//查找缓存中该用户的session信息
//	       
//	       UserInfo userinfo = new UserInfo();
	
//	       if (cachedSession!=null){//缓存中已有的session
//	    	   userinfo = (UserInfo)cachedSession;
//	    	   userinfo.setBuyitem(buyitem);
//	    	   
//	    	   sessionCachClient.replace(userid, userinfo);//更新原缓存的session
//	       }else{//缓存中没有的新session
//	    	   userinfo.setUserid(userid);
//	    	   userinfo.setBuyitem(buyitem);     	   
//	    	   
//	    	   sessionCachClient.add(userid, userinfo);//新增缓存session
//	       
//	       }
	
	
//	       request.getSession().setAttribute("userinfo", userinfo);	  
	

