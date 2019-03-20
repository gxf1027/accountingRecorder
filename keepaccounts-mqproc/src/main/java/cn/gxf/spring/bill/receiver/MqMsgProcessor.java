package cn.gxf.spring.bill.receiver;

import java.util.Date;

import javax.jms.Message;
import javax.jms.ObjectMessage;

import org.apache.cxf.frontend.ClientProxy;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.gxf.spring.bill.dao.CreditCardBillConsumingDao;
import cn.gxf.spring.bill.dao.MailDao;
import cn.gxf.spring.bill.mailsender.MailSenderService;
import cn.gxf.spring.bill.model.CreditCardBillConsumingInfo;
import cn.gxf.spring.cxf.AuthorizingInterceptor;
import cn.gxf.spring.quartz.job.endpoint.BillServiceEndpoint;
import cn.gxf.spring.quartz.job.model.CreditCardBill;
import cn.gxf.spring.quartz.job.model.FinancialProductsNotice;
import cn.gxf.spring.quartz.job.service.RpcService;

@Service
public class MqMsgProcessor implements ApplicationContextAware{
	private static int count=1;
	
	@Autowired
	private MailSenderService mailSenderService;
	
	@Autowired
	private MailDao maiDao;
	
	@Autowired
	private CreditCardBillConsumingDao creditCardConDao;
	
	private ApplicationContext appCtx;
	
	public void testProcessing(String info) throws InterruptedException{
		long thread_id = Thread.currentThread().getId();
		Thread.sleep(5000);
		System.out.println("thread_id: "+ thread_id+" info: "+ info);
	}
	
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
		
		// 判断是否已经接收过，如果已经接收过了就放弃
		long thread_id = Thread.currentThread().getId();
		int isExists = creditCardConDao.isPchExists(bill.getPch());
		if (isExists > 0){
			System.out.println("pch: "+bill.getPch()+" was already proceeded.");
			return;
		}
		
		CreditCardBillConsumingInfo ccbci = new CreditCardBillConsumingInfo();
		ccbci.setPch(bill.getPch());
		ccbci.setThreadId(String.valueOf(thread_id));
		ccbci.setRevTime(new Date());
		ccbci.setYxbz("Y");
		
		System.out.println("bill: " + bill);
		
		try {
			//int i = 1/0;
		} catch (Exception e) {
			
			e.printStackTrace();
			throw new RuntimeException();
		} 
		
		this.mailSenderService.sendSimpleMailThymeleaf(bill);
		
		this.setBillMailed(bill);
		
		// 保存发送的时间
		ccbci.setSendTime(new Date());
		creditCardConDao.saveConsumingInfo(ccbci);
		
		System.out.println("billProcessed");
		
	}
	
	public void financialProductsNoticeProcessing(FinancialProductsNotice notice){
		System.out.println("notice: " + notice);
		
		this.mailSenderService.sendSimpleMailThymeleaf(notice);
		
		this.setNoticeMailed(notice);
		System.out.println("noticeProcessed");
	}
	
	private void setBillMailed(CreditCardBill bill){
		
		BillServiceEndpoint billService =  (BillServiceEndpoint) appCtx.getBean("billService");
		
		AuthorizingInterceptor authorizingInterceptor = new AuthorizingInterceptor();
		// 模拟增加一个拦截器
//		authorizingInterceptor.setUserName("John");
//		authorizingInterceptor.setPassword("password");
//		org.apache.cxf.endpoint.Client cxfClient = ClientProxy.getClient(billService);
//		cxfClient.getOutInterceptors().add(authorizingInterceptor);
		
		billService.SetBillMailed(bill.getMxUuidList());
	}
	
	private void setNoticeMailed(FinancialProductsNotice notice){
		
		try {
			RpcService rpc = (RpcService) appCtx.getBean("rpcService");
			rpc.setFinanProductsNoticeMailed(notice.getUuid());
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

	@Override
	public void setApplicationContext(ApplicationContext arg0) throws BeansException {
		
		this.appCtx = arg0; 
	}
	
}
