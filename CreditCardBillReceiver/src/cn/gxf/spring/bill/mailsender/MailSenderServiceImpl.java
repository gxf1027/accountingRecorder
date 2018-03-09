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
	public void senderOne(CreditCardBill bill){
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
			this.senderOne(bill);
		}
	}

}
