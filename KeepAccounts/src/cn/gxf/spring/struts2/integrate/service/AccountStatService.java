package cn.gxf.spring.struts2.integrate.service;

import java.util.Date;
import java.util.List;
import java.util.Set;

import cn.gxf.spring.struts2.integrate.model.AccDateStat;

public interface AccountStatService {
		// 统计
		public List<AccDateStat> getDateStatMB(int user_id, String nd, String yf);
		public List<AccDateStat> getDateStatMB(int user_id, Date date_from , Date date_to);
	
		public List<AccDateStat> getDateStatIncome(int user_id, String nd, String yf);
		public List<AccDateStat> getDateStatIncome(int user_id, Date date_from, Date date_to);
		
		public List<AccDateStat> getDateStatPayment(int user_id, String nd, String yf);
		public List<AccDateStat> getDateStatPayment(int user_id, Date date_from, Date date_to);
		
		public List<AccDateStat> getDateStatTransfer(int user_id, String nd, String yf);
		public List<AccDateStat> getDateStatTransfer(int user_id, Date date_from, Date date_to);
		
		public List<AccDateStat> getDateStat(int user_id, String nd, String yf);
		
		// 清除缓存
//		public void EvictDateStatMB(int user_id, Date date_from , Date date_to);
//		public void EvictDateStatIncome(int user_id, Date date_from , Date date_to);
//		public void EvictDateStatPayment(int user_id, Date date_from , Date date_to);
//		public void EvictDateStatTransfer(int user_id, Date date_from , Date date_to);
		// 使用redis中方法清除缓存
		//public void EvictByUserId(int user_id);
		void EvictDateStatRedis(Set<String> keys);
}
