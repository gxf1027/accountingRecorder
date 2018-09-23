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

import cn.gxf.spring.struts2.integrate.dao.UserDao;

public class MyUserDetailService implements UserDetailsService{

//	@Autowired
//	private NamedParameterJdbcTemplate namedParameterJdbcTemplate;
	
	public static final int attemptsLimit = 3;

	
	@Autowired
	private UserDao userDao;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		// TODO Auto-generated method stub
		if (username == null || username.equals("")){
			return null;
		}
		
		List<UserLogin> user_list = userDao.getUserLoginByName(username);
		
//		String sql = "SELECT * from test.user_ss where username = :name";
//		Map<String, Object> paramMap = new HashMap<>();
//		paramMap.put("name", username);
//		List<UserLogin> user_list = namedParameterJdbcTemplate.query(sql, paramMap, new RowMapper<UserLogin>() {
//
//			@Override
//			public UserLogin mapRow(ResultSet rs, int rowNum) throws SQLException {
//				// TODO Auto-generated method stub
//				UserLogin u = new UserLogin();
//				u.setId(rs.getInt("id"));
//				u.setUsername(rs.getString("username"));
//				u.setPassword(rs.getString("password"));
//				u.setEnabled(rs.getString("enabled"));
//				u.setDescription(rs.getString("description"));
//				u.setAttemptLimit(rs.getInt("attempt_limit"));
//				return u;
//			}
//		});
		
		if(user_list.size() == 0){
			throw new MyUsernameNotFoundException("用户名不存在");
		}
		
		if(user_list.size() > 1){
			throw new MyUserAuthorityException("用户存在异常");
		}
		
		if(user_list.get(0).getAttemptLimit() <= 0){
			throw new MyUserAuthorityException("用户被锁定, 无法登陆");
		}
		
		List<GrantedAuthority> role_list = userDao.getUserAutoritiesByName(username);
		
//		sql = "SELECT c.role_name FROM user_role_dzb a, user_ss b, role_ss c WHERE a.user_id = b.id AND a.role_id = c.id "
//				+ " AND b.username = :name";
//		
//		List<GrantedAuthority> role_list = namedParameterJdbcTemplate.query(sql, paramMap, new RowMapper<GrantedAuthority>() {
//
//			@Override
//			public SimpleGrantedAuthority mapRow(ResultSet rs, int rowNum) throws SQLException {
//				
//				return new SimpleGrantedAuthority(rs.getString("role_name"));
//			}
//		});
		
		if(role_list.size() == 0){
			
			throw new MyRoleNotFound("此用户无有效权限");
			
		}
		
		UserLogin userLogin = new UserLogin();
		userLogin.setId(user_list.get(0).getId());
		userLogin.setUsername(user_list.get(0).getUsername());
		userLogin.setPassword(user_list.get(0).getPassword());
		userLogin.setEnabled(user_list.get(0).getEnabled());
		userLogin.setDescription(user_list.get(0).getDescription());
		userLogin.setAuthorities(role_list);
	
		return userLogin;

	}
	
}
