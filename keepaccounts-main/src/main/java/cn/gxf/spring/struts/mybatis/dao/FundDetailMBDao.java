package cn.gxf.spring.struts.mybatis.dao;

import java.util.List;
import java.util.Map;

import org.mybatis.spring.annotation.MapperScan;

import cn.gxf.spring.struts2.integrate.model.FundDetail;

@MapperScan
public interface FundDetailMBDao {
	public FundDetail getFundDetailByUuid(String uuid);
	public void addOne(FundDetail fundDetail);
	public void updateOne(FundDetail fundDetail);
	public void deleteOne(String transferuuid);
	public void deletePatch(List<String> uuidList);
}
