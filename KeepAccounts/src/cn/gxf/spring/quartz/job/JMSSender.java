package cn.gxf.spring.quartz.job;

import java.io.Serializable;

import javax.jms.Destination;

import cn.gxf.spring.quartz.job.model.CreditCardBill;

public interface JMSSender {
	public void send(CreditCardBill bill);
	public void send(Destination destination, Serializable objMsg);
}
