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

	// ��һ�������ڣ�������ݿ�/JMS�׳��쳣����ع�
	@Transactional(propagation=Propagation.REQUIRED)
	public int processCreditCardBill(){
		// 1. ��ȡ��Ҫ������˻�����
		List<String> zzdmList = this.getCreditCardInZDR();
		
		if (zzdmList == null || zzdmList.size() == 0){
			System.out.println("����û�����ÿ��˵���Ҫ����");
			return 0;
		}
		
		// 2. ��ȡ��Щ�˻����ڵ�֧�����������
		Date jyqz = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd"); // ��������Ϊ���� 
		
		System.out.println("��ǰʱ���ǣ�" + sdf.format(jyqz));
		 
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(jyqz); // ����Ϊ��ǰʱ��
        calendar.set(Calendar.MONTH, calendar.get(Calendar.MONTH) - 1); // ����Ϊ��һ����
        Date jyqq = calendar.getTime(); // ������ֹΪ����ͬһ��
 
        System.out.println("��һ���µ�ʱ�䣺 " + sdf.format(jyqq));
        
        // ��ѯ�д���"����������", С�ڵ���"��������ֹ"
        List<CreditCardTransRecord> recList = getCreditCardTranscationRecordInZDQ(zzdmList, jyqq, jyqz);
        System.out.println(recList);
 
        // 3. �����ܴ��ڹ�ʱ������Ϊ��Ч
        deleteInvalidRecord(zzdmList, jyqq, jyqz);
        
        
        // 4. ���˵���Ϣ�����˵���
        saveTranscationRecordInZDQ(recList, jyqq, jyqz);
        System.out.println("after save: " + recList);
        
        // 5. ������JMS
        sendToJMS(recList, jyqq, jyqz); 
        
  
		return 1;
	}
	
	private List<String> getCreditCardInZDR(){
		
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
		}
		
		System.out.println("zhcc: " + zhcc);
		
		return zhcc;
	}
	
	// ��ȡĳ���˵����ڵ����ÿ��˵���ϸ��¼
	private List<CreditCardTransRecord> getCreditCardTranscationRecordInZDQ( List<String> zhdmList, Date jyqq, Date jyqz){
		System.out.println("getCreditCardTranscationRecordInZDQ start...");
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd"); // ��������Ϊ����
		
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
			System.out.println("����"+num+"������");
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	@Transactional(propagation=Propagation.REQUIRED)
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
