package cn.gxf.spring.struts2.integrate.service;

import java.util.List;
import java.util.Map;

import cn.gxf.spring.struts2.integrate.model.FinancialProductDetail;

public interface FinanicalProductService {
	FinancialProductDetail getFinancialProductByUuid(String uuid);
	FinancialProductDetail getFinancialProductByTransferUuid(String uuid);
	FinancialProductDetail getFinancialProductByReturnUuid(String returnUuid);
	List<FinancialProductDetail> getFinancialProductByUserId(Integer userId);
	Map<String, String> getFinancialProductUnredeemedMap(Integer userId);  // 在“转账”中没有记赎回的理财产品
	Map<String, String> getFinancialProductUnreturnedMap(Integer userId);  // 在“收入”中没有记收益的理财产品
}
