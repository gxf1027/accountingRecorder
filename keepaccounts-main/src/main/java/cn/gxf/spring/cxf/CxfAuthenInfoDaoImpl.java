package cn.gxf.spring.cxf;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository("cxfAuthenInfoDaoImplJdbc")
public class CxfAuthenInfoDaoImpl implements CxfAuthenInfoDao{
	
	@Autowired
	private NamedParameterJdbcTemplate namedTemplate;

	@Cacheable(value="dmCache")
	@Override
	public Map<String, String> getAuthenInfo(String username) {
		String sql = "SELECT username, confidential_code "
				+ "FROM  cxf_authentication WHERE username = :username AND yxbz='Y'";
		Map<String, Object> paramMap = new HashMap<>();
		paramMap.put("username", username);
		
		return namedTemplate.query(sql, paramMap, new ResultSetExtractor<Map<String, String>>() {

			@Override
			public Map<String, String> extractData(ResultSet rs) throws SQLException, DataAccessException {
				Map<String, String> map = new TreeMap<String, String>();
				while(rs.next()){
					map.put(rs.getString("username"), rs.getString("confidential_code"));
				}
				
				if (map.size() > 1){
					throw new SQLException("cn.gxf.spring.cxf.CxfAuthenInfoDaoImpl 返回非唯一结果");
				}
				return map;
			}
		});
	}

	@Cacheable(value="dmCache")
	@Override
	public Map<String, String> getAuthenInfo() {
		String sql = "SELECT username, confidential_code "
				+ "FROM  cxf_authentication WHERE yxbz='Y'";
		Map<String, Object> paramMap = new HashMap<>();
		
		return namedTemplate.query(sql, paramMap, new ResultSetExtractor<Map<String, String>>() {

			@Override
			public Map<String, String> extractData(ResultSet rs) throws SQLException, DataAccessException {
				Map<String, String> map = new TreeMap<String, String>();
				while(rs.next()){
					map.put(rs.getString("username"), rs.getString("confidential_code"));
				}
				return map;
			}
		});
	}

	@Cacheable(value="dmCache")
	@Override
	public int accessValidating(String username, String interfaceName, String methodName) {
		
		String sql = "SELECT COUNT(1) FROM cxf_authentication a, cxf_interface b, cxf_interface_control c "
				+ " WHERE a.username = c.cxf_username AND c.cxf_interface_uuid = b.uuid "
				+ " AND a.username = :username AND b.cxf_interface_name = :interfaceName AND b.cxf_method_name = :methodName "
				+ " AND a.yxbz='Y' AND b.yxbz='Y' AND c.yxbz='Y' ";
		
		Map<String, Object> paramMap = new HashMap<>();
		paramMap.put("username", username);
		paramMap.put("interfaceName", interfaceName);
		paramMap.put("methodName", methodName);
		
		Integer rv = 0;
		try {
			rv = namedTemplate.queryForObject(sql, paramMap, Integer.class);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		
		
		return rv.intValue()>=1 ? 1 : 0;
	}
	
	
}
