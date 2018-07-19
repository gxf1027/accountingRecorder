package cn.gxf.spring.bill.mailsender;

import java.util.List;

import cn.gxf.spring.quartz.job.model.CreditCardBill;
import cn.gxf.spring.quartz.job.model.FinancialProductsNotice;

public interface MailSenderService {
	public void sendSimpleMailTxt(CreditCardBill bill);
	public void sendSimpleMailThymeleaf(CreditCardBill bill);
	public void sendBatch(List<CreditCardBill> bills);
	
	public void sendSimpleMailThymeleaf(FinancialProductsNotice notice);
	
	public void sendTest();
}
