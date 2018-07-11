<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<style type="text/css">
.loginBox{background-color:#ffe }
.loginBox{padding: 45px 54px 38px; border-radius:4px; border:1px solid #e3e3e3}
.formWrap{padding: 20px 28px}
.loginTitle{font-size:24px;margin-bottom:24px;font-weight:500}
.clearfix{
    overflow:hidden;
    clear:both;
    width:0;
    visibility:hidden;
    }
 .inputWrap{width:80%}
 .boxSize{-webkit-box-sizing:border-box;box-sizing:border-box }
 /* .input{border-color:#ec5524} */
 .input{height:32px;width:306px;border-radius:4px}
 .row{margin:0 0 20px}
 .p-error{color:#999; display:inline-block}
 .p-error-empty {margin:9px 0 0 10px}
 .p-error,.p-error-conflict,.p-error-empty,.p-note{margin:9px 0 0 10px}
</style>

<title>Insert title here</title>
</head>
<body>
<div class="loginBox formWrap">
	<form action="#" onsubmit="return false" class="rel" id="loginForm" >
	 	<h2 class="loginTitle">登录挖财</h2>
	 	<div class="row">
              <div class="inputWrap">
                  <input type="text"  class="input boxSize " id="username" name="user.account" placeholder="请在这里输入用户名" >
              </div>
               <span class="p-error">请输入正确格式的帐号</span>
               <span class="p-error-empty">请输入您的帐号</span>
         </div>
         
         <div class="row">
              <div class="inputWrap">
                   <input type="password"  class="input  boxSize" id="pwd" name="pwd" placeholder="您的密码" >
               </div>
               <span class="p-error">请输入6到16位的密码</span>
               <span class="p-error-empty">请输入您的密码</span>
        </div>
         
	</form>
</div>
</body>
</html>