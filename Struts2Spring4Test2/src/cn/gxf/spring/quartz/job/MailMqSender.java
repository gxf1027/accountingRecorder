package cn.gxf.spring.quartz.job;

import cn.gxf.spring.quartz.job.model.CreditCardBill;

public interface MailMqSender {
	public void send(CreditCardBill bill);
}
