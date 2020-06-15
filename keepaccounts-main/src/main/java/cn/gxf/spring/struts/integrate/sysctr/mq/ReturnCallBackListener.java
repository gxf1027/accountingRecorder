package cn.gxf.spring.struts.integrate.sysctr.mq;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.core.RabbitTemplate.ReturnCallback;
import org.springframework.stereotype.Service;

@Service
public class ReturnCallBackListener implements ReturnCallback{

	@Override
	public void returnedMessage(Message message, int replyCode, String replyText, String exchange, String routingKey) {
		
		//System.out.println("return--message:"+new String(message.getBody())+",replyCode:"+replyCode+",replyText:"+replyText+",exchange:"+exchange+",routingKey:"+routingKey);
		
		System.out.println("============returnedMessage==method=========");
		System.out.println("message: "+message);
        System.out.println("replyCode: "+replyCode);
        System.out.println("replyText: "+replyText);
        System.out.println("exchange: "+exchange);
        System.out.println("routingKey: "+routingKey);
	}

}
