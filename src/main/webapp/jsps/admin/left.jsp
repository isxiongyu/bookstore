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
<%--	<meta http-equiv="refresh"content="5">--%>
  </head>
  
  <body>
		<table align="center" border="0" width="100%">	
			<tr>	
				<td align="left">				
		  			<a href="<c:url value='/admin/book/selAllBookAdmin.do'/>" target="right">查询所有分类</a><br/>
				</td>			
				<td align="right">				
		  			<a href="<c:url value='/admin/category/addCategoryLoadLeft.do'/>" target="right">添加分类</a><br/>
				</td>			
			</tr>		
			<c:forEach items="${categoryList }" var="category">
				<tr>
					<td align="left">
						<a href="<c:url value='/admin/book/selBookAdmin.do?cid=${category.cid }'/>" target="right">${category.cname }</a>
					</td>
					<td align="right">
						<a href="<c:url value='/admin/category/delCategory.do?cid=${category.cid }'/>" target="right">删除目录</a>
					</td>
				</tr>
			</c:forEach>
		</table>
  </body>
</html>
