package cn.gxf.spring.motan;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import cn.gxf.spring.motan.test.SayHi;
import cn.gxf.spring.quartz.job.model.CreditCard;
import cn.gxf.spring.quartz.job.service.AccountStatisticsService;
import cn.gxf.spring.quartz.job.service.CreditCardsBillService;
import cn.gxf.spring.quartz.job.service.FinancialProductsNoticeService;
import cn.gxf.spring.struts.integrate.util.AuxiliaryTools;
import cn.gxf.spring.struts2.integrate.model.FinancialProductDetail;

public class motanTest {
	public static void main(String[] args) {
		ApplicationContext ctx = new ClassPathXmlApplicationContext("classpath:applicationContext-motan.xml");
		SayHi  service = (SayHi) ctx.getBean("motanTestRpc");
		
		//processStat(ctx);
		//processCreditCardsBill(ctx);
		processFinProducts(ctx);
		
		/*for (int i=1; i<=11; i++){
			service.say("hi");
			try {
				Thread.sleep(520);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}*/
		
	}
	
	
	public static int processStat(ApplicationContext ctx) {
		
		AccountStatisticsService statisticsService = (AccountStatisticsService) ctx.getBean("statService");
		
		// ��������û�
		Map<String, String> users = statisticsService.getUsersIdNames();
		
		for (String userid : users.keySet()){
			
			statisticsService.updateStatThisMonthByUserid(userid, users.get(userid));
			
		}
		
		return 1;
	}
	
	public static int processCreditCardsBill(ApplicationContext ctx){
		
		CreditCardsBillService creditCardBillService = (CreditCardsBillService) ctx.getBean("creditCardBillService");
		
		Date jyqz = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd"); // ��������Ϊ���� 
		 
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(jyqz); // ����Ϊ��ǰʱ��
        calendar.set(Calendar.MONTH, calendar.get(Calendar.MONTH) - 1); // ����Ϊ��һ����
        Date jyqq = calendar.getTime(); // ������ֹΪ����ͬһ��
 
        System.out.println("�����˵�ʱ�䣺 " + sdf.format(jyqz) + "��" + sdf.format(jyqq));
        
		// 1. ��ȡ��Ҫ������˻�����
		//List<String> zzdmList = creditCardBillDao.getCreditCardInZDR(Integer.valueOf(sdf.format(jyqz).split("-")[2]));
        List<CreditCard> creditCards = creditCardBillService.getCreditCardInZDR(Integer.valueOf(sdf.format(jyqz).split("-")[2]));

        if (creditCards.size() == 0 ){
        	System.out.println("����û�����ÿ��˵���Ҫ����");
			return 0;
        }
        
        // ��������û����˻��б� ���ձ�
        Map<String, List<String>> userZzdmMap = new HashMap<>();
        for (CreditCard card : creditCards){
        	List<String> zzdmList = userZzdmMap.get(card.getUser_id());
        	if (null == zzdmList){
        		zzdmList = new ArrayList<>();
        		zzdmList.add(card.getZh_dm());
        		userZzdmMap.put(card.getUser_id(), zzdmList);
        	}else{
        		userZzdmMap.get(card.getUser_id()).add(card.getZh_dm());
        	}
        }

		
        // ���û����д�������getCreditCardTranscationRecordInZDQ�������ݻ�̫��
        for (String userId : userZzdmMap.keySet()){
        	List<String> zzdmList = userZzdmMap.get(userId);
        	if (zzdmList == null || zzdmList.size() == 0){
    			//System.out.println("����û�����ÿ��˵���Ҫ����");
    			continue;
    		}
        	
        	// ÿ��ѭ�����������񣬷����������ʱ
        	creditCardBillService.processBill(zzdmList, jyqq, jyqz);
        }
        
		return 1;
	}
	
	
	public static int processFinProducts(ApplicationContext ctx){
		
		FinancialProductsNoticeService financialProductsNoticeService = (FinancialProductsNoticeService) ctx.getBean("productsNoticeService");
		
		Date current = new Date();
		Date date_from = AuxiliaryTools.getMonthFirstDate(current);
		Date date_to = AuxiliaryTools.getMonthLastDate(current);
		// ��ȡ�����û����½�Ҫ���ڵ���Ʋ�Ʒ
		List<FinancialProductDetail> finProducts = financialProductsNoticeService.queryFinancialProductDetailByEndDate(date_from, date_to);
		
		if (finProducts.size() == 0){
			return 0;
		}
		
		// ÿ���û�--��Ʋ�Ʒ�б�
		Map<Integer, List<FinancialProductDetail>> userProductMaps = new HashMap<Integer, List<FinancialProductDetail>>();
		for (FinancialProductDetail finanDetail : finProducts){
			List<FinancialProductDetail> finanDetailList = userProductMaps.get(finanDetail.getUser_id());
			if (null == finanDetailList){
				finanDetailList = new ArrayList<FinancialProductDetail>();
				finanDetailList.add(finanDetail);
				userProductMaps.put(finanDetail.getUser_id(), finanDetailList);
			}else{
				userProductMaps.get(finanDetail.getUser_id()).add(finanDetail);
			}
		}
		
		// ������ֽ�Ϊÿ���û�һ������
		for (Integer user_id : userProductMaps.keySet()){
			// ÿ���û��ֿ�����
			financialProductsNoticeService.processNotice(userProductMaps.get(user_id), date_from, date_to);
		}
		
		return 1;
	}
}
