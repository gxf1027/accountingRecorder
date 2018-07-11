package cn.gxf.spring.struts2.integrate.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class UserDaoImplJdbc implements UserDao{

	@Autowired
	private NamedParameterJdbcTemplate namedTemplate;
	
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

}
