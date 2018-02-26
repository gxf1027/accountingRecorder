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
.loginBox {
	background-color: #ffe;
	position: relative;
}

.loginBox {
	padding: 45px 54px 38px;
	border-radius: 4px;
	border: 1px solid #e3e3e3;
	height: 540px;
	max-width: 960px;
	max-height: 1024px;
	padding-top: 50px;
	margin: 40px auto auto auto; /* div 居中 */
}

.formWrap {
	padding: 20px 28px
}

.loginTitle {
	font-size: 24px;
	margin-bottom: 24px;
	font-weight: 800;
	/* text-align: center */
	margin-left: 118px
}

/* .clearfix {
		overflow: hidden;
		clear: both;
		width: 0;
		visibility: hidden;
	} */
.inputWrap {
	width: 80%
}

.boxSize {
	-webkit-box-sizing: border-box;
	box-sizing: border-box
}
/* .input{border-color:#ec5524} */
.input {
	*height: 20px;
	*width: 306px
}

.input {
	height: 40px;
	width: 332px
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
	padding: 13px 0;
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
	margin: 30px 0;
	padding: 0;
}

.subTxt {
	display: inline-block
}

em{
font-style: normal;
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
	


	<div class="loginBox formWrap">
		<form name='f' action='/Struts2Spring4Test2/j_spring_security_check'
			method='POST' onsubmit="return checkForNull()" style="margin: auto 0; top:5%; left:35%;  position: absolute;">
			<h2 class="loginTitle">登录挖财</h2>
			
			<font color="red">  
				${SPRING_SECURITY_LAST_EXCEPTION.message}
			</font>
			
			<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
			
			<div class="row">
				<div class="inputWrap">
					<input type="text" class="input boxSize " id="username"
						name="j_username" placeholder="请在这里输入用户名">
				</div>
				<!-- <span class="p-error">请输入正确格式的帐号</span> <span class="p-error-empty">请输入您的帐号</span> -->
			</div>

			<div class="row">
				<div class="inputWrap">
					<input type="password" class="input  boxSize" id="pwd"
						name="j_password" placeholder="您的密码">
				</div>
				<!-- <span class="p-error">请输入6到16位的密码</span> <span class="p-error-empty">请输入您的密码</span> -->
			</div>
			
			
			
			<!-- <input id="_spring_security_remember_me" name="_spring_security_remember_me" type="checkbox" />自动登录 -->
			<input id="_spring_security_remember_me" name="_spring_security_remember_me" type="checkbox" class="checkbix" data-text="自动登录"> 
			<div class="row btnRow">
				<button type="submit" name="submit"
					class="btn btn-primary btn-block" id="verifyBtn">
					<!-- <em class="loadingTxt"><i class="w_loading"></i>请稍候...</em>  -->
					<em	class="subTxt">登&nbsp;录</em>
				</button>
			</div>
			
		</form>
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