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

	<script type="text/javascript">
		function calTotalExp() { //计算函数 
			//var result = document.exp_table.shopping + document.exp_table.tax;
			var result = Number(document.forms["exp_table"]["shopping"].value) 
					+ Number(document.forms["exp_table"]["tax"].value) 
					+ Number(document.forms["exp_table"]["loan"].value)
					+ Number(document.forms["exp_table"]["others"].value);
			document.forms["exp_table"]["expenditure"].value =result;

		}
	
		
	</script>

	<script type="text/javascript">
		function getParamUrl(){
			var expenditure=document.getElementById("expenditure").value;
			var shopping=document.getElementById("shopping").value;
			var tax=document.getElementById("tax").value;
			var others=document.getElementById("others").value;
			//alert("exp!loanInfo?"+"expenditure=" + expenditure +"&shopping=" + shopping + "&tax=" + tax + "&others=" + others);
			return "exp!loanInfo?"+"expenditure=" + expenditure +"&shopping=" + shopping + "&tax=" + tax + "&others=" + others;
		}
	</script>
  

	<s:form name="exp_table" action="exp">
		<s:hidden name="expuuid" value="%{#session.Exp == null ? null : #session.Exp.expuuid}" />
		<s:hidden name="accuuid" value="%{#session.Exp == null ? null : #session.Exp.accuuid}" />
		<s:textfield id="expenditure" label="总支出" name="expenditure" value="%{#session.Exp == null ? 0 : #session.Exp.expenditure}" readonly="true"/>
		<s:textfield id="shopping" label="购物" name="shopping" value="%{#session.Exp == null ? 0 :#session.Exp.shopping}" onchange="calTotalExp()"  />
		<s:textfield id="tax" label="税费" name="tax" value="%{#session.Exp == null ? 0 :#session.Exp.tax}" onchange="calTotalExp()" />
		<s:textfield id="loan" label="贷款" name="loan" value="%{#session.Exp == null ? 0 :#session.Exp.loan}" onchange="calTotalExp()" />
		<s:textfield id="others" label="其它" name="others" value="%{#session.Exp == null ? 0 :#session.Exp.others}" onchange="calTotalExp()" />
		
		<s:submit key="保存" method="saveExp" />
		<s:token></s:token>
	</s:form>
	
	<a href= "javascript:window.location.href=getParamUrl()">获取贷款金额数据</a>  
	
	<s:debug></s:debug>
	
	
</body>
</html>