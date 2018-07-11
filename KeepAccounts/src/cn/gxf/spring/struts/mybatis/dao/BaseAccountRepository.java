package cn.gxf.spring.struts.mybatis.dao;

import java.util.List;
import java.util.Map;


import cn.gxf.spring.struts2.integrate.model.AccountObject;

public interface BaseAccountRepository<T extends AccountObject> {
	public List<T> getIncomeDetailByUserId(int user_id);
	public void addOne(T detail);
	public void updateOne(T detail);
	public void deleteOne(Map<String, Object> paramMap);
}
