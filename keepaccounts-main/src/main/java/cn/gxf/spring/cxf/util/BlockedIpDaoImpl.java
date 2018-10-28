package cn.gxf.spring.cxf.util;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import cn.gxf.spring.cxf.CXFWebServiceController;

@Repository
public class BlockedIpDaoImpl implements BlockedIpDao{
	@Autowired
	private StringRedisTemplate redisTemplate;
	
	private NamedParameterJdbcTemplate namedTemplate;
	private JdbcTemplate jdbcTemplate;
	
	@Autowired
	@Qualifier("datasource")
	private DataSource dataSource;
	
	@PostConstruct
	public void postInit(){
		this.namedTemplate = new NamedParameterJdbcTemplate(dataSource);
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}
	
	@Override
	public long listCount(){
		String sql = "SELECT COUNT(1) FROM cxf_ip_blocked";
		return jdbcTemplate.queryForObject(sql, long.class);
	}
	
	@Cacheable(value="redisCacheCxfIpBlocked")
	@Override
	public Map<String, String> getIpBlocked(){
		String sql = "SELECT ip FROM cxf_ip_blocked";
		List<String> ipList =jdbcTemplate.queryForList(sql, String.class);
		
		Map<String, String> ipMap = new HashMap<>();
		
		for(String ip : ipList){
			ipMap.put(ip, "1");
		}
		return ipMap;
	}
	
	@Override
	public int isIpBlocked(String ip) {
		
		String sql = "SELECT ip FROM cxf_ip_blocked WHERE ip = :ip";
		Map<String, String> paramMap = new HashMap<>();
		paramMap.put("ip", ip);
		
		String queryIp = namedTemplate.queryForObject(sql, paramMap, String.class);
	
		return queryIp == null ? 0 : 1;
	}
	
	@CacheEvict(value="redisCacheCxfIpBlocked")
	@Override
	public void addIpBlocked(String ip){
		String sql = "INSERT INTO cxf_ip_blocked VALUES(:ip, NOW())";
		Map<String, String> paramMap = new HashMap<>();
		paramMap.put("ip", ip);
		namedTemplate.update(sql, paramMap);
	}
	
	@CacheEvict(value="redisCacheCxfIpBlocked")
	@Override
	public void removeIpBlocked(String ip){
		String sql = "DELETE FROM cxf_ip_blocked WHERE ip = :ip";
		Map<String, String> paramMap = new HashMap<>();
		paramMap.put("ip", ip);
		namedTemplate.update(sql, paramMap);
	}

//	@Override
//	@Transactional(value="dsTransactionManager", isolation=Isolation.READ_COMMITTED)
//	public int persistBlockedIpFromRedis() {
//		
//		long count = redisTemplate.opsForSet().size(CXFWebServiceController.CXF_BLOCKED_IP_SET);
//		String sql;
//		
//		if (count > 0){
//			List<String> blockedIps = new ArrayList<>();
//			while (true){
//				String ip = redisTemplate.opsForSet().pop(CXFWebServiceController.CXF_BLOCKED_IP_SET);
//				if (null == ip){
//					break;
//				}
//				blockedIps.add(ip);
//			}
//			
//			// 删除临时表
//			sql = "DELETE FROM cxf_ip_blocked_tmp";
//			jdbcTemplate.execute(sql);
//			
//			// 新数据插入临时表
//			sql = "INSERT INTO cxf_ip_blocked_tmp VALUES(ip,lrrq) ";
//			Date tm = new Date();
//			for (String ip : blockedIps){
//				sql += "("+ip+","+tm+") ";
//			}
//			jdbcTemplate.execute(sql);
//		}
//		
//		if (count > 0){
//			// 更新表
//			sql = "DELETE FROM cxf_ip_blocked WHERE ip NOT IN (SELECT ip FROM cxf_ip_blocked_tmp)";
//			jdbcTemplate.execute(sql);
//			
//			sql = "INSERT INTO cxf_ip_blocked SELECT ip, lrrq FROM cxf_ip_blocked_tmp tmp WHERE tmp.ip NOT IN (SELECT ip FROM cxf_ip_blocked)";
//			jdbcTemplate.execute(sql);
//		}else if (count == 0){
//			sql = "DELETE FROM cxf_ip_blocked";
//			jdbcTemplate.execute(sql);
//		}
//		
//		
//		return 1;
//	}

}
