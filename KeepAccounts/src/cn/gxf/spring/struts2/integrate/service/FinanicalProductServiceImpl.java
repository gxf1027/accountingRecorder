package cn.gxf.spring.struts2.integrate.service;

import java.util.ArrayList;
import java.util.Date;
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
		Map<String, String> deltailMap = new HashMap<String, String>();
		for (FinancialProductDetail d:details){
			String startDate = d.getStartDateToShow() == null ? "null" : d.getStartDateToShow().replaceAll("-", "");
			String endDate = d.getEndDateToShow() == null ? "null" : d.getEndDateToShow().replaceAll("-", "");
			deltailMap.put(d.getUuid(), d.getYh_mc()+"-"+d.getProductName()+"-"+d.getJe()+"-"+d.getDateCount()+"天-"+startDate+"-"+endDate);
		}
		return deltailMap;
	}
	
	@Override
	public Map<String, String> getFinancialProductUnreturnedMap(Integer userId) {
		if (userId == null){
			return null;
		}
	    // 获取还未被“收入”关联的理财产品
		List<FinancialProductDetail> details = this.financialProductDetailMBDao.getFinancialProductDetailUnreturned(userId);
		if (details == null){
			return null;
		}
		Map<String, String> deltailMap = new HashMap<String, String>();
		for (FinancialProductDetail d:details){
			deltailMap.put(d.getUuid(), d.getYh_mc()+"-"+d.getProductName()+"-"+d.getDateCount()+"天-"+d.getJe());
		}
		return deltailMap;
	}

	@Override
	public List<FinancialProductDetail> getFinancialProductByRedeemDate(Integer userId, Date date_from, Date date_to) {
		if (userId == null){
			return null;
		}
		return this.financialProductDetailMBDao.queryFinancialProductDetailByRedeemDate(userId, date_from, date_to);
	}

	
}
