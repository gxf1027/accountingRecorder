<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<link rel="stylesheet" type="text/css" href="../css/list-transfer.css" />
	<link rel="stylesheet" type="text/css" href="../css/header-account.css" />
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath }/js/checkbix/css/checkbix.css">
	
	<script type="text/javascript" src="../js/jquery.js"></script>
	<script src="../js/checkbix/js/checkbix.min.js"></script>
	<script>
		Checkbix.init();
	</script>
	
	<script type="text/javascript">
		function chooseTab(A){
			var AA = $("#"+A);
			switch (A) {
			case "detail-all":
				AA.siblings().removeClass("current");
				AA.addClass("current");
				window.location.href = "${pageContext.request.contextPath}/demo/listDetail!listByMonth";
				break;
				
			case "detail-outgo":
				AA.siblings().removeClass("current");
				AA.addClass("current");
				window.location.href = "${pageContext.request.contextPath}/demo/listDetail!listPaymentByMonth";
				break;
				
			case "detail-income":
				AA.siblings().removeClass("current");
				AA.addClass("current");
				window.location.href = "${pageContext.request.contextPath}/demo/listDetail!listIncomeByMonth";
				break;
			case "detail-transfer":
				AA.siblings().removeClass("current");
				AA.addClass("current");
				window.location.href = "${pageContext.request.contextPath}/demo/listDetail!listTransferByMonth";
				break;
				
			case "record":
				window.location.href = "${pageContext.request.contextPath}/demo/paymentDetail!inputPayment";
				break;
				
			case "query-payment":
				window.location.href = "${pageContext.request.contextPath}/demo/customTailoredQuery!inputQuery";
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
			var query_url = "${pageContext.request.contextPath}/demo/listDetail!listTransferByMonth?date_from=" +first_date_str+ "&date_to=" + last_date_str;
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
 			var query_url = "${pageContext.request.contextPath}/demo/listDetail!listTransferByMonth?date_from=" +first_date_str+ "&date_to=" + last_date_str;
// 			alert(query_url);
			window.location.href = query_url;
		}
		
		
		function deleteAccItem(){
			var chk_value =[]; 
			var  url;
			var  thisurl = window.location.href;
			
			
			url = "${pageContext.request.contextPath}/demo/transferDetail!delPatch?";
			$('input[name="ckitem"]:checked').each(function(){ 
				chk_value.push($(this).val());
				url += "mxuuidList=" + $(this).val()+"&";
			}); 
			//url +="mxuuidList=0000";
			url += "&date_from=" + $("#date_from").val();
			url += "&date_to=" + $("#date_to").val();
			
			if (chk_value.length == 0){
				//alert(chk_value.length==0 ?'你还没有选择任何内容！':chk_value); 
				alert('你还没有选择任何内容！'); 
				return;
			}
			
			if (confirm("确定删除？")){
				window.location.href = url;
			}
		}
		
		function checkAll(){
			 //判断checkbox是否选中（jquery 1.6以前版本 用  $(this).attr("checked")
			
			var chk = $('input:checkbox:first').prop("checked");
			
			$('input:checkbox').each(function() {
				$(this).prop('checked', chk);
			});
		}
		
		
		function editAccItem(){
			var chk_value =[]; 
			var  url;
			var  thisurl = window.location.href;
			
			url = "${pageContext.request.contextPath}/demo/transferDetail!editShow?";
			
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
			
			url +="mxuuidList="+chk_value[0];
			
			url += "&date_from=" + $("#date_from").val();
			url += "&date_to=" + $("#date_to").val();
			// alert(url);
			
			window.location.href = url;
		}
	</script>

<link rel="shortcut icon" href="../components/favicon.ico" type="image/x-icon" />
<title>转账明细</title>
</head>
<body>

	<div class="site-nav" id="siteNav">
		<div class="g-layout-header">
			<ul >
				<li>
					<a href="${pageContext.request.contextPath}/j_spring_security_logout" class="home " style=" text-decoration: none;">退出</a>
				</li>
				
				<li>
					<a href="frontStatistics!inputFront"  class="home before" style=" text-decoration: none;">首页</a>
				</li>
				
				<li>
					<%-- <springsec:authentication property="name"/> --%>
					<div class="subNavTitle rel">
						<i class="g-icon-header"></i>
						<span class="userAccount"><span>${pageContext.request.userPrincipal.name}</span></span>
					</div>
				</li>
				
			</ul>
		</div>
	</div>
	
	<div class="mainWidth" style="position:relative;">
	<div id="nav" class="nav">
		  <div id="menu" >
			<style>
				body{background:#f5f5f5;}
				.header {
				  border-bottom: 1px solid #e8e8e8;
				  -webkit-box-shadow: rgba(0,0,0,.1) 0 1px 5px;
				  box-shadow: rgba(0,0,0,.1) 0 1px 5px;
				}
				.header .logo ,
				.site-nav .tool {margin-left:6px;}
				.g-layout {
				max-width: 1184px;
				min-width: 960px;
				width: 100%;
				margin: 0 auto;
				_width: expression(document.documentElement.clientWidth>1280 ? "1280px":(document.documentElement.clientWidth < 960? "960px":"auto"));
				}
				
				
				.news h1 {
					display: none;
				}
			</style>
			<div id="record" class="menu" firsttabid="record-outgo" href="/biz/recordPage_main.action" onclick="chooseTab('record');">
				<div class="itemWrap">
					<div class="img_0_1 icon-nav ddfix">&nbsp;</div>
					<!-- <img id="calc_img" src="../components/record.png" class="img_0_1 icon-nav ddfix" > -->
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
	<s:form action="listDetail" method="post">
	<div>
		<div id="nav_left" onclick="queryPreMonth()"><div class="img_2_4">&nbsp;</div></div>
		<div style="float: left">
		<input id="date_from" type="date" value="${requestScope.date_from }" name="date_from" style="height:24px; width:150px; padding-top: 0px; padding-bottom: 0px; margin-bottom:2px; margin-left:2px; margin-right:10px; background: #ffffff; border:1px solid #CCC">
		至
		<input id="date_to" type="date" value="${requestScope.date_to }" name="date_to" style="height:24px; width:150px; padding-top: 0px; padding-bottom: 0px; margin-bottom:2px; margin-left:10px; margin-right:2px; background: #ffffff; border:1px solid #CCC">
		</div>
		<div id="nav_right" onclick="queryNextMonth()"><div class="img_2_5">&nbsp;</div></div>
		<div style="float:left; margin-left: 10px; ">
			<s:submit id="sub_query" key="查询" method="listTransferByMonth" theme="simple" />
			<%-- <s:property value="#request.listType" /> --%>
		</div>
		<div style="float:left; margin-left: 10px; ">
			<input id="patch_del" type="button" value="批量删除" onclick="deleteAccItem();">
		</div>
		<div style="float:left; margin-left: 10px; ">
			<input id="editOne" type="button" value="编辑" onclick="editAccItem();">
		</div>
	</div>
	</s:form>
	
	<div id="tabList" class="tabList">  
			<div id="detail-all" class="tab"  onclick="chooseTab('detail-all')">全部</div>  
			<div id="detail-outgo" class="tab" onclick="chooseTab('detail-outgo')">支出</div>  
			<div id="detail-income" class="tab" onclick="chooseTab('detail-income')">收入</div>  
			<div id="detail-transfer" class="tab current" onclick="chooseTab('detail-transfer')">转账</div>
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
				<li class="left sep" style="width:160px;">转出账户/金额</li>
				<li class="left sep" style="width:165px;">转入账户/金额</li>
				<li class="left sep" style="width:80px;">类型</li>
				<li class="left sep" style="width:40px;">备注</li>
			</ul>
		</div>
		<div id="list_content">
			
			
			<s:iterator value="#request.detailList" var="dateStat" status="st">
				<!-- 时间信息头 -->
				<%-- <s:property value="#st.count"/> --%>
				<div class="list_day_head" style="display: block;">
				
					<div class="left" style="height:23px;line-height:23px;width:15px;padding: 0 0px;">&nbsp;</div>
					<ul> 
						<li class="left" style="padding-left:70px;"><span>${dateToShow }</span></li> 
						<li class="left" style="padding-left:20px;"><span>转入:￥${transfersum } &nbsp; &nbsp; &nbsp;转出:￥${transfersum }</span></li> 
					</ul>
				</div>
				
				<s:iterator value="detail_list" var="accoutVo" status="sst">
					<%-- <s:property value="#sst.count"/> --%>
					<div  class="listLine" type="outgo" bizid="">
						<div class="left lineCheck">
							<%-- <input type="checkbox" name="ckitem" value="${mxuuid }" style="_width:13px;"> --%>
							<input id="${mxuuid }" name="ckitem" type="checkbox" value="${mxuuid }" class="checkbix" data-text="" data-size="tiny" >
						</div>
						<ul onclick="openEditPage('f5cb7170c88a4b8dacaaba103abb8917','detail',bindDetail, 'outgo',true)">
							<li class="right lineLast" style="width:52px;">&nbsp;</li>
							<li class="left" style="width:15px;height:35px;">&nbsp;</li>
							<li class="left " style="width:60px;">${fsrqToShow }</li>
							<li class="left " style="width:160px; line-height: 20px; ">
								<p class="cutlong">${srcZhmc }</p> 
								<p>${je }</p>
							</li>
							<li class="left " style="width:160px; line-height: 20px; ">
								<p class="cutlong">${tgtZhmc }</p>
								<p>${je }</p>
							</li>
							<li class="left" style="width:80px;"> ${zzlxmc } </li>	
							<li class="cutlongForIe" style="color:#999;"> ${bz } &nbsp;</li>
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