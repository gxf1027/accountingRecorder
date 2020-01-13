package cn.gxf.spring.quartz.job;


import java.io.Serializable;
import java.util.Map;
import java.util.UUID;

import org.springframework.amqp.AmqpException;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.support.CorrelationData;

import cn.gxf.spring.quartz.job.model.CreditCardBill;
import cn.gxf.spring.quartz.job.model.FinancialProductsNotice;
import cn.gxf.spring.struts2.integrate.model.MsgLog;
import cn.gxf.spring.struts2.integrate.service.MsgLogService;


public class RabbitSenderImpl implements RabbitSender{

	private String exchageName;
	private RabbitTemplate rabbitTemplate;
	private Map<String, String> class2routkeyMap;
	private MsgLogService msgLogService;
	private int maxResendTimes = 3;
	
	@Override
	public void send(String routingKey, Serializable objMsg) {
		try {
			String msgId = getId(objMsg);
			MsgLog msgLog = new MsgLog(msgId, objMsg, "direct.exchange", routingKey);
			msgLogService.insertMsgLog(msgLog);// 消息入库

	        CorrelationData correlationData = new CorrelationData(msgId);
	        
			rabbitTemplate.convertAndSend(exchageName, routingKey, objMsg, correlationData);
		} catch (AmqpException e) {
			// TODO: handle exception
		}
    	
	}
	
	// 只用于重发消息，消息日志不用持久化到数据库
	@Override
	public void convertAndSend(Serializable object) {
		
		String msgId = getId(object);
		CorrelationData correlationData = new CorrelationData(msgId);
		
		String routingKey = getRoutingKey(object);
		rabbitTemplate.convertAndSend(this.exchageName, routingKey, object, correlationData);
	}
	
	public String getId(Serializable objMsg){
		 if (objMsg instanceof CreditCardBill) {
			 CreditCardBill bill = (CreditCardBill) objMsg;
			 return bill.getPch();
		 }else if (objMsg instanceof FinancialProductsNotice){
			 FinancialProductsNotice notice = (FinancialProductsNotice) objMsg;
			 return notice.getUuid();
		 }
		 
		 // TODO else...
		 
		 String str = UUID.randomUUID().toString();
	     return str.replaceAll("-", "");
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


	public void setRabbitTemplate(RabbitTemplate rabbitTemplate) {
		this.rabbitTemplate = rabbitTemplate;
	}
	
	public void setMsgLogService(MsgLogService msgLogService) {
		this.msgLogService = msgLogService;
	}
	
	public Map<String, String> getClass2routkeyMap() {
		return class2routkeyMap;
	}
	
	public void setClass2routkeyMap(Map<String, String> class2routkeyMap) {
		this.class2routkeyMap = class2routkeyMap;
	}

	public void setMaxResendTimes(int maxResendTimes) {
		this.maxResendTimes = maxResendTimes;
	}
	
	public int getMaxResendTimes() {
		return maxResendTimes;
	}

	public String getRoutingKey(Object obj) {
		
		return this.getClass2routkeyMap().get(obj.getClass().getName());
	}

	@Override
	public boolean exceedMaxResendTimes(int currentTimes) {
		
		return currentTimes > this.maxResendTimes;
	}

}
