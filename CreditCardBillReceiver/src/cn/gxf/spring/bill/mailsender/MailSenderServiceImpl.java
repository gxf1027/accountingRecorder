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
							"	�˻����ƣ�"+ bill.getZh_mc() +"<br> " +
							"	�˵�����"+ bill.getSsqq() + "<br> " +
							"	�˵���ֹ��"+ bill.getSsqz() + "<br> " +
							"	<table border=\"1\" cellspacing=\"0\"> " +
							"		<thead> " +
							"           <tr> " +
							"			  <td width='150'>����ʱ��</td> " +
							"			  <td width='100'>���</td> " +
							"			  <td width='100'>����</td> " +
							"			  <td width='200'>��ע</td> " +
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
			helper.setFrom(mailSender.getUsername()); // ������
			helper.setTo(bill.getEmail()); //�ռ���
			helper.setSubject(bill.getZh_mc()+" "+ bill.getSsqq()+" ��  "+bill.getSsqz()+" �˵�");//����
			helper.setText(convertToHtml(bill), true);//����
	        mailSender.send(message);
	        System.out.println("�ʼ��������");
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	@Override
	public void senderBatch(List<CreditCardBill> bills) {
		
		if (null == bills || bills.size() == 0){
			System.out.println("û����Ҫ���͵��˵�");
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
			message.setSubject(bill.getZh_mc()+" "+ bill.getSsqq()+" ��  "+bill.getSsqz()+" �˵�");
			message.setFrom(mailSender.getUsername()); // ������
		    message.setTo(bill.getEmail()); //�ռ���

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
