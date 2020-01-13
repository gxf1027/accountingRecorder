package cn.gxf.spring.quartz.job;

import java.io.Serializable;


public interface RabbitSender {
	public void send(String routingKey, Serializable objMsg);
	public void convertAndSend(final Serializable object);
	public boolean exceedMaxResendTimes(int currentTimes);
	//public String getRoutingKey(String className);
}
