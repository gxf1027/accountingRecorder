<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib uri="/struts-tags" prefix="s" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="_csrf" content="${_csrf.token}"/>
<!-- default header name is X-CSRF-TOKEN -->
<meta name="_csrf_header" content="${_csrf.headerName}"/>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath }/css/footer.css" />
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath }/css/login-header.css" /> 
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath }/js/checkbix/css/checkbix.min.css"> 
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath }/js/toastr/toastr.css" />
<style type="text/css">
.loginBox {
	background-color: #ffe
}

.loginBox {
	padding: 45px 54px 38px;
	border-radius: 4px;
	border: 1px solid #e3e3e3;
	height: 680px;
	max-width: 960px;
	max-height: 1024px;
	margin: auto; /* div 居中 */
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

.clearfix {
	overflow: hidden;
	clear: both;
	width: 0;
	visibility: hidden;
}

.inputWrap {
	width: 80%
}

.boxSize {
	-webkit-box-sizing: border-box;
	box-sizing: border-box
}
/* .input{border-color:#ec5524} */
.input{*height:20px;*width:306px}
.input{height:40px; width:332px}

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

.subTxt{display:inline-block}

em{
	font-style: normal;
}

</style>
<script type="text/javascript" src="${pageContext.request.contextPath }/js/jquery.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath }/js/jquery.md5.js"></script>
<script src="./js/checkbix/js/checkbix.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath }/js/toastr/toastr.js"></script>
<script>
	Checkbix.init();
</script>

<script type="text/javascript">

	if(typeof jQuery !='undefined'){
		
		 
	}else{
	 
	    alert("jQuery library is not found!");
	 
	}
	
	function myTrim(x) {
		return x.replace(/^\s+|\s+$/gm, '');
	}
	
	$(function(){
		 /* toastr 提示栏 配置 */
		   toastr.options = {
			  "closeButton": true,
			  "debug": false,
			  "positionClass": "toast-bottom-full-width",
			  "onclick": null,
			  "showDuration": "300",
			  "hideDuration": "1000",
			  "timeOut": "5000",
			  "extendedTimeOut": "1000",
			  "showEasing": "swing",
			  "hideEasing": "linear",
			  "showMethod": "fadeIn",
			  "hideMethod": "fadeOut"
			};
		 
		$(":input[name='username']").blur(function(){
			var uname = $(this).val();
			console.log(uname);
			uname = $.trim(uname);
			
			if (uname != "" && uname.length >= 6){
				
				var token = $("meta[name='_csrf']").attr("content");
				var header = $("meta[name='_csrf_header']").attr("content");
				var headers = {};
				headers['__RequestVerificationToken'] = token;
				var url ="${pageContext.request.contextPath }/demo/checkRegisterName.action"
				var args={"username":uname, "timestamp":new Date()};
				console.log(url);
				/* $.post(url, args, function(data){
					console.log("data.username: " + data.username);
					console.log("data.msg: " + data.msg);
					console.log("data:" + data);
					$("#username_msg").html(data);
				}); */
				
				 $.ajax({
				        type: "POST",
				        headers: headers,
				        cache: false,
				        url: url,
				        data: args,
				        dataType:"text",
				        async: true,
				        error: function(data, error) {},
				        success: function(data)
				        {
				        	//$("#username_msg").html(data);
				        	console.log("rv:"+data);
				        	toastr.info(data);
				        }
				    });
			}else
				{
					$("#username_msg").html('');
				}
		});	
	})
	

	function checkSubmit() {
		
		var rename = new RegExp("[a-zA-Z_][a-zA-Z_0-9]{0,}", "");
		var isname= rename.test($("#username").val());
		if (isname == false){
			$("#info_username").text("用户名只能由大小写字母、数字和下划线组成，且不能以数字开头");
			$("#info_username").show();
			return false;
		}
		
		var username = $("#username").val();
		
// 		if (username.length < 6){
// 			$("#info_username").text("用户名长度不能小于6个字符");
// 			$("#info_username").show();
// 			return false;
// 		}
		
		if (username.length > 20){
			$("#info_username").text("用户名长度不能大于20个字符");
			$("#info_username").show();
			return false;
		}
		
		
		var repass = new RegExp("[a-zA-Z_0-9]{6,16}", "");
		var ispassword = repass.test($("#pwd").val());
		if (ispassword == false){
			$("#info_pwd").text("密码只能由大小写字母、数字和下划线组成, 长度为6至16位");
			$("#info_pwd").show();
			return false;
		}
		
		if ($("#pwd").val() != $("#confirm_pwd").val()){
			//alert($("#pwd").val() + "  " + $("#confirm_pwd").val());
			$("#info_pwd").hide();
			$("#info_pwd_confirm").text("两次密码不同");
			$("#info_pwd_confirm").show();
			return false;
		}
		
		var pwd = $("#pwd").val();
		var confirm_pwd = $("#confirm_pwd").val();
		$("#pwd").val($.md5(pwd));
		$("#confirm_pwd").val($.md5(confirm_pwd));
		
		return true;
	}
	
	function pwdConfirmChange() {
		$("#info_pwd").hide();
		$("#info_pwd_confirm").hide();
	}
	
	function usernameChange(){
		$("#info_username").hide();
	}
	
</script>

<title>注册</title>
</head>
<body onload='document.f.username.focus();'>
	<div class="loginBox formWrap">
		<s:form name="f" action="registerUser" namespace="/demo"
			method="POST" onsubmit="return checkSubmit()" style="margin: auto 0; top:10%; left:40%;  position: absolute;">
			<s:token></s:token>
			<h2 class="loginTitle">注册新用户</h2>
			
			<font color="red">  
				<%-- ${SPRING_SECURITY_LAST_EXCEPTION.message} --%>
			</font>
			<div class="row">
				<div class="inputWrap">
					<p id="info_username" style="display:none"> </p>
					<input type="text" class="input boxSize " id="username"
						name="username" placeholder="请在这里输入用户名" required="required" onchange="usernameChange()">
				</div>
				<div id="username_msg" style="display: inline;"></div>
				<!-- <span class="p-error">请输入正确格式的帐号</span> <span class="p-error-empty">请输入您的帐号</span> -->
			</div>

			<div class="row">
				<div class="inputWrap">
					<p id="info_pwd" style="display:none"> </p>
					<input type="password" class="input  boxSize" id="pwd"
						name="pwd" placeholder="您的密码" required="required">
						
				</div>
			</div>
			
			<div class="row">
				<div class="inputWrap">
					<p id="info_pwd_confirm" style="display: none" > </p>
					<input type="password" class="input  boxSize" id="confirm_pwd"
						name="confirm_pwd" placeholder="确认密码" required="required" onchange="pwdConfirmChange()" >
				</div>
				<!-- <span class="p-error">请输入6到16位的密码</span> <span class="p-error-empty">请输入您的密码</span> -->
			</div>
			
			<p style="text-align: left;font-size: 15px;" >
                  <label for="agreement" style="cursor: pointer; vertical-align: middle; display: inline-block;">
                       <!-- <input type="checkbox"  class="icons" name="agreement" id="agreement" required=""> -->
                       <input id="agreement_checkbix" type="checkbox" name="agreement" class="checkbix" data-text=""> 
            
                  </label>
                  <em class="vm">&nbsp;我已阅读并同意<a href="/reform/web/register_aggrement" class="di blue" target="_blank">《服务条款》</a><a href="/reform/web/privacy_policy" class="di blue" target="_blank">《隐私权政策》</a>
                  </em>
            </p>
            <p>
            	<a href="/Struts2Spring4Test2/myLoginWcai.jsp">用已有账号登录</a>
            </p>

			<div class="row btnRow">
				<button type="submit" name="submit"
					class="btn btn-primary btn-block" id="verifyBtn"  >
					<em	class="subTxt">注册</em>
				</button>
				<%-- <s:submit name="submit" class="btn btn-primary btn-block" id="verifyBtn" theme="simple" value=" " >
					<em	class="subTxt">注册</em>
				</s:submit> --%>
			</div>
			
		</s:form>
	
		
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