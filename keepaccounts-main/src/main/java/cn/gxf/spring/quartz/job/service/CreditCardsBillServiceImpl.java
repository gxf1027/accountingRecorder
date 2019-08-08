package cn.gxf.spring.quartz.job.service;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.jms.Destination;
import javax.jms.Queue;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import cn.gxf.spring.quartz.job.JMSSender;
import cn.gxf.spring.quartz.job.dao.CreditCardBillDao;
import cn.gxf.spring.quartz.job.model.CreditCard;
import cn.gxf.spring.quartz.job.model.CreditCardBill;
import cn.gxf.spring.quartz.job.model.CreditCardTransRecord;
import cn.gxf.spring.struts2.integrate.service.UserService;

@Service
public class CreditCardsBillServiceImpl implements CreditCardsBillService{
	
    private Logger logger = LogManager.getLogger();

	@Autowired
	private CreditCardBillDao creditCardBillDao;
	
	@Autowired
	@Qualifier("mailQueueDest")
	private Queue queue;
	
	@Autowired
	private JMSSender jmsSender;
	
	@Autowired
	private UserService userService;
	
	// ��һ�������ڣ�������ݿ�/JMS�׳��쳣����ع�
	@Transactional(value="JtaXAManager",propagation=Propagation.REQUIRED)
	@Override
	public int processBill(List<String> zzdmList, Date jyqq, Date jyqz) {
		if (zzdmList == null || zzdmList.size() == 0){
			//System.out.println("����û�����ÿ��˵���Ҫ����");
			return 0;
		}
    	
    	// 2. ��ȡ����
        // ��ȡ�˵���ϸ
        List<CreditCardTransRecord> recList = getCreditCardTranscationRecordInZDQ(zzdmList, jyqq, jyqz);
        System.out.println(recList); 
        if (recList.size() == 0){
        	System.out.println("���ÿ� " + zzdmList + "�������˵�");
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
        sendToJMS(this.queue, bills);
        
        return 1;
	}
	
	@Transactional(value="JtaXAManager")
	@Override
	public int processBillManually(int user_id, Date jyqq, Date jyqz){
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
        if (recList.size() == 0){
        	System.out.println("���ÿ� " + recList + "�������˵�");
        	return 0;
        }
        
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
        sendToJMS(this.queue, bills);
        
        return 1;
	}
	
	@Override
	public List<CreditCard> getCreditCardInZDR(int zdr) {
		
		return this.creditCardBillDao.getCreditCardInZDR(zdr);
	}
	
	
	// ��ȡĳ���˵����ڵ����ÿ��˵���ϸ��¼
	private List<CreditCardTransRecord> getCreditCardTranscationRecordInZDQ( List<String> zhdmList, Date jyqq, Date jyqz){
		
		Map<String, Object> params = new HashMap<>();
		params.put("jyqq", jyqq);
		params.put("jyqz", jyqz);
		params.put("zh_dm", zhdmList);
		List<CreditCardTransRecord> recList = new ArrayList<>();
		try {
			List<String> zhdm_list1 = new ArrayList<String>();
			for (String zhdm : zhdmList){
				zhdm_list1.clear();
				zhdm_list1.add(zhdm);
				params.put("zh_dm", zhdm_list1);
				// ����һ�β�ѯ���zhdm������SQL�н�ʹ��IN(,,,)���ʽ
				recList.addAll(creditCardBillDao.getCreditCardTranscationRecordInZDQ(params));
			}
		} catch (Exception e) {
			logger.error(e.getMessage());
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
			logger.error(e.getMessage());
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
			logger.error(e.getMessage());
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
        	BigDecimal b = BigDecimal.valueOf(yhkje);  
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
	public void sendToJMS(Destination destination, List<CreditCardBill> bills){
		for (CreditCardBill bill : bills){
			this.jmsSender.send(destination, bill);
		}
	}
}
