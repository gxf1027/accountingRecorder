package cn.gxf.spring.bill.mailsender;

import java.util.List;

import cn.gxf.spring.quartz.job.model.CreditCardBill;

public interface MailSenderService {
	public void sendSimpleMailTxt(CreditCardBill bill);
	public void sendSimpleMailThymeleaf(CreditCardBill bill);
	public void sendBatch(List<CreditCardBill> bills);
	
	public void sendTest();
}
