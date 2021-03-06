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

	public static final String common = "common";
	
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
				
				Map<String, String> map = new TreeMap<String, String>();
				while(rs.next()){
					map.put(rs.getString("yh_dm"), rs.getString("yh_mc"));
				}
				return map;
			}

			
		});
	}
	
	@Override
	public Map<String, String> getFinancialProdType() {
		String sql = "SELECT * FROM dm_financial_product WHERE xybz='Y' AND yxbz='Y'";
		return jdbcTemplate.query(sql, new ResultSetExtractor<Map<String, String>>(){

			@Override
			public Map<String, String> extractData(ResultSet rs) throws SQLException, DataAccessException {
				
				Map<String, String> map = new TreeMap<String, String>();
				while(rs.next()){
					map.put(rs.getString("fin_prod_dm"), rs.getString("fin_prod_mc"));
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

	// 获取整个类别代码表
	@Override
	public Map<String, Map<String, String>> getOutgoCategory(){
		String sql = "SELECT outgo_category_dm, outgo_category_mc, user_id"
				+ " FROM dm_outgo_category "
				+ "WHERE yxbz='Y' and xybz='Y'"
				+ "ORDER BY outgo_category_dm";
	
		

		return 	jdbcTemplate.query(sql, new ResultSetExtractor<Map<String, Map<String, String>>>() {

			@Override
			public Map<String, Map<String, String>> extractData(ResultSet rs) throws SQLException, DataAccessException {
				
				//Map<String, String> map = new TreeMap<String, String>();
				String user_id;
				String outgo_category_dm, outgo_category_mc;
				Map<String, Map<String, String>> userCat = new HashMap<>();
				while(rs.next()){
					outgo_category_dm = rs.getString("outgo_category_dm");
					outgo_category_mc = rs.getString("outgo_category_mc");
					//System.out.println("test-------"+rs.getInt("user_id"));
					// 如果user_id为空值，rs.getInt("user_id")返回为0
					//user_id = String.valueOf(rs.getInt("user_id"));
					user_id = rs.getObject("user_id") == null ? DmUtilDaoImplJdbc.common : String.valueOf(rs.getInt("user_id"));
					Map<String, String> catMap = userCat.get(user_id);
					if (null == catMap){
						catMap = new TreeMap<String, String>();
						catMap.put(outgo_category_dm, outgo_category_mc);
						userCat.put(user_id.toString(), catMap);
					}else{
						catMap.put(outgo_category_dm, outgo_category_mc);
					}
				}
				return userCat;
			}
			
		});
	}
	
	// 获取user_id为制定值以及user_id为空（公用）的类别代码表
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

	// 获取转账类似的代码表（完整）
	@Override
	public Map<String, Map<String, String>> getTransferType() {
		String sql = "SELECT zzlx_dm, zzlx_mc, user_id"
				+ " FROM dm_zzlx "
				+ "WHERE yxbz='Y' and xybz='Y'"
				+ "ORDER BY zzlx_dm";
	

		return 	jdbcTemplate.query(sql, new ResultSetExtractor<Map<String, Map<String, String>>>() {

			@Override
			public Map<String, Map<String, String>> extractData(ResultSet rs) throws SQLException, DataAccessException {
				
				//Map<String, String> map = new TreeMap<String, String>();
				String user_id;
				String zzlx_dm, zzlx_mc;
				Map<String, Map<String, String>> userTransType = new HashMap<>();
				while(rs.next()){
					zzlx_dm = rs.getString("zzlx_dm");
					zzlx_mc = rs.getString("zzlx_mc");
					user_id = rs.getObject("user_id") == null ? DmUtilDaoImplJdbc.common : String.valueOf(rs.getInt("user_id")); 
//					user_id = String.valueOf(rs.getInt("user_id"));
//					if (null == user_id || user_id.equals("0")){
//						user_id = DmUtilDaoImplJdbc.common;
//					}
					Map<String, String> catMap = userTransType.get(user_id);
					if (null == catMap){
						catMap = new TreeMap<String, String>();
						catMap.put(zzlx_dm, zzlx_mc);
						userTransType.put(user_id.toString(), catMap);
					}else{
						catMap.put(zzlx_dm, zzlx_mc);
					}
				}
				return userTransType;
			}
			
		});
	}

	
}
