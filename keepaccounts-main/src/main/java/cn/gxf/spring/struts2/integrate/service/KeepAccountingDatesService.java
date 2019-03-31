package cn.gxf.spring.struts2.integrate.service;

import java.util.Date;

public interface KeepAccountingDatesService {
	static public String keepdatesHash = "KEEPACC_DATES_HASH";
	static public String prefix_keepdates = "KEEPACC_DATES_";
	
	public int getKeepAccountingDates(Integer user_id);
	public void updatekeepAccountingDates(Integer user_id, Date date);
}
