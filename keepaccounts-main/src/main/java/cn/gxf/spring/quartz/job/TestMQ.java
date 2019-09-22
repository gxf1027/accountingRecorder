package cn.gxf.spring.quartz.job;

import java.util.Date;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;

/*
 * 
 * ≤‚ ‘MQ∑¢ÀÕ
 * 
 */
public class TestMQ {
	public static void main(String[] args) {
		ApplicationContext ctx = new ClassPathXmlApplicationContext(new String[]{"d-applicationContext-mqtest.xml"});
		JmsTemplate jmsTemplate = (JmsTemplate) ctx.getBean("jmsQueueTemplate");
		for (int i=0; i<20; i++){
			
			jmsTemplate.send("mailQueueFinaProducts", new MessageCreator() {
				
				@Override
				public Message createMessage(Session session) throws JMSException {
					String text = "A test for MessageCreator " + (new Date());
					Message message = session.createTextMessage(text);
					return message;
				}
			});
		}
	}
}
