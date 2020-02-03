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
    
    <title>My JSP 'main.jsp' starting page</title>
    
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
	<table border="1" bordercolor="red" cellspacing="0"  align="center" width="100%" height="100%">
		<tr style="height:120px">
			<td colspan="2">
    			<iframe frameborder="0"  height="100%" width="100%" src="<c:url value='/jsps/admin/top.jsp'/>" name="top"></iframe>
    		</td>
		</tr>
		<tr>
    		<td style="width:250px;" valign="top" align="center">
    			<iframe frameborder="0" height="100%" width="100%" src="<c:url value='/admin/book/selAllCategoryAdmin.do'/>" name="left"></iframe>
    		</td>
    		<td >
    			<iframe frameborder="0" height="100%" width="100%" src="<c:url value='/jsps/admin/right.jsp'/>" name="right"></iframe>
    		</td>
    	</tr>
	</table>
  </body>
</html>
