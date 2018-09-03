package cn.gxf.spring.quartz.job;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;


import cn.gxf.spring.quartz.job.service.FinancialProductsNoticeService;
import cn.gxf.spring.quartz.job.service.FinancialProductsNoticeServiceAsync;
import cn.gxf.spring.struts.integrate.util.AuxiliaryTools;
import cn.gxf.spring.struts2.integrate.model.FinancialProductDetail;

@Service
public class FinancialProductsNoticeProcessor implements JobProcessor{
	
	
	@Autowired
	@Qualifier("productsNoticeService")
	private FinancialProductsNoticeService financialProductsNoticeService;
	
	@Autowired
	@Qualifier("productsNoticeServiceAsync")
	private FinancialProductsNoticeServiceAsync financialProductsNoticeServiceAsync;
	
	// ��һ�������ڣ�������ݿ�/JMS�׳��쳣����ع�
	//@Transactional(value="JtaXAManager",propagation=Propagation.REQUIRED)
	@Override
	public int process(){
		
		Date current = new Date();
		Date date_from = AuxiliaryTools.getMonthFirstDate(current);
		Date date_to = AuxiliaryTools.getMonthLastDate(current);
		// ��ȡ�����û����½�Ҫ���ڵ���Ʋ�Ʒ
		List<FinancialProductDetail> finProducts = financialProductsNoticeService.queryFinancialProductDetailByEndDate(date_from, date_to);
		
		if (finProducts.size() == 0){
			return 0;
		}
		
		// ÿ���û�--��Ʋ�Ʒ�б�
		Map<Integer, List<FinancialProductDetail>> userProductMaps = new HashMap<Integer, List<FinancialProductDetail>>();
		for (FinancialProductDetail finanDetail : finProducts){
			List<FinancialProductDetail> finanDetailList = userProductMaps.get(finanDetail.getUser_id());
			if (null == finanDetailList){
				finanDetailList = new ArrayList<FinancialProductDetail>();
				finanDetailList.add(finanDetail);
				userProductMaps.put(finanDetail.getUser_id(), finanDetailList);
			}else{
				userProductMaps.get(finanDetail.getUser_id()).add(finanDetail);
			}
		}
		
		// ������ֽ�Ϊÿ���û�һ������
		for (Integer user_id : userProductMaps.keySet()){
			// ÿ���û��ֿ�����
			financialProductsNoticeServiceAsync.processNotice(userProductMaps.get(user_id), date_from, date_to);
		}
		
		return 1;
	}
	
	

	
}
