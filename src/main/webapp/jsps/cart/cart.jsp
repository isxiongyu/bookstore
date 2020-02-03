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
    
    <title>My JSP 'cart.jsp' starting page</title>
    
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
  	<h2 style="text-align: center;">${ExceedMaxRestError.message}</h2>
    <c:choose>
    	<c:when test="${empty cartList}">
    		<h2 style="color:blue">您的购物车空空如也</h2>
    	</c:when>
    	<c:otherwise>
   			<table align="center" border="1" bordercolor="red" cellspacing="0" width="80%">
   				<tr height="40px" align="center">
   					<td colspan="6"><a href="<c:url value='/cart/emptyCart.do'/>">清空购物车</a></td>
   				</tr>
   				<tr height="40px" align="center">
   					<td>书名</td>
   					<td>作者</td>
   					<td>单价</td>
   					<td>数量</td>
   					<td>小计</td>
   					<td>操作</td>
   				</tr>
	    		<c:forEach items="${cartList }" var="cart">
	    			<tr height="40px" align="center">
	    				<td>${cart.book.bname }</td>
	    				<td>${cart.book.author }</td>
	    				<td>${cart.book.bprice }</td>
	    				<td>${cart.num }</td>
	    				<td>${cart.subtotal }</td>
	    				<td><a href="<c:url value='/cart/delCart.do?cartId=${cart.cartId }'/>">删除</a></td>
	    			</tr>
	    		</c:forEach>
	    		<tr height="40px" align="center">
	    			<td colspan="5">总价</td>
	    			<td>${total }元</td>
	    		</tr>
	    		<tr height="40px" align="center">
	    			<td colspan="6"><a href=<c:url value='/order/addOrder.do?uid=${sessionScope.user.uid }'/>>提交订单</a></td>
	    		</tr>
   			</table>
    	</c:otherwise>
    </c:choose>
  </body>
</html>
