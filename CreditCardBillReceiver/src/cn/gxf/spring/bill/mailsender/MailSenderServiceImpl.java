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

public class MailSenderServiceImpl implements MailSenderService, ApplicationContextAware {

	private ApplicationContext applicationContext;
	private static final String EMAIL_SIMPLE_TEMPLATE_NAME = "bill-format.html";
	
	private JavaMailSenderImpl mailSender;
	
    private TemplateEngine htmlTemplateEngine;
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
	public void senderBatch(List<CreditCardBill> bills) {
		
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
    public void sendSimpleMailThymeleaf (CreditCardBill bill){

    	
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
		    //final String htmlContent = this.templateEngine.process(EMAIL_SIMPLE_TEMPLATE_NAME, ctx);
		    String htmlContent = this.templateEngine().process("E:\\javaxf\\CreditCardBillReceiver\\src\\NewFile.html", ctx);
		    message.setText(htmlContent, true /* isHtml */);
		} catch (Exception e) {
			System.out.println(e);
			e.printStackTrace();
			throw new RuntimeException();
		}
       

        // Send email
        this.mailSender.send(mimeMessage);
    }
    
    private ITemplateResolver templateResolver() {
        SpringResourceTemplateResolver resolver = new SpringResourceTemplateResolver();
        resolver.setApplicationContext(applicationContext);
        resolver.setPrefix("/");
        resolver.setTemplateMode(TemplateMode.HTML);
        return resolver;
      }
    
    public TemplateEngine templateEngine() {
        SpringTemplateEngine engine = new SpringTemplateEngine();
        engine.setEnableSpringELCompiler(true);
        engine.setTemplateResolver(templateResolver());
        return engine;
      }

	@Override
	public void setApplicationContext(ApplicationContext arg0) throws BeansException {
		
		this.applicationContext = arg0; 
	}

}
