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
  	<h1 style="color: red" align="center">欢迎光临熊宇书城后台管理页面</h1>
  	<c:if test="${not empty sessionScope.admin}">
  			<span>欢迎管理员${sessionScope.admin.aname}</span>
  			<a href="<c:url value='/admin/quit.do'/>" target="_parent">退出</a>
  			<a href="<c:url value='/admin/order/allOrder.do'/>" target="right">所有用户订单</a>
  	</c:if>
  </body>
</html>
