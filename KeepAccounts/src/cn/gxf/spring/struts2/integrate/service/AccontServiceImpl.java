package cn.gxf.spring.struts2.integrate.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.gxf.spring.struts2.integrate.dao.AccountDao;
import cn.gxf.spring.struts2.integrate.model.AccountInfo;

@Service("accountService")
public class AccontServiceImpl implements AccountService {

	@Autowired
	@Qualifier("accountDao")
	private AccountDao accountDao;
	
	@Override
	public List<AccountInfo> listAll() {
		
		
		return accountDao.getAll();
	}
	
	@Override
	public List<AccountInfo> listAll(String nd, String yf) {
		
		return accountDao.getByNdYf(nd, yf);
	}

	@Transactional
	@Override
	public void deleteAccExpInfo(String accuuid) {
		// TODO Auto-generated method stub
		accountDao.delAccountInfo(accuuid);
		accountDao.delExpInfo(accuuid);
	}

	@Override
	public AccountInfo getAccExpInfo(String accuuid)
	{
		return accountDao.getAccExpByAccuuid(accuuid);
	}
	
	
	@Override
	public void saveOneAccExp(AccountInfo accountInfo){
		
		accountDao.persistOne(accountInfo);
	}

	@Transactional
	@Override
	public void updateOneAccExp(AccountInfo accountInfo) {
		if(accountInfo != null && accountInfo.getExpInfo() != null){
			accountDao.updateAccountInfo(accountInfo);
			accountDao.updateExpInfo(accountInfo.getExpInfo());
		}
	}

}
