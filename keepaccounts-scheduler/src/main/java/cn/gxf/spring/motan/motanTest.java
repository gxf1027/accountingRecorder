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

import com.weibo.api.motan.rpc.Future;
import com.weibo.api.motan.rpc.FutureListener;
import com.weibo.api.motan.rpc.ResponseFuture;

import cn.gxf.spring.motan.test.SayHi;
import cn.gxf.spring.quartz.job.model.CreditCard;
import cn.gxf.spring.quartz.job.service.AccountStatisticsService;
import cn.gxf.spring.quartz.job.service.AccountStatisticsServiceAsync;
import cn.gxf.spring.quartz.job.service.CreditCardsBillService;
import cn.gxf.spring.quartz.job.service.FinancialProductsNoticeService;
import cn.gxf.spring.quartz.job.service.FinancialProductsNoticeServiceAsync;
import cn.gxf.spring.quartz.job.service.UserRecoveryService;
import cn.gxf.spring.struts.integrate.util.AuxiliaryTools;
import cn.gxf.spring.struts2.integrate.model.FinancialProductDetail;

public class motanTest {
	public static void main(String[] args) {
		ApplicationContext ctx = new ClassPathXmlApplicationContext("classpath:applicationContext-motan.xml");
		SayHi  service = (SayHi) ctx.getBean("motanTestRpc");
		
		//processUserRecovery(ctx);
		//processStat(ctx);
		processStatSync(ctx);
		//processCreditCardsBill(ctx);
		//processFinProducts(ctx);
		
//		for (int i=1; i<=11; i++){
//			service.say("hi");
//			try {
//				Thread.sleep(520);
//			} catch (InterruptedException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//		}
		
	}
	
	
	public static int processStat(ApplicationContext ctx) {
		
		AccountStatisticsServiceAsync statisticsService = (AccountStatisticsServiceAsync) ctx.getBean("statServiceAsync");
		
		long tm_start = System.currentTimeMillis();
		int usersToProcessNum = statisticsService.getUsersNumToProcessing();
		System.out.println("time cost statisticsService.getUsersNumToProcessing:" + (System.currentTimeMillis()-tm_start));
		System.out.println("usersToProcessNum: " + usersToProcessNum);
		
		if (0 == usersToProcessNum){
			return 0;
		}
		
		int patchSize = 200;
		int start = 0;
		while (true){
			
			// 获取待处理的用户（并非全部用户）
			Map<String, String> users = statisticsService.getUsersIdNamePairToProcessByLimit(start, patchSize);
			if (users.size() == 0){
				break;
			}
			
			Map<ResponseFuture, String> rvMap = new HashMap<ResponseFuture, String>();
			// async with listener
		    FutureListener listener = new FutureListener() {
		        @Override
		        public void operationComplete(Future future) throws Exception {
		            System.out.println("async call, user " +
		            		rvMap.get(future) + ": "+ (future.isSuccess() ? "sucess! value:" + future.getValue() : "fail! exception:"
		                            + future.getException().getMessage()));
		            if (future.isSuccess() == false){
		            	future.getException().printStackTrace();
		            }
		        }
		    };
		    
			for (String userid : users.keySet()){
				
				//statisticsService.updateStatThisMonthByUserid(userid, users.get(userid));
				// 异步方法
				ResponseFuture future =  statisticsService.updateStatThisMonthByUseridAsync(userid, users.get(userid));
				rvMap.put(future, users.get(userid));
				future.addListener(listener);
				
				try {
					Thread.sleep(200);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			
			start += patchSize;
			
			if (start >= usersToProcessNum){
				break;
			}
			
		}
		
		return 1;
	}
	
	// 同步
	public static int processStatSync(ApplicationContext ctx) {
		
		AccountStatisticsServiceAsync statisticsService = (AccountStatisticsServiceAsync) ctx.getBean("statServiceAsync");
		
		long tm_start = System.currentTimeMillis();
		int usersToProcessNum = statisticsService.getUsersNumToProcessing();
		System.out.println("time cost statisticsService.getUsersNumToProcessing:" + (System.currentTimeMillis()-tm_start));
		System.out.println("usersToProcessNum: " + usersToProcessNum);
		
		if (0 == usersToProcessNum){
			return 0;
		}
		
		int patchSize = 200;
		int start = 0;
		while (true){
			
			
			Map<String, String> users = statisticsService.getUsersIdNamePairToProcessByLimit(start, patchSize);
			if (users.size() == 0){
				break;
			}
		    
			for (String userid : users.keySet()){
				
				statisticsService.updateStatThisMonthByUserid(userid, users.get(userid));
				
				try {
					Thread.sleep(200);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			
			start += patchSize;
			
			if (start >= 200/*usersToProcessNum*/){
				break;
			}
			
		}
		
		return 1;
	}
	
	public static int processCreditCardsBill(ApplicationContext ctx){
		
		CreditCardsBillService creditCardBillService = (CreditCardsBillService) ctx.getBean("creditCardBillService");
		
		Date jyqz = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd"); // 交易日起为本日 
		 
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(jyqz); // 设置为当前时间
        calendar.set(Calendar.MONTH, calendar.get(Calendar.MONTH) - 1); // 设置为上一个月
        Date jyqq = calendar.getTime(); // 交易日止为上月同一天
 
        System.out.println("本期账单时间： " + sdf.format(jyqz) + "至" + sdf.format(jyqq));
        
		// 1. 获取需要处理的账户代码
		//List<String> zzdmList = creditCardBillDao.getCreditCardInZDR(Integer.valueOf(sdf.format(jyqz).split("-")[2]));
        long start = System.currentTimeMillis();
        List<CreditCard> creditCards = creditCardBillService.getCreditCardInZDR(Integer.valueOf(sdf.format(jyqz).split("-")[2]));
        System.out.println("creditCardBillService.getCreditCardInZDR:" + (System.currentTimeMillis()-start));
        
        if (creditCards.size() == 0 ){
        	System.out.println("今天没有信用卡账单需要处理");
			return 0;
        }
        
        // 待处理的用户—账户列表 对照表
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

		
        // 分用户进行处理，否则getCreditCardTranscationRecordInZDQ返回数据会太多
        for (String userId : userZzdmMap.keySet()){
        	List<String> zzdmList = userZzdmMap.get(userId);
        	if (zzdmList == null || zzdmList.size() == 0){
    			//System.out.println("今天没有信用卡账单需要处理");
    			continue;
    		}
        	
        	// 每个循环单独的事务，否则可能事务超时
        	creditCardBillService.processBill(zzdmList, jyqq, jyqz);
        }
        
		return 1;
	}
	
	
	public static int processFinProducts(ApplicationContext ctx){
		
		FinancialProductsNoticeServiceAsync financialProductsNoticeService = (FinancialProductsNoticeServiceAsync) ctx.getBean("productsNoticeServiceAsync");
		
		Date current = new Date();
		Date date_from = AuxiliaryTools.getMonthFirstDate(current);
		Date date_to = AuxiliaryTools.getMonthLastDate(current);
		// 获取所有用户本月将要到期的理财产品
		List<FinancialProductDetail> finProducts = financialProductsNoticeService.queryFinancialProductDetailByEndDate(date_from, date_to);
		
		if (finProducts.size() == 0){
			return 0;
		}
		
		// 每个用户--理财产品列表
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
		
		Map<ResponseFuture, Integer> rvMap = new HashMap<ResponseFuture, Integer>();
		// async with listener
	    FutureListener listener = new FutureListener() {
	        @Override
	        public void operationComplete(Future future) throws Exception {
	            System.out.println("async call, user_id " +
	            		rvMap.get(future) + ": "+ (future.isSuccess() ? "sucess! value:" + future.getValue() : "fail! exception:"
	                            + future.getException().getMessage()));
	        }
	    };
		
		// 将事务分解为每个用户一个事务
		for (Integer user_id : userProductMaps.keySet()){
			// 每个用户分开处理
			ResponseFuture future = financialProductsNoticeService.processNoticeAsync(userProductMaps.get(user_id), date_from, date_to);
			rvMap.put(future, user_id);
			future.addListener(listener);
		}
		
		return 1;
	}
	
	public static int processUserRecovery(ApplicationContext ctx){
		
		UserRecoveryService userRecoveryService = (UserRecoveryService) ctx.getBean("userRecoveryService");
		userRecoveryService.recoverUsers(3);
		return 1;
	}
}
