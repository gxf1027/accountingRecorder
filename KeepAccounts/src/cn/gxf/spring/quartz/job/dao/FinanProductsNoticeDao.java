package cn.gxf.spring.quartz.job.dao;

import org.apache.ibatis.annotations.Param;
import org.mybatis.spring.annotation.MapperScan;

import cn.gxf.spring.quartz.job.model.FinancialProductsNotice;

@MapperScan
public interface FinanProductsNoticeDao {
	public void saveNotice(FinancialProductsNotice notice);
	public int isSent(@Param("pch") String pch, @Param("user_id") Integer user_id);
	public void setMailed(@Param("uuid") String uuid);
}
