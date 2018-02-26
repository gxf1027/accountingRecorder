package cn.gxf.spring.struts2.integrate.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.imageio.spi.RegisterableService;
import javax.imageio.spi.ServiceRegistry;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import cn.gxf.spring.struts.integrate.security.UserLogin;

@Repository
public class RegisterUserDaoImpl implements RegisterUserDao {

	@Autowired
	private NamedParameterJdbcTemplate namedTemplate;

	@Override
	public List<UserLogin> getExistUserByUserName(String userName) {
		
		String sql = "SELECT * FROM user_ss WHERE username = :userName";
		Map<String, Object> paramMap = new HashMap<>();
		paramMap.put("userName", userName);
		return namedTemplate.query(sql, paramMap, new RowMapper<UserLogin>() {

			@Override
			public UserLogin mapRow(ResultSet rs, int rowNum) throws SQLException {
				
				UserLogin user = new UserLogin();
				user.setId(rs.getInt("id"));
				user.setUsername(rs.getString("username"));
				return user;
			}
		});
	}

	@Override
	public int generateUserId() {
		String sql = "SELECT max(id) from user_ss";
		Integer newid = namedTemplate.query(sql, new ResultSetExtractor<Integer>() {

			@Override
			public Integer extractData(ResultSet rs) throws SQLException, DataAccessException {
				rs.next();
				rs.getInt(1);
				return rs.getInt(1);
			}
		});
		return newid+1;
	}

	@Override
	public void saveUser(UserLogin userLogin) {
		String sql = "INSERT INTO user_ss(id,username, password, enabled, description) VALUES(:id, :username, :password, '1', null)";
		
		Map<String, Object> paramMap = new HashMap<>();
		paramMap.put("id", userLogin.getId());
		paramMap.put("username", userLogin.getUsername());
		paramMap.put("password", userLogin.getPassword());
		
		namedTemplate.update(sql, paramMap);
		
	}

	@Override
	public void saveRole(int user_id, List<Integer> roles) {
		String sql = "INSERT INTO user_role_dzb(user_id, role_id) VALUES(:user_id, :role_id)";
		Map<String, Object> paramMap = new HashMap<>();
		paramMap.put("user_id", user_id);
		
		for (Integer role_id : roles){
			
			paramMap.put("role_id", role_id);
			namedTemplate.update(sql, paramMap);
		}
		
	}

	/*@Transactional
	@Override
	public void registerUser(UserLogin userLogin) {
		
		String sql = "SELECT max(id) from user_ss";
		Integer newid = namedTemplate.query(sql, new ResultSetExtractor<Integer>() {

			@Override
			public Integer extractData(ResultSet rs) throws SQLException, DataAccessException {
				rs.next();
				rs.getInt(1);
				return rs.getInt(1);
			}
		});
		
		newid += 1;
		userLogin.setId(newid.intValue());
		System.out.println("newid: " + newid);
		
		sql = "INSERT INTO user_ss(id,username, password, enabled, description) VALUES(:id, :username, :password, '1', null)";
		
		Map<String, Object> paramMap = new HashMap<>();
		paramMap.put("id", userLogin.getId());
		paramMap.put("username", userLogin.getUsername());
		paramMap.put("password", userLogin.getPassword());
		//namedTemplate.update(sql, paramMap);
		
		sql = "INSERT INTO user_role_dzb(user_id, role_id) VALUES(:id, :role_id)";
		paramMap.put("role_id", 2);
		//namedTemplate.update(sql, paramMap);
	}*/
	

}
