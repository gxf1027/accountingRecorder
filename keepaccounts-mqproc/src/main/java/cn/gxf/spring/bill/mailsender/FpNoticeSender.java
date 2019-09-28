package cn.gxf.spring.bill.mailsender;

import javax.mail.internet.MimeMessage;

import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring4.SpringTemplateEngine;

import cn.gxf.spring.quartz.job.model.FinancialProductsNotice;

public class FpNoticeSender implements EmailSender{

	private JavaMailSenderImpl mailSender;
    private SpringTemplateEngine templateEngine;
    
    public FpNoticeSender() {
		super();
		this.mailSender = null;
		this.templateEngine = null;
	}
    
	public FpNoticeSender(JavaMailSenderImpl mailSender, SpringTemplateEngine templateEngine) {
		super();
		this.mailSender = mailSender;
		this.templateEngine = templateEngine;
	}


	@Override
	public int send(Object msgObj) {
		
		sendSimpleMailThymeleaf((FinancialProductsNotice)msgObj);
		
		return 1;
	}

	
	private void sendSimpleMailThymeleaf(FinancialProductsNotice notice) {
		
		// Prepare the evaluation context
        final Context ctx = new Context();
        ctx.setVariable("notice", notice);
        // Prepare message using a Spring helper
        final MimeMessage mimeMessage = this.mailSender.createMimeMessage();
        final MimeMessageHelper message = new MimeMessageHelper(mimeMessage, "UTF-8");
        try {
			message.setSubject(notice.getSsqq()+" 至  "+notice.getSsqz()+" 理财产品到期提醒");
			message.setFrom(mailSender.getUsername()); // 发件人
			if (null == notice.getEmail()){
				System.out.println("userid: " + notice.getUser_id() + "username: " + notice.getUsername() + " email addr is null");
				return;
			}
		    message.setTo(notice.getEmail()); //收件人

		    // Create the HTML body using Thymeleaf
		    final String htmlContent = this.templateEngine.process(EMAIL_SIMPLE_TEMPLATE_NOTICE, ctx);
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
}
