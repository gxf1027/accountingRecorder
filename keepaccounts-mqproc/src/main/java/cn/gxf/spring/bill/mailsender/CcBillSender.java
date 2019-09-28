package cn.gxf.spring.bill.mailsender;

import java.util.Date;

import javax.mail.internet.MimeMessage;

import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring4.SpringTemplateEngine;

import cn.gxf.spring.bill.dao.CreditCardBillConsumingDao;
import cn.gxf.spring.bill.model.CreditCardBillConsumingInfo;
import cn.gxf.spring.quartz.job.model.CreditCardBill;

public class CcBillSender implements EmailSender{

	private JavaMailSenderImpl mailSender;
    private SpringTemplateEngine templateEngine;
    private CreditCardBillConsumingDao creditCardBillConsumingDao;

	@Override
	public int send(Object msgObj) {
		
		CreditCardBill bill = (CreditCardBill)msgObj;
		
		// �ж��Ƿ��Ѿ����չ�������Ѿ����չ��˾ͷ���
		long thread_id = Thread.currentThread().getId();
		int isExists = creditCardBillConsumingDao.isPchExists(bill.getPch());
		if (isExists > 0){
			System.out.println("pch: "+bill.getPch()+" was already proceeded.");
			return 0;
		}
		
		CreditCardBillConsumingInfo ccbci = new CreditCardBillConsumingInfo();
		ccbci.setPch(bill.getPch());
		ccbci.setThreadId(String.valueOf(thread_id));
		ccbci.setRevTime(new Date());
		ccbci.setYxbz("Y");
				
		// �����ʼ�
		sendSimpleMailThymeleaf(bill);
		
		
		// ���淢�͵�ʱ��
		ccbci.setSendTime(new Date());
		creditCardBillConsumingDao.saveConsumingInfo(ccbci);
		
		System.out.println("billProcessed");
		return 1;
	}

	
	private void sendSimpleMailThymeleaf(CreditCardBill bill){

        // Prepare the evaluation context
        final Context ctx = new Context();
        ctx.setVariable("bill", bill);
        // Prepare message using a Spring helper
        final MimeMessage mimeMessage = this.mailSender.createMimeMessage();
        final MimeMessageHelper message = new MimeMessageHelper(mimeMessage, "UTF-8");
        try {
			message.setSubject(bill.getZh_mc()+" "+ bill.getSsqq()+" ��  "+bill.getSsqz()+" �˵�");
			message.setFrom(mailSender.getUsername()); // ������
		    message.setTo(bill.getEmail()); //�ռ���

		    // Create the HTML body using Thymeleaf
		    final String htmlContent = this.templateEngine.process(EMAIL_SIMPLE_TEMPLATE_BILL, ctx);
		    System.out.println("mail content: " + htmlContent);
		    message.setText(htmlContent, true /* isHtml */);
		} catch (Exception e) {
			System.out.println(e);
			e.printStackTrace();
			throw new RuntimeException();
		}
       

        // Send email
        this.mailSender.send(mimeMessage);
    }
	
	public void setMailSender(JavaMailSenderImpl mailSender) {
		this.mailSender = mailSender;
	}
	
	public void setTemplateEngine(SpringTemplateEngine templateEngine) {
		this.templateEngine = templateEngine;
	}
	
	public void setCreditCardBillConsumingDao(CreditCardBillConsumingDao creditCardBillConsumingDao) {
		this.creditCardBillConsumingDao = creditCardBillConsumingDao;
	}
}
