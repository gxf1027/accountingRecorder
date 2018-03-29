<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="_csrf" content="${_csrf.token}"/>
<!-- default header name is X-CSRF-TOKEN -->
<meta name="_csrf_header" content="${_csrf.headerName}"/>
<link rel="stylesheet" type="text/css" href="../css/header.css" />
<link rel="stylesheet" type="text/css" href="../css/header-account.css" />
<link rel="stylesheet" type="text/css" href="../css/footer.css" />
<link rel="stylesheet" type="text/css" href="../css/left-nav.css" />
<link rel="stylesheet" type="text/css" href="../css/buttons.css" />
<link rel="stylesheet" type="text/css" href="../js/toastr/toastr.css" />


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
		
		.record_biz, .query_condition{font-size:12px;/* font-weight:normal; */width:836px;margin-top:2px;}
		.record_biz, .query_condition{border-top:10px #E0E0E0 solid;border-left:1px #BBB solid;border-right:1px #BBB solid;border-bottom:10px #E0E0E0 solid;background:#FFF;}
		.record_biz th{color:#646464;padding-right:6px;text-align:right;/* font-weight:normal; */width:65px;}
		.record_biz td{text-align: center; font-family: Arial; font-size: 14px}
		.query_condition th{color:#646464;padding-right:6px;text-align:right;/* font-weight:normal; */width:65px;}
		.record_biz tr{height:36px;}
		.query_condition tr{height:36px;}
		.recordInput{width:118px;padding-left:0px;height:24px;line-height:24px;border:1px solid #CCC;padding-right:2px;font-size:12px;}
		.selectInput{width:77px;padding-left:5px;height:28px;line-height:24px;border:1px solid #CCC;padding-right:20px;font-size:12px;}
		.queryBtn{cursor:pointer;height:30px;line-height:30px;_height:28px;_line-height:28px;text-align:center;}
		.queryBtn{background:#15A041;color:#FFF;width:110px;border:1px #15A041 solid;font-weight:bold;}
		
		.tab{position:relative;float:left;display:inline-block;text-align:center;color:#000;width:110px;height:39px;line-height:39px;cursor:pointer;border-right:1px #E0E0E0 solid;border-bottom:0;}
		
		.even{ background: #FFFFEE;}
		.odd{ background: #F5F5F5;border: 1px #F00 solid;}
		
		.adisabled { pointer-events: none; }
		a {text-decoration: none;}
		.plot-info{font-family:"微软雅黑";font-size: 14px;}
	</style>
	
	<script type="text/javascript" src="../js/jquery.js"></script>
	<script type="text/javascript" src="../js/jqplot.js"></script>
	<script type="text/javascript" src="../js/fixheader/jquery.fixme.js"></script>
	<!-- <link rel="stylesheet" href="../js/fixheader/style.css"> -->
	<script type="text/javascript" src="../js/select2/js/select2.min.js"></script>
	<script type="text/javascript" src="../js/toastr/toastr.js"></script>
	
	<script type="text/javascript">
		
		$(document).ready(function(){
			
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
			
			$(".record_biz tbody> tr:odd").addClass("odd");
			$(".record_biz tbody> tr:even").addClass("even");
			
			$("#nd").change(function(){
				window.location.href = "${pageContext.request.contextPath}/demo/frontStatistics!inputFront?nd="+$(this).val();
			});
			
			/* $("#queryResTable").fixMe(); */
			
			$('#logout-url').click(function (){	
				 $("#logout-form").submit();
			});
			
			$("a[name='billsend']").click(function(){
				//window.location.href = "${pageContext.request.contextPath}/demo/billsend";
				var token = $("meta[name='_csrf']").attr("content");
				var header = $("meta[name='_csrf_header']").attr("content");
				var headers = {};
				headers['__RequestVerificationToken'] = token;
				var url ="${pageContext.request.contextPath }/demo/billsend!ajaxrequest"
				var args={};
				
				/* $.post(url, args, function(data){
					console.log("data.username: " + data.username);
					console.log("data.msg: " + data.msg);
					console.log("data:" + data);
					$("#username_msg").html(data);
				}); */
				
				 $.ajax({
				        type: "POST",
				        //headers: headers,
				        cache: false,
				        url: url,
				        data: args,
				        dataType:"text",
				        async: true,
				        error: function(data, error) {},
				        beforeSend : function(xhr) {
			                xhr.setRequestHeader(header, token);
			            }, 
				        success: function(data)
				        {
				        	//$("#username_msg").html(data);
				        	console.log("rv:"+data);
				        	
				        	if (data == "success"){
				        		console.log("success");
				        		toastr.info("<b>账单已发送.</b>","提示");
				        	}
				        }
				    });
			});
		})
		
		
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
				$("#paymentbox").css("display", "initial").siblings().css("display", "none");
				$("#tab_payment").addClass("tabcurrent").siblings().removeClass("tabcurrent");
				break;
			
			case "query-income":
				$("#incomebox").css("display", "initial").siblings().css("display", "none");
				$("#tab_income").addClass("tabcurrent").siblings().removeClass("tabcurrent");
				break;
				
			case "query-transfer":
				$("#transferbox").css("display", "initial").siblings().css("display", "none");
				$("#tab_transfer").addClass("tabcurrent").siblings().removeClass("tabcurrent");
			default:
				break;
			}
			
		}
	</script>
	<link rel="shortcut icon" href="../components/favicon.ico" type="image/x-icon" />
	
<title>收支概要</title>
</head>
<body>
	<div class="site-nav" id="siteNav">
		<div class="g-layout-header">
			<ul >
				<li>
					<%-- <a href="${pageContext.request.contextPath}/j_spring_security_logout" class="home " bi="8013">退出</a> --%>
					<form id="logout-form" action="${pageContext.request.contextPath}/j_spring_security_logout" method="post" hidden >
						<input type="submit" value="退出" class="home " />
						<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
					</form>
					<a id="logout-url" href="#" class="home " style=" text-decoration: none;">退出</a>
				</li>
				
				<li>
					<a href="frontStatistics!inputFront"  class="home before" bi="8012">首页</a>
				</li>
				
				<li>
					<a href="#" name="billsend" class="home before" bi="8013">发送账单</a>
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
				<div id="query" class="menu current" onclick="chooseTab('queryPayment')">
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
			
			
			<div class="main">	
				<input type="hidden" id="queryType" name="queryType" value="${requestScope.queryType }"  />
				<div id="stat_table" class="contentPage_1" style=" min-width: 600px; margin-top: 16px; " >
					<div>
						<s:form action="frontStatistics" method="POST">
							<s:select list="{'2015','2016','2017','2018','2019','2020'}" name="nd" id="nd" class="selectInput" theme="simple"></s:select>
							&nbsp;
						
							<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
							<s:submit method="reProcStat" key="重新统计" theme="simple" class="button button-primary button-rounded button-small"></s:submit>
						</s:form>
					</div>
					
					<div style="min-height: 220px; margin-left: 170px; margin-top: 6px; ">
						<table id="queryResTable" class="record_biz" >
							<thead style="font-size: 14px;color:#737373; background-color: #eee; font-weight: bold;">
								<tr>
									<th style="text-align: center;">年度-月份</th>
									<th style="text-align: center;">支出</th>
									<th style="text-align: center;">收入</th>
									<th style="text-align: center;">净收入</th>
								</tr>
							</thead>
							
							<tbody style="min-height: 400px;">
								<s:iterator value="#request.statistics" var="stat" status="st">
									<tr>
										
										<td><span class="ndyf">${ndyf }</span></td>
										<td title="显示明细">
											<a class="pay-je" href="listDetail!listPaymentByMonth?date_from=${yfFirstDate }&date_to=${yfLastDate }">${paysumStr2 }</a>
										</td>
										<td title="显示明细">
											<a class="income-je" href="listDetail!listIncomeByMonth?date_from=${yfFirstDate }&date_to=${yfLastDate }">${incomesumStr2 }</a>
										</td>
										<td>${jeNetSumStr2 }</td>
									</tr>
								</s:iterator>
							</tbody>
							
							<tfoot style="background-color: #eee; font-weight: bold;" >
								<td>合计</td>
								<td>${statsum.paysum }</td>
								<td>${statsum.incomesum }</td>
								<td>${statsum.jeNetSumStr2 }</td>
							</tfoot>
						</table>
					</div>
					
					
				</div>
				
				<div id="ndyf_chart">
					<script type="text/javascript">
						$(document).ready(function() {
							var yfarray = [];
							$(".ndyf").each(function(){
								yfarray.push($(this).text().substr(5,2));
							});	
							//console.log("yf: "+yfarray);
							
							var paymentje = [];
							$(".pay-je").each(function(){
								paymentje.push(parseFloat($(this).text()));
							});
							//console.log("pay-je: "+ paymentje);
							
							var incomeje = [];
							$(".income-je").each(function(){
								incomeje.push(parseFloat($(this).text()));
							});
							//console.log("in-je: "+ incomeje);
							
							//var data = [[1,2,3,4,5,6,7,8,9],[15.54,6,8,1,11,22,4,21,6]];
							data=[[0,0,0,0,0,0,0,0,0,0,0,0],[0,0,0,0,0,0,0,0,0,0,0,0]];
							var y_max = 12000; //Y轴最大刻度
							var line_title = ["收入","支出"]; //曲线名称
							var y_label = "金额"; //Y轴标题
							var x_label = "月份"; //X轴标题
							var x_axis = ['01','02','03','04','05','06','07','08','09','10','11','12']; //定义X轴刻度值
							var title = "收入/支出"; //统计图标标题
							
							var max_v = -1.0;
							for (var i =0; i< incomeje.length; i++){
								var tmp = incomeje[i] > paymentje[i] ? incomeje[i] : paymentje[i];
								if (max_v < tmp){
									max_v = tmp;
								}
							}
						
							if (max_v >0){
								y_max = parseInt(max_v*1.2);
							}
		
							for (var i in yfarray){
								var indx = $.inArray(yfarray[i], x_axis);
								data[0][indx] = incomeje[i];
								data[1][indx] = paymentje[i];
							}
							
							
							// 以下调用jqplot函数
							//j.jqplot.diagram.base("front-chart", data, line_title, title, x_axis, x_label, y_label,  y_max, 2/*图表类型*/);
							var plot_type =2;
							var renderer = null;
							if(plot_type == 1) renderer = $.jqplot.BlockRenderer ;
							else if(plot_type == 2) renderer = $.jqplot.BarRenderer ;
							$.jqplot("ndyf_plot", data, {
								title:{         // 标题属性 
									text: title,
									show:true,              // 是否阴影  
									fontFamily:'微软雅黑',  // 标题字体   
			                        fontSize:14            // 标题字体大小
								},
					        	
								legend:{show:true,labels: line_title},
								animate:true,
								seriesDefaults: {  
					               renderer: renderer, // 利用渲染器（BarRenderer）渲染现有图表  
					               pointLabels: { show: true }  
					            },
					            
								axes:{
									yaxis:{
										label: y_label==null?"":y_label,
					            		max:y_max,
					            		min:0
									},
									xaxis:{
										renderer: $.jqplot.CategoryAxisRenderer, // 设置横（纵）轴上数据加载的渲染器,
										ticks: x_axis,
					                	label: x_label==null?"":x_label
									}
								}, 
								series:[{color:'#5FAB78'}] 
					        });
						});
						
					</script>
					
					<div id="ndyf_plot" style="margin-top:25px; width: 880px;margin-left: 140px;"></div>
				</div>
				
				
				<div id="pay_chart">
					<table id="payment_stat_table" style="display: none">
						<tbody>
							<s:iterator value="#request.paymentStatDl" var="stat" status="st">
								<tr>
									<td class="payment_stat_dl">${category_mc }:${je }</td>
								</tr>
							</s:iterator>
						</tbody>
					</table>
					<script type="text/javascript">
					 $(function(){  
						 var data = [];
						 $(".payment_stat_dl").each(function(){
							 var itemmap = $(this).text().split(':');
							 
							 var itemarray = [itemmap[0], parseFloat(itemmap[1])];
							 data.push(itemarray);
						 });
						 
		                $.jqplot('pay-plot', [data], {  
		                    title:{         // 标题属性  
		                        text:'支出统计',// 标题文本  
		                        show:true,              // 是否阴影  
		                        fontFamily:'微软雅黑',  // 标题字体   
		                        fontSize:14,            // 标题字体大小  
		                        textAlign:'center',       // 标题对齐方式  
		                        textColor:'#515151',    // 标题颜色（也可以写作属性color）  
		                        escapeHtml:false        // 是否转义HTML字符，值为false时，可以在text属性中使用HTML代码  
		                    },  
		                    seriesDefaults:{  
		                    	renderer: $.jqplot.DonutRenderer,  
		                        rendererOptions:{  
		                            showDataLabels:true,
		                            sliceMargin: 4
		                        },  
		                        pointLabels: {  // 显示数据点，依赖于jqplot.pointLabels.min.js文件  
		                            show: true  
		                        }  
		                    },  
		                    grid:{  
		                        drawBorder:false,  
		                        shadow:false,  
		                        background:'transparent'  
		                    },  
		                    legend:{        // 图例属性  
		                        show:true,  
		                        placement: 'outsideGrid' // 设置图例位于图表外部  
		                    }  
		                }); 
		                
		                $('#pay-plot').bind('jqplotDataClick', 
				            function (ev, seriesIndex, pointIndex, data) {
				                //$('#info_pay').html('series: '+seriesIndex+', point: '+pointIndex+', data: '+data);
				            	$('#info_pay').html(data[0]+": "+data[1]);
				            });
		                
		            });
					 
					 
					</script>
					<div><span id="info_pay" class="plot-info">&nbsp;</span></div>
					<div id="pay-plot" style="margin-top:25px; width: 880px;margin-left: 140px;"></div>
				</div>
				
				
				
				<div id="income_chart">
					<table id="income_stat_table" style="display: none">
						<tbody>
							<s:iterator value="#request.incomeStatLb" var="stat" status="st">
								<tr>
									<td class="income_stat_lb">${category_mc }:${je }</td>
								</tr>
							</s:iterator>
						</tbody>
					</table>
					<script type="text/javascript">
					 $(function(){  
						 var data = [];
						 $(".income_stat_lb").each(function(){
							 var itemmap = $(this).text().split(':');
							 
							 var itemarray = [itemmap[0], parseFloat(itemmap[1])];
							 data.push(itemarray);
						 });
					 
						 $.jqplot('income-plot', [data], {  
						     title:{         // 标题属性  
						         text:'收入统计',// 标题文本  
						         show:true,              // 是否阴影  
						         fontFamily:'微软雅黑',  // 标题字体   
						         fontSize:14,            // 标题字体大小  
						         textAlign:'center',       // 标题对齐方式  
						         textColor:'#515151',    // 标题颜色（也可以写作属性color）  
						         escapeHtml:false        // 是否转义HTML字符，值为false时，可以在text属性中使用HTML代码  
						     },  
						     seriesDefaults:{  
						     	renderer: $.jqplot.DonutRenderer,  
						         rendererOptions:{  
						             showDataLabels:true,
						             sliceMargin: 4
						         },  
						         pointLabels: {  // 显示数据点，依赖于jqplot.pointLabels.min.js文件  
						             show: true  
						         }  
						     },  
						     grid:{  
						         drawBorder:false,  
						         shadow:false,  
						         background:'transparent'  
						     },  
						     legend:{        // 图例属性  
						         show:true,  
						         placement: 'outsideGrid' // 设置图例位于图表外部  
						     }  
						 });  
						 
						 $('#income-plot').bind('jqplotDataClick', 
					            function (ev, seriesIndex, pointIndex, data) {
					                //$('#info_pay').html('series: '+seriesIndex+', point: '+pointIndex+', data: '+data);
					            	$('#info_incmoe').html(data[0]+": "+data[1]);
					            });
		            });
					</script>
					<div><span id="info_incmoe" class="plot-info">&nbsp;</span></div>
					<div id="income-plot" style="margin-top:25px; width: 880px;margin-left: 140px;"></div>
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