package cn.gxf.spring.quartz.job.service;

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
		
		// 获取该用户上次更新时间
		Date lastproc = statProcDao.getLastProcTime(Integer.valueOf(userid).intValue());
		List<String> monthsHashNewData = null;
		if (null == lastproc){
			// 如果对应用户上次更新时间不存在，那么直接运行stat存储过程
			statDao.procAccStatThisMonth(Integer.valueOf(userid));
			System.out.println("updateStatThisMonth username:"+username);
			// 更新last proc time
			statProcDao.insertProcTime(current, Integer.valueOf(userid).intValue());
		}else{
			Map<String, Object> params = new HashMap<>();
			params.put("user_id", userid);
			params.put("date_from", lastproc);
			// 存在新数据的“年度-月份”
			monthsHashNewData = statProcDao.queryMonthsHaveNewData(params);
			if (monthsHashNewData.size() == 0){
				// 从上次运行存储过程到现在，没有新增数据
				System.out.println("username:"+username+" hasn't new data.");
				// 即使这段时间没有数据也要更新运行时间，使下一段统计时间变短
				statProcDao.updateProcTime(current, Integer.valueOf(userid).intValue());
				return;
			}
			// 运行存储过程
			for (String ndyf : monthsHashNewData){
				String nd = ndyf.split("-")[0];
				String yf = ndyf.split("-")[1];
				statDao.procAccStatByMonth(Integer.valueOf(userid), nd, yf);
			}
			System.out.println("updateStatThisMonth username:"+username);
			// 更新last proc time
			statProcDao.updateProcTime(current, Integer.valueOf(userid).intValue());
		}
	}

}
