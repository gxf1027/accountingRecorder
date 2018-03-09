package cn.gxf.spring.quartz.job;

import cn.gxf.spring.quartz.job.model.CreditCardBill;

public interface JMSSender {
	public void send(CreditCardBill bill);
}
