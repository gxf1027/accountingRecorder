package cn.gxf.spring.quartz.job.service;


import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.gxf.spring.struts2.integrate.dao.UserDao;
import cn.gxf.spring.struts2.integrate.service.FrontStatisticsService;


@Service
public class AccountStatisticsServiceImpl implements AccountStatisticsService {

	@Autowired
	private UserDao userDao;
	
	@Autowired
	private FrontStatisticsService statisticsService;
	
	@Override
	public void updateStatThisMonthByUserid(String userid, String username){
		
		this.statisticsService.reProcStatThisMonthForScheduler(Integer.valueOf(userid), username);
	}
	
	@Override
	public void updateStatThisMonthForAllUsers()
	{
		// 获得所有用户
		Map<String, String> users = userDao.getUsersIdNames();
		
		for (String userid : users.keySet()){
			
			updateStatThisMonthByUserid(userid, users.get(userid));
			
		}
	}
	
	@Override
	public Map<String, String> getUsersIdNames() {
		
		return userDao.getUsersIdNames();
	}
}
