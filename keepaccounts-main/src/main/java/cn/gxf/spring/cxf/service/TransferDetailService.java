package cn.gxf.spring.cxf.service;

import javax.jws.WebMethod;
import javax.jws.WebService;

import cn.gxf.spring.struts2.integrate.model.TransferDetail;

@WebService
public interface TransferDetailService {
	
	@WebMethod
	public int save(TransferDetail transferDetail);
	
	@WebMethod
	public int update(TransferDetail transferDetailNew);
	
	@WebMethod
	public int delete(String mxuuid);
}
