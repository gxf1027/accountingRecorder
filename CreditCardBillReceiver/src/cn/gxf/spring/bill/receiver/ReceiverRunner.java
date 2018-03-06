package cn.gxf.spring.bill.receiver;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class ReceiverRunner {
	public static void main(String[] args) {
		
		ApplicationContext appContext = new ClassPathXmlApplicationContext( "applicationContext.xml");
		System.out.println("Spring Context startup...");
		
	}
}
