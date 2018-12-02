package cn.gxf.spring.struts.integrate.cache;


import java.util.Calendar;
import java.util.Date;

import org.springframework.stereotype.Service;

import cn.gxf.spring.struts.integrate.util.DateFomatTransfer;

@Service
public class StatDetailKeyGenerator {
	
	public final static String detailIncomeVoPrefix = "getIncomeVo_";
	public final static String detailPaymentVoPrefix = "getPaymentVo_";
	public final static String detailTransferVoPrefix = "getTransferVo_";
	
	public static String generateKey(String prefix, int user_id, Date date_from, Date date_to) {
		return prefix + user_id + "_" + DateFomatTransfer.date2CompactString(date_from) + "_" + DateFomatTransfer.date2CompactString(date_to);
	}
	
	
	public String detailIncomeKey(int user_id, Date shijian){
		Date date_from = this.getMonthFirstDate(shijian);
		Date date_to = this.getMonthLastDate(shijian);
		return detailIncomeVoPrefix + user_id + "_" + DateFomatTransfer.date2CompactString(date_from) + "_" + DateFomatTransfer.date2CompactString(date_to);
	}
	
	public String detailPaymentKey(int user_id, Date shijian){
		Date date_from = this.getMonthFirstDate(shijian);
		Date date_to = this.getMonthLastDate(shijian);
		return detailPaymentVoPrefix + user_id + "_" + DateFomatTransfer.date2CompactString(date_from) + "_" + DateFomatTransfer.date2CompactString(date_to);
	}
	
	public String detailTransferKey(int user_id, Date shijian){
		Date date_from = this.getMonthFirstDate(shijian);
		Date date_to = this.getMonthLastDate(shijian);
		return detailTransferVoPrefix + user_id + "_" + DateFomatTransfer.date2CompactString(date_from) + "_" + DateFomatTransfer.date2CompactString(date_to);
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
