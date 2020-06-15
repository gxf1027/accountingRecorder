package cn.gxf.spring.struts.integrate.sysctr.audit;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.core.RabbitTemplate;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;

public class AuditProcess implements Runnable{

	private Logger logger = LogManager.getLogger();
	
	private RabbitTemplate rabbitTemplate;
	private AuditInfo auditInfo;
	
	
	public AuditProcess(RabbitTemplate rabbitTemplate, AuditInfo autditInfo) {
		super();
		this.rabbitTemplate = rabbitTemplate;
		this.auditInfo = autditInfo;
	}


	@Override
	public void run() {
		
		try {
			String strMsg = JSON.toJSONString(auditInfo, SerializerFeature.WriteClassName);
			
//			MessageProperties messageProperties = new MessageProperties();
//	        messageProperties.getHeaders().put("desc", "audit msg");
//	        messageProperties.getHeaders().put("type", "json");
//	        Message message = new Message(strMsg.getBytes(), messageProperties);
	        
			rabbitTemplate.convertAndSend("auditmsg", strMsg); // convertAndSend("auditmsg",auditInfo);
		} catch (Exception e) {
			logger.warn("audit processing has exceptions with user :[{}], eception:[{}]", auditInfo.getUser_id(), e.getMessage());
		}
		
	}



	public RabbitTemplate getRabbitTemplate() {
		return rabbitTemplate;
	}



	public void setRabbitTemplate(RabbitTemplate rabbitTemplate) {
		this.rabbitTemplate = rabbitTemplate;
	}



	public AuditInfo getAutditInfo() {
		return auditInfo;
	}



	public void setAutditInfo(AuditInfo autditInfo) {
		this.auditInfo = autditInfo;
	}
	
	
	
}