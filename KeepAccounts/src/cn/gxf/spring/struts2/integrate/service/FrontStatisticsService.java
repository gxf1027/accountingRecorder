package cn.gxf.spring.struts2.integrate.service;

import java.util.List;

import cn.gxf.spring.struts2.integrate.model.StatByCategory;
import cn.gxf.spring.struts2.integrate.model.StatByMonth;

public interface FrontStatisticsService {
	public void reProcStat(String nd, Integer user_id);
	public void reProcStatThisMonth(Integer user_id); // 页面上使用
	public void reProcStatThisMonthForScheduler(Integer userid, String username);// 定时任务使用
	public List<StatByMonth> getStatByYear(String nd, Integer user_id);
	public List<StatByCategory> getIncomeStatByLb(String nd, Integer user_id);
	public List<StatByCategory> getPaymentStatByDl(String nd, Integer user_id);
}
