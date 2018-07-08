package cn.gxf.spring.struts2.integrate.dao;

import java.util.List;
import java.util.Map;

import cn.gxf.spring.struts2.integrate.model.AccountBook;
import cn.gxf.spring.struts2.integrate.model.DmPaymentDl;
import cn.gxf.spring.struts2.integrate.model.DmPaymentXl;

public interface DmUtilDao {
	public Map<String, String> getPaymentDl();
	public Map<String, String> getPaymentXl();
	public Map<String, String> getIncomeLb();
	public Map<String, String> getFundType();
	public Map<String, String> getYh();
	public Map<String, String> getZhHuLx();
	public Map<String, Map<String, String>> getOutgoCategory();
	public Map<String, String> getOutgoCategory(Integer user_id);
	public Map<String, Map<String, String>> getTransferType();
	public Map<String, String> getTransferType(Integer user_id);
	public Map<String, String> getFinancialProdType();
	
	public List<DmPaymentDl> getPaymentDlList();
	public List<DmPaymentXl> getPaymentXlList();
	public Map<DmPaymentDl, List<DmPaymentXl>> getPaymentDlXlDzb();
}
