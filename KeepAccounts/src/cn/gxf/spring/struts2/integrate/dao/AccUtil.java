package cn.gxf.spring.struts2.integrate.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Repository;

@Repository
public class AccUtil {
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	public String generateUuid() {
		String sql ="select replace(uuid(),'-','')";
		SqlRowSet rs = jdbcTemplate.queryForRowSet(sql);
		rs.next();
		String uuid = rs.getString(1);
		return uuid;
	}
}
