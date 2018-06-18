package cn.gxf.spring.quartz.job.service;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;

import cn.gxf.spring.struts.mybatis.dao.CustomTailorQueryDao;
import cn.gxf.spring.struts.mybatis.dao.StatNdYfMBDao;
import cn.gxf.spring.struts2.integrate.dao.UserDao;

@Service
public class AccountStatisticsServiceImpl implements AccountStatisticsService {

	@Autowired
	private StatNdYfMBDao statDao;
	
	@Autowired
	private UserDao userDao;
	
	@Autowired
	private CustomTailorQueryDao customTailorQueryDao;
	
	@CacheEvict(value="front-stat", allEntries=true)
	@Override
	public void updateStatThisMonth() {
		
		Map<String, String> users = userDao.getUsersIdNames();
		for (String userid : users.keySet()){
			statDao.procAccStatThisMonth(Integer.valueOf(userid));
			//System.out.println("updateStatThisMonth username:"+users.get(userid));
		}
	}

	@Override
	public void updateStatThisMonth(Integer user_id) {
		if (null == user_id){
			return;
		}
		
		statDao.procAccStatThisMonth(user_id);
		//System.out.println("updateStatThisMonth user_id:"+ user_id);
	}

}
