package cn.gxf.spring.struts.mybatis.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.MapKey;
import org.mybatis.spring.annotation.MapperScan;

import cn.gxf.spring.struts2.integrate.model.StatByMonth;

@MapperScan
public interface AccountStatMBDao {
	
	public List<StatByMonth>  getPaymentStatByMonth(String nd);
	
	
	public List<StatByMonth> getIncomeStatByMonth(String nd);
}
