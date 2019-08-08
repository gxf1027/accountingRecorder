package cn.gxf.spring.cxf.service;

import javax.jws.WebMethod;
import javax.jws.WebService;

import cn.gxf.spring.struts2.integrate.model.IncomeDetail;

@WebService
public interface IncomeDetailService {
	
	/*@WebMethod
	public String save(String incomeDetailXml);*/
	
	@WebMethod
	public int update(String incomeDetailNewXml);
	
	@WebMethod
	public int delete(String mxuuid);
	
	//test
	@WebMethod
	public int save2(IncomeDetail paymentDetail);
}
