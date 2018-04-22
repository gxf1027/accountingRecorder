<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet" type="text/css" href="../css/header.css" />
<link rel="stylesheet" type="text/css" href="../css/header-account.css" />
<link rel="stylesheet" type="text/css" href="../css/footer.css" />
<link rel="stylesheet" type="text/css" href="../css/left-nav.css" />
<link rel="stylesheet" type="text/css" href="../js/toastr/toastr.css" />
<link rel="stylesheet" type="text/css" href="../js/select2/css/select2.min.css" />


	<style type="text/css"> 
		.mainWidth {
		    max-width: 1390px;
		    min-width: 960px;
		    width: 60%;
		    margin: 0 auto;
		    overflow: hidden;
		}
		.contentPage_1{
			/* position:relative; */
			/* padding-top: 15px; */
			/* max-width: 1360px; */
			margin:auto; /* div 居中 */
   			/* padding-right: 10px;
    		padding-bottom: 0px;
    		padding-left: 10px; */
		}
		
		.record_biz, .query_condition{font-size:12px;font-weight:normal;width:836px;margin-top:2px;}
		.record_biz, .query_condition{border:1px #E0E0E0 solid;border-left:1px #BBB solid;border-right:1px #BBB solid;border-bottom:1px #BBB solid;background:#FFF;}
		.record_biz th{color:#646464;padding-right:6px;text-align:right;font-weight:normal;width:65px;}
		.record_biz td{text-align: center; font-family: Arial; font-size: 14px}
		.query_condition th{color:#646464;padding-right:6px;text-align:right;font-weight:normal;width:65px;}
		.record_biz tr{height:36px;}
		.query_condition tr{height:36px;}
		.recordInput{width:118px;padding-left:0px;height:24px;line-height:24px;border:1px solid #CCC;padding-right:2px;font-size:12px;}
		.selectInput{width:120px;padding-left:5px;height:28px;line-height:24px;border:1px solid #CCC;padding-right:20px;font-size:12px;}
		.queryBtn{cursor:pointer;height:30px;line-height:30px;_height:28px;_line-height:28px;text-align:center;}
		.queryBtn{background:#15A041;color:#FFF;width:110px;border:1px #15A041 solid;font-weight:bold;}
		
		.tabList {
		    width: 75%;
		    margin-left: 170px;
		    margin-top: 15px;
		    height: 40px;
		    background-color: #F0F2F5;
		    border: 1px #BBB solid;
		    border-bottom: 0;
		    font-size: 13px;
		}
		.tabcurrent{background:#FFF;font-weight:bold;height:41px;line-height:41px;margin-bottom:-1px;}
		.tab{position:relative;float:left;display:inline-block;text-align:center;color:#000;width:110px;height:39px;line-height:39px;cursor:pointer;border-right:1px #E0E0E0 solid;border-bottom:0;}
		a {text-decoration: none;}
		.prePage{color: #666;line-height: 25px;height: 25px;}
		.nextPage{color: #666;line-height: 25px;height: 25px;}
		.firstPage , .lastPage{color: #666;line-height: 25px;height: 25px;}
		
		.pageInfo{margin-left: 16%; margin-top: 10px;font:17px "Trebuchet MS", Arial, Helvetica, sans-serif;}
		.even{ background: #FFFFEE;}
		.odd{ background: #E0EEE0;}
		
		.adisabled { pointer-events: none; }
		
		tbody {
			 margin:0px; 
		         padding:0px;
		         color:#6B6854;
		         font:12px "Trebuchet MS", Arial, Helvetica, sans-serif;
		}
		
		.record_biz tbody > tr:hover td{background-color: #eee;color: #494A5F;}
	</style>
	
	<script type="text/javascript" src="../js/jquery.js"></script>
	<script type="text/javascript" src="../js/toastr/toastr.js"></script>
	<script type="text/javascript" src="../js/select2/js/select2.min.js"></script>
	
	<script type="text/javascript">
			
		$(document).ready(function(){
			
			$(".record_biz tbody> tr:odd").addClass("odd");
			$(".record_biz tbody> tr:even").addClass("even");
			
			// 页面加载时进行
			var curDlVal = $("#paydl_dm").val();
			var curXlVal = $("#payxl_dm").val();
			console.log("curDlVal: " + curDlVal);
			console.log("curXlVal: " + curXlVal);
			// 如果是按“查询”后跳转回到这个页面，且此时有大类已有选项
			if (curDlVal != ""){
				$("#payxl_dm").empty();
				$("#payxl_dm").append($("<option>").val("").text("全部"));
				$("#payxl_dm_bak option").each(function(){
					
					var patt1 = new RegExp("^"+curDlVal); // 以dl_dm开头
					if(patt1.test($(this).val())){
						var option = $("<option>").val($(this).val()).text($(this).text());
						$("#payxl_dm").append(option);
					}
				});
				$("#payxl_dm").val(curXlVal); // 通过“查询”后跳转回到这个页面, 设置小类select为查询选中项目
			}
			
		/* 	$('#prePagePayment').click(function(){
				console.log("pre-page");
				var curPageNum = $('#paymentCurPageNum').val();
				if (curPageNum == ""){
					console.log("curPageNum is null");
					return;
				}
				
				curPageNum = Number(curPageNum);
				if (curPageNum <= 1){
					console.log("not previous page");
					return ;
				}else{
					$('#paymentCurPageNum').val(curPageNum-1);
					$('#paymentQuerySubmit').click();
				}
				
			}); 
		
			$('#nextPagePayment').click(function(){
				console.log("next-page");
				var curPageNum = $('#paymentCurPageNum').val();
				if (curPageNum == ""){
					console.log("curPageNum is null");
					curPageNum = "1";
				}
				
				curPageNum = Number(curPageNum);
				$('#paymentCurPageNum').val(curPageNum+1);
				$('#paymentQuerySubmit').click();
				
			});*/
			
			
			$(".firstPage").click(function(){
				
				var $pageNumElem = $(this).parent().siblings().eq(0).children('.currentPageNum');
				var $totalPagesElem = $(this).parent().siblings().eq(0).children('.totalPages');
				
				if ($totalPagesElem.val()== '' || $totalPagesElem.val() == '1' ){
					console.log("total pages not set");
					return;
				}
				$pageNumElem.val('1');
				var $queryBtn = $(this).parent().siblings().eq(0).children().find('.queryBtn');
				$queryBtn.click();
			});
			
			$(".lastPage").click(function(){
				var $pageNumElem = $(this).parent().siblings().eq(0).children('.currentPageNum');
				var $totalPagesElem = $(this).parent().siblings().eq(0).children('.totalPages');
				if ($totalPagesElem.val() == '' || $totalPagesElem.val() == '1' ){
					console.log("total pages : 1");
					return;
				}
				$pageNumElem.val($totalPagesElem.val());
				var $queryBtn = $(this).parent().siblings().eq(0).children().find('.queryBtn');
				$queryBtn.click();
			});
			
			/*向前翻页的链接的class是prePage*/
			$(".prePage").click(function(){
				 console.log($(this).attr('id'));
				 /*当前tab的页码值*/
				 var $pageNumElem = $(this).parent().siblings().eq(0).children('.currentPageNum');
				 var curPageNum = $pageNumElem.val();
				 if (curPageNum == ""){
						console.log("curPageNum is null");
						return;
					}
					
				curPageNum = Number(curPageNum);
				if (curPageNum <= 1){
					console.log("not previous page");
					//style="pointer-events:none"
					return ;
				}else{
					$pageNumElem.val(curPageNum-1); // 页码--
					if (curPageNum-1 == 1){
						
						$(this).css('pointer-events','none');
					}
					var $queryBtn = $(this).parent().siblings().eq(0).children().find('.queryBtn');
					$queryBtn.click();
				}
			});
			
			$(".nextPage").click(function(){
				console.log($(this).attr('id'));
				/*当前tab的页码值*/
				var $pageNumElem = $(this).parent().siblings().eq(0).children('.currentPageNum');
				var curPageNum = $pageNumElem.val();
				
				/*总共多少页*/
				var $totalPagesElem =  $(this).parent().siblings().eq(0).children('.totalPages');
				console.log('总共：' + $totalPagesElem.val());
				var totalPages = Number($totalPagesElem.val());
				
				if (curPageNum == ""){
					console.log("curPageNum is null");
					curPageNum = "1";
				}
				
				$(this).siblings().css('pointer-events','auto'); // '上一页'链接可点击
				
				curPageNum = Number(curPageNum);
				
				if (curPageNum+1 == totalPages){
					$(this).css("pointer-events","none");
				}
				if (curPageNum+1 > totalPages){
					return;
				}
				
				$pageNumElem.val(curPageNum+1); // 页码++
				var $queryBtn = $(this).parent().siblings().eq(0).children().find('.queryBtn');
				
				$queryBtn.click();
			});
			
			$("#paydl_dm").change(function(){
				console.log($(this).val());
				console.log($(this).find("option:selected").text());
				var dl_dm = $(this).val();
				if(dl_dm==""){
					$("#payxl_dm").empty();
					return;
				}
				$("#payxl_dm").empty();
				console.log($("#payxl_dm_bak").find("option:contains('水电')").val()); // 输出100503
				console.log($("#payxl_dm_bak option[value^='1005']").eq(1).val()); // 输出100502
				
				console.log("len: " + $("#payxl_dm_bak option[value^='1005']").length);
				
				console.log("len: " + $("#payxl_dm_bak option").length);
				var option = $("<option>").val("").text("全部");
				$("#payxl_dm").append(option);

				$("#payxl_dm_bak option").each(function(){
					
					var patt1 = new RegExp("^"+dl_dm); // 以dl_dm开头
					if(patt1.test($(this).val())){
						var option = $("<option>").val($(this).val()).text($(this).text());
						$("#payxl_dm").append(option);
					}
				});
				
				
			});
			
			
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
			
			$('#logout-url').click(function (){	
				 $("#logout-form").submit();
			});
			
		})
		
		/* $(document).ready(function() {
			var item={id: 0,  text: 'enhancement' };
			var data=[];
			data.push(item);
		    $('.js-example-basic-multiple').select2({data: data});
		}); */
		
		function chooseTab(A){

			switch (A) {
			case "record-outgo":
				window.location.href = "${pageContext.request.contextPath}/demo/paymentDetail!inputPayment";
				break;
			
			case "queryPayment":
				window.location.href = "${pageContext.request.contextPath}/demo/customTailoredQuery!inputQuery";
				break;
				
			case "detail-all":
				window.location.href = "${pageContext.request.contextPath}/demo/listDetail!listByMonth";
				break;
				
			case "query-outgo":
				window.location.href = "${pageContext.request.contextPath}/demo/customTailoredQuery!inputPaymentQuery";
				break;
			
			case "query-income":
				window.location.href = "${pageContext.request.contextPath}/demo/customTailoredQuery!inputIncomeQuery";
				break;
				
			case "query-transfer":
				window.location.href = "${pageContext.request.contextPath}/demo/customTailoredQuery!inputTransferQuery";
			default:
				break;
			}
			
		}
		
		/* 检查输入是否是整数、浮点数，如果不是，则清空 */
		function CheckInputIntFloat(oInput) 
		{ 
			if('' != oInput.value.replace(/\d{1,}\.{0,1}\d{0,}/,'')) 
			{ 
				oInput.value = oInput.value.match(/\d{1,}\.{0,1}\d{0,}/) == null ? '' :oInput.value.match(/\d{1,}\.{0,1}\d{0,}/); 
			} 
		}

		
		function checkInput(type){
		
			switch (type) {
				case "payment":
				case "income":
					var je_gt = $("#je_gt").val();
					var je_lt = $("#je_lt").val();
					
					if (je_gt != "" && je_lt !="" && parseFloat(je_gt)>parseFloat(je_lt)){
						//alert("金额范围输入有误");
						toastr.error("<b>请输入正确的金额.</b>","提示");
						return false;
					}
					break;
	
				default:
					break;
				}
			
			// 如果是直接按查询建，无论当前分页序号是多少都查询首页
			/* switch (type) {
			case "payment":
				$('#paymentCurPageNum').val('1');
				break;
			case "income":
				$('#incomeCurPageNum').val('1');
				break;
			case "transfer":
				$('#transferCurPageNum').val('1');
				break;
			default:
				break;
			} */
			
			return true;
		}
	</script>
	<link rel="shortcut icon" href="../components/favicon.ico" type="image/x-icon" />
<title>查询</title>
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
					<%-- <a href="${pageContext.request.contextPath}/j_spring_security_logout"  class="home " bi="8013">退出</a> --%>
				</li>
				
				<li>
					<a href="frontStatistics!inputFront" class="home before" bi="8012">首页</a>
				</li>
				
				<li>
					<div class="subNavTitle rel">
						<i class="g-icon-header"></i>
						<span class="userAccount"><span>${pageContext.request.userPrincipal.name}</span></span>
					</div>
				</li>
			</ul>
		</div>
	</div>
	
	<div class="mainWidth" style="position:relative;">
		<div id="nav" class="nav" style="margin-top: 16px">
			  <div id="menu" >
				<div id="record" class="menu" firsttabid="record-outgo"  onclick="chooseTab('record-outgo');">
					<div class="itemWrap">
						<div class="img_0_1 icon-nav ddfix">&nbsp;</div>
						<span>记账</span>
					</div>
				</div>
				<div id="detail" class="menu " firsttabid="detail-all" onclick="chooseTab('detail-all');">
					<div class="itemWrap">
						<div class="img_0_3 icon-nav ddfix">&nbsp;</div>
						<span>明细</span>
					</div>
				</div>
				<div id="query" class="menu current" onclick="chooseTab('queryPayment');">
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
		
		<div>
			<div id="tabList" class="tabList">  
				<div id="tab_payment" class="tab"  onclick="chooseTab('query-outgo')"> 
					支出
				</div>  
				<div id="tab_income" class="tab"  onclick="chooseTab('query-income')">
					收入
				</div>  
				<div id="tab_transfer" class="tab tabcurrent"  onclick="chooseTab('query-transfer')">
					转账
				</div>  
			</div>
			
			<div class="tab_box">	
							
				<div id="transferbox" class="contentPage_1" style=" min-width: 600px">
					<s:form action="customTailoredQuery" onsubmit="return checkInput('transfer')">
						<table class="query_condition">
							<tr>
								<th>转出账户</th>
								<td>
									 <%-- <s:select name="zh_dm" list="#request.zh_info" listKey="zh_dm" listValue="zh_mc" theme="simple" class="selectInput" /> --%>
									 <s:select name="srcZh_dm" list="%{#{-1:'--选择账户--'}}" theme="simple" class="selectInput">
										<s:iterator value="#request.ZH_INFO_MAP" >
										  	<s:optgroup label="%{key}" list="value" listKey="zh_dm" listValue="zh_mc" />
										</s:iterator>
									 </s:select> 
								</td>
								
								<th>转入账户</th>
								<td>
									 <%-- <s:select name="zh_dm" list="#request.zh_info" listKey="zh_dm" listValue="zh_mc" theme="simple" class="selectInput" /> --%>
									 <s:select name="tgtZh_dm" list="%{#{-1:'--选择账户--'}}" theme="simple" class="selectInput">
										<s:iterator value="#request.ZH_INFO_MAP" >
										  	<s:optgroup label="%{key}" list="value" listKey="zh_dm" listValue="zh_mc" />
										</s:iterator>
									 </s:select> 
								</td>
								
								<th>发生时间起</th>
								<td>
									 <input type="date" id="date_from" class="recordInput" value="${requestScope.date_from }" name="date_from" />
								</td>
								
								<th>发生时间止</th>
								<td>
									 <input type="date" id="date_to" class="recordInput" value="${requestScope.date_to }"  name="date_to" />
								</td>
							</tr>
							
							
							<s:textfield id="transferCurPageNum" name="pageNumTransfer" class="currentPageNum" theme="simple" style="display: none;"  />
							<s:textfield id="transferTotalPages" name="totalPagesTransfer" class="totalPages" theme="simple" style="display: none;" />
							<tr>
								<th>金额大于</th>
								<td>
									 <s:textfield id="je_gt" name="je_gt" class="recordInput" theme="simple" onblur="javascript:CheckInputIntFloat(this);"/>
								</td>
								
								<th>金额小于</th>
								<td>
									 <s:textfield id="je_lt" name="je_lt" class="recordInput" theme="simple" onblur="javascript:CheckInputIntFloat(this);"/>
								</td>
								<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
								
								<th>转账类型</th>
								<td>
									<s:select id="zzlxDm" list="#request.dm_zzlx" listKey="key" listValue="value" name="zzlx_dm" headerKey="" headerValue="全部" theme="simple" class="selectInput" />	
      							</td>
      							
								<th></th>
								<td>
									<s:submit key="查询"  class="queryBtn"  method="transferQuery" theme="simple" />
								</td>
							</tr>
	
						</table>
					</s:form>
					
					
					<div style="min-height: 420px; margin-left: 170px; ">
						<table id="queryResTable" class="record_biz" >
							<thead>
								<tr >
									<th style="text-align: center;">序号</th>
									<th style="text-align: center;">时间</th>
									<th style="text-align: center;">转出账号</th>
									<th style="text-align: center;">转入账号</th>
									<th style="text-align: center;">金额</th>
									<th style="text-align: center;">备注</th>
								</tr>
							</thead>
							
							<tbody>
								<s:iterator value="#request.transferResult" var="stat" status="st">
									<tr>
										<td> <s:property value="#st.count+pageSize*pageNumTransfer-pageSize"/> </td>
										<td>${fsrqToShowFull }</td>
										<td>${srcZhmc }</td>
										<td>${tgtZhmc }</td>
										<td>${je }</td>
										<td>${bz }</td> 
									</tr>
								</s:iterator>
							</tbody>
						</table>
					</div>
					
					<div class="pageInfo">
						<a class="firstPage" href="#" target="_self"><span>首页</span></a> 
						<a id="prePageTransfer" class="prePage" href="#"><span>&lt;&lt;上一页</span></a> 
						第<s:property value="pageNumTransfer==null?1:pageNumTransfer"/>页（共<s:property value="totalPagesTransfer==null?' ':totalPagesTransfer"/>页）
						<a id="nextPageTransfer" class="nextPage" href="#"><span>下一页&gt;&gt;</span></a>
						<a class="lastPage" href="#" target="_self"><span>尾页</span></a>
					</div>
					
				</div>
				
			</div>
		
		
	</div>
	
	</div>
	
	<s:debug></s:debug>
	
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