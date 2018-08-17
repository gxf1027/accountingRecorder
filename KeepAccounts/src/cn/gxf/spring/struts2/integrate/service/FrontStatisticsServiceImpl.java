package cn.gxf.spring.struts2.integrate.service;

import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import cn.gxf.spring.struts.integrate.cache.EhCacheUtils;
import cn.gxf.spring.struts.integrate.cache.SpringCacheKeyGenerator;
import cn.gxf.spring.struts.mybatis.dao.StatNdYfMBDao;
import cn.gxf.spring.struts2.integrate.model.StatByCategory;
import cn.gxf.spring.struts2.integrate.model.StatByMonth;

@Service
public class FrontStatisticsServiceImpl implements FrontStatisticsService {

	@Autowired
	private StatNdYfMBDao statDao;
	
	@Autowired
	private EhCacheUtils cacheUtils;
	
	@Override
	public void reProcStat(String nd, Integer user_id) {
		
		removeCache(nd, user_id);
		
		statDao.procAccStatByNd(nd, user_id);
		
		// 获取该用户上次更新时间
		Date lastproc = statDao.getLastProcTime(user_id);
		Date current = new Date();
		if (null == lastproc){
			// 如果表中还没有这个用户的运行时间记录
			statDao.insertProcTime(current, user_id);
		}else{
			statDao.updateProcTime(current, user_id);
		}
	}

	@Override
	public void reProcStatThisMonth(Integer user_id) {
		
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
		removeCache(sdf.format(date), user_id);
		
		statDao.procAccStatThisMonth(user_id);
		
		// 获取该用户上次更新时间
		Date lastproc = statDao.getLastProcTime(user_id);
		Date current = new Date();
		if (null == lastproc){
			// 如果表中还没有这个用户的运行时间记录
			statDao.insertProcTime(current, user_id);
		}else{
			statDao.updateProcTime(current, user_id);
		}
	}

	// 使用<cache:annotation-driven ... key-generator="userKeyGenerator" />配置的自定义key生成器
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
		
		try {
			
			for (Method m : this.getClass().getDeclaredMethods()){
				if (!m.getName().startsWith("get")){
					continue;
				}
				
				String key = (String) keyGenerator.generate(this, m, nd, user_id);
				this.cacheUtils.remove("front-stat", key);
			}
			
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	

}
