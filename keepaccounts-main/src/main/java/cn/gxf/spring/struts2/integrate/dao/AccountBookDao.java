package cn.gxf.spring.struts2.integrate.dao;

import java.util.List;

import cn.gxf.spring.struts2.integrate.model.AccountBook;

public interface AccountBookDao {
	public List<AccountBook> getZhInfo(int user_id);
	public AccountBook getZhInfo(String zh_dm);
	/*
	* delt_je: ÔöÁ¿Öµ
	*/
	public void updateYe(String zh_dm, float delt_je);
	
	public void saveOne(AccountBook accountBook);
}
