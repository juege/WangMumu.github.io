<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<%@ page language="java" import="java.util.*" %>
<%@ page language="java" import="com.coe.common.entity.CoeUserInfo" %> 
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>列表页面</title>
</head>
<body>
<%
    List<CoeUserInfo> userInfos = (List)request.getAttribute("userInfos");
%>
<table>
<tr>
<td>
ID
</td>
<td>
名称
</td>
<td>
性别
</td>
<td>
年龄
</td>
</tr>
<%
for(int i = 0 ; i < userInfos.size() ; i ++ ){
	CoeUserInfo info = userInfos.get(i);
%>
<tr>
<td>
<%= info.getId() %>
</td>
<td>
<%= info.getUName() %>
</td>
<td>
<%= info.getSex() %>
</td>
<td>
<%= info.getAge() %>
</td>
</tr>
<%
}
%>
</table>


</body>
</html>