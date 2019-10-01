package cn.gxf.spring.struts2.integrate.service;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import cn.gxf.spring.struts.mybatis.dao.StatNdYfMBDao;

/*
 * HASH：KEEPACC_DATES_HASH
 * KEYS：KEEAPACC_DATES_#user_id
 * VALUE： YYYY-MM-dd,#dates,0/1
 * 例如：当一个用户某天登陆未进行记账时，value为：2019-03-31,349,0
 * 当该用户保存一条记录后，value更新为：2019-03-31,350,1
 * 当该用户再次保存后，不会进行更新，仍旧为：2019-03-31,350,1
 * 当该用户次日登陆进行记录后，更新为：2019-04-01,351,1
 * */
@Service
public class KeepAccountingDatesServiceImpl implements KeepAccountingDatesService {

	@Autowired
	private StringRedisTemplate stringRedisTemplate;
	
	@Autowired
	private StatNdYfMBDao statDao;
	
	@Override
	public int getKeepAccountingDates(Integer user_id) {
		
		String keepdatesValue = (String) this.stringRedisTemplate.opsForHash().get(KeepAccountingDatesService.keepdatesHash, KeepAccountingDatesService.prefix_keepdates+user_id);
		if (null != keepdatesValue){
			return Integer.valueOf(keepdatesValue.split(",")[1]); 
		}
		
		int dates = 0;
		try {
			statDao.getKeepAccountingDates(user_id);
		} catch (Exception e) {
			return 0;
		}
		
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		keepdatesValue = sdf.format(new Date())+","+dates+",0";
		this.stringRedisTemplate.opsForHash().put(KeepAccountingDatesService.keepdatesHash, KeepAccountingDatesService.prefix_keepdates+user_id, keepdatesValue);
		return dates;
	}
	
	@Override
	public void updatekeepAccountingDates(Integer user_id, Date date) {
		
		// 如果key不存在，则不进行更新
		String keepdatesValue = (String) this.stringRedisTemplate.opsForHash().get(KeepAccountingDatesService.keepdatesHash, KeepAccountingDatesService.prefix_keepdates+user_id);
		if (null == keepdatesValue){
			return;
		}
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String dateStr = keepdatesValue.split(",")[0];
		String dates = keepdatesValue.split(",")[1];
		String updated = keepdatesValue.split(",")[2];
		
		String dateStr_new = sdf.format(date);
		// 如果这个日期对应的value已存在
		if (dateStr.equals(dateStr_new) ){
			// 已更新过了
			if (updated.equals("1")){
				return;
			}
			// 未更新过，updated: 0（当天发生的还未统计）
			dates = String.valueOf(Integer.valueOf(dates)+1); // 记账天数+1
			keepdatesValue = dateStr+","+dates+","+"1";
			this.stringRedisTemplate.opsForHash().put(KeepAccountingDatesService.keepdatesHash, KeepAccountingDatesService.prefix_keepdates+user_id, keepdatesValue);
			return;
		}
		
		// 如果日期不一致，则肯定要更新
		int dates_new = 0;
		try {
			dates_new = statDao.getKeepAccountingDates(user_id);
		} catch (Exception e) {
			return;
		}
		
		keepdatesValue = dateStr_new+","+dates_new+",1";
		this.stringRedisTemplate.opsForHash().put(KeepAccountingDatesService.keepdatesHash, KeepAccountingDatesService.prefix_keepdates+user_id, keepdatesValue);
	}

}
