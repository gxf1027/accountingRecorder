package cn.gxf.spring.quartz.job.service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.jms.Queue;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.gxf.spring.quartz.job.JMSSender;
import cn.gxf.spring.quartz.job.dao.FinanProductsNoticeDao;
import cn.gxf.spring.quartz.job.model.FinancialProductsNotice;
import cn.gxf.spring.struts.mybatis.dao.FinancialProductDetailMBDao;
import cn.gxf.spring.struts2.integrate.model.FinancialProductDetail;
import cn.gxf.spring.struts2.integrate.service.UserService;

@Service
public class FinancialProductsNoticeServiceImpl implements FinancialProductsNoticeService{

	@Autowired
	private UserService userService;
	
	@Autowired
	private FinanProductsNoticeDao noticeDao;
	
	@Autowired
	private FinancialProductDetailMBDao finanProdDetailDao;
	
	@Autowired
	private JMSSender jmsSender;
	
	@Autowired
	@Qualifier("mailQueueDest_FinaProducts")
	private Queue queue;
	
	
	// �ȿ��Դ������û���Ҳ���Դ������û�
	@Transactional(value="JtaXAManager")
	@Override
	public int processNotice(List<FinancialProductDetail> finanProdList, Date ssqq, Date ssqz) {
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		
		Set<String> userIdSet = new HashSet<String>(); // �������ظ���������Set
		for (FinancialProductDetail item : finanProdList){
			userIdSet.add(item.getUser_id().toString());
		}
		List<String> userIds = new ArrayList<String>();
		userIds.addAll(userIdSet); // Set ת���� List
		Map<String, String> userEmails = userService.getUserEmail(userIds);
		
		// �û�--֪ͨ����
		Map<String, FinancialProductsNotice> noticeMaps = new HashMap<String, FinancialProductsNotice>();
		
		for(FinancialProductDetail item : finanProdList){
			FinancialProductsNotice notice = noticeMaps.get(item.getUser_id().toString());
			if (null == notice){
				notice = new FinancialProductsNotice();
				notice.setUser_id(item.getUser_id());
				notice.setEmail(userEmails.get(item.getUser_id().toString()));
				notice.setSsqq(sdf.format(ssqq));
				notice.setSsqz(sdf.format(ssqz));
				notice.setPch(this.getPch(item.getUser_id(), sdf.format(ssqq), sdf.format(ssqz)));
				List<FinancialProductDetail> list = new ArrayList<>();
				list.add(item);
				notice.setFinanProducts(list);
				noticeMaps.put(item.getUser_id().toString(), notice);
			}else{
				notice.getFinanProducts().add(item);
			}
		}
		
		
		for (String userId : noticeMaps.keySet()){
			FinancialProductsNotice notice = noticeMaps.get(userId);
			
			// ���Ȳ鿴�Ƿ��ѷ���
			int isSent = noticeDao.isSent(notice.getPch(), notice.getUser_id());
			if (isSent > 0){
				continue; // �Ѿ����͹�
			}
			
			// �������ݿ�
			noticeDao.saveNotice(notice);
			
			// JMS����
			jmsSender.send(this.queue/*destination*/, notice);
		}
		
		return 1;
	}
	
	@Override
	public List<FinancialProductDetail> queryFinancialProductDetailByEndDate(Date date_from, Date date_to) {
		
		return finanProdDetailDao.queryFinancialProductDetailByEndDate(date_from, date_to);
	}

	private String getPch(Integer userId, String ssqq, String ssqz){
		return userId.toString() + "-" + ssqq + "-" + ssqz;
	}
}
