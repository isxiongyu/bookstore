<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>My JSP 'pay.jsp' starting page</title>
    
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
    <c:choose>
    	<c:when test="${empty order }">
    		您还没有合适的订单
    	</c:when>
    	<c:otherwise>
    		<table align="center" border="1" bordercolor="red" cellspacing="0" width="90%">
    			<tr height="60px" align="center">
    				<td colspan="2">订单编号：${order.oid }</td>
    				<td colspan="2">下单时间：<fmt:formatDate pattern="yyyy-MM-dd HH:mm:ss" value="${order.orderTime }"/></td>
    				<td>总价：${order.total }</td>
    			</tr>
    			<c:forEach items="${order.orderItems }" var="orderItem">
    				<tr height="60px" align="center">
    					<td>书名：${orderItem.book.bname }</td>
    					<td>作者：${orderItem.book.author }</td>
    					<td>作者：${orderItem.book.bprice }</td>
    					<td>作者：${orderItem.num }</td>
    					<td>小计：${orderItem.subtotal }</td>
    				</tr>
    			</c:forEach>
    		</table><br/><br/><br/>
    		<form action="<c:url value='/order/pay.do?oid=${order.oid }'/>" method="POST" target="_blank">
    			请填写收货地址：
    			<input type="text" name="address"><br/>
    			<input type="submit" value="确认支付">
    		</form>
    	</c:otherwise>
    </c:choose>
  </body>
</html>
