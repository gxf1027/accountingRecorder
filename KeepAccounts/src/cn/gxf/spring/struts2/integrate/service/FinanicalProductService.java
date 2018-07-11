package cn.gxf.spring.struts2.integrate.service;

import java.util.List;
import java.util.Map;

import cn.gxf.spring.struts2.integrate.model.FinancialProductDetail;

public interface FinanicalProductService {
	FinancialProductDetail getFinancialProductByUuid(String uuid);
	FinancialProductDetail getFinancialProductByTransferUuid(String uuid);
	FinancialProductDetail getFinancialProductByReturnUuid(String returnUuid);
	List<FinancialProductDetail> getFinancialProductByUserId(Integer userId);
	Map<String, String> getFinancialProductUnredeemedMap(Integer userId);  // �ڡ�ת�ˡ���û�м���ص���Ʋ�Ʒ
	Map<String, String> getFinancialProductUnreturnedMap(Integer userId);  // �ڡ����롱��û�м��������Ʋ�Ʒ
}
