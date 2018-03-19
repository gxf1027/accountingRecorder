package cn.gxf.spring.struts2.integrate.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.gxf.spring.struts.integrate.security.UserLogin;
import cn.gxf.spring.struts2.integrate.dao.RegisterUserDao;

@Service
public class RegisterUserServiceImpl implements RegisterUserService {

	@Autowired
	private RegisterUserDao registerDao;
	
	@Transactional("dsTransactionManager")
	@Override
	public int registerUser(UserLogin user) {
		
		List<UserLogin> users = registerDao.getExistUserByUserName(user.getUsername());
		if (users.size() > 0){
			System.out.println("用户："+user.getUsername()+ " 已存在");
			return -1;
		}
		
		int newid = registerDao.generateUserId();
		user.setId(newid);
		registerDao.saveUser(user);
		
		List<Integer> roles = new ArrayList<>();
		roles.add(2); // ROLE_USER
		registerDao.saveRole(user.getId(), roles );
		return 1;
	}

	@Override
	public int isUserNameExists(String username) {
		
		List<UserLogin> users = registerDao.getExistUserByUserName(username);
		if (users.size() > 0){
			System.out.println("用户："+username+ " 已存在");
			return 1;
		}
		return 0;
	}

}
