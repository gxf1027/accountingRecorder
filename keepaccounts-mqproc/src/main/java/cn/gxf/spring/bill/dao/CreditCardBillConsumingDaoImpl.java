package cn.gxf.spring.bill.dao;


import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import cn.gxf.spring.bill.model.CreditCardBillConsumingInfo;

@Repository("CreditCardBillConsumingDao")
public class CreditCardBillConsumingDaoImpl implements CreditCardBillConsumingDao {

	@Autowired
	private NamedParameterJdbcTemplate namedTemplate;
	
	@Override
	public int isPchExists(String pch) {
		
		String sql = "SELECT count(1) FROM zh_detail_ccbill_consumer c WHERE c.pch = :pch";
		Map<String, String> paramsMap = new HashMap<>();
		paramsMap.put("pch", pch);
		
		int rv = namedTemplate.queryForObject(sql, paramsMap, Integer.class);
		return rv;
	}

	@Override
	public void saveConsumingInfo(CreditCardBillConsumingInfo ccbci) {
		
		String sql = "INSERT INTO zh_detail_ccbill_consumer "
				+ " VALUES(:pch, :thread_id, :rev_time, :send_time, 'Y')";
		
		Map<String, Object> paramsMap = new HashMap<>();
		paramsMap.put("pch", ccbci.getPch());
		paramsMap.put("thread_id", ccbci.getThreadId());
		paramsMap.put("rev_time", ccbci.getRevTime());
		paramsMap.put("send_time", ccbci.getSendTime());
		
		namedTemplate.update(sql, paramsMap);
	}

}
