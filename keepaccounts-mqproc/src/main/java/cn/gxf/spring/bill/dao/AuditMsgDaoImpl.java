package cn.gxf.spring.bill.dao;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class AuditMsgDaoImpl implements AuditMsgDao {

	@Autowired
	private NamedParameterJdbcTemplate namedTemplate;
	
	@Override
	public void save(Map<String, Object> auditMsg) {
		// TODO Auto-generated method stub
		String sql = "INSERT INTO audit_log(uuid, event_type, event_info, user_id, proc_time) "
				+ "VALUES (:uuid, :event_type, :event_info, :user_id, :proc_time)";
		
		namedTemplate.update(sql, auditMsg);
		
	}

}
