<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>登录</title>
</head>
<body onload='document.f.j_username.focus();'>


	<h3>用户登录</h3>
	<br>
	
	<font color="red">  
		${SPRING_SECURITY_LAST_EXCEPTION.message}
	</font>
        
    <script type="text/javascript">
    function myTrim(x) {
        return x.replace(/^\s+|\s+$/gm,'');
    }
    
    function checkForNull(){
    	if (myTrim(f.j_username.value) == ""){
    		alert("用户名不能为空！");
    		return false;
    	}
    	
    	if (myTrim(f.j_password.value) == ""){
    		alert("密码不能为空！");
    		return false;
    	}
    	
    	return true;
    }
    </script>
    
        
	<form name='f' action='/Struts2Spring4Test2/j_spring_security_check'
		method='POST' onsubmit ="return checkForNull()">
		<table>
			<tr>
				<td>用户名:</td>
				<td><input type='text' name='j_username' value=''></td>
			</tr>
			<tr>
				<td>密码:</td>
				<td><input type='password' name='j_password' /></td>
			</tr>
			<tr>
				<td colspan='2'><input name="submit" type="submit"
					value="登录" /></td>
			</tr>
		</table>
		<%-- <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" /> --%>
	</form>
	

</body>
</html>