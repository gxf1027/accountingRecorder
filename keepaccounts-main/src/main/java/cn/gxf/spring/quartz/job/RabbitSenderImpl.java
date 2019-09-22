package cn.gxf.spring.quartz.job;


import java.io.Serializable;

import org.springframework.amqp.AmqpException;
import org.springframework.amqp.core.AmqpTemplate;


public class RabbitSenderImpl implements RabbitSender{

	private String exchageName;
	private AmqpTemplate rabbitTemplate;
	
	@Override
	public void send(String routingKey, Serializable objMsg) {
		try {
			rabbitTemplate.convertAndSend(exchageName, routingKey, objMsg);
		} catch (AmqpException e) {
			// TODO: handle exception
		}
    	
	}


	public String getExchageName() {
		return exchageName;
	}


	public void setExchageName(String exchageName) {
		this.exchageName = exchageName;
	}

	public AmqpTemplate getRabbitTemplate() {
		return rabbitTemplate;
	}


	public void setRabbitTemplate(AmqpTemplate rabbitTemplate) {
		this.rabbitTemplate = rabbitTemplate;
	}
	
	
	
}
