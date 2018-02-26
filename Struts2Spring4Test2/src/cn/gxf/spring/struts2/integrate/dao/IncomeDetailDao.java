package cn.gxf.spring.struts2.integrate.dao;

import java.util.Date;
import java.util.List;

import cn.gxf.spring.struts2.integrate.model.IncomeDetail;

public interface IncomeDetailDao {
	public List<IncomeDetail> getIncomeDetailByUserId(int user_id);
	public List<IncomeDetail> getIncomeDetailByUserIdAndNy(int user_id, Date ssqq, Date ssqz);
	public void addOne(IncomeDetail incomeDetail);
	public void updateOne(IncomeDetail incomeDetail);
	public void deleteOne(String uuid);
}
