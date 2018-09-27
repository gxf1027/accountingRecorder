package cn.gxf.spring.quartz.job.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository("accountStatisticDaoJdbcImpl")
public class AccountStatisticDaoJdbcImpl implements AccountStatisticDao{

	@Autowired
	private NamedParameterJdbcTemplate namedTemplate;
	
	@Override
	public int getUsersNumHavingData() {
		
		String sql = "SELECT COUNT(1) FROM  user_ss u, timedtask_acc_stat s " +
				"WHERE u.enabled='1' AND u.id = s.userid " +
				"AND EXISTS (SELECT 1 FROM test.account_detail d WHERE d.user_id = u.id AND (d.rec_dm = '1' or d.rec_dm = '2') AND (d.lrrq > s.proctime or d.xgrq > s.proctime) AND d.yxbz='Y') ";
		Map<String, Object> paramsMap = new HashMap<>();
		Integer num1 = namedTemplate.queryForObject(sql, paramsMap, Integer.class);
		
		sql = "SELECT COUNT(1)FROM user_ss u " +
				"WHERE u.enabled='1' " +
				"AND NOT EXISTS (SELECT 1 FROM timedtask_acc_stat s WHERE u.id = s.userid) " + 
				"AND EXISTS (SELECT 1 FROM  account_detail d WHERE d.user_id = u.id AND (d.rec_dm = '1' or d.rec_dm = '2') AND d.yxbz='Y')";
		Integer num2 = namedTemplate.queryForObject(sql, paramsMap, Integer.class);
		
		return num1.intValue() + num2.intValue();
	}
	
	@Override
	public Map<String, String> getUsersIdNames(Integer start, Integer limit) {
		
		// 只读取发生业务数据的users
		String sql = "SELECT id, username FROM  user_ss u, timedtask_acc_stat s " +
					"WHERE u.enabled='1' AND u.id = s.userid " +
					"AND EXISTS (SELECT 1 FROM test.account_detail d WHERE d.user_id = u.id AND (d.rec_dm = '1' or d.rec_dm = '2') AND (d.lrrq > s.proctime or d.xgrq > s.proctime) AND d.yxbz='Y') " +
					"UNION ALL " +
					"SELECT id, username FROM user_ss u " +
					"WHERE u.enabled='1' " +
					"AND NOT EXISTS (SELECT 1 FROM timedtask_acc_stat s WHERE u.id = s.userid) " + 
					"AND EXISTS (SELECT 1 FROM  account_detail d WHERE d.user_id = u.id AND (d.rec_dm = '1' or d.rec_dm = '2') AND d.yxbz='Y') " +
					"LIMIT :start, :limit";
		
		Map<String, Object> paramMap = new HashMap<>();
		paramMap.put("start", start==null ? 0:start);
		paramMap.put("limit", limit==null ? Integer.MAX_VALUE:limit);
		
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

//	@Override
//	public Map<String, String> getUsersIdNamesAll() {
//		// 当users数量非常大时，无法一次读取！！！
//		String sql = "SELECT id, username FROM  user_ss WHERE enabled='1'";
//		
//		Map<String, String> paramMap = new HashMap<>();
//		return namedTemplate.query(sql, paramMap, new ResultSetExtractor<Map<String, String>>() {
//
//			@Override
//			public Map<String, String> extractData(ResultSet rs) throws SQLException, DataAccessException {
//				Map<String, String> map = new TreeMap<String, String>();
//				while(rs.next()){
//					map.put(rs.getString("id"), rs.getString("username"));
//				}
//				return map;
//			}
//		});
//		
//	}

}
