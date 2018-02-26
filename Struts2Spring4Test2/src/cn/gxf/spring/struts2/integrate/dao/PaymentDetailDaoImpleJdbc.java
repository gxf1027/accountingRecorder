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

import cn.gxf.spring.struts2.integrate.model.PaymentDetail;

@Repository("PaymentDetailDao")
public class PaymentDetailDaoImpleJdbc implements PaymentDetailDao{

	@Autowired
	private NamedParameterJdbcTemplate namedParameterJdbcTemplate;
	
	@Autowired
	private AccUtil accUtil;
	
	@Override
	public void saveOne(PaymentDetail paymentDetail) {
		
		String sql = "INSERT INTO account_payment_detail(mxuuid, accuuid, user_id, je, dl_dm, xl_dm, seller, zh_dm, shijian, bz, yxbz, xgrq)   "
				+ "VALUES(:mxuuid, :accuuid, :user_id, :je, :dl_dm, :xl_dm, :seller, :zh_dm, :shijian, :bz, 'Y', :xgrq)";
		Map<String, Object> paramMap = new HashMap<>();
		paramMap.put("mxuuid", accUtil.generateUuid());
		paramMap.put("accuuid", accUtil.generateUuid());
		paramMap.put("user_id", paymentDetail.getUser_id());
		paramMap.put("je", paymentDetail.getJe());
		paramMap.put("dl_dm", paymentDetail.getDl_dm());
		paramMap.put("xl_dm", paymentDetail.getXl_dm());
		paramMap.put("seller", paymentDetail.getSeller());
		paramMap.put("zh_dm", paymentDetail.getZh_dm());
		paramMap.put("shijian", paymentDetail.getShijian());
		paramMap.put("bz", paymentDetail.getBz());
		paramMap.put("xgrq", new Date());
		
		namedParameterJdbcTemplate.update(sql, paramMap);
	}

	@Override
	public void deleteOne(String uuid) {
		// TODO Auto-generated method stub
		String sql = "UPDATE account_payment_detail SET yxbz='N', xgrq = :xgrq "
				+ "WHERE mxuuid = :uuid";
		Map<String, Object> paramMap = new HashMap<>();
		paramMap.put("uuid", uuid);
		paramMap.put("xgrq", new Date());
		
		namedParameterJdbcTemplate.update(sql, paramMap);
	}

	@Override
	public void updateOne(PaymentDetail paymentDetail) {
		// TODO Auto-generated method stub
		String sql = "UPDATE account_payment_detail SET je = :je, dl_dm = :dl_dm, xl_dm = :xl_dm, seller= :seller,  "
				+ "zh_dm = :zh_dm, shijian = :shijian, bz = :bz, xgrq = :xgrq  "
				+ "WHERE mxuuid = :uuid";
		Map<String, Object> paramMap = new HashMap<>();
		paramMap.put("uuid", paymentDetail.getMxuuid());
		paramMap.put("je", paymentDetail.getJe());
		paramMap.put("dl_dm", paymentDetail.getDl_dm());
		paramMap.put("xl_dm", paymentDetail.getXl_dm());
		paramMap.put("seller", paymentDetail.getSeller());
		paramMap.put("zh_dm", paymentDetail.getZh_dm());
		paramMap.put("shijian", paymentDetail.getShijian());
		paramMap.put("bz", paymentDetail.getBz());
		paramMap.put("xgrq", new Date());
		
		namedParameterJdbcTemplate.update(sql, paramMap);
	}
	
	@Override
	public List<PaymentDetail> getPaymentDetailByUserId(int user_id) {
		// TODO Auto-generated method stub
		
		String sql = "SELECT * from account_payment_detail WHERE user_id = :user_id AND yxbz='Y'";
		Map<String, Object> paramMap = new HashMap<>();
		paramMap.put("user_id", user_id);
		List<PaymentDetail> list = namedParameterJdbcTemplate.query(sql, paramMap, new RowMapper<PaymentDetail>() {

			@Override
			public PaymentDetail mapRow(ResultSet rs, int rowNum) throws SQLException {
				// TODO Auto-generated method stub
				PaymentDetail paymentDetail = new PaymentDetail();
				paymentDetail.setMxuuid(rs.getString("mxuuid"));
				paymentDetail.setAccuuid(rs.getString("accuuid"));
				paymentDetail.setUser_id(rs.getInt("user_id"));
				paymentDetail.setJe(rs.getFloat("je"));
				paymentDetail.setDl_dm(rs.getString("dl_dm"));
				paymentDetail.setXl_dm(rs.getString("xl_dm"));
				paymentDetail.setSeller(rs.getString("seller"));
				paymentDetail.setZh_dm(rs.getString("zh_dm"));
				paymentDetail.setShijian(rs.getDate("shijian"));
				paymentDetail.setBz(rs.getString("bz"));
				paymentDetail.setYxbz("Y");
				paymentDetail.setXgrq(rs.getDate("xgrq"));
				return paymentDetail;
			}
		});
		return list;
	}

	@Override
	public List<PaymentDetail> getPaymentDetailByUserIdAndNy(int user_id, Date ssqq, Date ssqz) {
		String sql = "SELECT * from account_payment_detail WHERE user_id = :user_id AND yxbz='Y' AND "
				+ "shijian >= :ssqq AND shijian <= :ssqz";
		Map<String, Object> paramMap = new HashMap<>();
		paramMap.put("user_id", user_id);
		paramMap.put("ssqq", new java.sql.Date(ssqq.getTime()));
		paramMap.put("ssqz", new java.sql.Date(ssqq.getTime()));
		
		List<PaymentDetail> list = namedParameterJdbcTemplate.query(sql, paramMap, new RowMapper<PaymentDetail>() {

			@Override
			public PaymentDetail mapRow(ResultSet rs, int rowNum) throws SQLException {
				// TODO Auto-generated method stub
				PaymentDetail paymentDetail = new PaymentDetail();
				paymentDetail.setMxuuid(rs.getString("mxuuid"));
				paymentDetail.setAccuuid(rs.getString("accuuid"));
				paymentDetail.setUser_id(rs.getInt("user_id"));
				paymentDetail.setJe(rs.getFloat("je"));
				paymentDetail.setDl_dm(rs.getString("dl_dm"));
				paymentDetail.setXl_dm(rs.getString("xl_dm"));
				paymentDetail.setSeller(rs.getString("seller"));
				paymentDetail.setZh_dm(rs.getString("zh_dm"));
				paymentDetail.setShijian(rs.getDate("shijian"));
				paymentDetail.setBz(rs.getString("bz"));
				paymentDetail.setYxbz("Y");
				paymentDetail.setXgrq(rs.getDate("xgrq"));
				return paymentDetail;
			}
		});
		
		return list;
	}
	
	
	
}
