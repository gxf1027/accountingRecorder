package cn.gxf.spring.bill.receiver;

import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.ObjectMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.gxf.spring.bill.dao.MailDao;
import cn.gxf.spring.bill.mailsender.MailSenderService;
import cn.gxf.spring.quartz.job.model.CreditCardBill;



@Service
public class CreditCardBillMsgListener implements MessageListener {

	private static int count=1;
	
	@Autowired
	private MailSenderService mailSenderService;
	
	@Autowired
	private MailDao maiDao;
	
	public MailSenderService getMailSenderService() {
		return mailSenderService;
	}
	
	public void setMailSenderService(MailSenderService mailSenderService) {
		this.mailSenderService = mailSenderService;
	}
	
	@Transactional
	@Override
	public void onMessage(Message message) {
		
		if(message instanceof ObjectMessage){
			
			//maiDao.testXA();
			
			System.out.println("onMessage count: " + count);
			count++;
			
			ObjectMessage objectMsg =  (ObjectMessage)message;
			System.out.println("Object Message: " + objectMsg);
			CreditCardBill bill;
			try {
				//int i = 1/0; 
			} catch (Exception e) {
				e.printStackTrace();
				throw new RuntimeException("Error");  
			}
			
			try {
				bill = (CreditCardBill)objectMsg.getObject();
				System.out.println("bill: " + bill);
				this.mailSenderService.sendSimpleMailTxt(bill);
				
			} catch (Exception e) {
				
				e.printStackTrace();
				throw new RuntimeException("Error");  
			}
			
		}
	}

}
