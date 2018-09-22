package cn.gxf.spring.struts2.integrate.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Repository;

import cn.gxf.spring.struts.integrate.security.UserLogin;

@Repository
public class UserDaoImplJdbc implements UserDao{

	
	private NamedParameterJdbcTemplate namedTemplate;
	
	@Autowired
	@Qualifier("datasource")
	private DataSource dataSource;
	
	@PostConstruct
	public void postInit(){
		this.namedTemplate = new NamedParameterJdbcTemplate(dataSource);
	}
	
	@Override
	public Map<String, String> getUserEmail(List<String> useridList) {
		String sql = "SELECT id, email FROM  user_ss WHERE id in (:useridList) ";
		Map<String, Object> paramMap = new HashMap<>();
		paramMap.put("useridList", useridList);
		return namedTemplate.query(sql, paramMap, new ResultSetExtractor<Map<String, String>>() {

			@Override
			public Map<String, String> extractData(ResultSet rs) throws SQLException, DataAccessException {
				Map<String, String> map = new TreeMap<String, String>();
				while(rs.next()){
					map.put(rs.getString("id"), rs.getString("email"));
				}
				return map;
			}
		});
		
	}

	@Override
	public Map<String, String> getUsersIdNames() {
		
		String sql = "SELECT id, username FROM  user_ss";
		Map<String, String> paramMap = new HashMap<>();
		return namedTemplate.query(sql, paramMap, new ResultSetExtractor<Map<String, String>>() {

			@Override
			public Map<String, String> extractData(ResultSet rs) throws SQLException, DataAccessException {
				Map<String, String> map = new TreeMap<String, String>();
				while(rs.next()){
					map.put(rs.getString("id"), rs.getString("username"));
				}
				return map;
			}
		});
	}

	@Override
	public List<UserLogin> getUserLoginByName(String userName) {
		
		String sql = "SELECT * from test.user_ss where username = :name";
		Map<String, Object> paramMap = new HashMap<>();
		paramMap.put("name", userName);
		List<UserLogin> user_list = namedTemplate.query(sql, paramMap, new RowMapper<UserLogin>() {

			@Override
			public UserLogin mapRow(ResultSet rs, int rowNum) throws SQLException {
				// TODO Auto-generated method stub
				UserLogin u = new UserLogin();
				u.setId(rs.getInt("id"));
				u.setUsername(rs.getString("username"));
				u.setPassword(rs.getString("password"));
				u.setEnabled(rs.getString("enabled"));
				u.setDescription(rs.getString("description"));
				u.setAttemptLimit(rs.getInt("attempt_limit"));
				return u;
			}
		});
		
		return user_list;
	}

	@Override
	public List<GrantedAuthority> getUserAutoritiesByName(String userName) {
		
		if (null == userName){
			return null;
		}
		
		String sql = "SELECT c.role_name FROM user_role_dzb a, user_ss b, role_ss c "
						+ "WHERE a.user_id = b.id AND a.role_id = c.id "
						+ " AND b.username = :name";
		Map<String, Object> paramMap = new HashMap<>();
		paramMap.put("name", userName);
		
		List<GrantedAuthority> role_list = namedTemplate.query(sql, paramMap, new RowMapper<GrantedAuthority>() {

			@Override
			public SimpleGrantedAuthority mapRow(ResultSet rs, int rowNum) throws SQLException {
				
				return new SimpleGrantedAuthority(rs.getString("role_name"));
			}
		});
		
		return role_list;
	}
	
	@Override
	public int resetUserAttemptLimit(String userName) {
		
		if (null == userName){
			return 0;
		}
		
		String sql = "UPDATE user_ss SET attempt_limit = 3 "
					+ "WHERE username = :name";
		
		Map<String, Object> paramMap = new HashMap<>();
		paramMap.put("name", userName);
		
		int rv = namedTemplate.update(sql, paramMap);
		return rv;
	}

	@Override
	public int decreaseUserAttempts(String userName) {
		
		if (null == userName){
			return 0;
		}
		
		String sql = "UPDATE user_ss SET attempt_limit = attempt_limit-1 "
				+ "WHERE username = :name";
		
		Map<String, Object> paramMap = new HashMap<>();
		paramMap.put("name", userName);
		
		int rv = namedTemplate.update(sql, paramMap);
		return rv;
	}

	@Override
	public int getUserAttempts(String userName) {
		
		if (null == userName){
			return 0;
		}
		
		String sql = "SELECT attempt_limit from user_ss where username = :name";
		Map<String, Object> paramMap = new HashMap<>();
		paramMap.put("name", userName);
		Integer attempts = namedTemplate.queryForObject(sql, paramMap, Integer.class);
		
		return attempts.intValue();
	}

}
