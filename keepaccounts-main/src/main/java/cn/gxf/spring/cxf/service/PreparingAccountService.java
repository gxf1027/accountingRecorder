package cn.gxf.spring.cxf.service;

import java.util.List;

import javax.jws.WebMethod;
import javax.jws.WebService;

import cn.gxf.spring.struts2.integrate.model.FinancialProductDetail;

@WebService
public interface PreparingAccountService {
	
	public static final int UNRETURN_PRODUCT = 1;
	public static final int UNREDEEM_PRODUCT = 2;
	
	@WebMethod
	public List<FinancialProductDetail> getFinancialProducts(int user_id, int type);
}
