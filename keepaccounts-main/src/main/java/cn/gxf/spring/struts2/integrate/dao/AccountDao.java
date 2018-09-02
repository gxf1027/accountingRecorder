package cn.gxf.spring.struts2.integrate.dao;

import java.util.List;

import cn.gxf.spring.struts.integrate.test.AccountVO;
import cn.gxf.spring.struts2.integrate.model.AccountInfo;
import cn.gxf.spring.struts2.integrate.model.ExpInfo;


public interface AccountDao {
	public void insertOneRecord(AccountVO accountVO);
	public void updateOneRecord(AccountVO accountVO);
	public AccountInfo queryTest();
	public int persistOne(AccountInfo accontInfo);
	public List<AccountInfo> getByNdYf(String nd, String yf);
	public List<AccountInfo> getAll();
	
	public void updateAccountInfo(AccountInfo accountInfo);
	public void updateExpInfo(ExpInfo expInfo);
	public void delAccountInfo(String accuuid);
	public void delExpInfo(String accuuid);
	public AccountInfo getAccExpByAccuuid(String accuuid);
}
