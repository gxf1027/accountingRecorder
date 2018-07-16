package cn.gxf.spring.bill.dao;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class MailDaoImplJdbc implements MailDao {

	@Autowired
	private NamedParameterJdbcTemplate namedTemplate;
	
	@Override
	public void testXA() {
		String sql = "INSERT INTO testmqxa values(uuid(), now())";
		Map<String, Object> paramMap = new HashMap<>();
		namedTemplate.update(sql, paramMap);
	}

}
