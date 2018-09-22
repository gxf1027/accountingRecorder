package cn.gxf.spring.struts2.integrate.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import cn.gxf.spring.struts2.integrate.model.FinancialProductDetail;

public interface FinanicalProductService {
	public FinancialProductDetail getFinancialProductByUuid(String uuid);
	public FinancialProductDetail getFinancialProductByTransferUuid(String uuid);
	public FinancialProductDetail getFinancialProductByReturnUuid(String returnUuid);
	public List<FinancialProductDetail> getFinancialProductByUserId(Integer userId);
	public List<FinancialProductDetail> getFinancialProductUnRedeemed(Date date_from, Date date_to);
	public Map<String, String> getFinancialProductUnredeemedMap(Integer userId);  // �ڡ�ת�ˡ���û�м���ص���Ʋ�Ʒ
	public Map<String, String> getFinancialProductUnreturnedMap(Integer userId);  // �ڡ����롱��û�м��������Ʋ�Ʒ
}
