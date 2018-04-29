package cn.gxf.spring.struts2.integrate.service;

import java.util.List;
import java.util.Map;

import cn.gxf.spring.struts2.integrate.model.FinancialProductDetail;

public interface FinanicalProductService {
	FinancialProductDetail getFinancialProductByUuid(String uuid);
	List<FinancialProductDetail> getFinancialProductByUserId(Integer userId);
	Map<String, String> getFinancialProductUnredeemedMap(Integer userId); 
}
