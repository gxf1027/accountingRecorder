package cn.gxf.spring.cxf.service;

import javax.jws.WebMethod;
import javax.jws.WebService;

import cn.gxf.spring.struts2.integrate.model.PaymentDetail;

@WebService
public interface PaymentDetailService {
	
	@WebMethod
	public int save(PaymentDetail paymentDetail);
	
	@WebMethod
	public int update(PaymentDetail paymentDetailNew);
	
	@WebMethod
	public int delete(String mxuuid);
}
