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
		
		// ��ȡ���û��ϴθ���ʱ��
		Date lastproc = statProcDao.getLastProcTime(Integer.valueOf(userid).intValue());
		
		if (null == lastproc){
			// �����Ӧ�û��ϴθ���ʱ�䲻���ڣ���ôֱ������stat�洢����
			statisticsService.updateStatThisMonth(Integer.valueOf(userid));
			System.out.println("updateStatThisMonth username:"+username);
			// ����last proc time
			statProcDao.insertProcTime(current, Integer.valueOf(userid).intValue());
		}else{
			Map<String, Object> params = new HashMap<>();
			params.put("user_id", userid);
			params.put("date_from", lastproc);
			int income_num = statProcDao.isNewIncomeDataExists(params);
			int payment_num = statProcDao.isNewPaymentDataExists(params);
			if (income_num == 0 && payment_num == 0){
				// ���ϴ����д洢���̵����ڣ�û����������
				System.out.println("username:"+username+" hasn't new data.");
				return;
			}
			// ���д洢����
			statisticsService.updateStatThisMonth(Integer.valueOf(userid));
			System.out.println("updateStatThisMonth username:"+username);
			// ����last proc time
			statProcDao.updateProcTime(current, Integer.valueOf(userid).intValue());
		}
	}
}
