package cn.gxf.spring.bill.receiver;

import javax.jms.Message;
import javax.jms.ObjectMessage;

import org.apache.cxf.frontend.ClientProxy;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.gxf.spring.bill.dao.MailDao;
import cn.gxf.spring.bill.mailsender.MailSenderService;
import cn.gxf.spring.cxf.AuthorizingInterceptor;
import cn.gxf.spring.quartz.job.endpoint.BillServiceEndpoint;
import cn.gxf.spring.quartz.job.model.CreditCardBill;
import cn.gxf.spring.quartz.job.model.FinancialProductsNotice;

@Service
public class MqMsgProcessor implements ApplicationContextAware{
	private static int count=1;
	
	@Autowired
	private MailSenderService mailSenderService;
	
	@Autowired
	private MailDao maiDao;
	
	private ApplicationContext appCtx;
	
	
	
	/*
	       将POJO作为处理消息的类，接受的消息由Adapter直接转化为CreditCardBill类型
	   MessageListenerAdapter会把接收到的消息做如下转换：
       TextMessage转换为String对象；
       BytesMessage转换为byte数组；
       MapMessage转换为Map对象；
       ObjectMessage转换为对应的Serializable对象。
                  所以，billProcessing的参数不能是Message类型
	 */
	
	public void billProcessing(CreditCardBill bill){
		//maiDao.testXA(); // 测试XA
		
		System.out.println("onMessage count: " + count);
		count++;
		
		System.out.println("bill: " + bill);
		
		try {
			//int i = 1/0;
		} catch (Exception e) {
			
			e.printStackTrace();
			throw new RuntimeException();
		} 
		
		//this.mailSenderService.sendSimpleMailTxt(bill);
		this.mailSenderService.sendSimpleMailThymeleaf(bill);
		
		this.setBillMailed(bill);
		
		System.out.println("billProcessed");
		
	}
	
	public void financialProductsNoticeProcessing(FinancialProductsNotice notice){
		System.out.println("financialProductsNoticeProcessing: " + notice);
		
		System.out.println("noticeProcessed");
	}
	
	private void setBillMailed(CreditCardBill bill){
		
		BillServiceEndpoint billService =  (BillServiceEndpoint) appCtx.getBean("billService");
		
		AuthorizingInterceptor authorizingInterceptor = new AuthorizingInterceptor();
		authorizingInterceptor.setUserName("John");
		authorizingInterceptor.setPassword("password");
		org.apache.cxf.endpoint.Client cxfClient = ClientProxy.getClient(billService);
		cxfClient.getOutInterceptors().add(authorizingInterceptor);
		
		billService.SetBillMailed(bill.getMxUuidList());
	}

	@Override
	public void setApplicationContext(ApplicationContext arg0) throws BeansException {
		
		this.appCtx = arg0; 
	}
	
}
