package cn.gxf.spring.quartz.job.dao;

import java.util.Map;


public interface AccountStatisticDao {
	
	public int updateUsersToProcess(); // 更新需要统计的users到临时表，并返回数量
	public Map<String, String> getUsersIdNames(Integer start, Integer limit); // 只读取发生业务数据的用户
	// 当users数量很大时，读取会超时
	//public Map<String, String> getUsersIdNamesAll();
}
