<%@page import="java.util.List"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.Date"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="springsec" uri="http://www.springframework.org/security/tags" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>账务查询</title>
</head>
<body>

	<style type="text/css">
		.left {
			/* border: 1px solid blue; */
			margin-left: 15px;
			width: 8%;
			height: 400px;
			float: left;
		}
		
		.main {
			/* border: 1px solid blue; */
			margin-left: 10%;
			margin-right: 10%;
			
			height: 400px;
		}
	</style>



	<br><br>
	
	

	<script type="text/javascript">
	
	// 无法接受某个循环的值
// 		function getUrl()
// 		{
// 			var uuid=document.getElementById("hvalue").value;
// 			alert("account!deleteOne?accuuid="+uuid);
// 			return "account!deleteOne?accuuid="+uuid;
// 		}
	
	
	
		function getUrl1(accuuid)
		{
			if (confirm("确定删除？(accuuid="+accuuid+")")){
				window.location.href = "account!deleteOne?accuuid="+accuuid;
			}
			
		}
		
		function queryByNd()
		{
			var nd = document.getElementById("ndsel").value
			//alert("account!listAllAccInfo?nd="+nd);
			
			window.location.href = "account!listAllAccInfo?nd="+nd;
		}
	</script>
	
	<%
		String nd_now = (new SimpleDateFormat("YYYY")).format(new Date());
		String yf_now = (new SimpleDateFormat("MM")).format(new Date());
		request.setAttribute("nd_now", nd_now);
		request.setAttribute("yf_now", yf_now);
		List<String> ndall = new ArrayList<String>();
		int i = 0;
		for(; i<5; i++){
			ndall.add(i, ""+(Integer.parseInt(nd_now)-4+i));
		}
		ndall.add(i, "全部");
		request.setAttribute("nd_all", ndall);
	%>
	
	<br>
	
	<div class="left">
		<h4>查询页面</h4>	
		<br>
		<s:select id="ndsel" name="nd" list="#request.nd_all" 
			label="年度" headerKey="" headerValue="全部"  onchange="queryByNd()"
			style="width: 60%;" /> 
		<br><br>
		<a href="account!input">增加</a><br><br>
		<a href="account!listAllAccInfo">刷新</a><br><br>
		<a href="${pageContext.request.contextPath}/homepage.jsp">返回首页</a>
		<a href="${pageContext.request.contextPath}/j_spring_security_logout"> Logout</a>
		
	</div>
	
	
	<div class="main">
	用户：<springsec:authentication property="name"/>
	<!-- 用户：${pageContext.request.userPrincipal.name} 或者使用这个方法获得当前用户 -->
	
	
	<table border="2" >
		<tr>
			<td><strong>序号</strong></td>
			<td><strong>所属年月</strong></td>
			<td><strong>总收入</strong></td>
			<td><strong>其中：工资</strong></td>
			<td><strong>其中：奖金</strong></td>
			<td><strong>总支出</strong></td>
			<td><strong>其中：购物</strong></td>
			<td><strong>其中：税费</strong></td>
			<td><strong>其中：贷款</strong></td>
			<td><strong>其中：其他</strong></td>
			<td><strong>净收入</strong></td>
			<td align="center"><strong>编辑</strong></td>
			<td align="center"><strong>删除</strong></td>
		</tr>
		
		
		<s:iterator value="myrequest['accountinfo_all']" status="st" >
			<s:hidden id="hvalue" value="%{accuuid }"  />
			
			
			<tr>
				<td><s:property value="#st.count" /> </td>
				<td> ${ssny.substring(0,4) }年${ssny.substring(4) }月 </td>
				<td> ${income } </td>
				<td> ${salary } </td>
				<td> ${bonus } </td>
				<td> ${expenditure } </td>
				<td> ${expInfo.shopping } </td>
				<td> ${expInfo.tax } </td>
				<td> ${expInfo.loan } </td>
				<td> ${expInfo.others } </td>
				<td> ${netincome } </td>
				
				<td width="50" align="center">
					<springsec:authorize access="hasRole('ROLE_ADMIN')">
					 	<a href="account!input?accuuid=${accuuid }">编辑</a>
					</springsec:authorize> 
				</td>
	<%-- 				<td width="50"> <a href="account!deleteOne?accuuid=${accuuid }">删除</a> </td> --%>
					
	<!-- 				<td width="50"> <a href="javascript:if(confirm('确定删除？')) window.location.href=getUrl1()">删除</a> </td>  -->
				
				<td width="50" align="center"> 
					<springsec:authorize access="hasRole('ROLE_ADMIN')">
					<a href="javascript:void(0);" onclick=getUrl1("${accuuid }")>删除</a>
					</springsec:authorize> 
				</td>
				
			</tr>
		</s:iterator>
		
		<tfoot style="color:blue" >
			<tr>
				<td>汇总</td>
				<td align="center">-</td>
				<td> ${acc_aggregation.income } </td>
				<td> ${acc_aggregation.salary } </td>
				<td> ${acc_aggregation.bonus } </td>
				<td> ${acc_aggregation.expenditure } </td>
				<td> ${acc_aggregation.expInfo.shopping } </td>
				<td> ${acc_aggregation.expInfo.tax } </td>
				<td> ${acc_aggregation.expInfo.loan } </td>
				<td> ${acc_aggregation.expInfo.others } </td>
				<td> ${acc_aggregation.netincome } </td>
				<td align="center">-</td>
				<td align="center">-</td>
			</tr>
		</tfoot>
	</table>
	
	</div>
	
	<br>
	
	<s:debug></s:debug>
	
</body>
</html>