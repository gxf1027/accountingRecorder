package cn.gxf.spring.bill.receiver;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.mail.MailSender;

import cn.gxf.spring.bill.mailsender.MailSenderService;

public class ReceiverRunner {
	public static void main(String[] args) {
		
		ApplicationContext appContext = new ClassPathXmlApplicationContext( "applicationContext*.xml");
		System.out.println("Spring Context startup...");
		
		
		/*MailSenderService mailSenderService = (MailSenderService) appContext.getBean("mailSenderService");
		mailSenderService.sendTest();*/
	}
}
