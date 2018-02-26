package cn.gxf.spring.struts.mybatis.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.mybatis.spring.annotation.MapperScan;

import cn.gxf.spring.struts2.integrate.model.StatByCategory;
import cn.gxf.spring.struts2.integrate.model.StatByMonth;

@MapperScan
public interface StatNdYfMBDao {
	public void procAccStatByNd(@Param("nd") String nd,@Param("user_id") Integer user_id);
	public List<StatByMonth> getNdYfStat(@Param("nd") String nd,@Param("user_id") Integer user_id);
	public List<StatByCategory> getPaymentStatOnDl(@Param("nd") String nd,@Param("user_id") Integer user_id);
	public List<StatByCategory> getIncomeStatOnSrlb(@Param("nd") String nd,@Param("user_id") Integer user_id);
}
