package cn.gxf.spring.struts2.integrate.service;

import java.util.Date;
import java.util.List;

import cn.gxf.spring.struts2.integrate.model.AccDateStat;

public interface AccountStatService {
		// Í³¼Æ
		public List<AccDateStat> getDateStatMB(int user_id, String nd, String yf);
		public List<AccDateStat> getDateStatMB(int user_id, Date date_from , Date date_to);
	
		public List<AccDateStat> getDateStatIncome(int user_id, String nd, String yf);
		public List<AccDateStat> getDateStatIncome(int user_id, Date date_from, Date date_to);
		
		public List<AccDateStat> getDateStatPayment(int user_id, String nd, String yf);
		public List<AccDateStat> getDateStatPayment(int user_id, Date date_from, Date date_to);
		
		public List<AccDateStat> getDateStatTransfer(int user_id, String nd, String yf);
		public List<AccDateStat> getDateStatTransfer(int user_id, Date date_from, Date date_to);
		
		public List<AccDateStat> getDateStat(int user_id, String nd, String yf);
}
