package cn.gxf.spring.bill.mailsender;

import java.text.SimpleDateFormat;
import java.util.List;

import javax.mail.internet.MimeMessage;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;

import cn.gxf.spring.quartz.job.model.CreditCardBill;
import cn.gxf.spring.quartz.job.model.CreditCardRecordSimplified;

public class MailSenderServiceImpl implements MailSenderService {

	private JavaMailSenderImpl mailSender;
	
	public void setMailSender(JavaMailSenderImpl mailSender) {
		this.mailSender = mailSender;
	}
	
	public JavaMailSenderImpl getMailSender() {
		return mailSender;
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
	public void senderOne(CreditCardBill bill){
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
			this.senderOne(bill);
		}
	}

}
