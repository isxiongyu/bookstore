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
    
    <title>My JSP 'order.jsp' starting page</title>
    
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
    	<c:when test="${empty orderList }">
    		<h2 style="color:blue">您还没有合适的订单</h2>
    	</c:when>
    	<c:otherwise>
	    	<table align="center" border="1" bordercolor="red" cellspacing="0" width="90%">
	    		<c:forEach items="${orderList }" var="order">
	    			<tr height="60px" align="center" bgcolor="gray">
	    				<td colspan="2">订单编号：${order.oid }</td>
	    				<td colspan="2">下单时间：<fmt:formatDate pattern="yyyy-MM-dd HH:mm:ss" value="${order.orderTime }"/></td>
	    				<td>总价：${order.total }</td>
	    				<td>
	    					<c:choose>
	    						<c:when test="${order.state eq 1 }">
	    							<a href="<c:url value='/order/pay.do?oid=${order.oid }'/>" style="color: red;" target="_blank">付款</a>
	    						</c:when>
	    						<c:when test="${order.state eq 2 }">
	    							等待发货
	    						</c:when>
	    						<c:when test="${order.state eq 3 }">
	    							<a href="<c:url value='/order/updateOrderState.do?oid=${order.oid }&state=4'/>" style="color: green;">确认收货</a>
	    						</c:when>
	    						<c:otherwise>
	    							订单完成
	    						</c:otherwise>
	    					</c:choose>
	    				</td>
	    			</tr>
	    			<c:forEach items="${order.orderItems }" var="orderItem">
	    				<tr height="60px" align="center">
	    					<td>书名：${orderItem.book.bname }</td>
	    					<td>作者：${orderItem.book.author }</td>
	    					<td colspan="2">单价：${orderItem.book.bprice }</td>
	    					<td>数量：${orderItem.num }</td>
	    					<td>小计：${orderItem.subtotal }</td>
	    				</tr>
	    			</c:forEach>
	    		</c:forEach>
	    	</table>
    	</c:otherwise>
    </c:choose>
  </body>
</html>
