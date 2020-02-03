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
	所属分类：<select id="categorySelect" onchange="selectCategory(this.value)">
		<c:forEach items="${categories }" var="myCategory"> 
			<c:choose>
				<c:when test="${not empty requestScope.category }">
					<c:choose>					
						<c:when test="${requestScope.category.cid eq myCategory.cid }">
							<option value="${myCategory.cid}" selected="selected">${myCategory.cname}</option>	
						</c:when>
						<c:otherwise>
							<option value="${myCategory.cid}">${myCategory.cname}</option>	
						</c:otherwise>
					</c:choose>
				</c:when>
				<c:otherwise>
					<option value="${myCategory.cid}">${myCategory.cname}</option>	
				</c:otherwise>
			</c:choose> 	
		</c:forEach>
	 </select>
	 <button onclick='window.location.href="<c:url value='/admin/category/addCategoryLoad.do'/>"'>添加分类</button>
	 <form action="<c:url value='/admin/book/add.do'/>" target="right" method="POST" enctype="multipart/form-data">
	 	图片：<input type="file" name="img"><font color="red">${imgError }</font><br/>
	 	书名：<input type="text" name="bname" value="${book.bname }"><font color="red">${nameError }</font><br/>
	 	价格：<input type="text" name="bprice" value="${book.bprice }"><font color="red">${priceError }</font><br/>
	 	作者：<input type="text" name="author" value="${book.author }"><font color="red">${authorError }</font><br/>
	 	库存：<input type="text" name="rest" value="${book.rest }"><font color="red">${restError }</font><br/>
	 	<input type="hidden" name="cid" id="cid"><br/>
	 	<input type="submit" value="添加" onclick="submit()">
	 </form>
	 <input type="hidden" value="${msg }" id="msg">
  </body>
	  <script type="text/javascript">
	  	function selectCategory(cid){
	  		var cidElement = document.getElementById("cid");
	  		cidElement.value = cid;
	  	}
	  	window.onload = function(){
	  		var sel = document.getElementById("categorySelect");
	  		var categoryId = sel.options[sel.selectedIndex].value;
	  		var cidElement = document.getElementById("cid");
	  		cidElement.value = categoryId;
	  		var msgEle = document.getElementById("msg");
	  		if(msgEle.value != ''){
	  			alert(msgEle.value);
	  		}
	  		
	  	};
	  </script>
  </body>
</html>
