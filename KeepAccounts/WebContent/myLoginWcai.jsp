<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath }/css/footer.css" />
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath }/css/login-header.css" />
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath }/js/checkbix/css/checkbix.min.css">

 
<style type="text/css">

a {text-decoration: none;}

div {margin: 0; padding: 0;}

.wrap {
    height: 560px;
    background: url();
    background-repeat: no-repeat;
    background-position: top center;
    margin: 20px auto;
    /* background-image: url(http://m.tuniucdn.com/fb2/t1/G2/M00/A1/A3/Cii-T1f4lhCITSjhAAMhh0OTedQAADJqwM5SCIAAyGf70.jpeg); */
    background-image: url("${pageContext.request.contextPath }/components/login-background-image.jpg");
    background-position-x:center;
    background-position-y:center;
    background-size: 100% 100%;
}

.content {
    width: 1400px;
    height: 560px;
    margin: 0 auto;
    position: relative;
}

.login-content {
    position: absolute;
    top: 60px;
    right: 40px;
    z-index: 10;
    width: 400px;
    height:380px;
    box-shadow: 0 1px 4px rgba(51,51,51,.2);
}

.login-box-inner {
    background: #fff;
    width: 400px;
    height:380px;
}

.login-tab {
    margin: 0;
    text-align: center;
    list-style: none;
    padding: 14.5px 0;
    border-bottom: solid 1px #ebebeb;
}

.login-tab:after {
    display: block;
    clear: both;
    content: "";
    visibility: hidden;
    height: 0;
}

.login-table {
    margin: 0 auto;
}

.account-login{
	width:400px;
	height:380px;
}
.login-table {
    border: 0;
    border-collapse: collapse;
    border-spacing: 0;
}


.loginTitle {
	font-size: 24px;
	margin-bottom: 24px;
	font-weight: 500;
	/* text-align: center */
	margin-left: 118px;
	font-family: "Helvetica Neue";
}

.inputWrap {
	width: 80%
}

.boxSize {
	-webkit-box-sizing: border-box;
	box-sizing: border-box
}

.input {
	*height: 20px;
	*width: 306px
}

.input {
	height: 40px;
	width: 332px;
	border-radius: 4px; 
	border: 1px solid #ccc;
	padding-top: 6px;
    padding-right: 12px;
    padding-bottom: 6px;
    padding-left: 44px;
}

#username{
	background: url(${pageContext.request.contextPath }/components/login_icon.png) no-repeat;
	background-position: 15px -9px;
}

#pwd{
	background: url(${pageContext.request.contextPath }/components/login_icon.png) no-repeat;
	background-position: 15px -48px;
}

.row {
	margin: 0 0 20px
}

.p-error {
	color: #999;
	display: inline-block
}

.p-error-empty {
	margin: 9px 0 0 10px
}

.p-error, .p-error-conflict, .p-error-empty, .p-note {
	margin: 9px 0 0 10px
}

.btnRow .btn-block {
	font-size: 20px;
	padding: 10px 0;
	*padding-top: 0;
	*padding-bottom: 0;
	*line-height: 48px;
	*height: 48px;
	height: 48px;
	width: 332px
}

.btn-primary{
	color: #fff;
    border: 1px solid #ad1f1f;
	border-color: #ff503f;
    background-color: #ff503f;
}

.btn {
	width: 200px;
	line-height: 1;
	color: #fff;
	font-weight: 400;
	text-align: center;
	vertical-align: top;
	white-space: nowrap;
	background: #D94B40;
	border-color: #D94B40;
	border-radius: 4px;
	cursor: pointer;
	margin: 10px 0;
	padding: 0;
}

.subTxt {
	display: inline-block
}

em{
font-style: normal;
}

#line_3 td{
	height:20px;
}

</style>

<script type="text/javascript" src="${pageContext.request.contextPath }/js/jquery.js"></script>
<script src="${pageContext.request.contextPath }/js/checkbix/js/checkbix.min.js"></script>

<script>
	Checkbix.init();
</script>

<script type="text/javascript">
	function myTrim(x) {
		return x.replace(/^\s+|\s+$/gm, '');
	}

	function checkForNull() {
		if (myTrim(f.j_username.value) == "") {
			alert("用户名不能为空！");
			return false;
		}

		if (myTrim(f.j_password.value) == "") {
			alert("密码不能为空！");
			return false;
		}

		return true;
	}

	
	
</script>
<link rel="shortcut icon" href="./components/favicon.ico" type="image/x-icon" />
<title>登录</title>
</head>
<body onload='document.f.j_username.focus();'>
	
	
	<header class="header">
		<div class="container clearfix">
			
			<div class="fr hd-login">
				<a	href="${pageContext.request.contextPath}/myRegisterWcai.jsp" class="reg" style="text-decoration: none;">注册新用户</a>
			</div>
			<!-- "//s1.wacdn.com/common/c/header/img/logo@2x.png" -->
			<div class="fl">
				<h1 class="logo">
					<a href="${pageContext.request.contextPath}/myLoginWcai.jsp" title="挖财，我的资产管家"
						class="clearfix fl">
						<img src= "${pageContext.request.contextPath }/components/logo@2x.png"
						class="responsive" style="display: inline-block;max-width: 100%;height: auto;" 
						alt="挖财，我的资产管家"></a>
					<div class="sub-nav-title rel">欢迎来到挖财</div>
				</h1>
			</div>
		</div>
	</header>
	

	<div id="loginWrap" class="wrap">
		<div id="content" class="content">
		
		<div id="cent_link" class="cent_link">
            <a style="display:block;" target="_blank" id="bg_img" href="http://tmc.tuniu.com/"></a>
        </div>
        
		<!-- <div class="loginBox formWrap" > -->
		<div id="login-content" class="login-content" style="background-color: #fff;">
		<div id="login-box" class="login-box-inner" >
			<!-- <ul id="login-tab" class="login-tab">
	            <li id="login-tab-user" class="login-tab-li cur">账户登录<b></b></li>
	            <li id="login-tab-pass" class="login-tab-li">扫码登录<b></b></li>
	        </ul> -->
			<div id="account-login" class="account-login">
				<form name='f' action='/Struts2Spring4Test2/j_spring_security_check'
					method='POST' onsubmit="return checkForNull()" style="margin: 0;padding-top: 20px;padding-left: 10px">
					<h2 class="loginTitle">账户登录</h2>
					
					<table class="login-table">
						<tbody>
							<tr id="line_0">
								<td>
									<font color="red">  
										${SPRING_SECURITY_LAST_EXCEPTION.message}
									</font>
								</td>
							</tr>
							
							<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
							
							<tr id="line_1">
								<td>
									<div class="row">
										<div class="inputWrap">
											<input type="text" class="input boxSize " id="username"
												name="j_username" placeholder="请在这里输入用户名">
										</div>
										<!-- <span class="p-error">请输入正确格式的帐号</span> <span class="p-error-empty">请输入您的帐号</span> -->
									</div>
								</td>
							</tr>
							
							<tr id="line_2">
								<td>
									<div class="row">
										<div class="inputWrap">
											<input type="password" class="input  boxSize" id="pwd"
												name="j_password" placeholder="您的密码">
										</div>
										<!-- <span class="p-error">请输入6到16位的密码</span> <span class="p-error-empty">请输入您的密码</span> -->
									</div>
								</td>
							</tr>
							
							
							<tr id="line_3">
								<td>
									<!-- <input id="_spring_security_remember_me" name="_spring_security_remember_me" type="checkbox" />自动登录 -->
									<input id="_spring_security_remember_me" name="_spring_security_remember_me" type="checkbox" class="checkbix" data-text="自动登录">
									<a href="javascript:void(0)" id="goDynamic" class="search_psw" style="float: right; color: #666;">忘记密码？</a>
								</td>
							</tr> 
							
							<tr id="line_4">
								<td>
									<div class="btnRow">
										<button type="submit" name="submit"
											class="btn btn-primary btn-block" id="verifyBtn">
											<!-- <em class="loadingTxt"><i class="w_loading"></i>请稍候...</em>  -->
											<em	class="subTxt">登&nbsp;录</em>
										</button>
									</div>
								</td>
							</tr>
						</tbody>
					</table>
				</form>
			</div>
		</div>
		</div>
		</div>
	</div>
	
	<section class="footer-evaluate">
		<ul class="g-layout">
			<li>
				<i class="g-icon g-icon-evaluate-01"></i>
				<strong>8年积淀</strong>
				
				1.6亿用户的选择
			
			</li>
			<li>
				<i class="g-icon g-icon-evaluate-02"></i>
				<strong>7x24小时</strong>
				
				您随时的资产管家
			
			</li>
			<li>
				<i class="g-icon g-icon-evaluate-03"></i>
				<strong>三重十道</strong>
				
				全面的风控管理策略
			
			</li>
			<li>
				<i class="g-icon g-icon-evaluate-04"></i>
				<strong>2亿美元融资</strong>
				
				领先的在线金融专家
			
			</li>
		</ul>
	</section>
	
</body>
</html>