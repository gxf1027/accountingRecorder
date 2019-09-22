package cn.gxf.spring.struts.integrate.sysctr.mq;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class mqtest {
	public static void main(String[] args) {
		ApplicationContext ctx = new ClassPathXmlApplicationContext(new String[]{"d-applicationContext-rabbit.xml"});
		AmqpTemplate rabbitTemplate = (AmqpTemplate) ctx.getBean("rabbitTemplate");
		try {
			rabbitTemplate.convertAndSend("direct.exchange", "creditcards", "hello77");
		} catch (Exception e) {
			System.out.println("bad");
		}
		
		System.out.println("hello");
	}
}
