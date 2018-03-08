package cn.gxf.spring.struts2.integrate.dao;

import java.util.List;
import java.util.Map;

public interface UserDao {
	public Map<String, String> getUserEmail(List<String> useridList);
}
