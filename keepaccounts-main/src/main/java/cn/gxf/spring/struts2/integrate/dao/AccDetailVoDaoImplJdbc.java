package cn.gxf.spring.struts2.integrate.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import cn.gxf.spring.struts2.integrate.model.AccDetailVO;
import cn.gxf.spring.struts2.integrate.model.AccountingDetailVO;
import cn.gxf.spring.struts2.integrate.model.IncomeDetailVO;
import cn.gxf.spring.struts2.integrate.model.PaymentDetailVO;
import cn.gxf.spring.struts2.integrate.model.TransferDetailVO;

@Repository("accDetailVoDao")
public class AccDetailVoDaoImplJdbc implements AccDetailVoDao{
	
	@Autowired
	private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

	@Override
	public List<AccDetailVO> getAccDetailVo(int user_id, String nd, String yf) {
		
		String dd = nd+"-" + yf + "-01";
		
		String sql = "SELECT t.user_id, DATE_FORMAT(t.shijian, '%Y-%m-%d') FSRQ, t.je je, lb.srlb_mc  lb_mc, " +
						"t.fkfmc seller, t.bz, 1 AS type "+
						" FROM	account_income_detail t, dm_srlb lb " +
					" WHERE t.lb_dm = lb.srlb_dm "+
					" AND t.shijian BETWEEN DATE_FORMAT(:dd, '%Y-%m-%d') AND last_day(DATE_FORMAT(:dd, '%Y-%m-%d')) " +
					" AND t.user_id = :user_id" +
					" AND t.yxbz='Y' " +
					" union all " +
					"SELECT p.user_id, DATE_FORMAT(p.shijian, '%Y-%m-%d') FSRQ, p.je je, CONCAT(dl.dl_mc, '-',xl.xl_mc) lb_mc, " +
						" p.seller seller,p.bz,	2 AS type " +
					" FROM account_payment_detail p, dm_dl dl, dm_xl xl " +
					" WHERE p.dl_dm = dl.dl_dm and p.xl_dm = xl.xl_dm " +
					" AND p.shijian BETWEEN DATE_FORMAT(:dd, '%Y-%m-%d') AND last_day(DATE_FORMAT(:dd, '%Y-%m-%d')) " +
					" AND p.user_id = :user_id" +
					" AND p.yxbz='Y' ";
		Map<String, Object> map = new HashMap<>();
		map.put("dd", dd);
		map.put("user_id", user_id);
		
		List<AccDetailVO> list = namedParameterJdbcTemplate.query(sql, map, new RowMapper<AccDetailVO>() {

			@Override
			public AccDetailVO mapRow(ResultSet rs, int rowNum) throws SQLException {
				AccDetailVO acc = new AccDetailVO();
				acc.setUser_id(rs.getInt("user_id"));
				acc.setFsrq(rs.getDate("FSRQ"));
				acc.setJe(rs.getFloat("je"));
				acc.setLbmc(rs.getString("lb_mc"));
				acc.setSeller(rs.getString("seller"));
				acc.setBz(rs.getString("bz"));
				acc.setType(rs.getInt("type"));
				return acc;
			}
		});
		
		return list;
	}

	@Override
	public List<IncomeDetailVO> getIncomeDetailVo(int user_id, String nd, String yf) {
		
		String dd = nd+"-" + yf + "-01";
		
		String sql = "SELECT t.user_id, DATE_FORMAT(t.shijian, '%Y-%m-%d %H:%i:00') FSRQ, t.je je, lb.srlb_mc  lb_mc, " +
						"t.fkfmc seller, t.bz, 1 AS type "+
						" FROM	account_income_detail t, dm_srlb lb " +
					" WHERE t.lb_dm = lb.srlb_dm "+
					" AND t.shijian BETWEEN DATE_FORMAT(:dd, '%Y-%m-%d') AND last_day(DATE_FORMAT(:dd, '%Y-%m-%d')) " +
					" AND t.user_id = :user_id" +
					" AND t.yxbz='Y' ";
		Map<String, Object> map = new HashMap<>();
		map.put("dd", dd);
		map.put("user_id", user_id);
		
		List<IncomeDetailVO> income_list = namedParameterJdbcTemplate.query(sql, map, new RowMapper<IncomeDetailVO>() {

			@Override
			public IncomeDetailVO mapRow(ResultSet rs, int rowNum) throws SQLException {
				// TODO Auto-generated method stub
				IncomeDetailVO incomeDetailVO = new IncomeDetailVO();
				incomeDetailVO.setUser_id(rs.getInt("user_id"));
				incomeDetailVO.setSeller(rs.getString("seller"));
				incomeDetailVO.setJe(rs.getFloat("je"));
				incomeDetailVO.setFsrq(rs.getTimestamp("FSRQ"));
				incomeDetailVO.setLbmc(rs.getString("lb_mc"));
				incomeDetailVO.setBz(rs.getString("bz"));
				incomeDetailVO.setType(rs.getInt("type"));
				return incomeDetailVO;
			}
		});
		
		return income_list;
	}

	@Override
	public List<PaymentDetailVO> getPaymentDetailVo(int user_id, String nd, String yf) {
		String dd = nd+"-" + yf + "-01";
		String sql = "SELECT p.user_id,  DATE_FORMAT(p.shijian, '%Y-%m-%d %H:%i:00') FSRQ, p.je je, CONCAT(dl.dl_mc, '-',xl.xl_mc) lb_mc, " +
				" p.seller seller,p.bz,	2 AS type " +
			" FROM account_payment_detail p, dm_dl dl, dm_xl xl " +
			" WHERE p.dl_dm = dl.dl_dm and p.xl_dm = xl.xl_dm " +
			" AND p.shijian BETWEEN DATE_FORMAT(:dd, '%Y-%m-%d') AND last_day(DATE_FORMAT(:dd, '%Y-%m-%d')) " +
			" AND p.user_id = :user_id" +
			" AND p.yxbz='Y' ";
		
		Map<String, Object> map = new HashMap<>();
		map.put("dd", dd);
		map.put("user_id", user_id);
		
		List<PaymentDetailVO> list = namedParameterJdbcTemplate.query(sql, map, new RowMapper<PaymentDetailVO>() {

			@Override
			public PaymentDetailVO mapRow(ResultSet rs, int rowNum) throws SQLException {
				// TODO Auto-generated method stub
				PaymentDetailVO paymentDetailVO = new PaymentDetailVO();
				paymentDetailVO.setUser_id(rs.getInt("user_id"));
				paymentDetailVO.setFsrq(rs.getTimestamp("FSRQ"));
				paymentDetailVO.setJe(rs.getFloat("je"));
				paymentDetailVO.setLbmc(rs.getString("lb_mc"));
				paymentDetailVO.setSeller(rs.getString("seller"));
				paymentDetailVO.setBz(rs.getString("bz"));
				paymentDetailVO.setType(rs.getInt("type"));
				return paymentDetailVO;
			}
		});
		
		return list;
	}

	@Override
	public List<TransferDetailVO> getTransferDetailVo(int user_id, String nd, String yf) {
		String dd = nd+"-" + yf + "-01";
		String sql = "SELECT t.user_id,  DATE_FORMAT(t.shijian, '%Y-%m-%d %H:%i:00') FSRQ, t.je je, " +
			" (select z.zh_mc from zh_detail_info z where z.zh_dm = t.srcZh_dm) srcZh_mc, " +
			" (select z.zh_mc from zh_detail_info z where z.zh_dm = t.tgtZh_dm) trgZh_mc, " +
			" t.bz,	3 AS type " +
			" FROM account_transfer_detail t " +
			" WHERE t.shijian BETWEEN DATE_FORMAT(:dd, '%Y-%m-%d') AND last_day(DATE_FORMAT(:dd, '%Y-%m-%d')) " +
			" AND t.user_id = :user_id AND t.yxbz='Y' ";
		Map<String, Object> map = new HashMap<>();
		map.put("dd", dd);
		map.put("user_id", user_id);
		
		List<TransferDetailVO> list = namedParameterJdbcTemplate.query(sql, map, new RowMapper<TransferDetailVO>() {

			@Override
			public TransferDetailVO mapRow(ResultSet rs, int rowNum) throws SQLException {
				// TODO Auto-generated method stub
				TransferDetailVO transferDetailVO = new TransferDetailVO();
				transferDetailVO.setUser_id(rs.getInt("user_id"));
				transferDetailVO.setFsrq(rs.getTimestamp("FSRQ"));
				transferDetailVO.setJe(rs.getFloat("je"));
				transferDetailVO.setSrcZhmc(rs.getString("srcZh_mc"));
				transferDetailVO.setTgtZhmc(rs.getString("trgZh_mc"));
				transferDetailVO.setBz(rs.getString("bz"));
				transferDetailVO.setType(rs.getInt("type"));
				return transferDetailVO;
			}
		});
		
		return list;
	}

	@Override
	public List<AccountingDetailVO> getAccDetailVoAll(int user_id, String nd, String yf) {
		// TODO Auto-generated method stub
		
		List<IncomeDetailVO> income_list = getIncomeDetailVo(user_id, nd, yf);
		List<PaymentDetailVO> payment_list = getPaymentDetailVo(user_id, nd, yf);
		List<TransferDetailVO> transfer_list = getTransferDetailVo(user_id, nd, yf);
		
		List<AccountingDetailVO> list = new ArrayList<>();
		
		for (IncomeDetailVO e : income_list){
			list.add(e);
		}
		
		for (PaymentDetailVO e : payment_list){
			list.add(e);
		}
		
		for (TransferDetailVO e : transfer_list){
			list.add(e);
		}
		
		return list;
	}

}
