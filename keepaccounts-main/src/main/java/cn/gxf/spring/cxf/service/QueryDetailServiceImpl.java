package cn.gxf.spring.cxf.service;

import java.util.List;
import java.util.Map;

import javax.jws.WebService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.gxf.spring.struts2.integrate.model.IncomeDetailVO;
import cn.gxf.spring.struts2.integrate.model.PaymentDetailVO;
import cn.gxf.spring.struts2.integrate.model.TransferDetailVO;
import cn.gxf.spring.struts2.integrate.service.CustomTailorQueryService;

@Service
@WebService(endpointInterface="cn.gxf.spring.cxf.service.QueryDetailService")
public class QueryDetailServiceImpl implements QueryDetailService{

	@Autowired
	private CustomTailorQueryService customTailorQueryService;
	
	@Override
	public List<IncomeDetailVO> queryIncomeDetail(Map<String, Object> params, int pageNum, int pageSize) {
	
		
		return customTailorQueryService.queryIncome(params, pageNum, pageSize);
	}
	

	@Override
	public List<PaymentDetailVO> queryPaymentDetail(Map<String, Object> params, int pageNum, int pageSize) {
		
		return customTailorQueryService.queryPayment(params, pageNum, pageSize);
	}



	@Override
	public List<TransferDetailVO> queryTransferDetail(Map<String, Object> params, int pageNum, int pageSize) {
		
		return customTailorQueryService.queryTransfer(params, pageNum, pageSize);
	}
	
	private boolean checkParams(Map<String, Object> params){
		
		return true;
	}

}
