package cn.gxf.spring.cxf.service;

import java.util.List;
import java.util.Map;

import javax.jws.WebMethod;
import javax.jws.WebService;

import cn.gxf.spring.struts2.integrate.model.IncomeDetailVO;
import cn.gxf.spring.struts2.integrate.model.PaymentDetailVO;
import cn.gxf.spring.struts2.integrate.model.TransferDetailVO;

@WebService
public interface QueryDetailService {
	@WebMethod
	public List<IncomeDetailVO> queryIncomeDetail(Map<String, Object> params, int pageNum, int pageSize);
	@WebMethod
	public List<PaymentDetailVO> queryPaymentDetail(Map<String, Object> params, int pageNum, int pageSize);
	@WebMethod
	public List<TransferDetailVO> queryTransferDetail(Map<String, Object> params, int pageNum, int pageSize);
}
