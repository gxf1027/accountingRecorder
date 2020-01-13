package cn.gxf.spring.struts.integrate.sysctr.mq;

import org.springframework.amqp.rabbit.core.RabbitTemplate.ConfirmCallback;
import org.springframework.amqp.rabbit.support.CorrelationData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.gxf.spring.struts2.integrate.service.MsgLogService;

@Service
public class ConfirmCallBackListener implements ConfirmCallback{
	
	@Autowired
	private MsgLogService msgLogService;

	@Override
	public void confirm(CorrelationData correlationData, boolean ack, String cause) {
		// TODO Auto-generated method stub
		System.out.println("confirm--:correlationData:"+correlationData+",ack:"+ack+",cause:"+cause);  
		
		try {
			if (ack){
				msgLogService.updateStatus(correlationData.getId(), Constant.MsgLogStatus.DELIVER_SUCCESS);
			}else{
				System.out.println("���͵�exchangeʧ��");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

}
