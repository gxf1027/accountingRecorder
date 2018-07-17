<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
<h2></h2> <br>


<p>Session 超时</p> <br>
<a href="homepage">返回主页</a> <br>

<%-- <s:property value="exceptionStack"/> --%>
<s:fielderror />
<%-- <s:property value="actionErrors"/> --%>
	
<s:debug></s:debug>


</body>
</html>