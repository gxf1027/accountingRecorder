package cn.gxf.spring.bill.receiver;

import javax.jms.Message;
import javax.jms.ObjectMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.gxf.spring.bill.dao.MailDao;
import cn.gxf.spring.bill.mailsender.MailSenderService;
import cn.gxf.spring.quartz.job.model.CreditCardBill;

@Service
public class BillProcessor {
	private static int count=1;
	
	@Autowired
	private MailSenderService mailSenderService;
	
	@Autowired
	private MailDao maiDao;
	
	public BillProcessor(){
		System.out.println("BillProcessor constructor...");
	}
	
	/*
	       将POJO作为处理消息的类，接受的消息由Adapter直接转化为CreditCardBill类型
	   MessageListenerAdapter会把接收到的消息做如下转换：
       TextMessage转换为String对象；
       BytesMessage转换为byte数组；
       MapMessage转换为Map对象；
       ObjectMessage转换为对应的Serializable对象。
                  所以，billProcessing的参数不能是Message类型
	 */
	
	public void billProcessing(CreditCardBill bill){
		//maiDao.testXA(); // 测试XA
		
		System.out.println("onMessage count: " + count);
		count++;
		
		System.out.println("bill: " + bill);
		
		try {
			//int i = 1/0;
		} catch (Exception e) {
			
			e.printStackTrace();
			throw new RuntimeException();
		} 
		
		//this.mailSenderService.sendSimpleMailTxt(bill);
		this.mailSenderService.sendSimpleMailThymeleaf(bill);
		
		
	}
	
}
