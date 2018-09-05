package cn.gxf.spring.motan;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import cn.gxf.spring.motan.test.SayHi;

public class motanTest {
	public static void main(String[] args) {
		ApplicationContext ctx = new ClassPathXmlApplicationContext("classpath:applicationContext-motan.xml");
		SayHi  service = (SayHi) ctx.getBean("motanTestRpc");
		service.say("hi");
	}
}
