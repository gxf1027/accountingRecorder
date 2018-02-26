package cn.gxf.spring.struts2.integrate.service;

import java.util.List;

import cn.gxf.spring.struts2.integrate.model.AccDateStat;
import cn.gxf.spring.struts2.integrate.model.IncomeDetail;
import cn.gxf.spring.struts2.integrate.model.PaymentDetail;
import cn.gxf.spring.struts2.integrate.model.TransferDetail;

public interface DetailAccountService {
	public void saveOnePayment(PaymentDetail paymentDetail);
	public void saveOneIncome(IncomeDetail incomeDetail);
	public void saveOneTransfer(TransferDetail transferDetail);

	
	public void saveOneIncomeMB(IncomeDetail incomeDetail);
	public void saveOnePaymentMB(PaymentDetail paymentDetail);
	public void saveOneTransferMB(TransferDetail transferDetail);
	
}
