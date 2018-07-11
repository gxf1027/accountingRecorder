package cn.gxf.spring.quartz.job;


import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import cn.gxf.spring.quartz.job.dao.CreditCardBillDao;
import cn.gxf.spring.quartz.job.model.CreditCard;
import cn.gxf.spring.quartz.job.model.CreditCardBill;
import cn.gxf.spring.quartz.job.model.CreditCardTransRecord;
import cn.gxf.spring.struts2.integrate.service.UserService;

@Service
public class BillProcessor {
	
	@Autowired
	private CreditCardBillDao creditCardBillDao;
	
	@Autowired
	private JMSSender mailSender;
	
	@Autowired
	private UserService userService;

	// 在一个事务内，如果数据库/JMS抛出异常，会回滚
	@Transactional(value="JtaXAManager",propagation=Propagation.REQUIRED)
	public int processCreditCardBill(){
		
		Date jyqz = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd"); // 交易日起为本日 
		 
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(jyqz); // 设置为当前时间
        calendar.set(Calendar.MONTH, calendar.get(Calendar.MONTH) - 1); // 设置为上一个月
        Date jyqq = calendar.getTime(); // 交易日止为上月同一天
 
        System.out.println("本期账单时间： " + sdf.format(jyqz) + "至" + sdf.format(jyqq));
        
		// 1. 获取需要处理的账户代码
		List<String> zzdmList = creditCardBillDao.getCreditCardInZDR(Integer.valueOf(sdf.format(jyqz).split("-")[2]));
		if (zzdmList == null || zzdmList.size() == 0){
			System.out.println("今天没有信用卡账单需要处理");
			return 0;
		}
		
		// 2. 获取数据
        // 获取账单明细
        List<CreditCardTransRecord> recList = getCreditCardTranscationRecordInZDQ(zzdmList, jyqq, jyqz);
        System.out.println("账单明细： "+recList);
        if (recList == null || recList.size() == 0){
        	System.out.println("本期无账单");
        	return 0;
        }
        
        // 准备要发送的bill数据
        List<CreditCardBill> bills = prepareCreditCardBills(recList, jyqq, jyqz);
        System.out.println("账单：" + bills);
        
        // 3. 数据持久化：
        // 3.1. 将可能存在过时明细数据置为无效
        deleteInvalidRecord(zzdmList, jyqq, jyqz);
        // 3.2. 将账单明细信息插入账单明细表
        saveTranscationRecordInZDQ(recList, jyqq, jyqz);
        System.out.println("明细数据: " + recList);
        // 3.3. 批次号回写到明细中
        writePchToTranscationRecord(bills);
        // 3.4 保存bill数据
        creditCardBillDao.saveCreditCardBill(bills);
        
        
        // 4. 发送至JMS
        sendToJMS(bills);
       
        //int i=1/0;
        
		return 1;
	}
	
	// 查询近一周
		@Transactional(value="JtaXAManager")
		public int processCreditCardBillManually(int user_id){
			Date jyqz = new Date();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd"); // 交易日止为本日 
			 
	        Calendar calendar = Calendar.getInstance();
	        calendar.setTime(jyqz); // 设置为当前时间
	        calendar.set(Calendar.MONTH, calendar.get(Calendar.MONTH) - 1); // 设置为上一个月
	        Date jyqq = calendar.getTime(); // 交易日止为上月同一天
	 
	        System.out.println("本期账单时间： " + sdf.format(jyqz) + "至" + sdf.format(jyqq));
	        
			// 1. 获取需要处理的账户代码
			List<CreditCard> creditCards = creditCardBillDao.getCreditCardbyUserId(user_id);
			if (creditCards == null || creditCards.size() == 0){
				System.out.println("用户没有需要处理的账户");
				return 0;
			}
			
			// 2. 获取数据
	        // 获取账单明细
	        List<CreditCardTransRecord> recList = getCreditCardTranscationRecordInZDQ(CreditCard.getZhdmList(creditCards), jyqq, jyqz);
	        System.out.println(recList);  
	        
	        // 准备要发送的bill数据
	        List<CreditCardBill> bills = prepareCreditCardBills(recList, jyqq, jyqz);
	        System.out.println("账单：" + bills);
	        
	        // 3. 数据持久化：
	        // 3.1. 将可能存在过时明细数据置为无效
	        deleteInvalidRecord(CreditCard.getZhdmList(creditCards), jyqq, jyqz);
	        // 3.2. 将账单明细信息插入账单明细表
	        saveTranscationRecordInZDQ(recList, jyqq, jyqz);
	        System.out.println("明细数据: " + recList);
	        // 3.3. 批次号回写到明细中
	        writePchToTranscationRecord(bills);
	        // 3.4 保存bill数据
	        creditCardBillDao.saveCreditCardBill(bills);
	        
	        
	        // 4. 发送至JMS
	        sendToJMS(bills);
	        
	        //int i=1/0;
	        
			return 1;
		}
	
	/*private List<String> getCreditCardInZDR(){
		
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
			throw new RuntimeException();
		}
		
		System.out.println("zhcc: " + zhcc);
		
		return zhcc;
	}*/
	
	// 获取某个账单期内的信用卡账单明细记录
	private List<CreditCardTransRecord> getCreditCardTranscationRecordInZDQ( List<String> zhdmList, Date jyqq, Date jyqz){
		
		Map<String, Object> params = new HashMap<>();
		params.put("jyqq", jyqq);
		params.put("jyqz", jyqz);
		params.put("zh_dm", zhdmList);
		List<CreditCardTransRecord> recList = null;
		try {
			recList = creditCardBillDao.getCreditCardTranscationRecordInZDQ(params);
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException();
		}
		
		return recList;
	}
	
	@Transactional(value="JtaXAManager", propagation=Propagation.REQUIRED)
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
			throw new RuntimeException();
		}
		
	}
	
	@Transactional(value="JtaXAManager", propagation=Propagation.REQUIRED)
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
			throw new RuntimeException();
		}
		
	}
	
	// 准备要发送的bill数据
	public List<CreditCardBill> prepareCreditCardBills(List<CreditCardTransRecord> recList, Date jyqq, Date jyqz){
		Map<String, CreditCardBill> ccbMap = new HashMap<>();
		Map<String, String> userMap = new HashMap<>();
		
        for(CreditCardTransRecord cctr : recList){
        	userMap.put(cctr.getUser_id().toString(), "1");
        	String keystr = cctr.getUser_id().toString() +"-"+ cctr.getZh_dm();
        	CreditCardBill ccb = ccbMap.get(keystr);
        	if  (null == ccb){
        		ccb = new CreditCardBill();
        		ccb.init(cctr, jyqq, jyqz); // 会生成批次号
        		ccbMap.put(keystr, ccb);
        	}else{
        		ccb.add(cctr);
        	}
        }
       
        
        // 获得email数据
        Map<String, String> userEmails = userService.getUserEmail(new ArrayList<>(userMap.keySet()));
        for(String key : ccbMap.keySet()){
        	CreditCardBill bill = ccbMap.get(key);
        	bill.setEmail(userEmails.get(key.split("-")[0]));
        	// 对应还款数保留两位小数，四舍五入
        	float yhkje = bill.getYhkje();
        	BigDecimal b = new BigDecimal(yhkje);  
        	bill.setYhkje(b.setScale(2, BigDecimal.ROUND_HALF_UP).floatValue());
        }
        
        return new ArrayList<CreditCardBill>(ccbMap.values());
	}
	
	@Transactional(value="JtaXAManager", propagation=Propagation.REQUIRED)
	void writePchToTranscationRecord(List<CreditCardBill> bills){
		 for(CreditCardBill bill : bills){
        	// 将pch回写到明细中
        	creditCardBillDao.setTranscationRecordPch(bill.getMxUuidList(), bill.getPch());
        }
	}
	
	/*@Transactional(value="JtaXAManager", propagation=Propagation.REQUIRED)
	public void sendToJMS(List<CreditCardTransRecord> recList, Date jyqq, Date jyqz){
		
		Map<String, CreditCardBill> ccbMap = new HashMap<>();
		Map<String, String> userMap = new HashMap<>();
		
        for(CreditCardTransRecord cctr : recList){
        	userMap.put(cctr.getUser_id().toString(), "1");
        	String keystr = cctr.getUser_id().toString() +"-"+ cctr.getZh_dm();
        	CreditCardBill ccb = ccbMap.get(keystr);
        	if  (null == ccb){
        		ccb = new CreditCardBill();
        		ccb.init(cctr, jyqq, jyqz); // 会生成批次号
        		ccbMap.put(keystr, ccb);
        	}else{
        		ccb.add(cctr);
        	}
        }
       
        
        // 获得email数据
        Map<String, String> userEmails = userService.getUserEmail(new ArrayList<>(userMap.keySet()));
        for(String key : ccbMap.keySet()){
        	CreditCardBill bill = ccbMap.get(key);
        	bill.setEmail(userEmails.get(key.split("-")[0]));
        	
        	// 将pch回写到明细中
        	System.out.println("uuidList:"+bill.getMxUuidList());
        	creditCardBillDao.setTranscationRecordPch(bill.getMxUuidList(), bill.getPch());
        }
        creditCardBillDao.saveCreditCardBill(new ArrayList<CreditCardBill>(ccbMap.values()));

        System.out.println(ccbMap);
        
        
        for(String key : ccbMap.keySet()){
        	
        	mailSender.send(ccbMap.get(key));
        }
	}*/
	
	@Transactional(value="JtaXAManager", propagation=Propagation.REQUIRED)
	public void sendToJMS(List<CreditCardBill> bills){
		for (CreditCardBill bill : bills){
			this.mailSender.send(bill);
		}
	}
	
}
