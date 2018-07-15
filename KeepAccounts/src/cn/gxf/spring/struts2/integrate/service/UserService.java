package cn.gxf.spring.struts2.integrate.service;

import java.util.List;
import java.util.Map;


public interface UserService {
	//public String getUserEmail(Integer user_id);
	public Map<String, String> getUserEmail(List<String> useridList);
}
