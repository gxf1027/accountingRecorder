package cn.gxf.spring.quartz.job.service;


import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import cn.gxf.spring.quartz.job.dao.AccountStatisticDao;
import cn.gxf.spring.struts2.integrate.dao.UserDao;
import cn.gxf.spring.struts2.integrate.service.FrontStatisticsService;


@Service
public class AccountStatisticsServiceImpl implements AccountStatisticsService {

	@Autowired
	@Qualifier("accountStatisticDaoJdbcImpl")
	private AccountStatisticDao statDao;
	
	@Autowired
	private FrontStatisticsService statisticsService;
	
	@Override
	public void updateStatThisMonthByUserid(String userid, String username){
		
		this.statisticsService.reProcStatThisMonthForScheduler(Integer.valueOf(userid), username);
	}
	
	@Override
	public void updateStatThisMonthForAllUsers()
	{
		int userToStat = statDao.updateUsersToProcess(); // 要统计的用户(id,name)写到临时表中
		// 获得所有用户
		Map<String, String> users = statDao.getUsersIdNames(null, null); // 从临时表中获取所有用户
		
		for (String userid : users.keySet()){
			
			updateStatThisMonthByUserid(userid, users.get(userid));
			
		}
	}
	
	@Override
	public Map<String, String> getAllUsersIdNamePairToProcess() {
		
		return statDao.getUsersIdNames(null, null);
	}

	@Override
	public Map<String, String> getUsersIdNamePairToProcessByLimit(Integer start, Integer limit) {
		if (null == start){
			start = 0;
		}
		
		if (null == limit){
			limit = Integer.MAX_VALUE;
		}
		
		return statDao.getUsersIdNames(start, limit);
	}

	// 将要统计的用户信息写到临时表，并且返回行数
	@Override
	public int getUsersNumToProcessing() {
		
		return statDao.updateUsersToProcess();
	}
}
