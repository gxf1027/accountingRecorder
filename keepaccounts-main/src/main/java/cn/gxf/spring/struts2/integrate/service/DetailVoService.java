package cn.gxf.spring.struts2.integrate.service;

import java.util.Date;
import java.util.List;

import cn.gxf.spring.struts2.integrate.model.IncomeDetailVO;
import cn.gxf.spring.struts2.integrate.model.PaymentDetailVO;
import cn.gxf.spring.struts2.integrate.model.TransferDetailVO;

public interface DetailVoService {
	public List<PaymentDetailVO> getPaymentVo(int user_id, Date date_from , Date date_to);
	public List<IncomeDetailVO> getIncomeVo(int user_id, Date date_from , Date date_to);
	public List<TransferDetailVO> getTransferVo(int user_id, Date date_from , Date date_to);
}
