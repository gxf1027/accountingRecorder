package cn.gxf.spring.struts2.integrate.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.gxf.spring.struts.mybatis.dao.FinancialProductDetailMBDao;
import cn.gxf.spring.struts2.integrate.model.FinancialProductDetail;

@Service
public class FinanicalProductServiceImpl implements FinanicalProductService {

	@Autowired
	private FinancialProductDetailMBDao financialProductDetailMBDao;

	
	@Override
	public List<FinancialProductDetail> getFinancialProductByUserId(Integer userId) {
		if (userId == null){
			return null;
		}
		return financialProductDetailMBDao.getFinancialProductDetailByUserId(userId);
	}

	@Override
	public FinancialProductDetail getFinancialProductByUuid(String uuid) {
		if (uuid == null){
			return null;
		}
		return financialProductDetailMBDao.getFinancialProductDetailByUuid(uuid);
	}
	
	@Override
	public FinancialProductDetail getFinancialProductByTransferUuid(String uuid) {
		if (uuid == null){
			return null;
		}
		
		return financialProductDetailMBDao.getFinancialProductDetailByTransferUuid(uuid);
	}
	
	@Override
	public FinancialProductDetail getFinancialProductByReturnUuid(String returnUuid) {
		if (returnUuid == null){
			return null;
		}
		return financialProductDetailMBDao.getFinancialProductDetailByReturnUuid(returnUuid);
	}
	
	public List<FinancialProductDetail> getFinancialProductUnredeemed(Integer userId) {
		if (userId == null){
			return null;
		}
		List<FinancialProductDetail> details = financialProductDetailMBDao.getFinancialProductDetailByUserId(userId);
		List<FinancialProductDetail> unredeemed = new ArrayList<>();
		for (FinancialProductDetail d:details){
			if (d.getIs_redeem() == null || d.getIs_redeem().equals("N")){
				unredeemed.add(d);
			}
		}
		return unredeemed.size() == 0 ? null : unredeemed;
	}
	

	@Override
	public Map<String, String> getFinancialProductUnredeemedMap(Integer userId) {
		if (userId == null){
			return null;
		}
		List<FinancialProductDetail> details = getFinancialProductUnredeemed(userId);
		if (details == null){
			return null;
		}
		Map<String, String> deltailMap = new LinkedHashMap<String, String>(); // ��HashMap�޷�ʹ�����value����
		for (FinancialProductDetail d:details){
			String startDate = d.getStartDateToShow() == null ? "null" : d.getStartDateToShow().replaceAll("-", "");
			String endDate = d.getEndDateToShow() == null ? "null" : d.getEndDateToShow().replaceAll("-", "");
			deltailMap.put(d.getUuid(), d.getYh_mc()+"-"+d.getProductName()+"-"+d.getJe()+"-"+d.getDateCount()+"��-"+startDate+"-"+endDate);
		}
		
		
		//���ｫmap.entrySet()ת����list
        List<Map.Entry<String,String>> list = new ArrayList<Map.Entry<String,String>>(deltailMap.entrySet());
        //Ȼ��ͨ���Ƚ�����ʵ������
        Collections.sort(list,new Comparator<Map.Entry<String,String>>() {
            //��������
            public int compare(Entry<String, String> o1, Entry<String, String> o2) {
                return -1*o1.getValue().compareTo(o2.getValue());
            }
            
        });
        
        deltailMap.clear();
        for(Map.Entry<String,String> mapping:list){ 
        	//System.out.println(mapping.getKey()+":"+mapping.getValue());
        	deltailMap.put(mapping.getKey(), mapping.getValue());
        } 
        
		return deltailMap;
	}
	
	@Override
	public Map<String, String> getFinancialProductUnreturnedMap(Integer userId) {
		if (userId == null){
			return null;
		}
	    // ��ȡ��δ�������롱��������Ʋ�Ʒ
		List<FinancialProductDetail> details = this.financialProductDetailMBDao.getFinancialProductDetailUnreturned(userId);
		if (details == null){
			return null;
		}
		Map<String, String> deltailMap = new LinkedHashMap<String, String>();
		for (FinancialProductDetail d:details){
			String startDate = d.getStartDateToShow() == null ? "null" : d.getStartDateToShow().replaceAll("-", "");
			String endDate = d.getEndDateToShow() == null ? "null" : d.getEndDateToShow().replaceAll("-", "");
			deltailMap.put(d.getUuid(), d.getYh_mc()+"-"+d.getProductName()+"-"+d.getJe()+"-"+d.getDateCount()+"��-"+startDate+"-"+endDate);
		}
		
		
		//���ｫmap.entrySet()ת����list
        List<Map.Entry<String,String>> list = new ArrayList<Map.Entry<String,String>>(deltailMap.entrySet());
        //Ȼ��ͨ���Ƚ�����ʵ������
        Collections.sort(list,new Comparator<Map.Entry<String,String>>() {
            //��������
            public int compare(Entry<String, String> o1, Entry<String, String> o2) {
                return -1*o1.getValue().compareTo(o2.getValue());
            }
            
        });
        
        deltailMap.clear();
        for(Map.Entry<String,String> mapping:list){ 
        	//System.out.println(mapping.getKey()+":"+mapping.getValue());
        	deltailMap.put(mapping.getKey(), mapping.getValue());
        }
        
		return deltailMap;
	}

	@Override
	public List<FinancialProductDetail> getFinancialProductUnRedeemed(Date date_from, Date date_to) {
		
		return this.financialProductDetailMBDao.queryFinancialProductDetailByEndDate(date_from, date_to);
	}

	
}
