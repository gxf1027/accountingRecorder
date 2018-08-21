package cn.gxf.spring.quartz.job.service;

import java.util.Map;

public interface AccountStatisticsService {
	public void updateStatThisMonthByUserid(String userid, String username);
	public void updateStatThisMonthForAllUsers();
	public Map<String, String> getUsersIdNames();
}
