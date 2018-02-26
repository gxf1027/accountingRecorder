package cn.gxf.spring.struts.integrate.security;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

public class MyUserDetailService implements UserDetailsService{

	@Autowired
	private NamedParameterJdbcTemplate namedParameterJdbcTemplate;
	
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		// TODO Auto-generated method stub
		if (username == null || username.equals("")){
			return null;
		}
		
		
			String sql = "SELECT * from test.user_ss where username = :name";
			Map<String, Object> paramMap = new HashMap<>();
			paramMap.put("name", username);
			List<UserLogin> user_list = namedParameterJdbcTemplate.query(sql, paramMap, new RowMapper<UserLogin>() {

				@Override
				public UserLogin mapRow(ResultSet rs, int rowNum) throws SQLException {
					// TODO Auto-generated method stub
					UserLogin u = new UserLogin();
					u.setId(rs.getInt("id"));
					u.setUsername(rs.getString("username"));
					u.setPassword(rs.getString("password"));
					u.setEnabled(rs.getString("enabled"));
					u.setDescription(rs.getString("description"));
					return u;
				}
			});
			
			if(user_list.size() == 0){
				//throw new UsernameNotFoundException("用户名不存在");
				//throw new UsernameNotFoundException("用户名不存在");
				
				throw new MyUsernameNotFoundException("用户名不存在");
			}
			
			sql = "SELECT c.role_name FROM user_role_dzb a, user_ss b, role_ss c WHERE a.user_id = b.id AND a.role_id = c.id "
					+ " AND b.username = :name";
			//paramMap.put("name", username);
			List<GrantedAuthority> role_list = namedParameterJdbcTemplate.query(sql, paramMap, new RowMapper<GrantedAuthority>() {

				@Override
				public SimpleGrantedAuthority mapRow(ResultSet rs, int rowNum) throws SQLException {
					// TODO Auto-generated method stub
					
					return new SimpleGrantedAuthority(rs.getString("role_name"));
				}
			});
			
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
