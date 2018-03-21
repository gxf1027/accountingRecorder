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
	<link rel="stylesheet" type="text/css" href="../js/toastr/toastr.css" />

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
	</style>

	
	<%-- <script src="http://code.jquery.com/jquery-latest.js"></script> --%>
	<script type="text/javascript" src="../js/jquery.js"></script>
	<script type="text/javascript" src="../js/calculator.js"></script>
	<script type="text/javascript" src="../js/toastr/toastr.js"></script>
	<script type="text/javascript">	
	
	if(typeof jQuery !='undefined'){
		
		 
	}else{
	 
	    alert("jQuery library is not found!");
	 
	}
	
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
			window.location.href = "${pageContext.request.contextPath}/demo/customTailoredQuery!inputQuery";
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
	    
	    $('#input_je').blur(function(){
	    	
	    	var je = $(this).val();
	    	//console.log(parseFloat(je));
	    	if (isNaN(parseFloat(je)) || isNaN(je)){
	    		toastr.error("<b>请输入正确的金额.</b>","提示");
	    	}
	    });
	   
	})

	function fix(num, length) {
	  return ('' + num).length < length ? ((new Array(length + 1)).join('0') + num).slice(-length) : '' + num;
	} 
	/* 获取当前时间 END*/

	function selChange(obj){
		 
		var p1=$(obj).children('option:selected').val();//这就是selected的值 
		if (p1 == 'z999'){
			//alert($(obj).children('option:selected').val());
			var url = "${pageContext.request.contextPath}/demo/accountBook!inputBookFromIncome?";
			url += "incomeDetail.je="+$("#input_je").val();
			url += "&incomeDetail.fkfmc="+$("#input_fkfmc").val();
			url += "&incomeDetail.lb_dm="+$("#sel_lb").val();
			if (document.getElementById("fsrq")){ 
				url += "&incomeDetail.fsrq="+$("#fsrq").val();	
			}else{
				url += "&incomeDetail.fsrq="+$("#fsrq2").val();	
			}
			
			url += "&incomeDetail.bz="+$("#bz").val();
			//alert(url);
			window.location.href = url;
		} 
	}
	
	
	function checkBeforeSave(){
     	
    	var je = $('#input_je').val();
    	if (isNaN(parseFloat(je)) || isNaN(je)){
    		return false;
    	}
    	if (parseFloat(je)<0){
    		toastr.info("<b>收入金额不能小于零.</b>","提示");
    		return false;
    	}
		 
		return true;
	}
	
	</script>
<link rel="shortcut icon" href="../components/favicon.ico" type="image/x-icon" />
<title>收入</title>
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
		<div id="record-income" class="tab current"  onclick="chooseTab('record-income')">
			<a href="${pageContext.request.contextPath}/demo/incomeDetail!inputIncome" style="text-decoration:none">收入</a>
		</div>  
		<div id="record-transfer" class="tab" href="/biz/recordPage_transfer.action" onclick="chooseTab('record-transfer')">
			<a href="${pageContext.request.contextPath}/demo/transferDetail!inputTransfer" style="text-decoration:none">转账</a>
		</div>  
		<div id="record-loan" class="tab" href="/biz/recordPage_loan_payback.action" onclick="choiceTab('record-loan')" style="text-decoration:none">借贷</div> 
	</div>
	
	<div id="bizContent" class="bizContent"  style="min-height: 610px;">
	
	<s:form action="incomeDetail" method="post" onsubmit="return checkBeforeSave()" style="visibility: visible;">
		<table class="record_biz">
			<tbody>
				<s:textfield name="mxuuid" theme="simple" style="display:none" />
				<s:textfield name="accuuid" theme="simple" style="display:none" />
				<s:textfield name="date_from" theme="simple" style="display:none"/>
				<s:textfield name="date_to" theme="simple" style="display:none"/>
				<tr>
					<th>金额</th>
					<td>
						<%-- <s:textfield name="je" class="recordInput calc keepMoney {required:true,money:1,messages:{required:'收入金额必填'}} hasCalculator" theme="simple" /> --%>
						<!-- <input type="text" id="input_je" name="je" maxlength="16" required="required" class="recordInput calc keepMoney {required:true,money:1,messages:{required:'收入金额必填'}} hasCalculator" /> -->
						<s:textfield  id="input_je" name="je"  maxlength="16" required="required" class="recordInput calc keepMoney {required:true,money:1,messages:{required:'收入金额必填'}} hasCalculator" theme="simple" />
						<img id="calc_img" src="../components/calculator/calculator.png" alt="计算器" title="计算器" class="calculator-trigger" onclick="calcDisplay()">
					</td>	
				</tr>
				
				<tr>
					<th>类别</th>
					<td> 
						<s:select id="sel_lb" list="#request.SRLB" listKey="key" listValue="value"  name="lb_dm" theme="simple" class="selectInput"></s:select>
					</td>
				</tr>
				
				<tr style="height:10px;font-size:0px;">
					<td colspan="3" style="border-bottom: 1px solid #DDE2E8;">&nbsp;</td>
				</tr>
				
				<tr style="height:10px;font-size:0px;">
					<td></td>
				</tr>
				
				<tr>
	    			<th>付款方</th>
	    			<td><input type="text" id="input_fkfmc" name="fkfmc" maxlength="20" value="<s:property  value='fkfmc'/>" class="recordInput"></td>
	  			</tr>
	  			
	  			<tr>
					<th>账户</th>
					<td>
						<s:select list="#request.ZH_INFO" listKey="zh_dm" listValue="zh_mc" name="zh_dm" theme="simple" class="selectInput" onchange="selChange(this)" />
					</td>
				</tr>
	  			
	  			<tr>
					<th>时间</th>
					<td>
						<%-- <input type="date" id="shijian" name="shijian" value="${request.NOW_DATE }"  required="required" class="recordInput {required:true,messages:{required:'收入日期必填!'}} dateInputBg" style="background-position: 115px 12px;"> --%>
						<s:if test="#request.DETAIL_MODE=='EDIT'">
							<input type="datetime-local"  id="fsrq" name="fsrq" value="<s:property value='fsrqToShow'/>" style="height:24px;line-height:24px;border:1px solid #CCC;background-position: 115px 12px;" required="required">
						</s:if>
						<s:else>
							<input type="datetime-local"  id="fsrq2" name="fsrq"  style="height:24px;line-height:24px;border:1px solid #CCC;background-position: 115px 12px;" required="required">
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
					<th style="vertical-align:top">备注</th>
					<td><s:textarea name="bz" cols="35" rows="5" id="bz" class="{maxlength:200,messages:{字数不能多于200;}}"  theme="simple"/></td>
				</tr>
				<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
			</tbody>
		</table>
		
		<div class="submitButton" style="margin-top:16px;">
			<s:if test="#request.DETAIL_MODE=='EDIT'">
				<s:submit class="btn1 save" id="save1" value="保存" method="updateIncome" theme="simple"/>
			</s:if>
			<s:else>
				<s:submit class="btn1 save" id="save1" value="保存" method="saveIncome" theme="simple"/>
    			<s:submit class="btn2 save" id="save2" style="margin-left:12px;" value="保存后再记" method="saveIncomeAndRec" theme="simple" />
			</s:else>
			
		</div>
		<s:token></s:token>   <!--服务器通过token标签 来产生盾牌随机数-->
	</s:form>
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