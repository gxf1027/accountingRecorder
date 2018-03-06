package cn.gxf.spring.bill.receiver;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.ObjectMessage;

import cn.gxf.spring.quartz.job.model.CreditCardBill;




public class CreditCardBillMsgListener implements MessageListener {

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
			} catch (JMSException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
	}

}
