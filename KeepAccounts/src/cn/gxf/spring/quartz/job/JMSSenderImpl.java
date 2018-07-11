package cn.gxf.spring.quartz.job;


import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.ObjectMessage;
import javax.jms.Session;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.stereotype.Component;

import cn.gxf.spring.quartz.job.model.CreditCardBill;

@Component
public class JMSSenderImpl implements JMSSender{

	@Autowired
	@Qualifier("jmsQueueTemplate")
	private JmsTemplate jmsTemplate; 
	
	@Override
	public void send(CreditCardBill bill) {
		MessageCreator messageCreator = new MessageCreator(){

			public Message createMessage(Session session) throws JMSException {
				ObjectMessage objMessage = session.createObjectMessage(bill);
			
				System.out.println("·¢ËÍÏûÏ¢£º"+ objMessage.toString());
				return objMessage;
			}
		};

		jmsTemplate.send(messageCreator);
		
	}
	
}
