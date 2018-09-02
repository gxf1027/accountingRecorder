package cn.gxf.spring.struts2.integrate.service;

import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import cn.gxf.spring.struts.integrate.cache.EhCacheUtils;
import cn.gxf.spring.struts.integrate.cache.SpringCacheKeyGenerator;
import cn.gxf.spring.struts.mybatis.dao.StatNdYfMBDao;
import cn.gxf.spring.struts.mybatis.dao.StatProcDao;
import cn.gxf.spring.struts2.integrate.model.StatByCategory;
import cn.gxf.spring.struts2.integrate.model.StatByMonth;

@Service
public class FrontStatisticsServiceImpl implements FrontStatisticsService {

	@Autowired
	private StatNdYfMBDao statDao;
	
	@Autowired
	private StatProcDao statProcDao;
	
	@Autowired
	private EhCacheUtils cacheUtils;
	
	@Transactional(value="dsTransactionManager",propagation=Propagation.REQUIRED)
	@Override
	public void reProcStat(String nd, Integer user_id) {
		
		removeCache(nd, user_id);
		
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
	
	@Transactional(value="dsTransactionManager",propagation=Propagation.REQUIRED)
	@Override
	public void reProcStatThisMonthForScheduler(Integer userid, String username){
		
		Date current = new Date();
		
		// ��ȡ���û��ϴθ���ʱ��
		Date lastproc = statProcDao.getLastProcTime(Integer.valueOf(userid).intValue());
		List<String> monthsHashNewData = null;
		if (null == lastproc){
			Date date = new Date();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
			removeCache(sdf.format(date), Integer.valueOf(userid));
			// �����Ӧ�û��ϴθ���ʱ�䲻���ڣ���ôֱ������stat�洢����
			statDao.procAccStatThisMonth(Integer.valueOf(userid));
			System.out.println("updateStatThisMonth username:"+username);
			// ����last proc time
			statProcDao.insertProcTime(current, Integer.valueOf(userid).intValue());
		}else{
			Map<String, Object> params = new HashMap<>();
			params.put("user_id", userid);
			params.put("date_from", lastproc);
			// ���������ݵġ����-�·ݡ�
			monthsHashNewData = statProcDao.queryMonthsHaveNewData(params);
			if (monthsHashNewData.size() == 0){
				// ���ϴ����д洢���̵����ڣ�û����������
				System.out.println("username:"+username+" hasn't new data.");
				// ��ʹ���ʱ��û������ҲҪ��������ʱ�䣬ʹ��һ��ͳ��ʱ����
				statProcDao.updateProcTime(current, Integer.valueOf(userid).intValue());
				return;
			}
			
			// ���д洢����
			for (String ndyf : monthsHashNewData){
				String nd = ndyf.split("-")[0];
				String yf = ndyf.split("-")[1];
				removeCache(nd, Integer.valueOf(userid));
				statDao.procAccStatByMonth(Integer.valueOf(userid), nd, yf);
			}
			System.out.println("updateStatThisMonth username:"+username);
			// ����last proc time
			statProcDao.updateProcTime(current, Integer.valueOf(userid).intValue());
		}
	}
	
	/*
	 * �����ǡ��ϴμӹ�ʱ�䡱���Ա������ݽ������¼ӹ�
	 * �ӹ���ɺ���¡��ϴμӹ�ʱ�䡱
	 */
	@Transactional(value="dsTransactionManager",propagation=Propagation.REQUIRED)
	@Override
	public void reProcStatThisMonth(Integer user_id) {
		
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
		removeCache(sdf.format(date), user_id);
		
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

	private void removeCache(String nd, Integer user_id){
		
		SpringCacheKeyGenerator keyGenerator = new SpringCacheKeyGenerator();
		//System.out.println("before remove: " + this.cacheUtils.getAllKeys("front-stat"));
		try {
			
			for (Method m : this.getClass().getDeclaredMethods()){
				if (!m.getName().startsWith("get")){
					continue;
				}
				
				String key = (String) keyGenerator.generate(this, m, nd, user_id);
				this.cacheUtils.remove("front-stat", key);
			
			}
			//System.out.println("after remove: " + this.cacheUtils.getAllKeys("front-stat"));
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	

}
