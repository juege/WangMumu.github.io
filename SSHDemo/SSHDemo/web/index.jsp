<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>My JSP 'index.jsp' starting page</title>
    
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
     <form action="addUser.action" method="post"  >
    	姓别：<input type="text" name="usersex" value = ""/>
    	姓名：<input type="text" name="username"/>
   	 	<input type="submit" value="提交"/>
    </form>
    
    
    	<form name="frm" action="saveUser.action">
		<input type="radio" name="sex" value="1" /> 男生 
		<input type="radio" name="sex" value="0" /> 女生 <br />
		年龄：<input type="text" name="age"/><br>
		姓名：<input type="text" name="UName"/><br>
		</select> <input type="submit" value="submit" />
	</form>
	<a href = "addUser.action">dianji</a>
  </body>
</html>
