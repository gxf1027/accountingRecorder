package cn.gxf.spring.struts.integrate.actions;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.struts2.ServletActionContext;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.opensymphony.xwork2.ActionSupport;
import com.show.api.ShowApiRequest;

import cn.gxf.spring.struts2.integrate.model.FundInfo;

/*
 * 
 * https://www.showapi.com/api/view/902
 * */
public class FundInfoAction extends ActionSupport{

	private static final long serialVersionUID = -6235277611608364555L;
    private Logger logger = LogManager.getLogger();
	private String fundCode;
	private ShowApiRequest apiRequestBasic;
	private ShowApiRequest apiRequestExtra;
	
	public String queryBasicInfo() throws IOException{
		
//		String res=new ShowApiRequest("http://route.showapi.com/902-1","79115","cff25d777bea4ca1b414b510c2cc4b5d")
//				.addTextPara("fundCode",fundCode)
//				.addTextPara("page","1")
//				.addTextPara("maxResult","20")
//				.addTextPara("needDetails","0")
//			  .post();
		
		// return format jason
		String res = apiRequestBasic.addTextPara("fundCode",fundCode)
					.addTextPara("page","1")
					.addTextPara("maxResult","20")
					.addTextPara("needDetails","0")
					.post();
		
		System.out.println(res);
		
		String fundName = "";
		try {
			JSONObject jsonObject = JSON.parseObject(res);
			JSONObject body = jsonObject.getJSONObject("showapi_res_body");
			String ret_code = body.getString("ret_code");
			if (!ret_code.equals("0")){
				String msg = body.getString("msg");
				throw new RuntimeException(msg); 
			}
			
			JSONObject detail = (JSONObject) body.get("data");
			//fundName = detail.getString("simpleName");
			FundInfo fund = JSON.parseObject(detail.toJSONString(), FundInfo.class);
			fundName = fund.getSimpleName();
		} catch (Exception e) {
			fundName = "error";
			logger.warn("FundInfoAction#queryBasicInfo processing has exceptions with eception:[{}]", e.getMessage());
		}
		
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html);charset=utf-8");
		PrintWriter write = response.getWriter();
		
		write.print(fundName);
		write.flush();
		write.close();
		return null;
	}
	
	public String queryExtraInfo() throws IOException{
		String res = apiRequestExtra.addTextPara("fundCode",fundCode)
									.addTextPara("date","2016-01-01")
									.post();
		System.out.println(res);
		return null;
	}
	
	public String hello(){
		System.out.println("hello");
		return "success";
	}
	
	public String getFundCode() {
		return fundCode;
	}
	
	public void setFundCode(String fundCode) {
		this.fundCode = fundCode;
	}
	
	public void setApiRequestBasic(ShowApiRequest apiRequest) {
		this.apiRequestBasic = apiRequest;
	}
	
	public void setApiRequestExtra(ShowApiRequest apiRequestExtra) {
		this.apiRequestExtra = apiRequestExtra;
	}
}
