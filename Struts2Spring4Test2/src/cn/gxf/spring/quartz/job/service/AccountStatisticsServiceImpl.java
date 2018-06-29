package cn.gxf.spring.quartz.job.service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import cn.gxf.spring.quartz.job.dao.StatProcDao;
import cn.gxf.spring.struts.mybatis.dao.CustomTailorQueryDao;
import cn.gxf.spring.struts.mybatis.dao.StatNdYfMBDao;
import cn.gxf.spring.struts2.integrate.dao.UserDao;

@Service
public class AccountStatisticsServiceImpl implements AccountStatisticsService {

	@Autowired
	private StatNdYfMBDao statDao;

	@Autowired
	private StatProcDao statProcDao;
	
	@Transactional(value="JtaXAManager",propagation=Propagation.REQUIRED)
	@Override
	public void updateStatThisMonthByUserid(String userid, String username){
		Date current = new Date();
		
		// ��ȡ���û��ϴθ���ʱ��
		Date lastproc = statProcDao.getLastProcTime(Integer.valueOf(userid).intValue());
		
		if (null == lastproc){
			// �����Ӧ�û��ϴθ���ʱ�䲻���ڣ���ôֱ������stat�洢����
			statDao.procAccStatThisMonth(Integer.valueOf(userid));
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
				// ��ʹ���ʱ��û������ҲҪ��������ʱ�䣬ʹ��һ��ͳ��ʱ����
				statProcDao.updateProcTime(current, Integer.valueOf(userid).intValue());
				return;
			}
			// ���д洢����
			statDao.procAccStatThisMonth(Integer.valueOf(userid));
			System.out.println("updateStatThisMonth username:"+username);
			// ����last proc time
			statProcDao.updateProcTime(current, Integer.valueOf(userid).intValue());
		}
	}

}
