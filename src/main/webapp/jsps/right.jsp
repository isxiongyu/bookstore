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
  	<c:choose>
  		<c:when test="${not empty bookList}">
  			<table align="center" border="1" bordercolor="red" cellspacing="0" width="80%">
  				<tr height="40px" align="center">
  					<td>书名</td>
  					<td>价格</td>
  					<td >作者</td>
  					<td>剩余数量</td>
  					<td>所属分类</td>
  					<td>详情</td>
  				</tr>
  				<c:forEach items="${bookList }" var="book">
	  				<tr height="40px" align="center">
	  					<td>${book.bname }</td>
	  					<td>${book.bprice }</td>
	  					<td>${book.author }</td>
	  					<td>${book.rest }</td>
	  					<td>${book.category.cname }</td>
	  					<td><a href=<c:url value='/book/detail.do?bid=${book.bid }'/> target="right">详情</a></td>
	  				</tr>
  				</c:forEach>
  			</table>
  		</c:when>

  	</c:choose>
  <body>
    
  </body>
</html>
