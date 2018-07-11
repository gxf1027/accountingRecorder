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

	// ��һ�������ڣ�������ݿ�/JMS�׳��쳣����ع�
	@Transactional(value="JtaXAManager",propagation=Propagation.REQUIRED)
	public int processCreditCardBill(){
		
		Date jyqz = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd"); // ��������Ϊ���� 
		 
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(jyqz); // ����Ϊ��ǰʱ��
        calendar.set(Calendar.MONTH, calendar.get(Calendar.MONTH) - 1); // ����Ϊ��һ����
        Date jyqq = calendar.getTime(); // ������ֹΪ����ͬһ��
 
        System.out.println("�����˵�ʱ�䣺 " + sdf.format(jyqz) + "��" + sdf.format(jyqq));
        
		// 1. ��ȡ��Ҫ������˻�����
		List<String> zzdmList = creditCardBillDao.getCreditCardInZDR(Integer.valueOf(sdf.format(jyqz).split("-")[2]));
		if (zzdmList == null || zzdmList.size() == 0){
			System.out.println("����û�����ÿ��˵���Ҫ����");
			return 0;
		}
		
		// 2. ��ȡ����
        // ��ȡ�˵���ϸ
        List<CreditCardTransRecord> recList = getCreditCardTranscationRecordInZDQ(zzdmList, jyqq, jyqz);
        System.out.println("�˵���ϸ�� "+recList);
        if (recList == null || recList.size() == 0){
        	System.out.println("�������˵�");
        	return 0;
        }
        
        // ׼��Ҫ���͵�bill����
        List<CreditCardBill> bills = prepareCreditCardBills(recList, jyqq, jyqz);
        System.out.println("�˵���" + bills);
        
        // 3. ���ݳ־û���
        // 3.1. �����ܴ��ڹ�ʱ��ϸ������Ϊ��Ч
        deleteInvalidRecord(zzdmList, jyqq, jyqz);
        // 3.2. ���˵���ϸ��Ϣ�����˵���ϸ��
        saveTranscationRecordInZDQ(recList, jyqq, jyqz);
        System.out.println("��ϸ����: " + recList);
        // 3.3. ���κŻ�д����ϸ��
        writePchToTranscationRecord(bills);
        // 3.4 ����bill����
        creditCardBillDao.saveCreditCardBill(bills);
        
        
        // 4. ������JMS
        sendToJMS(bills);
       
        //int i=1/0;
        
		return 1;
	}
	
	// ��ѯ��һ��
		@Transactional(value="JtaXAManager")
		public int processCreditCardBillManually(int user_id){
			Date jyqz = new Date();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd"); // ������ֹΪ���� 
			 
	        Calendar calendar = Calendar.getInstance();
	        calendar.setTime(jyqz); // ����Ϊ��ǰʱ��
	        calendar.set(Calendar.MONTH, calendar.get(Calendar.MONTH) - 1); // ����Ϊ��һ����
	        Date jyqq = calendar.getTime(); // ������ֹΪ����ͬһ��
	 
	        System.out.println("�����˵�ʱ�䣺 " + sdf.format(jyqz) + "��" + sdf.format(jyqq));
	        
			// 1. ��ȡ��Ҫ������˻�����
			List<CreditCard> creditCards = creditCardBillDao.getCreditCardbyUserId(user_id);
			if (creditCards == null || creditCards.size() == 0){
				System.out.println("�û�û����Ҫ������˻�");
				return 0;
			}
			
			// 2. ��ȡ����
	        // ��ȡ�˵���ϸ
	        List<CreditCardTransRecord> recList = getCreditCardTranscationRecordInZDQ(CreditCard.getZhdmList(creditCards), jyqq, jyqz);
	        System.out.println(recList);  
	        
	        // ׼��Ҫ���͵�bill����
	        List<CreditCardBill> bills = prepareCreditCardBills(recList, jyqq, jyqz);
	        System.out.println("�˵���" + bills);
	        
	        // 3. ���ݳ־û���
	        // 3.1. �����ܴ��ڹ�ʱ��ϸ������Ϊ��Ч
	        deleteInvalidRecord(CreditCard.getZhdmList(creditCards), jyqq, jyqz);
	        // 3.2. ���˵���ϸ��Ϣ�����˵���ϸ��
	        saveTranscationRecordInZDQ(recList, jyqq, jyqz);
	        System.out.println("��ϸ����: " + recList);
	        // 3.3. ���κŻ�д����ϸ��
	        writePchToTranscationRecord(bills);
	        // 3.4 ����bill����
	        creditCardBillDao.saveCreditCardBill(bills);
	        
	        
	        // 4. ������JMS
	        sendToJMS(bills);
	        
	        //int i=1/0;
	        
			return 1;
		}
	
	/*private List<String> getCreditCardInZDR(){
		
		// ��ǰ����
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
		// ���������catch�쳣���ڿ���̨�в����ӡ�쳣
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
	
	// ��ȡĳ���˵����ڵ����ÿ��˵���ϸ��¼
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
			System.out.println("����"+num+"������");
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException();
		}
		
	}
	
	@Transactional(value="JtaXAManager", propagation=Propagation.REQUIRED)
	public void deleteInvalidRecord(List<String> zzdmList, Date jyqq, Date jyqz){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd"); // ��������Ϊ����
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
	
	// ׼��Ҫ���͵�bill����
	public List<CreditCardBill> prepareCreditCardBills(List<CreditCardTransRecord> recList, Date jyqq, Date jyqz){
		Map<String, CreditCardBill> ccbMap = new HashMap<>();
		Map<String, String> userMap = new HashMap<>();
		
        for(CreditCardTransRecord cctr : recList){
        	userMap.put(cctr.getUser_id().toString(), "1");
        	String keystr = cctr.getUser_id().toString() +"-"+ cctr.getZh_dm();
        	CreditCardBill ccb = ccbMap.get(keystr);
        	if  (null == ccb){
        		ccb = new CreditCardBill();
        		ccb.init(cctr, jyqq, jyqz); // ���������κ�
        		ccbMap.put(keystr, ccb);
        	}else{
        		ccb.add(cctr);
        	}
        }
       
        
        // ���email����
        Map<String, String> userEmails = userService.getUserEmail(new ArrayList<>(userMap.keySet()));
        for(String key : ccbMap.keySet()){
        	CreditCardBill bill = ccbMap.get(key);
        	bill.setEmail(userEmails.get(key.split("-")[0]));
        	// ��Ӧ������������λС������������
        	float yhkje = bill.getYhkje();
        	BigDecimal b = new BigDecimal(yhkje);  
        	bill.setYhkje(b.setScale(2, BigDecimal.ROUND_HALF_UP).floatValue());
        }
        
        return new ArrayList<CreditCardBill>(ccbMap.values());
	}
	
	@Transactional(value="JtaXAManager", propagation=Propagation.REQUIRED)
	void writePchToTranscationRecord(List<CreditCardBill> bills){
		 for(CreditCardBill bill : bills){
        	// ��pch��д����ϸ��
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
        		ccb.init(cctr, jyqq, jyqz); // ���������κ�
        		ccbMap.put(keystr, ccb);
        	}else{
        		ccb.add(cctr);
        	}
        }
       
        
        // ���email����
        Map<String, String> userEmails = userService.getUserEmail(new ArrayList<>(userMap.keySet()));
        for(String key : ccbMap.keySet()){
        	CreditCardBill bill = ccbMap.get(key);
        	bill.setEmail(userEmails.get(key.split("-")[0]));
        	
        	// ��pch��д����ϸ��
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
