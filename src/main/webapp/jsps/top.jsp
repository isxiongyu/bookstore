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
    
    <title>My JSP 'top.jsp' starting page</title>
    
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
  	<h1 style="color: red" align="center">欢迎光临熊宇书城</h1>
  	<c:choose>
  		<c:when test="${empty sessionScope.user}">
		    <a href="<c:url value='/jsps/user/register.jsp'/>" target="_parent">注册</a>
		    <a href="<c:url value='/jsps/user/login.jsp'/>" target="_parent">登录</a>
  		</c:when>
  		<c:otherwise>
  			<span>欢迎用户${sessionScope.user.username}</span>
  			<a href="<c:url value='/user/quit.do'/>" target="_parent">退出</a>
  			<a href="<c:url value='/cart/myCart.do?uid=${sessionScope.user.uid }'/>" target="right">我的购物车</a>
  			<a href="<c:url value='/order/myOrder.do?uid=${sessionScope.user.uid }'/>" target="right">我的订单</a>
  		</c:otherwise>
  	</c:choose>
  </body>
</html>
