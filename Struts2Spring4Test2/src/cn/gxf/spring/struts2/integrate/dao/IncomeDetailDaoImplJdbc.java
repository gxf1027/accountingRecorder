package cn.gxf.spring.struts2.integrate.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import cn.gxf.spring.struts2.integrate.model.IncomeDetail;

@Repository("IncomeDetailDao")
public class IncomeDetailDaoImplJdbc implements IncomeDetailDao {

	@Autowired
	private NamedParameterJdbcTemplate namedParameterJdbcTemplate;
	
	@Autowired
	private AccUtil accUtil;
	
	@Override
	public void addOne(IncomeDetail incomeDetail) {
		// TODO Auto-generated method stub
		String sql = "INSERT INTO account_income_detail(mxuuid, accuuid, user_id, je, lb_dm, fkfmc, zh_dm, shijian, bz, yxbz, xgrq)   "
				+ "VALUES(:mxuuid, :accuuid, :user_id, :je, :lb_dm, :fkfmc, :zh_dm, :shijian, :bz, 'Y', :xgrq)";
		Map<String, Object> paramMap = new HashMap<>();
		//paramMap.put("mxuuid", accUtil.generateUuid());
		//paramMap.put("accuuid", accUtil.generateUuid());
		paramMap.put("mxuuid", incomeDetail.getMxuuid());
		paramMap.put("accuuid", incomeDetail.getAccuuid());
		paramMap.put("user_id", incomeDetail.getUser_id());
		paramMap.put("je", incomeDetail.getJe());
		paramMap.put("lb_dm", incomeDetail.getLb_dm());
		paramMap.put("fkfmc", incomeDetail.getFkfmc());
		paramMap.put("zh_dm", incomeDetail.getZh_dm());
		paramMap.put("shijian", incomeDetail.getShijian());
		paramMap.put("bz", incomeDetail.getBz());
		paramMap.put("xgrq", new Date());
		
		namedParameterJdbcTemplate.update(sql, paramMap);
		
	}

	@Override
	public void updateOne(IncomeDetail incomeDetail) {
		// TODO Auto-generated method stub
		String sql = "UPDATE account_income_detail SET je = :je, lb_dm = :lb_dm, fkfmc = :fkfmc,  "
				+ "zh_dm = :zh_dm, shijian = :shijian, bz = :bz, xgrq = :xgrq  "
				+ "WHERE mxuuid = :uuid";
		Map<String, Object> paramMap = new HashMap<>();
		paramMap.put("uuid", incomeDetail.getMxuuid());
		paramMap.put("je", incomeDetail.getJe());
		paramMap.put("lb_dm", incomeDetail.getLb_dm());
		paramMap.put("fkfmc", incomeDetail.getFkfmc());
		paramMap.put("zh_dm", incomeDetail.getZh_dm());
		paramMap.put("shijian", incomeDetail.getShijian());
		paramMap.put("bz", incomeDetail.getBz());
		paramMap.put("xgrq", new Date());
		
		namedParameterJdbcTemplate.update(sql, paramMap);
	}

	@Override
	public void deleteOne(String uuid) {
		// TODO Auto-generated method stub
		String sql = "UPDATE account_income_detail SET yxbz='N', xgrq = :xgrq "
				+ "WHERE mxuuid = :uuid";
		Map<String, Object> paramMap = new HashMap<>();
		paramMap.put("uuid", uuid);
		paramMap.put("xgrq", new Date());
		
		namedParameterJdbcTemplate.update(sql, paramMap);
	}

	@Override
	public List<IncomeDetail> getIncomeDetailByUserId(int user_id) {
		// TODO Auto-generated method stub
		
		String sql = "SELECT * from account_income_detail WHERE user_id = :user_id AND yxbz='Y'";
		Map<String, Object> paramMap = new HashMap<>();
		paramMap.put("user_id", user_id);
		List<IncomeDetail> list = namedParameterJdbcTemplate.query(sql, paramMap, new RowMapper<IncomeDetail>() {

			@Override
			public IncomeDetail mapRow(ResultSet rs, int rowNum) throws SQLException {
				// TODO Auto-generated method stub
				IncomeDetail incomeDetail = new IncomeDetail();
				incomeDetail.setMxuuid(rs.getString("mxuuid"));
				incomeDetail.setAccuuid(rs.getString("accuuid"));
				incomeDetail.setUser_id(rs.getInt("user_id"));
				incomeDetail.setJe(rs.getFloat("je"));
				incomeDetail.setLb_dm(rs.getString("lb_dm"));
				incomeDetail.setFkfmc(rs.getString("fkfmc"));
				incomeDetail.setZh_dm(rs.getString("zh_dm"));
				incomeDetail.setShijian(rs.getDate("shijian"));
				incomeDetail.setBz(rs.getString("bz"));
				incomeDetail.setYxbz("Y");
				incomeDetail.setXgrq(rs.getDate("xgrq"));
				return incomeDetail;
			}
		});
		return list;
	}

	@Override
	public List<IncomeDetail> getIncomeDetailByUserIdAndNy(int user_id, Date ssqq, Date ssqz) {
		// TODO Auto-generated method stub
		String sql = "SELECT * from account_income_detail WHERE user_id = :user_id AND yxbz='Y' AND "
				+ "shijian >= :ssqq AND shijian <= :ssqz";
		Map<String, Object> paramMap = new HashMap<>();
		paramMap.put("user_id", user_id);
		paramMap.put("ssqq", new java.sql.Date(ssqq.getTime()));
		paramMap.put("ssqz", new java.sql.Date(ssqq.getTime()));
		
		List<IncomeDetail> list = namedParameterJdbcTemplate.query(sql, paramMap, new RowMapper<IncomeDetail>() {

			@Override
			public IncomeDetail mapRow(ResultSet rs, int rowNum) throws SQLException {
				// TODO Auto-generated method stub
				IncomeDetail incomeDetail = new IncomeDetail();
				incomeDetail.setMxuuid(rs.getString("mxuuid"));
				incomeDetail.setAccuuid(rs.getString("accuuid"));
				incomeDetail.setUser_id(rs.getInt("user_id"));
				incomeDetail.setJe(rs.getFloat("je"));
				incomeDetail.setLb_dm(rs.getString("lb_dm"));
				incomeDetail.setFkfmc(rs.getString("fkfmc"));
				incomeDetail.setZh_dm(rs.getString("zh_dm"));
				incomeDetail.setShijian(rs.getDate("shijian"));
				incomeDetail.setBz(rs.getString("bz"));
				incomeDetail.setYxbz("Y");
				incomeDetail.setXgrq(rs.getDate("xgrq"));
				return incomeDetail;
			}
		});
		
		return list;
	}

}
