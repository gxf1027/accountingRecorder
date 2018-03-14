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
	       ��POJO��Ϊ������Ϣ���࣬���ܵ���Ϣ��Adapterֱ��ת��ΪCreditCardBill����
	   MessageListenerAdapter��ѽ��յ�����Ϣ������ת����
       TextMessageת��ΪString����
       BytesMessageת��Ϊbyte���飻
       MapMessageת��ΪMap����
       ObjectMessageת��Ϊ��Ӧ��Serializable����
                  ���ԣ�billProcessing�Ĳ���������Message����
	 */
	
	public void billProcessing(CreditCardBill bill){
		//maiDao.testXA(); // ����XA
		
		System.out.println("onMessage count: " + count);
		count++;
		
		System.out.println("bill: " + bill);
		
		try {
			//int i = 1/0;
		} catch (Exception e) {
			
			e.printStackTrace();
			throw new RuntimeException();
		} 
		
		this.mailSenderService.sendSimpleMailTxt(bill);
		
		
		
	}
}
