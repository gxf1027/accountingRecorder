package cn.gxf.spring.bill.mailsender;

public interface EmailSender {
	public static final String EMAIL_SIMPLE_TEMPLATE_BILL = "bill-format";
	public static final String EMAIL_SIMPLE_TEMPLATE_NOTICE = "notice-format";
	
	public int send(Object msgObj);
}
