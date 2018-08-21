package cn.gxf.spring.quartz.job;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import cn.gxf.spring.quartz.job.service.FinancialProductsNoticeService;
import cn.gxf.spring.struts.integrate.util.AuxiliaryTools;
import cn.gxf.spring.struts2.integrate.model.FinancialProductDetail;

@Service
public class FinancialProductsNoticeProcessor implements JobProcessor{
	
	
	@Autowired
	private FinancialProductsNoticeService financialProductsNoticeService;
	
	// 在一个事务内，如果数据库/JMS抛出异常，会回滚
	//@Transactional(value="JtaXAManager",propagation=Propagation.REQUIRED)
	@Override
	public int process(){
		
		Date current = new Date();
		Date date_from = AuxiliaryTools.getMonthFirstDate(current);
		Date date_to = AuxiliaryTools.getMonthLastDate(current);
		// 获取所有用户本月将要到期的理财产品
		List<FinancialProductDetail> finProducts = financialProductsNoticeService.queryFinancialProductDetailByEndDate(date_from, date_to);
		
		if (finProducts.size() == 0){
			return 0;
		}
		
		// 每个用户--理财产品列表
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
		
		// 将事务分解为每个用户一个事务
		for (Integer user_id : userProductMaps.keySet()){
			// 每个用户分开处理
			financialProductsNoticeService.processNotice(userProductMaps.get(user_id), date_from, date_to);
		}
		
		return 1;
	}
	
	

	
}
