<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<link rel="stylesheet" type="text/css" href="../css/header.css" />
	<link rel="stylesheet" type="text/css" href="../css/header-account.css" />
	<link rel="stylesheet" type="text/css" href="../css/footer.css" />
	<link rel="stylesheet" type="text/css" href="../css/left-nav.css" />
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath }/js/checkbix/css/checkbix.css">
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath }/js/go2top/style.css"> 
	
<style type="text/css">
	body {
	    font-family: Verdana,sans-serif;
	    margin: 0;
	    padding: 0;
	    font-size: 12px;
	    background: #f5f5f5;
	}
	
	.contentPage_1{
			position:relative;
			padding-top: 15px;
			max-width: 960px;
			margin:auto; /* div 居中 */
   			/* padding-right: 10px;
    		padding-bottom: 0px;
    		padding-left: 10px; */
		}
	
		
	.bizContent{border:1px #E0E0E0 solid;border-left:1px #BBB solid;border-right:1px #BBB solid;background:#FFF;}
		
	#list_page, .list_page {
	    /* margin: 10px; */
	    color: #000;
	    min-height: 620px;
	    padding-top:  10px;
	}
	
	#list_header, .list_header {
	    height: 35px;
	    line-height: 35px;
	    color: #737373;
	    background: #eee;
	}
	
	#list_header .lineCheck, .list_header .lineCheck {
	    margin-top: 10px;
	    margin-bottom: 0;
	}
	
	.lineCheck {
	    width: 15px;
	    height: 15px;
	    line-height: 15px;
	    margin: 10px 10px;
	    _margin: 12px;
	    _font-size: 0;
	}
	
	#list_header ul li, .list_header ul li {
	    padding: 0 10px;
	}
	
	#list_header .sep, .list_header .sep {
	    background: url(../components/listHeaderSep.png) no-repeat 0;
	    
	}

	div, form, img, ul, ol, li, dl, dt, dd, p, span, input {
	    margin: 0;
	    padding: 0;
	    border: 0;
	}
	div {
	    display: block;
	}
	
	.list_day_head {
	    height: 25px;
	    line-height: 25px;
	    background: #F5F5F5;
	    border-bottom: 1px #D4DAE2 solid;
	    color: #737373;
	}
	
	.left {
	    float: left;
	    display: inline;
	}
	
	.listLine {
	    border-bottom: 1px #E7EBEF solid;
	    cursor: pointer;
	    height: 35px;
	    line-height: 35px;
	}
	
	.lineCheck {
	    width: 15px;
	    height: 15px;
	    line-height: 15px;
	    margin: 10px 10px;
	    _margin: 12px;
	    _font-size: 0;
	}
	
	.listLine ul li {
	    padding: 0 10px;
	    height: 35px;
	    line-height: 35px;
	}
	
	.right {
	    float: right;
	    display: inline;
	}
	
	li {
	    list-style-type: none;
	}
	
	.fontRight {
	    text-align: right;
	}
	
	.cutlong {
	    text-overflow: ellipsis;
	    overflow: hidden;
	    white-space: nowrap;
	    _word-wrap: break-word;
	}
	
	.fontRed {
	    color: #C80032;
	}
	
	.fontGreen {
    color: #009632;
	}
	
	.cutlongForIe {
	    text-overflow: ellipsis;
	    overflow: hidden;
	    white-space: nowrap;
	}
	
	.listEdit {
	    background-color: #F1F4F8;
	    border: 1px #B6C4D8 solid;
	    margin-top: -1px;
	}
	
	#tabList .current {
    background: #FFF;
    font-weight: bold;
    height: 41px;
    line-height: 41px;
    margin-bottom: -1px;
    
	}
	.tabList{
	height:40px;
	background-color:#F0F2F5;
	border:1px #BBB solid;
	border-bottom:0;
	font-size:13px;
	margin-top: 28px;  /* gxf 需体验效果可以取消这个样式查看*/
	}
	
	.tab {
	    position: relative;
	    float: left;
	    display: inline-block;
	    text-align: center;
	    color: #000;
	    width: 110px;
	    height: 39px;
	    line-height: 39px;
	    cursor: pointer;
	    border-right: 1px #E0E0E0 solid;
	    border-bottom: 0;
	}
	
	.mainWidth {
	    max-width: 1190px;
	    min-width: 960px;
	    width: 100%;
	    margin: 0 auto;
	    overflow: hidden;
	}

	
	#sub_query, #patch_del, #editOne, #refreshdetail{
	height: 26px; width:80px;background: #ffffff;  border: 2px solid #CCC;
	}
	
	#nav_left {
    border-right: 0;
    background: url(../components/bg_1.png);
    
	}
	#nav_right {
	    border-left: 0;
	    background: url(../components/bg_1.png);
	   
	}
	#nav_left, #nav_right {
	    float: left;
	    width: 24px;
	    border: 1px solid #CCC;
	    cursor: pointer;
	    height: 24px;
	    line-height: 24px;
	    text-align: center;
	    font-weight: bold;
	}
	
	.img_2_5 {
	    background-position: -223px -102px;
	}
	.img_2_4 {
	    background-position: -183px -102px;
	}
	.img_2_4, .img_2_5, .img_2_6, .img_2_7, .img_2_8 {
	    display: inline-block;
	    width: 3px;
	    height: 5px;
	    line-height: 0;
	    margin-top: 10px;
	    background-image: url(../components/innerpageSprite.gif);
	}


</style>
	<script type="text/javascript" src="../js/jquery.js"></script>
	<!-- Bootstrap CDN -->
	<%-- <link href="https://cdn.bootcss.com/bootstrap/3.3.7/css/bootstrap.min.css" rel="stylesheet">
	<script src="https://cdn.bootcss.com/bootstrap/3.3.7/js/bootstrap.min.js"></script> --%>
	<!-- 本地Bootstrap -->
	<%-- <link href="../bootstrap/css/bootstrap.min.css" rel="stylesheet">
	<script src="../bootstrap/js/bootstrap.min.js"></script> --%>
	<script src="../js/checkbix/js/checkbix.min.js"></script>
	<script src="../js/go2top/main.js"></script>
	
	<script>
		Checkbix.init();
	</script>
	<script type="text/javascript">
		$(document).ready(function() { 
			var url = window.location.href;
			
			if (url.indexOf("Payment")!=-1 || url.indexOf("payment")!=-1)
			{
				$("#detail-outgo").siblings().removeClass("current");
				$("#detail-outgo").addClass("current");	
				$("#refreshdetail").hide();
			}	
			else if (url.indexOf("Income")!=-1 || url.indexOf("income")!=-1){
				$("#detail-income").siblings().removeClass("current");
				$("#detail-income").addClass("current");	
				$("#refreshdetail").hide();
			}
			else if(url.indexOf("Transfer")!=-1 || url.indexOf("transfer")!=-1){
				$("#detail-transfer").siblings().removeClass("current");
				$("#detail-transfer").addClass("current");
				$("#refreshdetail").hide();
			}
			else {
				$("#detail-all").siblings().removeClass("current");
				$("#detail-all").addClass("current");	
				$("#refreshdetail").show();
			}
			
			$('#logout-url').click(function (){	
				 $("#logout-form").submit();
			});
		}); 
 

		
		function chooseTab(A){
			var AA = $("#"+A);
			switch (A) {
			case "detail-all":
				window.location.href = "${pageContext.request.contextPath}/demo/listDetail!listByMonth";
				break;
				
			case "detail-outgo":
				window.location.href = "${pageContext.request.contextPath}/demo/listDetail!listPaymentByMonth";
				break;
				
			case "detail-income":
				window.location.href = "${pageContext.request.contextPath}/demo/listDetail!listIncomeByMonth";
				break;
				
			case "detail-transfer":
				window.location.href = "${pageContext.request.contextPath}/demo/listDetail!listTransferByMonth";
				break;
				
			case "record":
				window.location.href = "${pageContext.request.contextPath}/demo/paymentDetail!inputPayment";
				break;
				
			case "query-payment":
				//window.location.href = "${pageContext.request.contextPath}/demo/customTailoredQuery!inputQuery";
				window.location.href = "${pageContext.request.contextPath}/demo/customTailoredQuery!inputPaymentQuery";
				break;
				
			default:
				break;
			}
			
		}
		
		/* $(function() {
		    var now = new Date();
		    var str = now.getFullYear() + "-" + fix((now.getMonth() + 1),2) + "-" + "01";
		    $("#date_from").val(str);
		    
		    // 获取本月最后一天
		    var new_year = now.getFullYear();    //取当前的年份           
		    var new_month = now.getMonth() + 1; //取下一个月的第一天，方便计算（最后一天不固定）           
		    if(new_month>12)            //如果当前大于12月，则年份转到下一年           
		    {          
		     new_month -= 12;        //月份减           
		     new_year++;             //年份增           
		    }          
		    var new_date = new Date(new_year,new_month,1);                //取当年当月中的第一天           
		    var date_count =  (new Date(new_date.getTime()-1000*60*60*24)).getDate();//获取当月的天数         
		    var last_date =   new Date(new_date.getTime()-1000*60*60*24);//获得当月最后一天的日期  
		    //alert(last_date);
		    str = last_date.getFullYear() + "-" + fix((last_date.getMonth() + 1),2) + "-" + fix(last_date.getDate(),2);
		    $("#date_to").val(str);
		})
		*/
		function fix(num, length) {
		  return ('' + num).length < length ? ((new Array(length + 1)).join('0') + num).slice(-length) : '' + num;
		} 
		
		function queryFixedPeriod(){
			var date_from = new Date($("#date_from").val());
			var date_from_str = date_from.getFullYear() + "-" + fix(date_from.getMonth()+1,2) + "-" + fix(date_from.getDate(),2);
			var date_to = new Date($("#date_to").val());
			var date_to_str = date_to.getFullYear() + "-" + fix(date_to.getMonth()+1, 2) + "-" + fix(date_to.getDate(), 2);
// 			alert(first_date_str+" "+last_date_str);
// 			alert("${pageContext.request.contextPath}/demo/listDetail!listByMonth?date_from=" +first_date_str+ "&date_to=" + last_date_str);

			var url = window.location.href;
			var query_url;
			if (url.indexOf("Payment")!=-1 || url.indexOf("payment")!=-1){
				query_url = "${pageContext.request.contextPath}/demo/listDetail!listPaymentByMonth?date_from=" +date_from_str+ "&date_to=" + date_to_str;	
			}else if (url.indexOf("Income")!=-1 || url.indexOf("income")!=-1){
				query_url = "${pageContext.request.contextPath}/demo/listDetail!listIncomeByMonth?date_from=" +date_from_str+ "&date_to=" + date_to_str;	
			}else if(url.indexOf("Transfer")!=-1 || url.indexOf("transfer")!=-1){
				query_url = "${pageContext.request.contextPath}/demo/listDetail!listTransferByMonth?date_from="+date_from_str+ "&date_to=" + date_to_str;	
			}else{
				query_url = "${pageContext.request.contextPath}/demo/listDetail!listByMonth?date_from=" +date_from_str+ "&date_to=" + date_to_str;
			}
			
			//alert(query_url);
			window.location.href = query_url;
		}
		
		function queryPreMonth(){
			//获取本月第一天
			var date_from = new Date($("#date_from").val());
			var date_1 = new Date(date_from.getFullYear(), date_from.getMonth(), 1); // 本月第一天
			// 上月最后一天
			var last_date = new Date(date_1.getTime()-1000*60*60*24);
			var last_date_str = last_date.getFullYear() + "-" + fix((last_date.getMonth() + 1),2) + "-" + fix(last_date.getDate(),2);
			
			// 计算上月第一天
			var first_date = new Date(last_date.getFullYear(), last_date.getMonth(), 1);
			var first_date_str = first_date.getFullYear() + "-" + fix((first_date.getMonth() + 1),2) + "-" + fix(first_date.getDate(),2);
// 			alert(first_date_str+" "+last_date_str);
// 			alert("${pageContext.request.contextPath}/demo/listDetail!listByMonth?date_from=" +first_date_str+ "&date_to=" + last_date_str);

			var url = window.location.href;
			var query_url;
			if (url.indexOf("Payment")!=-1 || url.indexOf("payment")!=-1){
				query_url = "${pageContext.request.contextPath}/demo/listDetail!listPaymentByMonth?date_from=" +first_date_str+ "&date_to=" + last_date_str;	
			}else if (url.indexOf("Income")!=-1 || url.indexOf("income")!=-1){
				query_url = "${pageContext.request.contextPath}/demo/listDetail!listIncomeByMonth?date_from=" +first_date_str+ "&date_to=" + last_date_str;	
			}else if(url.indexOf("Transfer")!=-1 || url.indexOf("transfer")!=-1){
				query_url = "${pageContext.request.contextPath}/demo/listDetail!listTransferByMonth?date_from=" +first_date_str+ "&date_to=" + last_date_str;	
			}else{
				query_url = "${pageContext.request.contextPath}/demo/listDetail!listByMonth?date_from=" +first_date_str+ "&date_to=" + last_date_str;
			}
			
			window.location.href = query_url;
		}
		
		function queryNextMonth(){
			var date_to = new Date($("#date_to").val());
			var new_year = date_to.getFullYear();
			var new_month = date_to.getMonth()+1;
			if (new_month == 12){
				new_year++;
				new_month = 0;
			}
			
			// 下月第一天
			var first_date = new Date(new_year, new_month, 1);
			new_month++;
			if (new_month == 12){
				new_month = 0;
				new_year++;
			}
			// 下下个月第一天
			var date_tmp = new Date(new_year, new_month, 1);
			var last_date = new Date(date_tmp.getTime()-1000*60*60*24)
			//alert(first_date+ " " + last_date);
			var first_date_str = first_date.getFullYear() + "-" + fix((first_date.getMonth() + 1),2) + "-" + fix(first_date.getDate(),2);
			var last_date_str = last_date.getFullYear() + "-" + fix((last_date.getMonth() + 1),2) + "-" + fix(last_date.getDate(),2);
			
			var url = window.location.href;
 			var query_url;
 			if (url.indexOf("Payment")!=-1 || url.indexOf("payment")!=-1){
 				query_url = "${pageContext.request.contextPath}/demo/listDetail!listPaymentByMonth?date_from=" +first_date_str+ "&date_to=" + last_date_str;
			}else if (url.indexOf("Income")!=-1 || url.indexOf("income")!=-1){
				query_url = "${pageContext.request.contextPath}/demo/listDetail!listIncomeByMonth?date_from=" +first_date_str+ "&date_to=" + last_date_str;
			}else if(url.indexOf("Transfer")!=-1 || url.indexOf("transfer")!=-1){
				query_url = "${pageContext.request.contextPath}/demo/listDetail!listTransferByMonth?date_from=" +first_date_str+ "&date_to=" + last_date_str;
			}else{
				query_url = "${pageContext.request.contextPath}/demo/listDetail!listByMonth?date_from=" +first_date_str+ "&date_to=" + last_date_str;
			}
 			
			window.location.href = query_url;
		}
		
		function editAccItem(){
			var chk_value =[]; 
			var  url;
			var  thisurl = window.location.href;
			
			if (thisurl.indexOf("Payment")!=-1 || thisurl.indexOf("payment")!=-1){
				url = "${pageContext.request.contextPath}/demo/paymentDetail!editShow?";
			}else if (thisurl.indexOf("Income")!=-1 || thisurl.indexOf("income")!=-1){
				url = "${pageContext.request.contextPath}/demo/incomeDetail!editShow?";
			}else if(thisurl.indexOf("Transfer")!=-1 || thisurl.indexOf("transfer")!=-1){
				url = "${pageContext.request.contextPath}/demo/transferDetail!editShow?";
			}else{
				url = "${pageContext.request.contextPath}/demo/accountDetail!editShow?";
			}
			
			
			$('input[name="ckitem"]:checked').each(function(){ 
				chk_value.push($(this).val());
			});
			
			if (chk_value.length == 0){
				alert('你还没有选择任何内容！'); 
				return;
			}else if (chk_value.length > 1){
				alert('只能选择一条记录！'); 
				return;
			}
			
			if (thisurl.indexOf("Payment")!=-1 || thisurl.indexOf("payment")!=-1 || 
					thisurl.indexOf("Income")!=-1 || thisurl.indexOf("income")!=-1 || 
					thisurl.indexOf("Transfer")!=-1 || thisurl.indexOf("transfer")!=-1){
		
				url +="mxuuidList="+chk_value[0];
			}
			else{
				
				url +="accuuidList="+chk_value[0];
			}
			url += "&date_from=" + $("#date_from").val();
			url += "&date_to=" + $("#date_to").val();
			//alert(url);
			
			window.location.href = url;
		}
		
		function refresh(){
			var  url;
			var  thisurl = window.location.href;
			
			if (thisurl.indexOf("Payment")!=-1 || thisurl.indexOf("payment")!=-1){
				return;
			}else if (thisurl.indexOf("Income")!=-1 || thisurl.indexOf("income")!=-1){
				return;
			}else if(thisurl.indexOf("Transfer")!=-1 || thisurl.indexOf("transfer")!=-1){
				return;
			}else{
				url = "${pageContext.request.contextPath}/demo/listDetail!listByMonthRefresh?";
			}
			
			url += "&date_from=" + $("#date_from").val();
			url += "&date_to=" + $("#date_to").val();
			
			window.location.href = url;
		}
		
		function deleteAccItem(){
			var chk_value =[]; 
			var  url;
			var  thisurl = window.location.href;
			if (thisurl.indexOf("Payment")!=-1 || thisurl.indexOf("payment")!=-1){
				url = "${pageContext.request.contextPath}/demo/paymentDetail!delPatch?";
			}else if (thisurl.indexOf("Income")!=-1 || thisurl.indexOf("income")!=-1){
				url = "${pageContext.request.contextPath}/demo/incomeDetail!delPatch?";
			}else if(thisurl.indexOf("Transfer")!=-1 || thisurl.indexOf("transfer")!=-1){
				url = "${pageContext.request.contextPath}/demo/transferDetail!delPatch?";
			}else{
				url = "${pageContext.request.contextPath}/demo/accountDetail!delPatch?";
			}
			
			if (thisurl.indexOf("Payment")!=-1 || thisurl.indexOf("payment")!=-1 || 
					thisurl.indexOf("Income")!=-1 || thisurl.indexOf("income")!=-1 || 
					thisurl.indexOf("Transfer")!=-1 || thisurl.indexOf("transfer")!=-1){
				$('input[name="ckitem"]:checked').each(function(){ 
					chk_value.push($(this).val());
					url += "mxuuidList=" + $(this).val()+"&";
				}); 
				url +="mxuuidList=0000";
			}
			else{
				$('input[name="ckitem"]:checked').each(function(){ 
					chk_value.push($(this).val());
					url += "accuuidList=" + $(this).val()+"&";
				});
				url +="accuuidList=0000";
			}
			
			if (chk_value.length == 0){
				//alert(chk_value.length==0 ?'你还没有选择任何内容！':chk_value); 
				alert('你还没有选择任何内容！'); 
				return;
			}
			// 保证删除后仍旧重定向到删除前页面
			url += "&date_from=" + $("#date_from").val();
			url += "&date_to=" + $("#date_to").val();
			//alert(url);
			if (confirm("确定删除？")){
				window.location.href = url;
			}
			// alert(chk_value.length==0 ?'你还没有选择任何内容！':chk_value); 
			//alert(url); 
		}
		
		function checkAll(){
			 //判断checkbox是否选中（jquery 1.6以前版本 用  $(this).attr("checked")
			
			var chk = $('input:checkbox:first').prop("checked");
			
			$('input:checkbox').each(function() {
				$(this).prop('checked', chk);
			});

		}
	</script>

<link rel="shortcut icon" href="../components/favicon.ico" type="image/x-icon" />
<title>明细</title>
</head>
<body>

	<div class="site-nav" id="siteNav">
		<div class="g-layout-header">
			<ul >
				<li>
					<form id="logout-form" action="${pageContext.request.contextPath}/j_spring_security_logout" method="post" hidden >
						<input type="submit" value="退出" class="home " />
						<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
					</form>
					<a id="logout-url" href="#" class="home " style=" text-decoration: none;">退出</a>
					<%-- <a href="${pageContext.request.contextPath}/j_spring_security_logout" class="home " style=" text-decoration: none;">退出</a> --%>
				</li>
				
				<li>
					<a href="frontStatistics!inputFront" class="home before" style=" text-decoration: none;" >首页</a>
				</li>
				
				<li>
					<%-- <springsec:authentication property="name"/> --%>
					<div class=" rel">
						<i class="g-icon-header"></i> <!-- 用户图标 -->
						<%-- <span class="glyphicon glyphicon-user"></span> --%>
						<span class="userAccount"><span>${pageContext.request.userPrincipal.name}</span></span>
					</div>
					
				</li>
				
			</ul>
		</div>
	</div>
	
	<div class="mainWidth" style="position:relative;">
		<div id="nav" class="nav" style="margin-top: 16px">
			  <div id="menu" >
				
				<div id="record" class="menu" firsttabid="record-outgo" href="/biz/recordPage_main.action" onclick="chooseTab('record');">
					<div class="itemWrap">
						<div class="img_0_1 icon-nav ddfix">&nbsp;</div>
						<span>记账</span>
					</div>
				</div>
				<div id="detail" class="menu current" firsttabid="detail-all" href="/biz/detailPage_main.action" onclick="chooseTab('detail-all');">
					<div class="itemWrap">
						<div class="img_0_3 icon-nav ddfix">&nbsp;</div>
						<span>明细</span>
					</div>
				</div>
				<div id="query" class="menu"  onclick="chooseTab('query-payment');">
					<div class="itemWrap rel">
						<div class="img_0_5 icon-nav ddfix">&nbsp;</div>
						<span>查询</span> <!-- <i class="icon-nav icon-arrow icon-arrow-down abs ddfix"></i> -->
					</div>
				</div>
				<div id="set" class="menu">
					<div class="itemWrap rel">
						<div class="img_0_7 icon-nav ddfix">&nbsp;</div>
						<span>设置</span> <!-- <i class="icon-nav icon-arrow icon-arrow-down abs ddfix"></i> -->
					</div>
				</div>
			
			</div>
		</div>
	
	<div id="content" class="content" style="min-height: 650px; height: auto;margin: 0px 5px 0 140px;" src="/biz/detailPage_main.action">
	
	<div class="contentPage_1" style="min-height: 650px;">
	<s:form action="listDetail">
	<div>
		<div id="nav_left" onclick="queryPreMonth()"><div class="img_2_4">&nbsp;</div></div>
		<div style="float: left">
		<input id="date_from" type="date" value="${requestScope.date_from }" name="date_from" style="height:24px; width:150px; padding-top: 0px; padding-bottom: 0px; margin-bottom:2px; margin-left:2px; margin-right:10px; background: #ffffff; border:1px solid #CCC">
		至
		<input id="date_to" type="date" value="${requestScope.date_to }" name="date_to" style="height:24px; width:150px; padding-top: 0px; padding-bottom: 0px; margin-bottom:2px; margin-left:10px; margin-right:2px; background: #ffffff; border:1px solid #CCC">
		</div>
		<div id="nav_right" onclick="queryNextMonth()"><div class="img_2_5">&nbsp;</div></div>
		<div style="float:left; margin-left: 10px; ">
			<%-- <s:submit id="sub_query" key="查询" onclick="queryFixedPeriod()" method="listByMonth" theme="simple" /> --%>
			<input type="button" id="sub_query" value="查询" onclick="queryFixedPeriod()" />
			<%-- <s:property value="#request.listType" /> --%>
		</div>
		<div style="float:left; margin-left: 10px; ">
			<input id="patch_del" type="button" value="批量删除" onclick="deleteAccItem();">
		</div>
		<div style="float:left; margin-left: 10px; ">
			<input id="editOne" type="button" value="编辑" onclick="editAccItem();">
		</div>
		<div style="float:left; margin-left: 10px; ">
			<input id="refreshdetail" type="button" value="刷新" onclick="refresh();">
		</div>
	</div>
	</s:form>
	
	<div id="tabList" class="tabList">  
			<div id="detail-all" class="tab"  onclick="chooseTab('detail-all')">全部</div>  
			<div id="detail-outgo" class="tab" onclick="chooseTab('detail-outgo')">支出</div>  
			<div id="detail-income" class="tab" onclick="chooseTab('detail-income')">收入</div>  
			<div id="detail-transfer" class="tab" onclick="chooseTab('detail-transfer')">转账</div>
	</div>	
		
	<div id="list_page" class ="contentPage_1 bizContent">

		<!-- <div id="list_header">
			
		</div> -->
		<div id="list_header">
			<div class="left lineCheck">
				<!-- <input type="checkbox" onclick="checkAll()"> -->
				<input id="top-checkbox" name="top-checkbox" type="checkbox" class="checkbix" data-text="" data-size="tiny" onclick="checkAll()">
			</div>
			<ul> 
				<li class="right lineLast" style="width:52px;">&nbsp;</li>
				<li class="left sep" style="width:15px;">
					<div class="img_1_9" style="margin:9px 3px;">&nbsp;</div>
				</li>
				<li class="left fontRight sep" style="width:60px;">时间</li>
				<li class="left sep" style="width:100px;">金额</li>
				<li class="left sep" style="width:80px;">类别</li>
				<s:if test="#request.listType=='listAll'">
					<li class="left sep" style="width:90px;">商家/付款方</li>
				</s:if>
				<s:elseif test="#request.listType=='listIncome'">
					<li class="left sep" style="width:90px;">付款方</li>
				</s:elseif>
				<s:elseif test="#request.listType=='listPayment'">
					<li class="left sep" style="width:90px;">商家</li>
				</s:elseif>
				<li class="left sep" style="width:40px;">备注</li>
			</ul>
		</div>
		<div id="list_content">
			<!-- 某一天汇总 -->
			
			<!-- 时间信息头 -->
			<%-- <div class="list_day_head" style="display: block;">
				<div class="left" style="height:23px;line-height:23px;width:15px;padding: 0 0px;">&nbsp;</div>
				<ul> 
					<li class="left" style="padding-left:0px;"><span>10-10 星期二</span></li> 
					<li class="left" style="padding-left:20px;"><span>支出:￥1000.00 &nbsp; &nbsp; &nbsp;收入:￥1184.25</span></li> 
				</ul>
			</div>
			
			<div id="list_line_f5cb7170c88a4b8dacaaba103abb8917" class="listLine" type="outgo" bizid="">
				<div class="left lineCheck">
					<input type="checkbox" style="_width:13px;" onclick="checkLine('f5cb7170c88a4b8dacaaba103abb8917', true)">
				</div>
				<ul onclick="openEditPage('f5cb7170c88a4b8dacaaba103abb8917','detail',bindDetail, 'outgo',true)">
					<li class="right lineLast" style="width:52px;">&nbsp;</li>
					<li class="left" style="width:15px;height:35px;">&nbsp;</li>
					<li class="left fontRight" style="width:60px;">08:37</li>
					<li class="left cutlong fontRed" style="width:100px;">￥1000.00</li>
					<li class="left cutlong" style="width:80px;">人情-礼金红包</li>
					<li class="left cutlong" style="width:90px;">无&nbsp;</li>
					<li class="cutlongForIe" style="color:#999;">无&nbsp;</li>
				</ul>
			</div> --%>
			
			<!-- 一天中明细的分割线 -->
			<!-- <div id="list_edit_f5cb7170c88a4b8dacaaba103abb8917" class="listEdit" style="display: none;"> 
				<div class="listIn loading" style="height:216px;_height:229px;margin-top:13px;"></div> 
			</div>
			
			
			<div id="list_line_37d93825fbbc46b59c879a00bb6e01ea" class="listLine" type="income" bizid="">
				 <div class="left lineCheck"> 
				 	<input type="checkbox" style="_width:13px;" onclick="checkLine('37d93825fbbc46b59c879a00bb6e01ea', true)"> 
				 </div> 
			 <ul onclick="openEditPage('37d93825fbbc46b59c879a00bb6e01ea','detail',bindDetail, 'income',true)"> 
			 	<li class="right lineLast" style="width:52px;">&nbsp;</li> 
			 	<li class="left" style="width:15px;height:35px;">&nbsp;</li> 
			 	<li class="left fontRight" style="width:60px;">08:37</li> 
			 	<li class="left cutlong fontGreen" style="width:100px;">￥1184.25</li> 
			 	<li class="left cutlong" style="width:80px;">理财利息</li> 
			 	<li class="left cutlong" style="width:90px;">&nbsp;</li> 
			 	<li class="cutlongForIe" style="color:#999;">&nbsp;</li> 
			 	</ul> 
			 </div>
			
			分割线	
			<div id="list_edit_37d93825fbbc46b59c879a00bb6e01ea" class="listEdit" style="display: none;"> 
				<div class="listIn loading" style="height:216px;_height:229px;margin-top:13px;"></div> 
			</div>	 -->
			
			
			<s:iterator value="#request.detailList" var="dateStat" status="st">
				<!-- 时间信息头 -->
				<%-- <s:property value="#st.count"/> --%>
				<div class="list_day_head" style="display: block;">
				
					<div class="left" style="height:23px;line-height:23px;width:15px;padding: 0 0px;">&nbsp;</div>
					<ul> 
						<li class="left" style="padding-left:70px;"><span>${dateToShow }</span></li> 
						<li class="left" style="padding-left:20px;"><span>支出:￥${paymentsum } &nbsp; &nbsp; &nbsp;收入:￥${incomesum }</span></li> 
					</ul>
				</div>
				
				<s:iterator value="detail_list" var="accoutVo" status="sst">
					<%-- <s:property value="#sst.count"/> --%>
					<div  class="listLine" type="outgo" bizid="">
						<div class="left lineCheck">
							<!-- s:checkbox的value无法使用，如果有值就会使box被选中 -->
							<%-- <s:checkbox name="ckitem" id="%{mxuuid}"  style="_width:13px;" theme="simple" /> --%>
							<s:if test="#request.listType=='listAll'">
								<%-- <input type="checkbox" name="ckitem" value="${accuuid }" style="_width:13px;"> --%>
								<input id="${accuuid }" name="ckitem" type="checkbox" value="${accuuid }" class="checkbix" data-text="" data-size="tiny" >
							</s:if>
							<s:else>
								<%-- <input type="checkbox" name="ckitem" value="${mxuuid }" style="_width:13px;"> --%>
								<input id="${mxuuid }" name="ckitem" type="checkbox" value="${mxuuid }" class="checkbix" data-text="" data-size="tiny" >
							</s:else>
						</div>
						<ul onclick="openEditPage('f5cb7170c88a4b8dacaaba103abb8917','detail',bindDetail, 'outgo',true)">
							<li class="right lineLast" style="width:52px;">&nbsp;</li>
							<li class="left" style="width:15px;height:35px;">&nbsp;</li>
							<li class="left fontRight" style="width:60px;">${fsrqToShow }</li>
							<s:if test="#accoutVo.type == 1">
								<li class="left cutlong fontRed" style="width:100px;">${je }</li>	
							</s:if>
							<s:elseif test="#accoutVo.type == 2">
								<li class="left cutlong fontGreen" style="width:100px;">${je }</li>
							</s:elseif>
							<s:else>
								<li class="left cutlong" style="width:100px;">${je }</li>
							</s:else>
							
							<s:if test="#accoutVo.type == 1 or #accoutVo.type == 2">
								<li class="left cutlong" style="width:80px;">${lbmc }</li>
								<li class="left cutlong" style="width:90px;">${seller }&nbsp;</li>
							</s:if>
							<s:elseif test="#accoutVo.type == 3">
								<li class="left cutlong" style="width:80px;">转账</li>
								<li class="left cutlong" style="width:90px;">无</li>
							</s:elseif>
							
							<li class="cutlongForIe" style="color:#999;"> ${bz } &nbsp;</li>
							<li style="display:none" id="itemType"> ${type } </li>
						</ul>
					</div>
					
					<!-- 分割线 -->	
					<div id="list_edit_37d93825fbbc46b59c879a00bb6e01ea" class="listEdit" style="display: none;"> 
						<div class="listIn loading" style="height:216px;_height:229px;margin-top:13px;"></div> 
					</div>	
				</s:iterator>
				
				
			</s:iterator>
			
		</div>
		
		</div>
		</div>
		</div>
		
		<a href="#0" class="cd-top">Top</a>
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
			
	
	
	<s:debug></s:debug>
</body>
</html>