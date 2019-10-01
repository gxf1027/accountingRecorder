package cn.gxf.spring.struts2.integrate.service;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import cn.gxf.spring.struts.mybatis.dao.StatNdYfMBDao;

/*
 * HASH��KEEPACC_DATES_HASH
 * KEYS��KEEAPACC_DATES_#user_id
 * VALUE�� YYYY-MM-dd,#dates,0/1
 * ���磺��һ���û�ĳ���½δ���м���ʱ��valueΪ��2019-03-31,349,0
 * �����û�����һ����¼��value����Ϊ��2019-03-31,350,1
 * �����û��ٴα���󣬲�����и��£��Ծ�Ϊ��2019-03-31,350,1
 * �����û����յ�½���м�¼�󣬸���Ϊ��2019-04-01,351,1
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
		
		// ���key�����ڣ��򲻽��и���
		String keepdatesValue = (String) this.stringRedisTemplate.opsForHash().get(KeepAccountingDatesService.keepdatesHash, KeepAccountingDatesService.prefix_keepdates+user_id);
		if (null == keepdatesValue){
			return;
		}
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String dateStr = keepdatesValue.split(",")[0];
		String dates = keepdatesValue.split(",")[1];
		String updated = keepdatesValue.split(",")[2];
		
		String dateStr_new = sdf.format(date);
		// ���������ڶ�Ӧ��value�Ѵ���
		if (dateStr.equals(dateStr_new) ){
			// �Ѹ��¹���
			if (updated.equals("1")){
				return;
			}
			// δ���¹���updated: 0�����췢���Ļ�δͳ�ƣ�
			dates = String.valueOf(Integer.valueOf(dates)+1); // ��������+1
			keepdatesValue = dateStr+","+dates+","+"1";
			this.stringRedisTemplate.opsForHash().put(KeepAccountingDatesService.keepdatesHash, KeepAccountingDatesService.prefix_keepdates+user_id, keepdatesValue);
			return;
		}
		
		// ������ڲ�һ�£���϶�Ҫ����
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
