package cn.gxf.spring.struts2.integrate.dao;

import java.util.List;

import cn.gxf.spring.struts2.integrate.model.AccDetailVO;
import cn.gxf.spring.struts2.integrate.model.AccountingDetailVO;
import cn.gxf.spring.struts2.integrate.model.IncomeDetailVO;
import cn.gxf.spring.struts2.integrate.model.PaymentDetailVO;
import cn.gxf.spring.struts2.integrate.model.TransferDetailVO;

public interface AccDetailVoDao {
	List<AccDetailVO> getAccDetailVo(int user_id, String nd, String yf);
	
	List<IncomeDetailVO> getIncomeDetailVo(int user_id, String nd, String yf);
	List<PaymentDetailVO> getPaymentDetailVo(int user_id, String nd, String yf);
	List<TransferDetailVO> getTransferDetailVo(int user_id, String nd, String yf);
	
	List<AccountingDetailVO> getAccDetailVoAll(int user_id, String nd, String yf);
}
