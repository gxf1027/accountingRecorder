package cn.gxf.spring.quartz.job;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.weibo.api.motan.rpc.Future;
import com.weibo.api.motan.rpc.FutureListener;
import com.weibo.api.motan.rpc.ResponseFuture;

import cn.gxf.spring.quartz.job.service.FinancialProductsNoticeServiceAsync;
import cn.gxf.spring.struts.integrate.util.AuxiliaryTools;
import cn.gxf.spring.struts2.integrate.model.FinancialProductDetail;

@Service
public class FinancialProductsNoticeProcessor implements JobProcessor{
	
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
		List<FinancialProductDetail> finProducts = financialProductsNoticeServiceAsync.queryFinancialProductDetailByEndDate(date_from, date_to);
		
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
		
		Map<ResponseFuture, Integer> rvMap = new HashMap<ResponseFuture, Integer>();
		// async with listener
	    FutureListener listener = new FutureListener() {
	        @Override
	        public void operationComplete(Future future) throws Exception {
	            System.out.println("async call, user_id " +
	            		rvMap.get(future) + ": "+ (future.isSuccess() ? "sucess! value:" + future.getValue() : "fail! exception:"
	                            + future.getException().getMessage()));
	        }
	    };
		// ������ֽ�Ϊÿ���û�һ������
		for (Integer user_id : userProductMaps.keySet()){
			// ÿ���û��ֿ�����
			// �첽
			ResponseFuture future = financialProductsNoticeServiceAsync.processNoticeAsync(userProductMaps.get(user_id), date_from, date_to);
			rvMap.put(future, user_id);
			future.addListener(listener);
		}
		
		return 1;
	}
	
	

	
}
