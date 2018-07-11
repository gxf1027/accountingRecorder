<%@page import="org.springframework.web.context.support.WebApplicationContextUtils"%>
<%@page import="cn.gxf.spring.struts.integrate.test.CostVO"%>
<%@page import="org.springframework.context.ApplicationContext"%>
<%@page import="org.springframework.web.context.WebApplicationContext"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@taglib uri="/struts-tags" prefix="s" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>

	<%
		//ApplicationContext ctx = (ApplicationContext)application.getAttribute(WebApplicationContext.ROOT_WEB_APPLICATION_CONTEXT_ATTRIBUTE);
		ApplicationContext ctx = WebApplicationContextUtils.getWebApplicationContext(application);
		CostVO costVO = (CostVO) ctx.getBean("cost");
		out.print(costVO);
	%>
	
	<s:debug></s:debug>
</body>
</html>