package cn.gxf.spring.cxf.service;

import javax.jws.WebMethod;
import javax.jws.WebService;

import cn.gxf.spring.struts2.integrate.model.IncomeDetail;

@WebService
public interface IncomeDetailService {
	
	@WebMethod
	public int save(IncomeDetail incomeDetail);
	
	@WebMethod
	public int update(IncomeDetail incomeDetailNew);
	
	@WebMethod
	public int delete(String mxuuid);
}
