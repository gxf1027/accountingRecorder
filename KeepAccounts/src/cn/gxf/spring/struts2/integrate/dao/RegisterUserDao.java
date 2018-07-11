package cn.gxf.spring.struts2.integrate.dao;

import java.util.List;

import cn.gxf.spring.struts.integrate.security.UserLogin;

public interface RegisterUserDao {
	public List<UserLogin> getExistUserByUserName(String userName);
	public int generateUserId();
	public void saveUser(UserLogin userLogin);
	public void saveRole(int user_id, List<Integer> roles);
	//public void registerUser(UserLogin userLogin);
}
