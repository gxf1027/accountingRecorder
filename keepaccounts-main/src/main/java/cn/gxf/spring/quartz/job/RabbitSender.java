package cn.gxf.spring.quartz.job;

import java.io.Serializable;

public interface RabbitSender {
	public void send(String routingKey, Serializable objMsg);
}
