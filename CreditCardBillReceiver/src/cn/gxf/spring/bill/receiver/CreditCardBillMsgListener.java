package cn.gxf.spring.bill.receiver;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.ObjectMessage;

import cn.gxf.spring.bill.mailsender.MailSenderService;
import cn.gxf.spring.quartz.job.model.CreditCardBill;




public class CreditCardBillMsgListener implements MessageListener {

	private MailSenderService mailSenderService;
	
	public MailSenderService getMailSenderService() {
		return mailSenderService;
	}
	
	public void setMailSenderService(MailSenderService mailSenderService) {
		this.mailSenderService = mailSenderService;
	}
	
	@Override
	public void onMessage(Message message) {
		
		if(message instanceof ObjectMessage){
			System.out.println("onMessage...");
			
			ObjectMessage objectMsg =  (ObjectMessage)message;
			System.out.println("Object Message: " + objectMsg);
			CreditCardBill bill;
			try {
				bill = (CreditCardBill)objectMsg.getObject();
				System.out.println("bill: " + bill);
				this.mailSenderService.senderOne(bill);
			} catch (JMSException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
	}

}
