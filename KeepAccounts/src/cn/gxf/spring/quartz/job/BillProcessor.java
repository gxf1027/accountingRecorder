package cn.gxf.spring.quartz.job;


import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import cn.gxf.spring.quartz.job.dao.CreditCardBillDao;
import cn.gxf.spring.quartz.job.model.CreditCard;
import cn.gxf.spring.quartz.job.service.CreditCardsBillService;

@Service
public class BillProcessor implements JobProcessor{


	@Autowired
	private CreditCardBillDao creditCardBillDao;
	
	@Autowired
	private CreditCardsBillService creditCardBillService;

	// ��һ�������ڣ�������ݿ�/JMS�׳��쳣����ع�
	// �ܵĺ����������񣬿��ܳ�ʱ
	//@Transactional(value="JtaXAManager",propagation=Propagation.REQUIRED)
	@Override
	public int process(){
		
		Date jyqz = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd"); // ��������Ϊ���� 
		 
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(jyqz); // ����Ϊ��ǰʱ��
        calendar.set(Calendar.MONTH, calendar.get(Calendar.MONTH) - 1); // ����Ϊ��һ����
        Date jyqq = calendar.getTime(); // ������ֹΪ����ͬһ��
 
        System.out.println("�����˵�ʱ�䣺 " + sdf.format(jyqz) + "��" + sdf.format(jyqq));
        
		// 1. ��ȡ��Ҫ������˻�����
		//List<String> zzdmList = creditCardBillDao.getCreditCardInZDR(Integer.valueOf(sdf.format(jyqz).split("-")[2]));
        List<CreditCard> creditCards = creditCardBillDao.getCreditCardInZDR(Integer.valueOf(sdf.format(jyqz).split("-")[2]));
        
        if (creditCards.size() == 0 ){
        	System.out.println("����û�����ÿ��˵���Ҫ����");
			return 0;
        }
        
        // ��������û����˻��б� ���ձ�
        Map<String, List<String>> userZzdmMap = new HashMap<>();
        for (CreditCard card : creditCards){
        	List<String> zzdmList = userZzdmMap.get(card.getUser_id());
        	if (null == zzdmList){
        		zzdmList = new ArrayList<>();
        		zzdmList.add(card.getZh_dm());
        		userZzdmMap.put(card.getUser_id(), zzdmList);
        	}else{
        		userZzdmMap.get(card.getUser_id()).add(card.getZh_dm());
        	}
        }

		
        // ���û����д�������getCreditCardTranscationRecordInZDQ�������ݻ�̫��
        for (String userId : userZzdmMap.keySet()){
        	List<String> zzdmList = userZzdmMap.get(userId);
        	if (zzdmList == null || zzdmList.size() == 0){
    			//System.out.println("����û�����ÿ��˵���Ҫ����");
    			continue;
    		}
        	
        	// ÿ��ѭ�����������񣬷����������ʱ
        	creditCardBillService.processBill(zzdmList, jyqq, jyqz);
        }
        
		return 1;
	}
	
	// ��ѯ��һ��
	//@Transactional(value="JtaXAManager")
	public int processCreditCardBillManually(int user_id){
		Date jyqz = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd"); // ������ֹΪ���� 
		 
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(jyqz); // ����Ϊ��ǰʱ��
        calendar.set(Calendar.MONTH, calendar.get(Calendar.MONTH) - 1); // ����Ϊ��һ����
        Date jyqq = calendar.getTime(); // ������ֹΪ����ͬһ��
 
        System.out.println("�����˵�ʱ�䣺 " + sdf.format(jyqz) + "��" + sdf.format(jyqq));
        
		this.creditCardBillService.processBillManually(user_id, jyqq, jyqz);
        
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
	
	
}
