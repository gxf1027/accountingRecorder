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
		int userToStat = statDao.updateUsersToProcess(); // Ҫͳ�Ƶ��û�(id,name)д����ʱ����
		// ��������û�
		Map<String, String> users = statDao.getUsersIdNames(null, null); // ����ʱ���л�ȡ�����û�
		
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

	// ��Ҫͳ�Ƶ��û���Ϣд����ʱ�����ҷ�������
	@Override
	public int getUsersNumToProcessing() {
		
		return statDao.updateUsersToProcess();
	}
}
