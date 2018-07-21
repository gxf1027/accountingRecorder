package cn.gxf.spring.quartz.job;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import cn.gxf.spring.quartz.job.dao.StatProcDao;
import cn.gxf.spring.quartz.job.service.AccountStatisticsService;

import cn.gxf.spring.struts2.integrate.dao.UserDao;

@Service
public class AccountStatProcessor implements JobProcessor{
	
	@Autowired
	private UserDao userDao;
	
	@Autowired
	private AccountStatisticsService statisticsService;
	
	@Override
	@CacheEvict(value="front-stat", allEntries=true)
	public int process() {
		
		// 获得所有用户
		Map<String, String> users = userDao.getUsersIdNames();
		
		for (String userid : users.keySet()){
			
			statisticsService.updateStatThisMonthByUserid(userid, users.get(userid));
			
		}
		
		return 1;
	}
	
	
	
}
