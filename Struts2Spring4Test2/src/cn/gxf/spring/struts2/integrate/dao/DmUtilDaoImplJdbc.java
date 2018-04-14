package cn.gxf.spring.struts2.integrate.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import cn.gxf.spring.struts2.integrate.model.AccountBook;
import cn.gxf.spring.struts2.integrate.model.DmPaymentDl;
import cn.gxf.spring.struts2.integrate.model.DmPaymentXl;

@Repository("daoImplJdbc")
public class DmUtilDaoImplJdbc implements DmUtilDao{

	@Autowired
	private JdbcTemplate jdbcTemplate;
	@Autowired
	private NamedParameterJdbcTemplate namedTemplate;

	
	
	public Map<String, String> getPaymentDl() {
		
		String sql = "SELECT * from dm_dl WHERE xybz='Y' AND yxbz='Y'";
		return jdbcTemplate.query(sql, new ResultSetExtractor<Map<String, String>>(){

			@Override
			public Map<String, String> extractData(ResultSet rs) throws SQLException, DataAccessException {
				
				Map<String, String> map = new TreeMap<String, String>();
				while(rs.next()){
					map.put(rs.getString("dl_dm"), rs.getString("dl_mc"));
				}
				return map;
			}

			
		});
		
	}

	
	public Map<String, String> getPaymentXl() {
		
		String sql = "SELECT * from dm_xl WHERE xybz='Y' AND yxbz='Y'";
		return jdbcTemplate.query(sql, new ResultSetExtractor<Map<String, String>>(){

			@Override
			public Map<String, String> extractData(ResultSet rs) throws SQLException, DataAccessException {
				
				Map<String, String> map = new TreeMap<String, String>();
				while(rs.next()){
					map.put(rs.getString("xl_dm"), rs.getString("xl_mc"));
				}
				return map;
			}

			
		});
	}

	
	public Map<String, String> getIncomeLb() {
		
		String sql = "SELECT * from dm_srlb WHERE xybz='Y' AND yxbz='Y'";
		return jdbcTemplate.query(sql, new ResultSetExtractor<Map<String, String>>(){

			@Override
			public Map<String, String> extractData(ResultSet rs) throws SQLException, DataAccessException {
				// TODO Auto-generated method stub
				Map<String, String> map = new TreeMap<String, String>();
				while(rs.next()){
					map.put(rs.getString("srlb_dm"), rs.getString("srlb_mc"));
				}
				return map;
			}

			
		});
	}
	
	@Override
	public Map<String, String> getFundType() {
		
		String sql = "SELECT * FROM dm_fund_type WHERE xybz='Y' AND yxbz='Y'";
		return jdbcTemplate.query(sql, new ResultSetExtractor<Map<String, String>>(){

			@Override
			public Map<String, String> extractData(ResultSet rs) throws SQLException, DataAccessException {
				// TODO Auto-generated method stub
				Map<String, String> map = new TreeMap<String, String>();
				while(rs.next()){
					map.put(rs.getString("fund_type_dm"), rs.getString("fund_type_mc"));
				}
				return map;
			}

			
		});
	}
	
	@Override
	public Map<String, String> getYh() {
		
		String sql = "SELECT * FROM dm_yh WHERE xybz='Y' AND yxbz='Y'";
		return jdbcTemplate.query(sql, new ResultSetExtractor<Map<String, String>>(){

			@Override
			public Map<String, String> extractData(ResultSet rs) throws SQLException, DataAccessException {
				// TODO Auto-generated method stub
				Map<String, String> map = new TreeMap<String, String>();
				while(rs.next()){
					map.put(rs.getString("yh_dm"), rs.getString("yh_mc"));
				}
				return map;
			}

			
		});
	}


	@Override
	public List<DmPaymentDl> getPaymentDlList() {
		
		String sql = "SELECT * from dm_dl where yxbz='Y' and xybz='Y'";
		return jdbcTemplate.query(sql, new ResultSetExtractor<List<DmPaymentDl>>(){

			@Override
			public List<DmPaymentDl> extractData(ResultSet rs) throws SQLException, DataAccessException {
				// TODO Auto-generated method stub
				List<DmPaymentDl> list = new ArrayList<>();
				while (rs.next()){
					DmPaymentDl dl = new DmPaymentDl();
					dl.setDl_dm(rs.getString("dl_dm"));
					dl.setDl_mc(rs.getString("dl_mc"));
					dl.setXybz("Y");
					dl.setYxbz("Y");
					list.add(dl);
				}
				return list;
			}});
		
	}


	@Override
	public List<DmPaymentXl> getPaymentXlList() {
		
		String sql = "SELECT * from dm_xl where yxbz='Y' and xybz='Y'";
		return jdbcTemplate.query(sql, new ResultSetExtractor<List<DmPaymentXl>>(){

			@Override
			public List<DmPaymentXl> extractData(ResultSet rs) throws SQLException, DataAccessException {
				// TODO Auto-generated method stub
				List<DmPaymentXl> list = new ArrayList<>();
				while (rs.next()){
					DmPaymentXl xl = new DmPaymentXl();
					xl.setXl_dm(rs.getString("xl_dm"));
					xl.setXl_mc(rs.getString("xl_mc"));
					xl.setDl_dm(rs.getString("dl_dm"));
					xl.setXybz("Y");
					xl.setYxbz("Y");
					list.add(xl);
				}
				return list;
			}});
		
	}


	@Override
	public Map<DmPaymentDl, List<DmPaymentXl>> getPaymentDlXlDzb() {
		// TODO Auto-generated method stub
		List<DmPaymentDl> listDl = getPaymentDlList();
		List<DmPaymentXl> listXl = getPaymentXlList();
		Map<DmPaymentDl, List<DmPaymentXl>> map = new TreeMap<>();
		
		for (DmPaymentDl dl : listDl){
			
			List<DmPaymentXl> dzxl = new ArrayList<>();
			for (DmPaymentXl xl : listXl){
				if (xl.getDl_dm().equals(dl.getDl_dm())){
					dzxl.add(xl);
				}	
			}
			
			map.put(dl, dzxl);
		}
		
			
		return map;
	}


	@Override
	public Map<String, String> getZhHuLx() {
		
		String sql = "SELECT * FROM dm_zhlx";
		return jdbcTemplate.query(sql, new ResultSetExtractor<Map<String, String>>(){

			@Override
			public Map<String, String> extractData(ResultSet rs) throws SQLException, DataAccessException {
				
				Map<String, String> map = new HashMap<String, String>();
				while(rs.next()){
					map.put(rs.getString("zhlx_dm"), rs.getString("zhlx_mc"));
				}
				return map;
			}

			
		});
	}


	@Override
	public Map<String, String> getOutgoCategory(Integer user_id) {
		String sql = "SELECT outgo_category_dm, outgo_category_mc"
					+ " FROM dm_outgo_category "
					+ "WHERE (user_id = ? or user_id is NULL) AND yxbz='Y' and xybz='Y'"
					+ "ORDER BY outgo_category_dm";
		
		
		PreparedStatementSetter pss = new PreparedStatementSetter() {
			
			@Override
			public void setValues(PreparedStatement ps) throws SQLException {
				ps.setString(1, user_id.toString());
			}
		};
		
		return jdbcTemplate.query(sql, pss, new ResultSetExtractor<Map<String, String>>() {

			@Override
			public Map<String, String> extractData(ResultSet rs) throws SQLException, DataAccessException {
				Map<String, String> map = new TreeMap<String, String>();
				while(rs.next()){
					map.put(rs.getString("outgo_category_dm"), rs.getString("outgo_category_mc"));
				}
				return map;
			}
		});
	}


	@Override
	public Map<String, String> getTransferType(Integer user_id) {
		String sql = "SELECT zzlx_dm, zzlx_mc"
				+ " FROM dm_zzlx "
				+ "WHERE (user_id = ? or user_id is NULL) AND yxbz='Y' and xybz='Y'"
				+ "ORDER BY zzlx_dm";
		
		PreparedStatementSetter pss = new PreparedStatementSetter() {
			
			@Override
			public void setValues(PreparedStatement ps) throws SQLException {
				ps.setString(1, user_id.toString());
			}
		};
		
		return jdbcTemplate.query(sql, pss, new ResultSetExtractor<Map<String, String>>() {

			@Override
			public Map<String, String> extractData(ResultSet rs) throws SQLException, DataAccessException {
				Map<String, String> map = new TreeMap<String, String>();
				while(rs.next()){
					map.put(rs.getString("zzlx_dm"), rs.getString("zzlx_mc"));
				}
				return map;
			}
		});
	}


	
}
