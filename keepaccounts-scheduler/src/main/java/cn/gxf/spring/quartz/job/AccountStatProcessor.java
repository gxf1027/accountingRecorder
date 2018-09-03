package cn.gxf.spring.quartz.job;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import cn.gxf.spring.quartz.job.service.AccountStatisticsService;
import cn.gxf.spring.quartz.job.service.AccountStatisticsServiceAsync;


@Service
public class AccountStatProcessor implements JobProcessor{
	
	
	@Autowired
	@Qualifier("statService")
	private AccountStatisticsService statisticsService;
	
	@Autowired
	@Qualifier("statServiceAsync")
	private AccountStatisticsServiceAsync statisticsServiceAsync;
	
	@Override
	public int process() {
		
		// 获得所有用户
		Map<String, String> users = statisticsService.getUsersIdNames();
		
		for (String userid : users.keySet()){
			
			//statisticsService.updateStatThisMonthByUserid(userid, users.get(userid));
			// 异步方法
			statisticsServiceAsync.updateStatThisMonthByUserid(userid, users.get(userid));
			
		}
		
		return 1;
	}
	
	
	
}
