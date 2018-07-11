var num_show="";
var stack = new Array();
var stack_aux = new Array(); // 记录数字和+-*/符号的输入
function calcDisplay() {
			/* alert(document.getElementById('calc_popup').style.display); */
			var calcState = document.getElementById('calc_popup').style.display;
			if (calcState=="none"){
				/* document.getElementById('calc_popup').style.display="block"; */
				/* $("#calc_popup").css('display','block'); */
				$("#calc_popup").show();
				/* document.getElementById('calc_res').innerText = '99'; */
				$("#calc_res").text("0");
				$("#calc_res").text($("#input_je").val()); // 弹出时显示金额框的值
				num_show="";
				
				// 设置弹出位置
				var img_x = $("#calc_img").offset().top;
				var img_y = $("#calc_img").offset().left;
				
				$("#calc_popup").offset({top: img_x,left: img_y+30});  
			}
			else if (calcState=="block"){
				/* document.getElementById('calc_popup').style.display="none"; */
				/* $("#calc_popup").css('display','none'); */
				$("#calc_popup").hide();
				num_show="";
			}
		}
		
		function calcShutDown(){
			
			/* $("#calc_popup").css('display', 'none'); */
			$("#calc_popup").hide();
			num_show="";
			stack.length = 0;
			stack_aux.length = 0;
		}
		
		function calcConfirm(){
			
			$("#input_je").val(parseFloat($("#calc_res").text()).toFixed(2));
			$("#calc_popup").hide();
			stack.length = 0;
			stack_aux.length = 0;
		}
		
		function calcClear(){
			num_show = "";
			$("#calc_res").text("0");
			stack.length = 0;
			stack_aux.length = 0;
		}
		
		function calcAlgrithmDown(obj){
			var id=$(obj).attr("id");
			var calc_txt =$(obj).text();
			switch (calc_txt) {
			case '1':
			case '2':
			case '3':
			case '4':
			case '5':
			case '6':
			case '7':
			case '8':
			case '9':
			case '0':
				if (num_show == "0"){
					num_show = "";
				}
				if (num_show.length <= 15){
					num_show += calc_txt;
				}
				$("#calc_res").text(num_show);
				stack_aux.push(calc_txt);
				break;
			case '.':
				
				if (num_show == ""){
					num_show ="0.";
				}
				else if (num_show.indexOf(".")  == -1){
					num_show += '.';
				}
				stack_aux.push(".");
				break;
			case '+':
			case '-':
			case '×':
			case '÷':
				
				var tmp = parseFloat($("#calc_res").text());
				if ( isNaN(tmp) ){
					alert("NaN " + calc_txt);
				}else{
					
					var item = stack.pop();
					if (item == null){
						// 第一次按下计算符号键，会把显示的数字和按键值依次压栈
						num_show="";
						stack.push(tmp);
						stack.push(calc_txt);
					}
					else if (isNaN(stack_aux[stack_aux.length-1])==false || stack_aux[stack_aux.length-1]==".")
					{
						// 连续计算的情况， 此时stack里依次为 数字+操作符，比如[100, '+'],其中操作符已经在上面pop中被弹出
						//alert(stack.toString()+ "item: " + item);
						var num2 = parseFloat($("#calc_res").text());
						var operation = item;
						var num1 = parseFloat(stack.pop());
						var res = calcRes(num1, num2, operation);
						stack.push(res+"");
						stack.push(calc_txt);
						
						num_show = res + "";
						$("#calc_res").text(num_show);
						num_show="";
					}
					else if(item == "+" || item == "-" || item == "×" || item == "÷"){
						
						//重复按下计算符号键，会把之前的操作符替换掉,
						// 可能此时屏幕上的数字已经被改变，也要修正
						stack.pop();
						stack.push($("#calc_res").text());
						stack.push(calc_txt);
						
						num_show="";
					}
					
				}
				stack_aux.push(calc_txt); // 本次按下的符号键必须也记录
				console.log(stack.toString() + " ||||| " + stack_aux.toString());
				break;
			case '=':
				
				var tmp = parseFloat(num_show);
				if ( isNaN(tmp) ){
					alert("NaN " + calc_txt);
				}
				else{
					if (stack.length == 2 ){
						var num2 = tmp;
						var operation = stack.pop();
						var num1 = parseFloat(stack.pop());
						var res = calcRes(num1, num2, operation);
						num_show = res + "";
						$("#calc_res").text(num_show);
						num_show = "";
						
						stack.length = 0;
						stack_aux.length = 0;
						
					}
				}
				break;
			case '←':
				var tmp = $("#calc_res").text();
				if (tmp.length > 1 ){
					tmp = tmp.substring(0, tmp.length-1);
				}else if (tmp.length == 1){
					tmp ="0";
				}
				num_show = tmp;
				$("#calc_res").text(tmp);
				break;
			default:
				break;
			} 
			//alert($(obj).text());
  			
		}
		
		function calcRes(num1, num2, oper){
			var res = 0;
			switch (oper) {
			case '+':
				res = num1+num2;
				break;
			case '-':
				res = num1-num2;
				break;
			case '×':
				res = num1 * num2;
				break;
			case '÷':
				res = num1 / num2;
				break;
			default:
				break;
			}
			return res;
		}