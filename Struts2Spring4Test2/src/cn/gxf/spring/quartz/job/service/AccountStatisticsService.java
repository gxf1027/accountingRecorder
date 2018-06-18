package cn.gxf.spring.quartz.job.service;

public interface AccountStatisticsService {
	public void updateStatThisMonth();
	public void updateStatThisMonth(Integer user_id);
}
