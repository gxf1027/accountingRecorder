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
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

@Repository("accountStatisticDaoJdbcImpl")
public class AccountStatisticDaoJdbcImpl implements AccountStatisticDao{

	@Autowired
	private NamedParameterJdbcTemplate namedTemplate;
	
	@Transactional(value="dsTransactionManager", isolation=Isolation.READ_COMMITTED)
	@Override
	public int updateUsersToProcess() {
		
//		String sql = "SELECT COUNT(1) FROM  user_ss u, timedtask_acc_stat s " +
//				"WHERE u.enabled='1' AND u.id = s.userid " +
//				"AND EXISTS (SELECT 1 FROM test.account_detail d WHERE d.user_id = u.id AND (d.rec_dm = '1' or d.rec_dm = '2') AND (d.lrrq > s.proctime or d.xgrq > s.proctime) AND d.yxbz='Y') ";
//		Map<String, Object> paramsMap = new HashMap<>();
//		Integer num1 = namedTemplate.queryForObject(sql, paramsMap, Integer.class);
//		
//		sql = "SELECT COUNT(1)FROM user_ss u " +
//				"WHERE u.enabled='1' " +
//				"AND NOT EXISTS (SELECT 1 FROM timedtask_acc_stat s WHERE u.id = s.userid) " + 
//				"AND EXISTS (SELECT 1 FROM  account_detail d WHERE d.user_id = u.id AND (d.rec_dm = '1' or d.rec_dm = '2') AND d.yxbz='Y')";
//		Integer num2 = namedTemplate.queryForObject(sql, paramsMap, Integer.class);
//		
//		return num1.intValue() + num2.intValue();
		
		Map<String, Object> paramMap = new HashMap<>();
		
		// 先删除临时表中的数据
		String sql = "DELETE FROM user_prepared_stat";
		namedTemplate.update(sql, paramMap);
		
		// 只读取发生业务数据的users
		sql = "INSERT INTO user_prepared_stat " +
				    "SELECT id, username FROM  user_ss u, timedtask_acc_stat s " +
					"WHERE u.enabled='1' AND u.id = s.userid " +
					"AND EXISTS (SELECT 1 FROM test.account_detail d WHERE d.user_id = u.id AND d.rec_dm IN ('1','2') AND (d.lrrq > s.proctime or d.xgrq > s.proctime) AND d.yxbz='Y') " +
					"UNION ALL " +
					"SELECT id, username FROM user_ss u " +
					"WHERE u.enabled='1' " +
					"AND NOT EXISTS (SELECT 1 FROM timedtask_acc_stat s WHERE u.id = s.userid) " + 
					"AND EXISTS (SELECT 1 FROM  account_detail d WHERE d.user_id = u.id AND d.rec_dm IN ('1','2') AND d.yxbz='Y') ";
		
		
		
		long startmillis = System.currentTimeMillis();
		int row = namedTemplate.update(sql, paramMap);
		System.out.println("getUsersNumHavingData: " + (System.currentTimeMillis()-startmillis));
		System.out.println("getUsersNumHavingData users num" + row);
		
		return row;
	}
	
	@Override
	public Map<String, String> getUsersIdNames(Integer start, Integer limit) {
		
		Map<String, Object> paramMap = new HashMap<>();
		paramMap.put("start", start==null ? 0:start);
		paramMap.put("limit", limit==null ? Integer.MAX_VALUE:limit);
		
		// 从临时表中获取要统计的用户
		String sql = "SELECT user_id, user_name FROM user_prepared_stat LIMIT :start, :limit";
		long startmillis = System.currentTimeMillis();
		Map<String, String> userMap = namedTemplate.query(sql, paramMap, new ResultSetExtractor<Map<String, String>>() {

			@Override
			public Map<String, String> extractData(ResultSet rs) throws SQLException, DataAccessException {
				Map<String, String> map = new TreeMap<>();
				while(rs.next()){
					map.put(rs.getString("user_id"), rs.getString("user_name"));
				}
				return map;
			}
		});
		System.out.println("getUsersIdNames: " + (System.currentTimeMillis()-startmillis));
		
		return userMap;
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
