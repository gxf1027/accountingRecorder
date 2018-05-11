package cn.gxf.spring.struts2.integrate.service;

import java.util.List;
import java.util.Map;

import cn.gxf.spring.struts2.integrate.model.AccountBook;
import cn.gxf.spring.struts2.integrate.model.DmPaymentDl;
import cn.gxf.spring.struts2.integrate.model.DmPaymentXl;

public interface DmService {
	public final static String zzlx_purchase_fin_prod_dm = "0002"; // 转账类型-购买理财产品
	public final static String zzlx_purchase_fund_dm = "0003"; // 转账类型-购买基金
	public final static String zzlx_redeem_fin_prod_dm = "0009"; // 转账类型-赎回理财产品
	public final static String srlb_fin_prod_dm = "2001"; // 收入类型-理财利息
	
	public List<AccountBook> getZhInfo(int user_id);
	public Map<String, List<AccountBook>> getZhInfoMap(int user_id);
	public List<AccountBook> getZhInfoSimple(int user_id);
	public void saveAccBook(AccountBook accBook);
	public Map<String, String> getPaymentDl();
	public Map<String, String> getPaymentXl();
	public Map<DmPaymentDl, List<DmPaymentXl>> getPaymentDlXlDzb();
	public Map<String, String> getIncomeLb();
	public Map<String, String> getFundType();
	public Map<String, String> getFinancialProdType();
	public Map<String, String> getYhInfo();
	public Map<String, String> getZhLx();
	public Map<String, String> getOutgoCategory(Integer user_id);
	public Map<String, String> getTransferType(Integer user_id);
	public void doCacheEvict();
}
