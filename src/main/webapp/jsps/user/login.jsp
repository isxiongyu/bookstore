<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>My JSP 'login.jsp' starting page</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->

  </head>
  
  <body>
  	<h1>用户登录页面</h1>
  	<font color="red">${loginError.message }</font>
  	<font color="blue">${activeMsg }</font>
  	<font color="red">${ex.message }</font>
	<form action="<c:url value='/user/login.do'/>" target="_parent" method="POST">
		用户名：<input type="text" name="username" value="${user.username }"/><br/>
		密码：<input type="text" name="password" value="${user.password }"/><br/>
		<input type="submit" value="登录"/>
	</form>
  </body>
</html>
