<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.util.Date"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="springsec" uri="http://www.springframework.org/security/tags" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta name="_csrf" content="${_csrf.token}"/>
<!-- default header name is X-CSRF-TOKEN -->
<meta name="_csrf_header" content="${_csrf.headerName}"/>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<link rel="stylesheet" type="text/css" href="../css/header.css" />
	<link rel="stylesheet" type="text/css" href="../css/header-account.css" />
	<link rel="stylesheet" type="text/css" href="../css/footer.css" />
	<link rel="stylesheet" type="text/css" href="../css/left-nav.css" />
	<link rel="stylesheet" type="text/css" href="../js/toastr/toastr.css" />
	<link rel="stylesheet" type="text/css" href="../js/icheck/skins/flat/green.css" />	
	
	<style type="text/css"> 
		.record_biz{font-size:12px;font-weight:normal;width:99%;margin:0 auto;margin-top:15px;}
		.record_biz th{color:#646464;padding-right:6px;text-align:right;font-weight:normal;width:94px;}
		.record_biz tr{height:34px;}
		.record_biz .recordInput{width:108px;padding-left:5px;height:24px;line-height:24px;border:1px solid #CCC;padding-right:20px;font-size:12px;}
		.selectInput{width:135px;padding-left:5px;height:28px;line-height:24px;border:1px solid #CCC;padding-right:20px;font-size:12px;}
		.record_biz .editable-select{width:108px!important;padding-left:5px;height:24px;line-height:24px;border:1px solid #CCC;padding-right:20px;}
		.submitButton{margin:0 auto;margin-top:5px;padding-bottom:30px;margin-left:100px;}
		.btn1,.btn2,.btn3,.btn4,.btn5,.cancelBtn{cursor:pointer;height:30px;line-height:30px;_height:28px;_line-height:28px;text-align:center;}
		.btn1,.btn4{background:#15A041;color:#FFF;width:110px;border:1px #15A041 solid;font-weight:bold;}.btn2{background:#FEF5C7;color:#7C491F;border:1px #D0BC99 solid;width:100px;}
		.btn2{margin-left:10px;}
		.btn2{background:#FEF5C7;color:#7C491F;border:1px #D0BC99 solid;width:100px;}
		.tabList{height:40px;background-color:#F0F2F5;border:1px #BBB solid;border-bottom:0;font-size:13px;}
		.current{background:#FFF;font-weight:bold;height:41px;line-height:41px;margin-bottom:-1px;}
		#tabList .current{background:#FFF;font-weight:bold;height:41px;line-height:41px;margin-bottom:-1px;}
		.rptTabList .current{border-top:1px #C0C2C4 solid;border-bottom:2px #D8D9DC solid;width:111px;}
		.tab{position:relative;float:left;display:inline-block;text-align:center;color:#000;width:110px;height:39px;line-height:39px;cursor:pointer;border-right:1px #E0E0E0 solid;border-bottom:0;}
		.rptTabList .tab{border:0;text-align:left;text-indent:30px;}
		div.hasCalculator,span.hasCalculator{position:relative;}
		button.calculator-trigger{width:25px;padding:0;}
		img.calculator-trigger{margin:2px;vertical-align:middle;margin-left:-26px;margin-top:-2px;_margin-top:-6px;cursor:pointer;}
		#bizContent{border:1px #E0E0E0 solid;border-left:1px #BBB solid;border-right:1px #BBB solid;background:#FFF;}
		.contentPage_1{
			position:relative;
			padding-top: 15px;
			max-width: 960px;
			margin: auto; /* div 居中 */
   			/* padding-right: 10px;
    		padding-bottom: 0px;
    		padding-left: 10px; */
		}
		
		/* .g-icon-bkk {
		    width: 40px;
		    height: 40px;
		    display: block;
		    float: left;
		    margin: 0 12px 0 20px;
		    background: url(//wacai-file.b0.upaiyun.com/common/c/header/img/g_app_icon_20be266.png?v=20170309) no-repeat;
		}
		
		.tl {
		    text-align: left;
		} */

		/* .site-app-list ul li ul { position:absolute; display:none;}
		.site-app-list ul li ul li { float:none;}
		.site-app-list ul li ul li a { border-right:none; border-top:1px dotted #ccc; background:#f5f5f5;}
		.site-app-list ul li:hover ul{ display:block; } */

		.doubleselectInput br{display: none} 
		
		/* 计算器 */		
		.calculator-popup {
		    display: none;
		    z-index: 10000;
		    margin: 0;
		    padding: 4px;
		    border: 1px solid #B6C4D8;
		    color: #AF5200;
		    background-color: #E8EEF7;
		    font-family: Arial,Helvetica,sans-serif;
		    width: 196px!important;
		}
		
		.calculator-result {
		    margin-left: -1px;
		    width: 189px;
		    clear: both;
		    padding-right: 6px;
		    text-align: right;
		    background-color: #FFF;
		    font-size: 20px;
		    border: 1px solid #CED8E8;
		    font-weight: bold;
		    color: #000;
		    height: 36px;
		    line-height: 36px;
		}
		
		.calculator-row {
		    clear: both;
		    width: 100%;
		}
		
		.calculator-row button {
		    position: relative;
		    float: left;
		    padding: 0;
		    border: 1px solid #CED8E8;
		    text-align: center;
		    cursor: pointer;
		    margin-left: -1px;
		    margin-top: -1px;
		}
		.calculator-oper, .calculator-digit, .calculator-ctrl {
		    height: 40px;
		    width: 50px;
		    font-size: 20px;
		    font-weight: bold;
		    color: #536D91;
		}
		.calculator-oper, .calculator-hex-digit {
		    background: #E8EEF7;
		}
		
		.calculator-undo, .calculator-clear {
		    width: 50px;
		}
		
		.calculator-keystroke{display:none;width:16px;position:absolute;left:-8px;top:-8px;color:#000;background-color:#fff;border:1px solid #888;font-size:80%;}
		
		.calculator-use {
		    background-color: #E8EEF7;
		    color: #536D91;
		    font-size: 16px;
		    width: 99px;
		    line-height: 35px;
		}
		.fund-list {margin-left: -35px; padding-bottom: 20px}
		.fund-list li{float:left; margin-right:10px}
		.fin-prod-list {margin-left: -35px; padding-bottom: 20px}
		.fin-prod-list li{float:left; margin-right:10px}
	</style>

	

	<script type="text/javascript" src="../js/jquery.js"></script>
	<script type="text/javascript" src="../js/calculator.js"></script>
	<script type="text/javascript" src="../js/toastr/toastr.js"></script>
	<script type="text/javascript" src="../js/icheck/icheck.js"></script>
	
	
	<script type="text/javascript">	
	function choiceTab(B)
	{
		
		var A=$("#"+B);
		
		/* removeErrTips(); */
		/* ajaxQueue.abort(); */
		$("#bizContent").html("");
		loading.show();
		gotourl(A);
		chooseTab(A);
		wac.cache.load("account");
	}
	
	function chooseTab(A){
		var AA = $("#"+A);
		switch (A) {
		case "record-outgo":
			window.location.href = "${pageContext.request.contextPath}/demo/paymentDetail!inputPayment";
			break;
		
		case "record-income":
			window.location.href = "${pageContext.request.contextPath}/demo/incomeDetail!inputIncome";
			break;
		case "record-transfer":
			window.location.href = "${pageContext.request.contextPath}/demo/transferDetail!inputTransfer";
			break;
		case "query-payment":
			//window.location.href = "${pageContext.request.contextPath}/demo/customTailoredQuery!inputQuery";
			window.location.href = "${pageContext.request.contextPath}/demo/customTailoredQuery!inputPaymentQuery";
			break;
			
			
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
			
		default:
			break;
		}
		
	}
	
	$(function() {
		/* 获取当前时间 */
	    var now = new Date();
	    var str = now.getFullYear() + "-" + fix((now.getMonth() + 1),2) + "-" + fix(now.getDate(),2) + "T" + fix(now.getHours(),2) + ":" + fix(now.getMinutes(),2);
	    $("#fsrq2").val(str);
	    
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
	    
	   $("input[name='fundDetail.fundType']").iCheck({
		    checkboxClass: 'icheckbox_flat-green',
		    radioClass: 'iradio_flat-green'
		  });
	   
	   $("input[name='financialProductDetail.productType']").iCheck({
		    checkboxClass: 'icheckbox_flat-green',
		    radioClass: 'iradio_flat-green'
		  });
	   
	   $("input[name='financialProductDetail.netvalused']").iCheck({
		    checkboxClass: 'icheckbox_flat-green',
		    increaseArea: '20%' // optional
		  });
	   
	    $('#input_je').blur(function(){
	    	
	    	var je = $(this).val();
	    	if (isNaN(parseFloat(je)) || isNaN(je)){
	    		toastr.error("<b>请输入正确的金额.</b>","提示");
	    	}
	    });
	    
	    $("#dateCount").blur(function() {
	    	var dateCount = $(this).val();
	    	if (isNaN(parseInt(dateCount)) || isNaN(dateCount)){
	    		toastr.error("<b>请输入正确的天数.</b>","提示");
	    	}
		});
	    
	    $('#logout-url').click(function (){	
			 $("#logout-form").submit();
		});
	    
	    // editShow打开时判断是否是理财或者基金
	    if ($('#zzlxDm').find('option:selected').val() == '0003' || $('#zzlxDm').val() == '0003'){
	    	$('#fund-extra-info').show();
	    }else{
	    	$('#fund-extra-info').hide();
	    }
	    if ($('#zzlxDm').find('option:selected').val() == '0002' || $('#zzlxDm').val() == '0002' ){
	    	$('#financial-extra-info').show();
	    }else{
	    	$('#financial-extra-info').hide();
	    }
	    if ($('#zzlxDm').find('option:selected').val() == '0009' || $('#zzlxDm').val() == '0009' || $('#zzlxDm').val() == '0011'){
	    	$('#fund-redeem').show();
	    }else{
	    	$('#fund-redeem').hide();
	    }
	    
	    // 转账类型选择后页面自动变化
	    $('#zzlxDm').change(function(){
	    	var selectVal = $(this).find('option:selected').val();
	    	console.log('select_val: ' + selectVal);
	    	
	    	if ('0003' == selectVal){
	    		$('#fund-extra-info').show();
	    	}else{
	    		$('#fund-extra-info').hide();
	    	}
	    	
	    	if ('0002' == selectVal){
	    		$('#financial-extra-info').show();
	    	}else{
	    		$('#financial-extra-info').hide();
	    	}
	    	
	    	if ('0009' == selectVal || '0011' == selectVal){
	    		$('#fund-redeem').show();
	    	}else{
	    		$('#fund-redeem').hide();
	    	}
	    });
	    
	    var finprodType = $("input[name='financialProductDetail.productType'][checked]").val();
	
	    if (finprodType == 'FP_A4'){
        	$('#dateEnd').attr("disabled","disabled");
        }else{
        	$('#dateEnd').removeAttr("disabled");
        }
	    
	    $("input:radio[name='financialProductDetail.productType']").on('ifChecked', function(event){
	        if (this.value == 'FP_A4'){
	        	$('#dateEnd').attr("disabled","disabled");
	        }else{
	        	$('#dateEnd').removeAttr("disabled");
	        }
	 	});
    	
	    // 加载时
	    if ($("input:checkbox[name='financialProductDetail.netvalused']").prop('checked')){
	    	$('#netv_purchase_tr').show();
    		$('#netv_selling_tr').show();
    		$(this).attr("value","1"); // 向后台传入
	    }else{
	    	$('#netv_purchase_tr').hide();
			$('#netv_selling_tr').hide();
			$(this).attr("value","0");  // 向后台传入
	    }
	    // “是否净值型”checked
	    $("input:checkbox[name='financialProductDetail.netvalused']").on('ifChecked', function(event){ 
    		$('#netv_purchase_tr').show(500);
    		$('#netv_selling_tr').show(500);
    		$(this).attr("value","1"); // 向后台传入
    		//console.log($(this).val());
	    });

		$("input:checkbox[name='financialProductDetail.netvalused']").on('ifUnchecked', function(event){ 
			$('#netv_purchase_tr').hide(500);
			$('#netv_selling_tr').hide(500);
			$(this).attr("value","0"); // 向后台传入，如果非净值型，但是买入净值非空，在后台被设置为0
			//console.log($(this).val());
	    });
	    
	    // 使用icheck控件以后，以下失效？要用$("input:radio[name='financialProductDetail.productType']").on('ifChecked', function(event){
	    /* $("input[type=radio][name='financialProductDetail.productType']").change(function(){
	    	console.log(this.value);
	    	if (this.value == 'FP_A3'){
	    		$('#netv_purchase').removeAttr("disabled");
	    		$('#netv_selling').removeAttr("disabled");
	    	}else{
	    		$('#netv_purchase').attr("disabled","disabled");
	    		$('#netv_selling').attr("disabled","disabled");
	    	}
	    }); */
	    
	    
	    // 理财预期收益随动变化
	    $("input[name='je']").change(function() {
	    	var je = $(this).val();
	    	var dateCount = $("#dateCount").val();
	    	var returnRate = $("#expectedReturnRate").val();
	    	if (isNaN(je) || isNaN(dateCount) || isNaN(returnRate)){
	    		return ;
	    	}
	    	var expectedReturn = je*1.0*dateCount/365*returnRate;
			$("#expectedReturn").val( parseFloat(expectedReturn.toFixed(2)) );
		});
	    
	    $("#dateCount").change(function() {
	    	var je = $("input[name='je']").val();
	    	var dateCount = $(this).val();
	    	var returnRate = $("#expectedReturnRate").val();
	    	if (isNaN(je) || isNaN(dateCount) || isNaN(returnRate)){
	    		return ;
	    	}
	    	var expectedReturn = je*1.0*dateCount/365*returnRate;
			$("#expectedReturn").val( parseFloat(expectedReturn.toFixed(2)) );
		});
	    
	    $("#expectedReturnRate").change(function() {
	    	var je = $("input[name='je']").val();
	    	var dateCount = $("#dateCount").val();
	    	var returnRate = $(this).val();
	    	if (isNaN(je) || isNaN(dateCount) || isNaN(returnRate)){
	    		return ;
	    	}
	    	var expectedReturn = je*1.0*dateCount/365*returnRate;
			$("#expectedReturn").val( parseFloat(expectedReturn.toFixed(2)) );
		});
	    
	    
	    $(":input[name='fundDetail.fundCode']").blur(function(){
	    	
	    	var existsName = $("#fundName").val(); // if fundname already exists,do nothing
	    	if (existsName != ""){
        		return;
        	}		
	    	
			var fundCode = $(this).val();
			console.log(fundCode);
			fundCode = $.trim(fundCode);
			
			if (fundCode != "" && fundCode.length >= 6){
				
				var token = $("meta[name='_csrf']").attr("content");
				var header = $("meta[name='_csrf_header']").attr("content");
				var headers = {};
				headers['__RequestVerificationToken'] = token;
				var url ="${pageContext.request.contextPath }/demo/fundInfo.action";
				var args={"fundCode":fundCode};
				console.log(url);
				
				 $.ajax({
				        type: "GET", /*不能用POST?*/
				        headers: headers,
				        cache: false,
				        url: url,
				        data: args,
				        dataType:"text",
				        async: true,
				        error: function(data, error) {},
				        success: function(data)
				        {
				        	console.log("rv:"+data);
				        	if (data == "error"){
				        		toastr.info("获取基金信息失败");
				        	}else{
				        		toastr.info("获取基金信息成功");
					        	var existsName = $("#fundName").val();
					        	console.log("exists: " + existsName);
					        	$(":input[name='fundDetail.fundName']").val(data);	
				        	}
				        }
				    });
			}else
				{
					console.log("error");
				}
		});	
	})
	
	
		
	

	function fix(num, length) {
	  return ('' + num).length < length ? ((new Array(length + 1)).join('0') + num).slice(-length) : '' + num;
	}
	/* 获取当前时间 END*/
	
	
	function checkForTransferRoles(){
		
    	if ($("#srcZh").val() == $("#tgtZh").val() ){
    		toastr.info("<b>转账的两账户必须不一样.</b>","提示");
    		return false;
    	}
    	
    	
    	return true;
    }
	
	function selChange(obj){
		 
		var p1=$(obj).children('option:selected').val();//这就是selected的值 
		if (p1 == 'z999'){
			alert($(obj).children('option:selected').val());
		} 
	}
	
	function checkBeforeSave(){
		
		if ($("select[name='srcZh_dm']").val() == "-1"){
			toastr.info("<b>请选择转出账户.</b>","提示");
			return false;
		}
		
		if ($("select[name='tgtZh_dm']").val() == "-1"){
			toastr.info("<b>请选择转入账户.</b>","提示");
			return false;
		}
		
    	var je = $('#input_je').val();
    	if (isNaN(parseFloat(je)) || isNaN(je)){
    		return false;
    	}
    	if (parseFloat(je)<0){
    		toastr.info("<b>转账金额不能小于零.</b>","提示");
    		return false;
    	}
    	
    	
    	if ($("input:checkbox[name='financialProductDetail.netvalused']").prop('checked')){
    		// 选择了净值型，买入净值必填且必须大于0
    		if ($("#netv_purchase").val() <= 0 || $("#netv_purchase").val() == ""){
    			toastr.info("<b>'买入净值'必须大于0.</b>","提示");
        		return false;
    		}
    	}else{
    		// 非净值型，买入和卖出净值都为0
    		$("#netv_purchase").val("0.0");
    		$("#netv_purchase").val("0.0");
    	}

		
		return checkForTransferRoles(); // 再检查转账账户是否设置正确
	}
	
	</script>
<link rel="shortcut icon" href="../components/favicon.ico" type="image/x-icon" />
<title>转账</title>
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
					<%-- <a href="${pageContext.request.contextPath}/j_spring_security_logout" class="home "  style=" text-decoration: none;">退出</a> --%>
				</li>
				
				<li>
					<a href="frontStatistics!inputFront"  class="home before" style=" text-decoration: none;">首页</a>
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
	
	<div class="contentPage_1" style="min-height: 650px;">
	
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
					<span>记账</span>
				</div>
			</div>
			<div id="detail" class="menu" firsttabid="detail-all" href="/biz/detailPage_main.action" onclick="chooseTab('detail-all');">
				<div class="itemWrap">
					<div class="img_0_3 icon-nav ddfix">&nbsp;</div>
					<span>明细</span>
				</div>
			</div>
			<div id="set" class="menu" onclick="chooseTab('query-payment')">
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
	
	<div id="content" class="content" style="min-height: 650px; height: auto;margin: 0px 5px 0 180px;" >
	
	<div id="tabList" class="tabList">  
		<div id="record-outgo" class="tab"  onclick="chooseTab('record-outgo')"> 
			<a href="${pageContext.request.contextPath}/demo/paymentDetail!inputPayment" style="text-decoration:none">支出</a> 
		</div>  
		<div id="record-income" class="tab"  onclick="chooseTab('record-income')">
			<a href="${pageContext.request.contextPath}/demo/incomeDetail!inputIncome" style="text-decoration:none">收入</a>
		</div>  
		<div id="record-transfer" class="tab current" href="/biz/recordPage_transfer.action" onclick="chooseTab('record-transfer')">
			<a href="${pageContext.request.contextPath}/demo/transferDetail!inputTransfer" style="text-decoration:none">转账</a>
		</div>  
		<div id="record-loan" class="tab" href="/biz/recordPage_loan_payback.action" onclick="choiceTab('record-loan')">借贷</div> 
	</div>
	
	<div id="bizContent" class="bizContent"  style="min-height: 610px;">
	
	<s:form action="transferDetail" method="post" onsubmit="return checkBeforeSave()" style="visibility: visible;">
		<table class="record_biz">
			<tbody>
				
				
				<s:textfield name="mxuuid" theme="simple" style="display:none" />
				<s:textfield name="accuuid" theme="simple" style="display:none" />
				<s:textfield name="date_from" theme="simple" style="display:none"/>
				<s:textfield name="date_to" theme="simple" style="display:none"/>
				
				<tr>
					<th>账户</th>
					<td>
						<%-- <s:select id="srcZh" list="#request.ZH_INFO" listKey="zh_dm" listValue="zh_mc" name="srcZh_dm" theme="simple" class="selectInput" onchange="selChange(this)" /> --%>
						<s:select id="srcZh" name="srcZh_dm" list="%{#{-1:'--选择账户--'}}" theme="simple" class="selectInput">
							<s:iterator value="#request.ZH_INFO_MAP" >
							  	<s:optgroup label="%{key}" list="value" listKey="zh_dm" listValue="zh_mc" />
							</s:iterator>
						</s:select> 
						<strong> > </strong>
						<%-- <s:select id="tgtZh" list="#request.ZH_INFO" listKey="zh_dm" listValue="zh_mc" name="tgtZh_dm" theme="simple" class="selectInput" /> --%>
						<s:select id="tgtZh" name="tgtZh_dm" list="%{#{-1:'--选择账户--'}}" theme="simple" class="selectInput">							
							<s:iterator value="#request.ZH_INFO_MAP" >
							  	<s:optgroup label="%{key}" list="value" listKey="zh_dm" listValue="zh_mc" />
							</s:iterator>
						</s:select> 
					</td>	
					
				</tr>
				
				<tr>
					<th>金额</th>
					<td>
						<!-- <input type="text" name="je" id="input_je" maxlength="16" class="recordInput calc keepMoney {required:true,money:1,messages:{required:'收入金额必填'}} hasCalculator" required="required" /> -->
						<!-- 使用name就能关联到值栈中的对象的属性了 -->
						<s:textfield name="je" id="input_je"  maxlength="16" class="recordInput calc keepMoney {required:true,money:1,messages:{required:'收入金额必填'}} hasCalculator" required="required"  theme="simple"/>
						<img id="calc_img" src="../components/calculator/calculator.png" alt="计算器" title="计算器" class="calculator-trigger" onclick="calcDisplay()">
					</td>	
				</tr>
				
				<tr>
					<th>类型</th>
					<td>
						<s:if test="#request.DETAIL_MODE == 'EDIT'"> 
							<!-- 编辑模式下zzlx的input为只读模式，由于select设置为只读比较麻烦，所以使用textfield代替 -->
							<!-- 只用于显示 -->
							<!-- 20180707修改：%{#request.dm_zzlx_user[zzlx_dm]} %{#request.dm_zzlx_com[zzlx_dm]},代码表来自两部分，公共部分和用户部分 -->
							<input type="text" id="zzlxDm_show" name="zzlx_dm_show" value="${dm_zzlx_user[zzlx_dm] }${dm_zzlx_com[zzlx_dm] }" disabled="true"  class="recordInput">
							<%-- <s:textfield id="zzlxDm_show" name="zzlx_dm_show" value="%{#request.dm_zzlx_user[zzlx_dm]} %{#request.dm_zzlx_com[zzlx_dm]}" disabled="true"  class="recordInput" theme="simple"/> --%>
							<!-- 接受editshow的数据，并用于提交时传递数据，不可见 -->
							<s:textfield id="zzlxDm" name="zzlx_dm" readonly="true" style="display: none;" theme="simple" />
						</s:if>
						<s:else>
							<%-- <s:select id="zzlxDm" list="#request.dm_zzlx" listKey="key" listValue="value" name="zzlx_dm" theme="simple" class="selectInput" /> --%>
							<!-- 20180707修改：代码表来自两部分，公共部分和用户部分 -->
							<select id="zzlxDm" name="zzlx_dm" class="selectInput">
								<s:iterator value="#request.dm_zzlx_com" >
									<option value="${key }">${value }</option>
								</s:iterator>
								<s:iterator value="#request.dm_zzlx_user" >
									<option value="${key }">${value }</option>
								</s:iterator>
							</select>
						</s:else>
					</td>
				</tr>
				
				
				
				<tr style="height:10px;font-size:0px;">
					<td colspan="3" style="border-bottom: 1px solid #DDE2E8;">&nbsp;</td>
				</tr>
				
				<tr style="height:10px;font-size:0px;">
					<td></td>
				</tr>
			
	  			
	  			<tr>
					<th>时间</th>
					<td>
					<!-- recordInput {required:true,messages:{required:'收入日期必填!'}} dateInputBg-->
					<%-- <input type="datetime-local" value="${request.NOW_DATE }" id="fsrq" name="shijian" class=" " style="background-position: 115px 12px;"> --%>
					<s:if test="#request.DETAIL_MODE == 'EDIT'">
						 <input type="datetime-local"  id="fsrq" name="fsrq" value="<s:property value='fsrqToShow'/>" style="height:24px;line-height:24px;border:1px solid #CCC; background-position: 115px 12px;" required="required"> 
					</s:if>
					<s:else>
					 	<input type="datetime-local"  id="fsrq2" name="fsrq"  style="height:24px;line-height:24px;border:1px solid #CCC; background-position: 115px 12px;" required="required">
					</s:else>	 
					</td>
				</tr>
	  			
	  			<tr style="height:10px;font-size:0px;">
					<td colspan="3" style="border-bottom: 1px solid #DDE2E8;">&nbsp;</td>
				</tr>
				<tr style="height:10px;font-size:0px;">
					<td></td>
				</tr>
				
				
				<tbody id="fund-extra-info">
					<tr id="fund-code">
						<th>基金代码</th>
						<td>
							<s:textfield name="fundDetail.fundCode" id="fundCode"  maxlength="16" class="recordInput" theme="simple"/>
						</td>
					</tr>
					<tr id="fund-name">
						<th>基金名称</th>
						<td>
							<s:textfield name="fundDetail.fundName" id="fundName"  maxlength="16" class="recordInput" theme="simple"/>
						</td>
					</tr>
					<tr id="unit-net">
						<th>单位净值</th>
						<td>
							<s:textfield name="fundDetail.unitNet" id="unitNet"  maxlength="16" class="recordInput" theme="simple"/>
						</td>
					</tr>
					<tr id="extra-fee">
						<th>手续费</th>
						<td>
							<s:textfield name="fundDetail.extraFee" id="extraFee"  maxlength="16" class="recordInput" theme="simple"/>
						</td>
					</tr>
					<tr>
						<th>基金类型</th>
						<td>
						 	<ul class="fund-list">
						 		<s:iterator value="#request.fund_type">
						 			<li>
						 				<input type="radio" name="fundDetail.fundType" value="${key }" <s:if test="fundDetail.fundType == key ">checked="checked"</s:if> />
					                  	<label for="flat-radio-1">${value }</label>
				                  	</li>
						 		</s:iterator>
			              	</ul>
		              	</td>
		             </tr>
	             	<tr id="fund-divider" style="height:10px;font-size:0px;">
						<td colspan="3" style="border-bottom: 1px solid #DDE2E8;">&nbsp;</td>
					</tr>
					<tr style="height:10px;font-size:0px;">
						<td></td>
					</tr>
				</tbody>
				
				<tbody id="financial-extra-info">
					<tr>
						<th>理财产品名称</th>
						<td>
							<s:textfield name="financialProductDetail.productName" id="productName"  maxlength="26" class="recordInput" theme="simple"/>
						</td>
					</tr>
					<tr>
						<th>产品周期</th>
						<td>
						 	<ul class="fin-prod-list">
						 		<s:iterator value="#request.fin_prod_type">
						 			<li>
						 				<input type="radio" name="financialProductDetail.productType" value="${key }" <s:if test="financialProductDetail.productType == key ">checked="checked"</s:if> />
					                  	<label for="flat-radio-1">${value }</label>
				                  	</li>
						 		</s:iterator>
			              	</ul>
		              	</td>
		             </tr>
		             <tr>
		             	<th>是否净值型</th>
		             	<td>
		             		<ul style="margin-left: -35px;" >
		             		    <!-- <s:if test="financialProductDetail.netvalused > 0 ">checked="checked"</s:if> 用于edit模式的回显 -->
			             		<input type="checkbox" name="financialProductDetail.netvalused" <s:if test="financialProductDetail.netvalused > 0 ">checked="checked"</s:if> value="" />
		             		</ul>
		             	</td>
		             </tr>
		             <tr id="netv_purchase_tr">
		             	<th>买入净值</th>
		             	<td>
		             		<s:textfield name="financialProductDetail.netvPurchase" id="netv_purchase"  maxlength="16" class="recordInput" theme="simple"/>
		             	</td>
		             </tr>
		             <tr id="netv_selling_tr">
		             	<th>卖出净值</th>
		             	<td>
		             		<s:textfield name="financialProductDetail.netvSelling" id="netv_selling"  maxlength="16" class="recordInput" theme="simple"/>
		             	</td>
		             </tr>
					<tr>
						<th>银行</th>
						<td>
							<s:select id="yh_dm" list="#request.yh_dm" listKey="key" listValue="value"  name="financialProductDetail.yh_dm" theme="simple" class="selectInput" />
						</td>
					</tr>
					<tr>
						<th>投资天数</th>
						<td>
							<s:textfield name="financialProductDetail.dateCount" id="dateCount"  maxlength="16" class="recordInput" theme="simple"/>
						</td>
					</tr>
					<tr>
						<th>投资期限</th>
						<td>
							<s:if test="#request.DETAIL_MODE=='EDIT'">
								<input type="date"  id="dateStart" name="financialProductDetail.startDate" value="<s:property value='financialProductDetail.startDateToShow'/>" style="height:24px;line-height:24px;border:1px solid #CCC;background-position: 115px 12px;" > 至
								<input type="date"  id="dateEnd" name="financialProductDetail.endDate" value="<s:property value='financialProductDetail.endDateToShow'/>" style="height:24px;line-height:24px;border:1px solid #CCC;background-position: 115px 12px;" >
							</s:if>
							<s:else>
								<input type="date"  id="dateStart" name="financialProductDetail.startDate"  style="height:24px;line-height:24px;border:1px solid #CCC;background-position: 115px 12px;" > 至
								<input type="date"  id="dateEnd" name="financialProductDetail.endDate"  style="height:24px;line-height:24px;border:1px solid #CCC;background-position: 115px 12px;" >
							</s:else>
						</td>
					</tr>
					<tr>
						<th>期望收益率</th>
						<td>
							<s:textfield name="financialProductDetail.expectedReturnRate" id="expectedReturnRate"  maxlength="16" class="recordInput" theme="simple"/>
						</td>
					</tr>
					<tr>
						<th>期望收益</th>
						<td>
							<s:textfield name="financialProductDetail.expectedReturn" id="expectedReturn"  maxlength="16" class="recordInput" theme="simple" disabled="true" />
						</td>
					</tr>
					<%-- <tr>
						<th>实际收益金额</th>
						<td>
							<s:textfield name="financialProductDetail.realReturn" id="realReturn"  maxlength="16" class="recordInput" theme="simple"/>
						</td>
					</tr> --%>
					<tr id="fund-divider" style="height:10px;font-size:0px;">
						<td colspan="3" style="border-bottom: 1px solid #DDE2E8;">&nbsp;</td>
					</tr>
					<tr style="height:10px;font-size:0px;">
						<td></td>
					</tr>
				</tbody>
				
				<tbody id="fund-redeem">
					<s:if test="#request.DETAIL_MODE != 'EDIT'">
							<tr>
								<th>理财产品</th>
								<td>
									<s:if test="#request.holding_product.size()>0">
										<s:select id="product-unredeemed" list="#request.holding_product" listKey="key" listValue="value" headerKey="-1" headerValue="请选择" name="productUnredeemed" theme="simple" class="selectInput" style="width:280px" />
									</s:if>
									<s:else>
										<s:select id="product-unredeemed" list="#{'-1':'无理财产品' }"  name="productUnredeemed" theme="simple" class="selectInput" disabled="true" />
									</s:else>
								</td>
							</tr>
							<%-- <tr>
								<th>实际收益</th>
								<td>
									<s:textfield name="financialProductDetail.realReturn" id="realReturn"  maxlength="16" class="recordInput" theme="simple"/>
								</td>
							</tr> --%>
					</s:if>
					<s:else>
						<tr>
							<th>理财产品</th>
							<td>
								<s:textfield name="product-redeemed-show" value="%{financialProductDetail.productName} %{financialProductDetail.dateCount}天 " theme="simple" class="recordInput" readonly="true"/> 
							</td>
						</tr>
					</s:else>
				</tbody>
				
	  			<tr>
					<th style="vertical-align:top">备注</th>
					<!-- 使用name就能关联到值栈中的对象的属性了 -->
					<td><s:textarea name="bz" cols="35" rows="5" id="bz" class="{maxlength:200,messages:{字数不能多于200;}}" theme="simple"/></td>
				</tr>
				<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
			</tbody>
		</table>
		
		<div class="submitButton" style="margin-top:16px;">
			<s:if test="#request.DETAIL_MODE=='EDIT'">
				<s:submit class="btn1 save" id="save1" value="保存" method="updateTransfer" theme="simple"/>
			</s:if>
			<s:else>
				<s:submit class="btn1 save" id="save1" value="保存" method="saveTransfer" theme="simple"/>
				<!-- <input type="button" class="btn1 save" id="save1" value="保存" method="saveTransfer" > -->
	    		<!-- <input type="button" class="btn2 save" id="save2" style="margin-left:12px;" value="保存后再记" > -->
	    		<s:submit class="btn2 save" id="save2" style="margin-left:12px;" value="保存后再记" method="saveTransferAndRec" theme="simple" />
    		</s:else>
		</div>
		<s:token></s:token>   <!--服务器通过token标签 来产生盾牌随机数-->
	</s:form>
	</div>
	</div>
	</div>
	<%-- <s:debug></s:debug> --%>
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
	
	
	<div id="calc_popup" class="calculator-popup" style="position: absolute; top: 125px; width: 208px; left: 750px; display: none;"  >
		<img style="position: absolute; left: -7px; top: 6px;" src="../components/calculator/calc.png">
		<div id="calc_res" class="calculator-result">
			<span id="calc_res">0</span>
		</div>
		
		<div class="calculator-row">
			<button id="btn_divide" type="button" keystroke="_÷" class="calculator-oper calculator-arith calculator-divide" style="border-left: 0px;" onclick="calcAlgrithmDown(this)">÷</button>
			<button type="button" keystroke="_×" class="calculator-oper calculator-arith calculator-multiply" onclick="calcAlgrithmDown(this)">×</button>
			<button type="button" keystroke="_-" class="calculator-oper calculator-arith calculator-subtract" onclick="calcAlgrithmDown(this)">-</button>
			<button type="button" keystroke="_+" class="calculator-oper calculator-arith calculator-add" style="border-right: 0px;" onclick="calcAlgrithmDown(this)">+</button>
		</div>
		
		<div class="calculator-row">
			<button type="button" keystroke="_1" class="calculator-digit" style="border-left: 0px; background: rgb(232, 238, 247);" onclick="calcAlgrithmDown(this)">1</button>
			<button type="button" keystroke="_2" class="calculator-digit" style="background: rgb(232, 238, 247);" onclick="calcAlgrithmDown(this)">2</button>
			<button type="button" keystroke="_3" class="calculator-digit" style="background: rgb(232, 238, 247);" onclick="calcAlgrithmDown(this)">3</button>
			<button type="button" keystroke="BS" class="calculator-ctrl calculator-undo" title="清除数值最后一位" style="border-right: 0px; background: url(&quot;/components/calculator/CA.png&quot;) center center no-repeat rgb(232, 238, 247);" 
					onclick="calcAlgrithmDown(this)">←</button>
		</div>
		<div class="calculator-row">
			<button type="button" keystroke="_4" class="calculator-digit" style="border-left: 0px; background: rgb(232, 238, 247);" onclick="calcAlgrithmDown(this)">4</button>
			<button type="button" keystroke="_5" class="calculator-digit" style="background: rgb(232, 238, 247);" onclick="calcAlgrithmDown(this)">5</button>
			<button type="button" keystroke="_6" class="calculator-digit" style="background: rgb(232, 238, 247);" onclick="calcAlgrithmDown(this)">6</button>
			<button type="button" keystroke="CA" class="calculator-ctrl calculator-clear" title="清零" style="border-right: 0px; background: rgb(232, 238, 247);" onclick="calcClear()" >C<span class="calculator-keystroke calculator-keyname">End</span>
			</button>
		</div>
		<div class="calculator-row">
			<button type="button" keystroke="_7" class="calculator-digit" style="border-left: 0px; background: rgb(232, 238, 247);" onclick="calcAlgrithmDown(this)">7</button>
			<button type="button" keystroke="_8" class="calculator-digit" style="background: rgb(232, 238, 247);" onclick="calcAlgrithmDown(this)">8</button>
			<button type="button" keystroke="_9" class="calculator-digit" style="background: rgb(232, 238, 247);" onclick="calcAlgrithmDown(this)">9</button>
			<button type="button" keystroke="_=" class="calculator-oper calculator-arith calculator-equals" style="border-right: 0px; background: rgb(232, 238, 247);" onclick="calcAlgrithmDown(this)">=</button>
		</div>
		<div class="calculator-row">
			<button type="button" keystroke="_0" class="calculator-digit" style="border-left: 0px; border-bottom: 0px; background: rgb(232, 238, 247);" onclick="calcAlgrithmDown(this)">0</button>
			<button type="button" keystroke="_." class="calculator-digit calculator-decimal" style="border-bottom: 0px; background: rgb(232, 238, 247);" onclick="calcAlgrithmDown(this)">.</button>
			<button type="button" keystroke="_." class="calculator-ctrl" style="border-bottom: 0px; background: rgb(232, 238, 247);" onclick="calcConfirm()" >确定</button>
			<button id="btn_shutdown" type="button" keystroke="_." class="calculator-ctrl" style="border-bottom: 0px; border-right: 0px; background: rgb(232, 238, 247);" onclick="calcShutDown()" >关闭</button>
			<!-- <button type="button" keystroke="_." class="calculator-ctrl calculator-use" style="border-bottom: 0px; background: rgb(232, 238, 247);">关闭</button> -->
			<%-- <button type="button" keystroke="@U" class="calculator-ctrl calculator-use" title="使用当前值" style="border-right: 0px; border-bottom: 0px; background: rgb(232, 238, 247);">
				<span style="text-align: center;">确认</span>
				<span class="calculator-keystroke calculator-keyname">Ent</span>
			</button> --%>
		</div>
		<div style="clear: both;"/>
	</div>
</body>
</html>