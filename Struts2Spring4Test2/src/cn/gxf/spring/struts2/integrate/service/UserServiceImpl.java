package cn.gxf.spring.struts2.integrate.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.gxf.spring.struts.integrate.security.UserLogin;
import cn.gxf.spring.struts2.integrate.dao.UserDao;


@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserDao userDao;
	
	@Override
	public Map<String, String> getUserEmail(List<String> useridList) {
		if (null == useridList || useridList.size() == 0){
			return new HashMap<String, String>();
		}
		
		
		return this.userDao.getUserEmail(useridList);
	}

}
