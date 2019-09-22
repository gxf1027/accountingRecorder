package cn.gxf.spring.struts.integrate.sysctr.mq;

import org.springframework.amqp.rabbit.core.RabbitTemplate.ConfirmCallback;
import org.springframework.amqp.rabbit.support.CorrelationData;
import org.springframework.stereotype.Service;

@Service
public class ConfirmCallBackListener implements ConfirmCallback{

	@Override
	public void confirm(CorrelationData correlationData, boolean ack, String cause) {
		// TODO Auto-generated method stub
		System.out.println("confirm--:correlationData:"+correlationData+",ack:"+ack+",cause:"+cause);  
	}

}
