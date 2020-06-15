package cn.gxf.spring.bill.dao;

import java.util.Map;

public interface AuditMsgDao {
	public void save(Map<String, Object> auditMsg);
}
