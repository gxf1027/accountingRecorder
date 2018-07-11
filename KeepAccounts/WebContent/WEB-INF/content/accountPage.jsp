<%@page import="java.util.Date"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>

	<script type="text/javascript">
		function calTotalIncome() {
			
			if(isNaN(document.forms["income_table"]["salary"].value)){
				alert("'salary'请输入数字");
				document.forms["income_table"]["salary"].focus();
			}
			
			if (isNaN(document.forms["income_table"]["bonus"].value)){
				alert("'bonus'请输入数字");
				document.forms["income_table"]["bonus"].focus();
			}
			
			var totalincome = Number(document.forms["income_table"]["salary"].value) 
					 			+ Number(document.forms["income_table"]["bonus"].value);
			document.forms["income_table"]["income"].value = totalincome;
			
			document.forms["income_table"]["netincome"].value = totalincome 
									- Number(document.forms["income_table"]["expenditure"].value);
		}
		
		function calNetIncome() {
			var net = Number(document.forms["income_table"]["salary"].value) 
 			+ Number(document.forms["income_table"]["bonus"].value)
 			- Numer(document.forms["income_table"]["expenditure"].value);
			
			document.forms["income_table"]["netincome"].value = net;
		}
	</script>

	<%-- 	<s:set var="income_s" value="%{#session.Account == null ? 1:0}"></s:set> --%>
	
	
	<%
		String nd_now = (new SimpleDateFormat("YYYY")).format(new Date());
		String yf_now = (new SimpleDateFormat("MM")).format(new Date());
		request.setAttribute("nd_now", nd_now);
		request.setAttribute("yf_now", yf_now);
	%>
	

	<s:form action="account" name="income_table" method="post">
<%-- 		<s:hidden name="accuuid" value="#session.Account==null ? null : #session.Account.accuuid"> </s:hidden> --%>
		<s:hidden name="accuuid" value= "%{#session.Account==null ? null : #session.Account.accuuid}" />
		<tr>
			<td>
				<s:if test="#session.Mode=='add_mode'">
					<s:select name="nd" list="{'2014', '2015', '2016', '2017'}" key="年度"
						value="#session.Account==null ? #request.nd_now : #session.Account.ssnd" />
				</s:if>
				<s:else>
					<s:textfield name="nd" label="年度" 
						value="%{#session.Account==null ? #request.nd_now : #session.Account.ssnd}" 
						readonly="true" />
				</s:else>
			</td> 
			<td>
				<s:if test="#session.Mode=='add_mode'">
					<s:select name="yf" list="{'01','02','03','04','05','06', '07', '08', '09', '10', '11', '12'}" 
						key="月份"  value="#session.Account==null ? #request.yf_now : #session.Account.ssyf"  />
				</s:if>
				<s:else>
					<s:textfield name="yf" label="月份" 
						value="%{#session.Account==null ? #request.yf_now : #session.Account.ssyf}" 
						readonly="true"/>
				</s:else>
			</td>
		</tr>

		<tr>
			<td><s:textfield label="总收入" name="income"
					value="%{#session.Account == null ? 0:#session.Account.income}"
					readonly="true" /></td>
		</tr>
		<tr>
			<td><s:textfield label="工资" name="salary"
					value="%{#session.Account == null ? 0: #session.Account.salary}"
					onchange="calTotalIncome()" /></td>
		</tr>
		<tr>
			<td><s:textfield label="奖金" name="bonus"
					value="%{#session.Account == null ? 0: #session.Account.bonus}"
					onchange="calTotalIncome()" /></td>
		</tr>
		<tr>
			<td><s:textfield label="总支出" name="expenditure"
					value="%{#session.Account == null ? 0 : #session.Account.expenditure}"
					readonly="true" onchange="calTotalIncome()" /></td>
			<td><s:submit key="填写支出" name="calculateExp"
					method="calculateExp"></s:submit></td>
		<tr>
			<td><s:textfield label="净收入" name="netincome"
					value="%{#session.Account.netincome}" readonly="true" /></td>
		</tr>

		<s:submit key="保存" name="accountSave" method="accountSave" />
		<s:actionerror/>
		<s:token></s:token>

	</s:form>


	<a href="account!resetAcc">重置</a><br>
	<a href="${pageContext.request.contextPath }/homepage.jsp">返回首页</a>

	<s:debug></s:debug>
</body>
</html>