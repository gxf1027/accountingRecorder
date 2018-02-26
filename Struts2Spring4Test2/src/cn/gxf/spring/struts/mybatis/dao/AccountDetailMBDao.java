package cn.gxf.spring.struts.mybatis.dao;


import java.util.List;
import java.util.Map;

import org.mybatis.spring.annotation.MapperScan;

import cn.gxf.spring.struts2.integrate.model.AccountingDetail;

@MapperScan
public interface AccountDetailMBDao {
	public List<AccountingDetail> getAccountingDetailByUserId(int user_id);
	public AccountingDetail getAccountingDetailByUuid(String accuuid);
	public List<AccountingDetail> getAccountingDetailByPatchUuid(List<String> accuuidList);
	public void addOne(AccountingDetail accountingDetail);
	public void updateOne(AccountingDetail accountingDetail);
	public void deleteOne(Map<String, Object> paramMap);
	public void deletePatch(List<String> accuuidList);
}
