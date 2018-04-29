package cn.gxf.spring.struts2.integrate.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
		Map<String, String> deltailMap = new HashMap<String, String>();
		for (FinancialProductDetail d:details){
			deltailMap.put(d.getUuid(), d.getYh_mc()+"-"+d.getProductName()+"-"+d.getDateCount()+"Ìì-"+d.getJe());
		}
		return deltailMap;
	}


	@Override
	public FinancialProductDetail getFinancialProductByUuid(String uuid) {
		if (uuid == null){
			return null;
		}
		
		return financialProductDetailMBDao.getFinancialProductDetailByUuid(uuid);
	}
	
	

}
