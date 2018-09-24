package cn.gxf.spring.quartz.job.dao;

import java.util.Map;


public interface AccountStatisticDao {
	
	public int getUsersNumHavingData(); // 需要处理的user(自上次stat后产生新数据的)的数量
	public Map<String, String> getUsersIdNames(Integer start, Integer limit); // 只读取发生业务数据的用户
	
	// 当users数量很大时，读取会超时
	//public Map<String, String> getUsersIdNamesAll();
}
