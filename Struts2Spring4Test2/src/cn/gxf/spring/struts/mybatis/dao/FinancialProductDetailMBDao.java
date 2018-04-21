package cn.gxf.spring.struts.mybatis.dao;

import java.util.List;

import org.mybatis.spring.annotation.MapperScan;

import cn.gxf.spring.struts2.integrate.model.FinancialProductDetail;


@MapperScan
public interface FinancialProductDetailMBDao {
	public FinancialProductDetail getFinancialProductDetailByUuid(String uuid);
	public List<FinancialProductDetail> getFinancialProductDetailByUserId(Integer userId);
	public void addOne(FinancialProductDetail financialProductDetail);
	public void updateOne(FinancialProductDetail financialProductDetail);
	public void deleteOne(String transferuuid);
	public void deletePatch(List<String> uuidList);
}
