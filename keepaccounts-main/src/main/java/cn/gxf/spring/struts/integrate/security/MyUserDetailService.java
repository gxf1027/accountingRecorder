package cn.gxf.spring.struts.integrate.security;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.struts2.ServletActionContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import cn.gxf.spring.struts.mybatis.dao.UserMBDao;
import cn.gxf.spring.struts2.integrate.dao.UserDao;

public class MyUserDetailService implements UserDetailsService{

//	@Autowired
//	private NamedParameterJdbcTemplate namedParameterJdbcTemplate;
	
	public static final int attemptsLimit = 5;

	@Autowired
	private UserMBDao userDao;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		// TODO Auto-generated method stub
		if (username == null || username.equals("")){
			return null;
		}
		
		UserLogin user = userDao.getUserByName(username);
		
		if(user == null){
			throw new MyUsernameNotFoundException("用户名不存在");
		}
		
		
		if(user.getAttemptLimit() <= 0){
			throw new MyUserAuthorityException("用户被锁定, 无法登陆");
		}
		
		
		if(user.getAuthorities() == null || user.getAuthorities().size() == 0){
			
			throw new MyRoleNotFound("此用户无有效权限");
			
		}
		
		return user;

	}
	
}
