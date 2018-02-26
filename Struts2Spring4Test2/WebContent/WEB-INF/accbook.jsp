<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

	<style type="text/css">
	
		.boxy-biz {
		    margin: auto 10px;
		    max-width: 560px;
		    border-bottom: 1px #E6E6E6 solid;
		    background: #FFF;
		    padding: 5px 0 15px;
		}

		.btn1 {
		    background: #15A041;
		    color: #FFF;
		    width: 110px;
		    border: 1px #15A041 solid;
		    font-weight: bold;
		    cursor: pointer;
		}
		
		.w112 {
		    width: 112px;
		}
		.popLabel {
		    width: 100px;
		    float: left;
		    display: inline;
		    padding-right: 10px;
		}
		.fontRight {
		    text-align: right;
		}
		
		.popInput-txt {
		    width: 123px;
		    padding: 0 5px;
		}
		.popInput, .popInput-txt, .popInputHidden {
		    width: 108px;
		    height: 26px;
		    line-height: 26px;
		    padding: 0 20px 0 5px;
		    border: 1px #CCC solid;
		}
		input, textarea {
		    outline: none;
		    background-color: white;
		}
		
		.addAccountOper {
		    width: 120px;
		    margin-top: 10px;
		    margin-left: 50px;
		    /* margin: 0 auto; */
		}
		.addAccountOper {
		   /*  position: relative; */
		}
		.boxy-operate {
		    /* padding: 20px 0; */
		    zoom: 1.5;
		    /* text-align: center; */
		}
	</style>

<title>账户</title>
</head>
<body>
	<s:form method="post" action="accountBook">
		<div class="boxy-biz">
			<p>
				<span class="fontRight popLabel w112">类别</span>
				<s:select id="sel_zhlx" list="#request.zhlx" listKey="key" listValue="value"  name="zhlx_dm" theme="simple" class="popInput popInput-txt" />
			</p>
			
			<p>
				<span class="fontRight popLabel w112">名称</span> 
				<s:textfield id="accountName"  name="zh_mc" maxlength="20"
					class="popInput popInput-txt" required="required" theme="simple" /> <s:property value="bookErrorMessage"/>
			</p>

				<span class="fontRight popLabel w112">卡号</span> 
				<s:textfield name="zhhm" maxlength="19"
					class="popInput {integer:true, messages:{integer:'只能是数字. '}}" required="required" theme="simple"/>
			</p>

			<p class="left tl">
				<span class="fontRight popLabel w112">余额</span>
				<s:textfield name="ye" 
					class="popInput keepMoney {required:true,money:{zero:true,negative:true}, messages:{required:'金额不能为空.'}}" required="required" theme="simple" />
			</p>


			<p >
				<span class="fontRight popLabel w112">备注</span>
				<s:textarea cols="35" rows="5" id="bz" name="bz"
					class="account_comment {maxlength:100,messages:{maxlength:&quot;字数不能多于{0}&quot;}}" theme="simple" />
			</p>
		</div>

	
		<div class="boxy-operate addAccountOper" >
			<s:submit  class="btn1" value="提 交" method="saveBook" theme="simple" />

		</div>
	</s:form>
	
	<s:debug></s:debug>
</body>
</html>