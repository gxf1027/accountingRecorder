package cn.gxf.spring.struts.integrate.test;

import com.show.api.ShowApiRequest;

public class TestFundQuery {
	public static void main(String[] args) {
		String res=new ShowApiRequest("http://route.showapi.com/902-1","79115","cff25d777bea4ca1b414b510c2cc4b5d")
				.addTextPara("fundCode","519185")
				.addTextPara("simpleName","��Ҿ�ѡ")
				.addTextPara("page","1")
				.addTextPara("maxResult","20")
				.addTextPara("needDetails","0")
			  .post();
			System.out.println(res);
	}
}
