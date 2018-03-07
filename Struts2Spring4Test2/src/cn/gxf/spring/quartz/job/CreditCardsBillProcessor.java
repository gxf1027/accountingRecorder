package cn.gxf.spring.quartz.job;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import cn.gxf.spring.quartz.job.dao.CreditCardBillDao;
import cn.gxf.spring.quartz.job.model.CreditCardBill;
import cn.gxf.spring.quartz.job.model.CreditCardRecordSimplified;
import cn.gxf.spring.quartz.job.model.CreditCardTransRecord;

@Service
public class CreditCardsBillProcessor {
	
	@Autowired
	private NamedParameterJdbcTemplate namedJdbcTemplate;
	
	@Autowired
	private CreditCardBillDao creditCardBillDao;
	
	@Autowired
	private MailMqSender mailSender;

	// 在一个事务内，如果数据库/JMS抛出异常，会回滚
	@Transactional(propagation=Propagation.REQUIRED)
	public int processCreditCardBill(){
		// 1. 获取需要处理的账户代码
		List<String> zzdmList = this.getCreditCardInZDR();
		
		if (zzdmList == null || zzdmList.size() == 0){
			System.out.println("今天没有信用卡账单需要处理");
			return 0;
		}
		
		// 2. 获取这些账户本期的支出和收入情况
		Date jyqz = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd"); // 交易日起为本日 
		
		System.out.println("当前时间是：" + sdf.format(jyqz));
		 
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(jyqz); // 设置为当前时间
        calendar.set(Calendar.MONTH, calendar.get(Calendar.MONTH) - 1); // 设置为上一个月
        Date jyqq = calendar.getTime(); // 交易日止为上月同一天
 
        System.out.println("上一个月的时间： " + sdf.format(jyqq));
        
        // 查询中大于"交易日期起", 小于等于"交易日期止"
        List<CreditCardTransRecord> recList = getCreditCardTranscationRecordInZDQ(zzdmList, jyqq, jyqz);
        System.out.println(recList);
 
        // 3. 将可能存在过时数据置为无效
        deleteInvalidRecord(zzdmList, jyqq, jyqz);
        
        
        // 4. 将账单信息插入账单表
        saveTranscationRecordInZDQ(recList, jyqq, jyqz);
        System.out.println("after save: " + recList);
        
        // 5. 发送至JMS
        sendToJMS(recList, jyqq, jyqz); 
        
  
		return 1;
	}
	
	private List<String> getCreditCardInZDR(){
		
		// 当前日期
		Date today = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String[] ydyfrq = sdf.format(today).split("-");
		System.out.println(ydyfrq[0] + " "+ydyfrq[1] + " "+ ydyfrq[2] );
		
		String sql = "SELECT a.zh_dm "+ 
				"FROM zh_detail_info a, zh_detail_creditcard b "+
				"WHERE a.zh_dm = b.zh_dm "+
				"AND a.yxbz='Y' " +
				"AND b.zdr = :zdr" ;
		
		Map<String, Object> paramMap = new HashMap<>();
		paramMap.put("zdr", ydyfrq[2]);
		System.out.println(paramMap);
		List<String> zhcc = null;
		// 以下如果不catch异常，在控制台中不会打印异常
		try {
			zhcc = namedJdbcTemplate.query(sql, paramMap, new RowMapper<String>() {
				@Override
				public String mapRow(ResultSet rs, int rowNum) throws SQLException {
					
					return rs.getString("zh_dm");
				}
			});
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		System.out.println("zhcc: " + zhcc);
		
		return zhcc;
	}
	
	// 获取某个账单期内的信用卡账单明细记录
	private List<CreditCardTransRecord> getCreditCardTranscationRecordInZDQ( List<String> zhdmList, Date jyqq, Date jyqz){
		System.out.println("getCreditCardTranscationRecordInZDQ start...");
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd"); // 交易日起为本日
		
		Map<String, Object> params = new HashMap<>();
		params.put("jyqq", jyqq);
		params.put("jyqz", jyqz);
		params.put("zh_dm", zhdmList);
		List<CreditCardTransRecord> recList = null;
		try {
			recList = creditCardBillDao.getCreditCardTranscationRecordInZDQ(params);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		System.out.println("getCreditCardTranscationRecordInZDQ end...");
		return recList;
	}
	
	@Transactional(propagation=Propagation.REQUIRED)
	public void saveTranscationRecordInZDQ(List<CreditCardTransRecord> cctrList, Date jyqq, Date jyqz){
		
		for (CreditCardTransRecord cctr : cctrList){
        	cctr.setBill_ssqq(jyqq);
        	cctr.setBill_ssqz(jyqz);
        	cctr.setYxbz("Y");
        	cctr.setIsMailed("N");
        }
		
		try {
			int num = creditCardBillDao.saveTranscationRecordInZDQ(cctrList);
			System.out.println("保存"+num+"条数据");
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	@Transactional(propagation=Propagation.REQUIRED)
	public void deleteInvalidRecord(List<String> zzdmList, Date jyqq, Date jyqz){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd"); // 交易日起为本日
		Map<String, Object> paramsMap = new HashMap<>();
		paramsMap.put("zh_dm", zzdmList);
		paramsMap.put("ssqq", sdf.format(jyqq));
		paramsMap.put("ssqz", sdf.format(jyqz));
		try {
			creditCardBillDao.deleteInvalidRecord(paramsMap);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	public void sendToJMS(List<CreditCardTransRecord> recList, Date jyqq, Date jyqz){
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd"); 
		Map<String, CreditCardBill> ccbMap = new HashMap<>();
		
        for(CreditCardTransRecord cctr : recList){
        	String keystr = cctr.getUser_id().toString() +"-"+ cctr.getZh_dm();
        	CreditCardBill ccb = ccbMap.get(keystr);
        	if  (null == ccb){
        		ccb = new CreditCardBill();
        		ccb.setUser_id(cctr.getUser_id().intValue());
        		ccb.setZh_dm(cctr.getZh_dm());
        		ccb.setZh_mc(cctr.getZh_mc());
        		ccb.setSsqq(sdf.format(jyqq));
        		ccb.setSsqz(sdf.format(jyqz));
        		ccb.setYhkje(0);
        		ccb.setCctrList(new ArrayList<CreditCardRecordSimplified>());
        		ccb.getCctrList().add(new CreditCardRecordSimplified(cctr));
        		ccbMap.put(keystr, ccb);
        	}else{
        		ccb.getCctrList().add(new CreditCardRecordSimplified(cctr));
        		ccb.setYhkje(ccb.getYhkje()+cctr.getJe());
        	}
        }
        
        System.out.println(ccbMap);
        
        
        for(String key : ccbMap.keySet()){
        	
        	mailSender.send(ccbMap.get(key));
        }
	}
}
