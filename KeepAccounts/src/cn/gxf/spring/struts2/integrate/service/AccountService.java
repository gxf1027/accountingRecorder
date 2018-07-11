package cn.gxf.spring.struts2.integrate.service;

import java.util.List;


import cn.gxf.spring.struts2.integrate.model.AccountInfo;

public interface AccountService {
//	public void save(AccountVO accountVO);
//	public void delete(String id);
//	public void update(AccountVO accountVO);
	public List<AccountInfo> listAll();
	
	public List<AccountInfo> listAll(String nd, String yf);
	
	public void deleteAccExpInfo(String accuuid);
	
	public AccountInfo getAccExpInfo(String accuuid);
	
	public void saveOneAccExp(AccountInfo accountInfo);
	
	public void updateOneAccExp(AccountInfo accountInfo);
}
