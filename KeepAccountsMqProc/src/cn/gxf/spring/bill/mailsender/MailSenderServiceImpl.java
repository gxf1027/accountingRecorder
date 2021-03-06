package cn.gxf.spring.bill.mailsender;

import java.text.SimpleDateFormat;
import java.util.List;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring4.SpringTemplateEngine;
import org.thymeleaf.spring4.templateresolver.SpringResourceTemplateResolver;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.ITemplateResolver;

import cn.gxf.spring.quartz.job.model.CreditCardBill;
import cn.gxf.spring.quartz.job.model.CreditCardRecordSimplified;
import cn.gxf.spring.quartz.job.model.FinancialProductsNotice;

public class MailSenderServiceImpl implements MailSenderService {

	private ApplicationContext applicationContext;
	private static final String EMAIL_SIMPLE_TEMPLATE_BILL = "bill-format";
	private static final String EMAIL_SIMPLE_TEMPLATE_NOTICE = "notice-format";
	
	private JavaMailSenderImpl mailSender;
    private SpringTemplateEngine templateEngine;
    

	public void setMailSender(JavaMailSenderImpl mailSender) {
		this.mailSender = mailSender;
	}
	
	public JavaMailSenderImpl getMailSender() {
		return mailSender;
	}
	
	public SpringTemplateEngine getTemplateEngine() {
		return templateEngine;
	}
	
	public void setTemplateEngine(SpringTemplateEngine templateEngine) {
		this.templateEngine = templateEngine;
	}
	
	private String convertToHtml(CreditCardBill bill){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String htmltxt = "<html> " +
							"<head> " +
							"<meta charset=\"UTF-8\"> " +
							"<title>Insert title here</title> " +
							"</head> " +
							"<body> " +
							"	账户名称："+ bill.getZh_mc() +"<br> " +
							"	账单日起："+ bill.getSsqq() + "<br> " +
							"	账单日止："+ bill.getSsqz() + "<br> " +
							"	<table border=\"1\" cellspacing=\"0\"> " +
							"		<thead> " +
							"           <tr> " +
							"			  <td width='150'>交易时间</td> " +
							"			  <td width='100'>金额</td> " +
							"			  <td width='100'>类型</td> " +
							"			  <td width='200'>备注</td> " +
							"           </tr> " +
							"		</thead> " +
							"		<tbody>";
		for (CreditCardRecordSimplified rec : bill.getCctrList()){
			htmltxt +=      "           <tr> ";
			htmltxt +=      "              <td>" + sdf.format(rec.getJysj()) + "</td>" +
							"              <td>" + rec.getJe() + "</td>" +
							"              <td>" + rec.getJylx() + "</td>" +
							"              <td>" + rec.getBz() + "</td>";
			htmltxt +=      "           </tr> ";
		}
		
		htmltxt +=       	"       </tbody> "+
							"   </table> " +
							"</body> " +
							"</html>";
		return htmltxt;
	}
	
	@Override
	public void sendSimpleMailTxt(CreditCardBill bill){
		try {
			MimeMessage message = mailSender.createMimeMessage();
			MimeMessageHelper helper = new MimeMessageHelper(message, true);
			helper.setFrom(mailSender.getUsername()); // 发件人
			helper.setTo(bill.getEmail()); //收件人
			helper.setSubject(bill.getZh_mc()+" "+ bill.getSsqq()+" 至  "+bill.getSsqz()+" 账单");//主题
			helper.setText(convertToHtml(bill), true);//正文
	        mailSender.send(message);
	        System.out.println("邮件发送完毕");
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	@Override
	public void sendBatch(List<CreditCardBill> bills) {
		
		if (null == bills || bills.size() == 0){
			System.out.println("没有需要发送的账单");
			return;
		}
		
		for (CreditCardBill bill :bills){
			//this.senderOne(bill);
			this.sendSimpleMailThymeleaf(bill);
		}
	}
	
	
	
	
	/* 
     * Send HTML mail (simple) 
     */
    public void sendSimpleMailThymeleaf(CreditCardBill bill){

    	
        // Prepare the evaluation context
        final Context ctx = new Context();
        ctx.setVariable("bill", bill);
        // Prepare message using a Spring helper
        final MimeMessage mimeMessage = this.mailSender.createMimeMessage();
        final MimeMessageHelper message = new MimeMessageHelper(mimeMessage, "UTF-8");
        try {
			message.setSubject(bill.getZh_mc()+" "+ bill.getSsqq()+" 至  "+bill.getSsqz()+" 账单");
			message.setFrom(mailSender.getUsername()); // 发件人
		    message.setTo(bill.getEmail()); //收件人

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
    

	@Override
	public void sendSimpleMailThymeleaf(FinancialProductsNotice notice) {
		
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

	@Override
	public void sendTest() {
		
		// Prepare the evaluation context
        final Context ctx = new Context();
        ctx.setVariable("message", "HAHA");
        // Prepare message using a Spring helper
        final MimeMessage mimeMessage = this.mailSender.createMimeMessage();
        final MimeMessageHelper message = new MimeMessageHelper(mimeMessage, "UTF-8");
        try {
			message.setSubject("11111");
			message.setFrom("gxf"); // 发件人
		    message.setTo("gxf1027@126.com"); //收件人

		    System.out.println("before process...");
		    // Create the HTML body using Thymeleaf
		    final String htmlContent = this.templateEngine.process("NewFile", ctx);

		    System.out.println("html: " + htmlContent);
		    message.setText(htmlContent, true /* isHtml */);
		    System.out.println("after process...");
		} catch (Exception e) {
			System.out.println(e);
			e.printStackTrace();
			throw new RuntimeException();
		}
       

        // Send email
        //this.mailSender.send(mimeMessage);
	}

}
