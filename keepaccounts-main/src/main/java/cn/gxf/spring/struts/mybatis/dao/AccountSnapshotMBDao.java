package cn.gxf.spring.struts.mybatis.dao;

import org.mybatis.spring.annotation.MapperScan;

import cn.gxf.spring.struts2.integrate.model.AccountSnapshot;

@MapperScan
public interface AccountSnapshotMBDao {
	public void addOne(AccountSnapshot snapshot);
}
