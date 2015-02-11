<%@page import="com.http.testmemcache.UserInfo"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Test memcached session</title>
</head>
<body>
<!--% UserInfo userInfo = (UserInfo)session.getAttribute("userinfo"); %-->
<br>
userid is : <%=session.getAttribute("userid") %>
<br>
attr1 is : <%=session.getAttribute("attr1") %>
<br>
attr2 is : <%=session.getAttribute("attr2") %>
<br>
attr3 is : <%=session.getAttribute("attr3") %>
<br>
attr4 is : <%=session.getAttribute("attr4") %>
<p>
</body>
</html>