package cn.gxf.spring.struts.mybatis.dao;

import org.mybatis.spring.annotation.MapperScan;

import cn.gxf.spring.struts.integrate.security.UserLogin;

@MapperScan
public interface UserMBDao {
	public UserLogin getUserByName(String username);
}
