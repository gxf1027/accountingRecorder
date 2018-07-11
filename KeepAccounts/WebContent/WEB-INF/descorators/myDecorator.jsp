<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.opensymphony.com/sitemesh/decorator"
	prefix="decorator"%>
<%@ taglib uri="http://www.opensymphony.com/sitemesh/page" prefix="page"%>


<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet" type="text/css" href="css/style.css"
	media="screen">
<title><decorator:title default="Welcome" />
</title>

<style>
	*{padding:0;margin:0;}
	html{height:100%;}
	body{min-height:100%;}
	body{position:relative;}
	.footer{
		font-family : 微软雅黑,宋体;
	    height:25px;
	    background:white;
	    width:100%;
	    position:absolute;
	    bottom:0;
	    left:0;
	}
</style>

<body>
	
	<div style="text-align:center;border:1px solid blue">
		<span>账单管理0.9</span>
	</div>
	
	<!--被装饰页面的body-->
	<decorator:body />
	
	<div class="footer" align="center">
		<hr>
		<span>Copyright ©2017-2020 Gxf</span>
	</div>
</body>




</html>
