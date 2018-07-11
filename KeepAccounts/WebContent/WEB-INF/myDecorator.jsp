<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>  
<%@ taglib prefix="decorator" uri="http://www.opensymphony.com/sitemesh/decorator" %>  
<%@ taglib prefix="page" uri="http://www.opensymphony.com/sitemesh/page" %>  
<%  
String path = request.getContextPath();  
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";  
%>  
  
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">  
<html>  
  <head>  
    <base href="<%=basePath%>">  
      
    <title><decorator:title default="装饰器页面"/></title>  
      
    <decorator:head/>  
  
  </head>  
    
  <body>  
    <decorator:body /><!--被装饰页面的body-->  
  </body> 
</html>  
