package cn.gxf.spring.struts.integrate.test;


import java.util.Calendar;
import java.util.Date;

import org.springframework.stereotype.Service;

@Service
public class StatDetailKeyGenerator {
	private final String detailAllPrefix = "getDateStatMB_";
	private final String detailIncomePrefix = "getDateStatIncome_";
	private final String detailPaymentPrefix = "getDateStatPayment_";
	private final String detailTransferPrefix = "getDateStatTransfer_";
	
	public String detailAllKey(int user_id, Date shijian){
		Date date_from = this.getMonthFirstDate(shijian);
		Date date_to = this.getMonthLastDate(shijian);
		return detailAllPrefix + user_id + "_" + date_from + "_" + date_to;
	}
	
	public String detailIncomeKey(int user_id, Date shijian){
		Date date_from = this.getMonthFirstDate(shijian);
		Date date_to = this.getMonthLastDate(shijian);
		return detailIncomePrefix + user_id + "_" + date_from + "_" + date_to;
	}
	
	public String detailPaymentKey(int user_id, Date shijian){
		Date date_from = this.getMonthFirstDate(shijian);
		Date date_to = this.getMonthLastDate(shijian);
		return detailPaymentPrefix + user_id + "_" + date_from + "_" + date_to;
	}
	
	public String detailTransferKey(int user_id, Date shijian){
		Date date_from = this.getMonthFirstDate(shijian);
		Date date_to = this.getMonthLastDate(shijian);
		return detailTransferPrefix + user_id + "_" + date_from + "_" + date_to;
	}
	
	
	public Date getMonthFirstDate(Date date){
		Calendar cale = null;
		cale = Calendar.getInstance();
		cale.setTime(date);
		
		// 获取这个月的第一天
        cale.add(Calendar.MONTH, 0);
        cale.set(Calendar.DAY_OF_MONTH, 1);
        cale.set(Calendar.HOUR_OF_DAY, 0);
        cale.set(Calendar.MINUTE, 0);
        cale.set(Calendar.SECOND, 0);
        
        return cale.getTime();
	}
	
	public Date getMonthLastDate(Date date){
		Calendar cale = null;
		cale = Calendar.getInstance();
		cale.setTime(date);
		
		// 获取这个月的最后一天
        cale.add(Calendar.MONTH, 1);
        cale.set(Calendar.DAY_OF_MONTH, 0);
        cale.set(Calendar.HOUR_OF_DAY, 0);
        cale.set(Calendar.MINUTE, 0);
        cale.set(Calendar.SECOND, 0);
        return cale.getTime();
	}
}
