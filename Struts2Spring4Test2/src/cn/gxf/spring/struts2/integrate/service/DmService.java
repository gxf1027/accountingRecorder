package cn.gxf.spring.struts2.integrate.service;

import java.util.List;
import java.util.Map;

import cn.gxf.spring.struts2.integrate.model.AccountBook;
import cn.gxf.spring.struts2.integrate.model.DmPaymentDl;
import cn.gxf.spring.struts2.integrate.model.DmPaymentXl;

public interface DmService {
	public List<AccountBook> getZhInfo(int user_id);
	public Map<String, List<AccountBook>> getZhInfoMap(int user_id);
	public List<AccountBook> getZhInfoSimple(int user_id);
	public void saveAccBook(AccountBook accBook);
	public Map<String, String> getPaymentDl();
	public Map<String, String> getPaymentXl();
	public Map<DmPaymentDl, List<DmPaymentXl>> getPaymentDlXlDzb();
	public Map<String, String> getIncomeLb();
	public Map<String, String> getFundType();
	public Map<String, String> getYhInfo();
	public Map<String, String> getZhLx();
	public Map<String, String> getOutgoCategory(Integer user_id);
	public Map<String, String> getTransferType(Integer user_id);
	public void doCacheEvict();
}
