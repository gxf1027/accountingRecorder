package cn.gxf.spring.bill.mailsender;

import java.util.List;

import cn.gxf.spring.quartz.job.model.CreditCardBill;

public interface MailSenderService {
	public void senderOne(CreditCardBill bill);
	public void senderBatch(List<CreditCardBill> bills);
}
