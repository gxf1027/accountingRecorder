package cn.gxf.spring.struts2.integrate.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import cn.gxf.spring.struts.mybatis.dao.StatNdYfMBDao;
import cn.gxf.spring.struts2.integrate.model.StatByCategory;
import cn.gxf.spring.struts2.integrate.model.StatByMonth;

@Service
public class FrontStatisticsServiceImpl implements FrontStatisticsService {

	@Autowired
	private StatNdYfMBDao statDao;
	
	@CacheEvict(value="front-stat", allEntries=true)
	@Override
	public void reProcStat(String nd, Integer user_id) {
		
		statDao.procAccStatByNd(nd, user_id);
		
		// ��ȡ���û��ϴθ���ʱ��
		Date lastproc = statDao.getLastProcTime(user_id);
		Date current = new Date();
		if (null == lastproc){
			// ������л�û������û�������ʱ���¼
			statDao.insertProcTime(current, user_id);
		}else{
			statDao.updateProcTime(current, user_id);
		}
	}
	
	@CacheEvict(value="front-stat", allEntries=true)
	@Override
	public void reProcStatThisMonth(Integer user_id) {
		
		statDao.procAccStatThisMonth(user_id);
		
		// ��ȡ���û��ϴθ���ʱ��
		Date lastproc = statDao.getLastProcTime(user_id);
		Date current = new Date();
		if (null == lastproc){
			// ������л�û������û�������ʱ���¼
			statDao.insertProcTime(current, user_id);
		}else{
			statDao.updateProcTime(current, user_id);
		}
	}

	// ʹ��<cache:annotation-driven ... key-generator="userKeyGenerator" />���õ��Զ���key������
	@Cacheable(value="front-stat")  
	@Override
	public List<StatByMonth> getStatByYear(String nd, Integer user_id) {
		
		return statDao.getNdYfStat(nd, user_id);
	}

	@Cacheable(value="front-stat") 
	@Override
	public List<StatByCategory> getIncomeStatByLb(String nd, Integer user_id) {
	
		return statDao.getIncomeStatOnSrlb(nd, user_id);
	}

	@Cacheable(value="front-stat") 
	@Override
	public List<StatByCategory> getPaymentStatByDl(String nd, Integer user_id) {
	
		return statDao.getPaymentStatOnDl(nd, user_id);
	}

	

}
