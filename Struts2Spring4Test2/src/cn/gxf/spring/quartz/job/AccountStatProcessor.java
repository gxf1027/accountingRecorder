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
public class AccountStatProcessor {
	
	@Autowired
	private UserDao userDao;
	
	@Autowired
	private StatProcDao statProcDao;
	
	@Autowired
	private AccountStatisticsService statisticsService;
	
	@CacheEvict(value="front-stat", allEntries=true)
	public void updateStatThisMonth() {
		
		Map<String, String> users = userDao.getUsersIdNames();
		
		for (String userid : users.keySet()){
			
			updateStatThisMonthByUserid(userid, users.get(userid));
			
		}
	}
	
	
	private void updateStatThisMonthByUserid(String userid, String username){
		Date current = new Date();
		
		// 获取该用户上次更新时间
		Date lastproc = statProcDao.getLastProcTime(Integer.valueOf(userid).intValue());
		
		if (null == lastproc){
			// 如果对应用户上次更新时间不存在，那么直接运行stat存储过程
			statisticsService.updateStatThisMonth(Integer.valueOf(userid));
			System.out.println("updateStatThisMonth username:"+username);
			// 更新last proc time
			statProcDao.insertProcTime(current, Integer.valueOf(userid).intValue());
		}else{
			Map<String, Object> params = new HashMap<>();
			params.put("user_id", userid);
			params.put("date_from", lastproc);
			int income_num = statProcDao.isNewIncomeDataExists(params);
			int payment_num = statProcDao.isNewPaymentDataExists(params);
			if (income_num == 0 && payment_num == 0){
				// 从上次运行存储过程到现在，没有新增数据
				System.out.println("username:"+username+" hasn't new data.");
				return;
			}
			// 运行存储过程
			statisticsService.updateStatThisMonth(Integer.valueOf(userid));
			System.out.println("updateStatThisMonth username:"+username);
			// 更新last proc time
			statProcDao.updateProcTime(current, Integer.valueOf(userid).intValue());
		}
	}
}
