package cn.gxf.spring.struts.mybatis.dao;

import org.mybatis.spring.annotation.MapperScan;
import cn.gxf.spring.struts2.integrate.model.FinancialProductIncomeDetail;


@MapperScan
public interface FinancialProductIncomeDetailMBDao {
	public FinancialProductIncomeDetail getFinancialProductIncomeByUuid(String uuid);
	public FinancialProductIncomeDetail getFinancialProductIncomeByIncomeUuid(String incomeUuid);
	public void addOne(FinancialProductIncomeDetail financialProductDetail);
	public void updateOne(FinancialProductIncomeDetail financialProductDetail);
	public void deleteOne(String uuid);
	public void deleteOneByIncomeUuid(String incomeuuid);
}
