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
    
    <title>My JSP 'detail.jsp' starting page</title>
    
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
  	<h1 id="h1"></h1>
    <div>
    	<!-- file为虚拟路径资源，在tomcat的conf的server.xml下面改 -->
    	<img alt="书的图片" src="<c:url value='/imgs/${book.img }'/>" height="160" width="120"/>
    </div>
    <ul>
    	<li>书名：${book.bname}</li>
    	<li>作者：${book.author}</li>
    	<li>价格：${book.bprice}元</li>
    	<li>库存：${book.rest}本</li>
    </ul>
    <input type="hidden" id="restId" value="${book.rest}">
    <form action="<c:url value='/cart/addCart.do'/>" method="POST">
    	<input id="bid" type="hidden" name="bid" value="${book.bid }">
    	<input type="hidden" name="uid" value="${sessionScope.user.uid }">
    	<font color="red">${ExceedMaxRestError.message }</font><br/>
    	<font id="fontId" color="red"></font><br/>
    	<input id="numId" type="text" name="num" value="1" onblur="blur()">
    	<input id="submitId" type="submit" value="添加至购物车">
    </form>
    
  </body>
  <script type="text/javascript">
  	window.onload=function(){  
  		/*
 		 创建xmlHttp对象	
	 	*/
	 	function createXMLHttpRequest(){
	 		try {
				return new XMLHttpRequest();	//大多数浏览器都支持这种方式
			} catch (e) {
				try {
					return new ActiveXObject("Msxml2.XMLHTTP");//IE6支持这种方式
				} catch (e) {
					try {
						return new ActiveXObject("Microsoft.XMLHTTP");//IE5.5支持这种
					} catch (e) {
						alert("哥们，你用的啥浏览器");
						throw e;
					}
				}
			}
	 	}
	  	var btn = document.getElementById("submitId");
	  	var h1 = document.getElementById("h1");
	  	var bidEle = document.getElementById("bid");
	  	var numEle = document.getElementById("numId");
	  	var restEle = document.getElementById("restId");
	  	var fontEle = document.getElementById("fontId");
	  	if(restEle.value <= 0){
	  		btn.disabled = true;
	  	}
	  	numEle.onblur = function(){	
	  		//得到xmlHttp
  			var xmlHttp = createXMLHttpRequest();
  			/*
  				打开与服务器之间的连接，参数1：GET请求
  				参数2：请求	URL
  				参数3：是否异步请求
  			*/
  			xmlHttp.open("POST","<c:url value='/book/judgeRest.do'/>",true);
  			xmlHttp.setRequestHeader("Content-Type","application/x-www-form-urlencoded");
  			xmlHttp.send("bid="+bidEle.value+"&"+"num="+numEle.value);	//参数表示请求体，即使get请求没有体，也得发送null
  			//xmlHttp对象状态监听，其有五个状态，当其状态发生改变时调用下列函数
  			xmlHttp.onreadystatechange = function(){
  				/*
  					我们只关心状态4，即表示服务器响应结束.
  					这里双重判断，即也判断响应码为200的状态，即成功
  				*/
  				if(xmlHttp.readyState==4&&xmlHttp.status==200){
  					var text = xmlHttp.responseText;
  					var object = eval("("+text+")");
  					if(object.restState == 1){
  						btn.disabled = true;
  						fontEle.innerHTML = "添加件数大于库存";
  					}else{  		
  						fontEle.innerHTML = "";
  						btn.disabled = false;
  					}
  				}
  			};
	  	};
  	};
  </script>
</html>
