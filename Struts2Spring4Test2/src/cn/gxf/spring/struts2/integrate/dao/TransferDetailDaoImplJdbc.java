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

import cn.gxf.spring.struts2.integrate.model.TransferDetail;

@Repository("transferDetailDao")
public class TransferDetailDaoImplJdbc implements TransferDetailDao{

	@Autowired
	private NamedParameterJdbcTemplate namedParameterJdbcTemplate;
	
	@Autowired
	private AccUtil accUtil;
	
	@Override
	public List<TransferDetail> getPaymentDetailByUserId(int user_id) {
		// TODO Auto-generated method stub
		
		String sql = "SELECT * from account_transfer_detail WHERE user_id = :user_id AND yxbz='Y'";
		Map<String, Object> paramMap = new HashMap<>();
		paramMap.put("user_id", user_id);
		List<TransferDetail> list = namedParameterJdbcTemplate.query(sql, paramMap, new RowMapper<TransferDetail>() {

			@Override
			public TransferDetail mapRow(ResultSet rs, int rowNum) throws SQLException {
				// TODO Auto-generated method stub
				TransferDetail transferDetail = new TransferDetail();
				transferDetail.setMxuuid(rs.getString("mxuuid"));
				transferDetail.setAccuuid(rs.getString("accuuid"));
				transferDetail.setUser_id(rs.getInt("user_id"));
				transferDetail.setJe(rs.getFloat("je"));
				transferDetail.setSrcZh_dm(rs.getString("srcZh_dm"));
				transferDetail.setTgtZh_dm(rs.getString("tgtZh_dm"));
				transferDetail.setShijian(rs.getDate("shijian"));
				transferDetail.setBz(rs.getString("bz"));
				transferDetail.setYxbz("Y");
				transferDetail.setXgrq(rs.getDate("xgrq"));
				return transferDetail;
			}
		});
		return list;
	}

	@Override
	public List<TransferDetail> getPaymentDetailByUserIdAndNy(int user_id, Date ssqq, Date ssqz) {
		// TODO Auto-generated method stub
		String sql = "SELECT * from account_transfer_detail WHERE user_id = :user_id AND yxbz='Y' AND "
				+ "shijian >= :ssqq AND shijian <= :ssqz";
		Map<String, Object> paramMap = new HashMap<>();
		paramMap.put("user_id", user_id);
		paramMap.put("ssqq", new java.sql.Date(ssqq.getTime()));
		paramMap.put("ssqz", new java.sql.Date(ssqq.getTime()));
		
		List<TransferDetail> list = namedParameterJdbcTemplate.query(sql, paramMap, new RowMapper<TransferDetail>() {

			@Override
			public TransferDetail mapRow(ResultSet rs, int rowNum) throws SQLException {
				// TODO Auto-generated method stub
				TransferDetail transferDetail = new TransferDetail();
				transferDetail.setMxuuid(rs.getString("mxuuid"));
				transferDetail.setAccuuid(rs.getString("accuuid"));
				transferDetail.setUser_id(rs.getInt("user_id"));
				transferDetail.setJe(rs.getFloat("je"));
				transferDetail.setSrcZh_dm(rs.getString("srcZh_dm"));
				transferDetail.setTgtZh_dm(rs.getString("tgtZh_dm"));
				transferDetail.setShijian(rs.getDate("shijian"));
				transferDetail.setBz(rs.getString("bz"));
				transferDetail.setYxbz("Y");
				transferDetail.setXgrq(rs.getDate("xgrq"));
				return transferDetail;
			}
		});
		
		return list;
	}

	@Override
	public void saveOne(TransferDetail transferDetail) {
		// TODO Auto-generated method stub
		String sql = "INSERT INTO account_transfer_detail(mxuuid, accuuid, user_id, je, srcZh_dm, tgtZh_dm, shijian, bz, yxbz, xgrq)   "
				+ "VALUES(:mxuuid, :accuuid, :user_id, :je, :srczh_dm, :tgtzh_dm, :shijian, :bz, 'Y', :xgrq)";
		Map<String, Object> paramMap = new HashMap<>();
		paramMap.put("mxuuid", accUtil.generateUuid());
		paramMap.put("accuuid", accUtil.generateUuid());
		paramMap.put("user_id", transferDetail.getUser_id());
		paramMap.put("je", transferDetail.getJe());
		paramMap.put("srczh_dm", transferDetail.getSrcZh_dm());
		paramMap.put("tgtzh_dm", transferDetail.getTgtZh_dm());
		paramMap.put("shijian", transferDetail.getShijian());
		paramMap.put("bz", transferDetail.getBz());
		paramMap.put("xgrq", new Date());
		
		namedParameterJdbcTemplate.update(sql, paramMap);
	}

	@Override
	public void updateOne(TransferDetail transferDetail) {
		// TODO Auto-generated method stub
		String sql = "UPDATE account_transfer_detail SET je = :je,  srcZh_dm = :srczh_dm,  "
				+ "tgtZh_dm = :tgtzh_dm, shijian = :shijian, bz = :bz, xgrq = :xgrq  "
				+ "WHERE mxuuid = :uuid";
		Map<String, Object> paramMap = new HashMap<>();
		paramMap.put("uuid", transferDetail.getMxuuid());
		paramMap.put("je", transferDetail.getJe());
		paramMap.put("srczh_dm", transferDetail.getSrcZh_dm());
		paramMap.put("tgtzh_dm", transferDetail.getTgtZh_dm());
		paramMap.put("shijian", transferDetail.getShijian());
		paramMap.put("bz", transferDetail.getBz());
		paramMap.put("xgrq", new Date());
		
		namedParameterJdbcTemplate.update(sql, paramMap);
	}

	@Override
	public void deleteOne(String uuid) {
		// TODO Auto-generated method stub
		String sql = "UPDATE account_transfer_detail SET yxbz='N', xgrq = :xgrq "
				+ "WHERE mxuuid = :uuid";
		Map<String, Object> paramMap = new HashMap<>();
		paramMap.put("uuid", uuid);
		paramMap.put("xgrq", new Date());
		
		namedParameterJdbcTemplate.update(sql, paramMap);
	}

}
