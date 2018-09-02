package cn.gxf.spring.struts.mybatis.dao;

import java.util.List;
import java.util.Map;

import org.mybatis.spring.annotation.MapperScan;

import cn.gxf.spring.struts2.integrate.model.IncomeDetail;

@MapperScan
public interface IncomeDetailMBDao/* extends BaseAccountRepository<IncomeDetail>*/ {
	public List<IncomeDetail> getIncomeDetailByUserId(int user_id);
	public List<IncomeDetail> getDetailByPatchUuid(List<String> mxuuidList);
	public List<IncomeDetail> getDetailByPatchAccuuid(List<String> accuuidList);
	public IncomeDetail getIncomeDetailByUuid(String mxuuid);
	
	public void addOne(IncomeDetail incomeDetail);
	public void updateOne(IncomeDetail incomeDetail);
	public void deleteOne(Map<String, Object> paramMap);
	public void deletePatch(List<String> mxuuidList);
}
