<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>

	<form name="frm" action="saveUser.action" method="post">
	
	    姓名：<input id="UName" type="text" name="coeUserInfo.UName" value="" /><br/>
	   年龄：<input id="age" type="text" name="coeUserInfo.age" value="" /><br/>
	    
	
		<input id="sex1" type="radio" name="coeUserInfo.sex" value="1" /> 男生 
		<input id="sex2" type="radio" name="coeUserInfo.sex" value="0" /> 女生 
			
			<br /> 
			<input type="submit" value="提交" />
	 </form>
</body>
</html>