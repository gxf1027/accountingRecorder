package cn.gxf.spring.quartz.job.service;

import java.util.Map;

import com.weibo.api.motan.transport.async.MotanAsync;

@MotanAsync
public interface AccountStatisticsService {
	public void updateStatThisMonthByUserid(String userid, String username);
	public void updateStatThisMonthForAllUsers();
	public Map<String, String> getAllUsersIdNamePairToProcess();
	public Map<String, String> getUsersIdNamePairToProcessByLimit(Integer start, Integer limit);
	public int getUsersNumToProcessing();
}
