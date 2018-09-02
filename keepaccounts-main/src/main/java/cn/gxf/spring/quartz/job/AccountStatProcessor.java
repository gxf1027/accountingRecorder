package cn.gxf.spring.quartz.job;


import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import cn.gxf.spring.quartz.job.service.AccountStatisticsService;


@Service
public class AccountStatProcessor implements JobProcessor{
	
	
	@Autowired
	private AccountStatisticsService statisticsService;
	
	@Override
	public int process() {
		
		// 获得所有用户
		Map<String, String> users = statisticsService.getUsersIdNames();
		
		for (String userid : users.keySet()){
			
			statisticsService.updateStatThisMonthByUserid(userid, users.get(userid));
			
		}
		
		return 1;
	}
	
	
	
}
