function chooseTab2(A){
		/* var AA=$("#"+A);
		AA.siblings().removeClass("current");
		AA.addClass("current"); */
		alert(A);
		if (A=="record-outgo"){
			window.location.href = "${pageContext.request.contextPath}/demo/paymentDetail!inputPayment";
		}
		else if (A=="record-income"){
			window.location.href = "${pageContext.request.contextPath}/demo/incomeDetail!inputIncome";
		}else if (A=="record-transfer"){
			window.location.href = "${pageContext.request.contextPath}/demo/transferDetail!inputTransfer";
		}
		
	}

function chooseTab1(A){
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