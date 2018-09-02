package cn.gxf.spring.struts2.integrate.service;

import java.util.List;
import java.util.Map;

import cn.gxf.spring.struts2.integrate.model.IncomeDetailVO;
import cn.gxf.spring.struts2.integrate.model.PaymentDetailVO;
import cn.gxf.spring.struts2.integrate.model.TransferDetailVO;

public interface CustomTailorQueryService {
	public List<PaymentDetailVO> queryPayment(Map<String, Object> params, int pageNum, int pageSize);
	public List<IncomeDetailVO> queryIncome(Map<String, Object> params, int pageNum, int pageSize);
	public List<TransferDetailVO> queryTransfer(Map<String, Object> params, int pageNum, int pageSize);
}
