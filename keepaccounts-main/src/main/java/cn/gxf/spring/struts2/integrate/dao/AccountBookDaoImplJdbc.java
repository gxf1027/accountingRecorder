package cn.gxf.spring.struts2.integrate.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import cn.gxf.spring.struts2.integrate.model.AccountBook;
import cn.gxf.spring.struts2.integrate.model.AccountBookVO;

@Repository
public class AccountBookDaoImplJdbc implements AccountBookDao{

	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@Autowired
	private AccUtil accUtil;
	
	
	@Override
	public List<AccountBook> getZhInfo(int user_id) {
		
		//String sql = "SELECT * from zh_detail_info WHERE user_id=? AND yxbz='Y' AND xybz='Y'";
		String sql = "SELECT zh_dm, Zh_mc, khrmc, zhhm, user_id, zhlx_dm, ye from zh_detail_info WHERE user_id=? AND yxbz='Y' AND xybz='Y'";
		
		PreparedStatementSetter pss = new PreparedStatementSetter() {
			
			@Override
			public void setValues(PreparedStatement ps) throws SQLException {
				// TODO Auto-generated method stub
				ps.setInt(1, user_id);
			}
		};
		
		return jdbcTemplate.query(sql, pss, new RowMapper<AccountBook>(){

			@Override
			public AccountBook mapRow(ResultSet rs, int rowNum) throws SQLException {
				// TODO Auto-generated method stub
				AccountBook adi = new AccountBook();
				adi.setZh_dm(rs.getString("zh_dm"));
				adi.setZh_mc(rs.getString("Zh_mc"));
				adi.setKhrmc(rs.getString("khrmc"));
				adi.setZhhm(rs.getString("zhhm"));
				adi.setUser_id(rs.getInt("user_id"));
				adi.setZhlx_dm(rs.getString("zhlx_dm"));
				adi.setYe(rs.getFloat("ye"));
				adi.setYxbz("Y");
				return adi;
			}});
	
	}
	
	@Override
	public List<AccountBookVO> getZhInfoVO(int user_id) {
		
		String sql = "SELECT a.zh_dm, "
				+ "a.zh_mc, "
				+ "b.zhlx_mc, "
				+ "a.zhhm, "
				+ "a.user_id, "
				+ "a.khrmc, "
				+ "a.ye, "
				+ "a.yxbz "
				+ "FROM zh_detail_info a LEFT JOIN dm_zhlx b ON a.zhlx_dm = b.zhlx_dm WHERE user_id = ? "
				+ "ORDER BY a.zhlx_dm, a.zh_dm";
		
		PreparedStatementSetter pss = new PreparedStatementSetter() {
			
			@Override
			public void setValues(PreparedStatement ps) throws SQLException {
				ps.setInt(1, user_id);
			}
		};
		
		return jdbcTemplate.query(sql, pss, new RowMapper<AccountBookVO>(){

			@Override
			public AccountBookVO mapRow(ResultSet rs, int rowNum) throws SQLException {
				AccountBookVO book = new AccountBookVO();
				book.setZh_dm(rs.getString("zh_dm"));
				book.setZh_mc(rs.getString("Zh_mc"));
				book.setKhrmc(rs.getString("khrmc"));
				book.setZhhm(rs.getString("zhhm"));
				book.setUser_id(rs.getInt("user_id"));
				book.setZhlx_mc(rs.getString("zhlx_mc"));
				book.setYe(rs.getFloat("ye"));
				book.setYxbz(rs.getString("yxbz"));
				return book;
			}});
	
	}
	
	@Override
	public AccountBook getZhInfo(String zh_dm) {
		
		String sql = "SELECT * from zh_detail_info WHERE zh_dm=? AND yxbz='Y' AND xybz='Y'";
		
		PreparedStatementSetter pss = new PreparedStatementSetter() {
			
			@Override
			public void setValues(PreparedStatement ps) throws SQLException {
				// TODO Auto-generated method stub
				ps.setString(1, zh_dm);
			}
		};
		
		List<AccountBook> books = jdbcTemplate.query(sql, pss, new RowMapper<AccountBook>(){

			@Override
			public AccountBook mapRow(ResultSet rs, int rowNum) throws SQLException {
				// TODO Auto-generated method stub
				AccountBook adi = new AccountBook();
				adi.setZh_dm(rs.getString("zh_dm"));
				adi.setZh_mc(rs.getString("Zh_mc"));
				adi.setKhrmc(rs.getString("khrmc"));
				adi.setZhhm(rs.getString("zhhm"));
				adi.setUser_id(rs.getInt("user_id"));
				adi.setZhlx_dm(rs.getString("zhlx_dm"));
				adi.setYe(rs.getFloat("ye"));
				adi.setYxbz("Y");
				return adi;
			}});
		
		return books.get(0);
	}

	
	@Transactional(value="dsTransactionManager", propagation=Propagation.REQUIRED, isolation=Isolation.READ_COMMITTED)
	//@CacheEvict(value="dmCache", allEntries=true)
	@Override
	public void updateYe(String zh_dm, float delt_je) {
		String sql = "UPDATE zh_detail_info SET ye = ye + ? WHERE zh_dm = ? and yxbz='Y'";
		
		PreparedStatementSetter pss = new PreparedStatementSetter() {
			@Override
			public void setValues(PreparedStatement ps) throws SQLException {
				// TODO Auto-generated method stub
				ps.setFloat(1, delt_je);
				ps.setString(2, zh_dm);
			}
		};
		
		jdbcTemplate.update(sql, pss);
	}

	@Override
	public void saveOne(AccountBook accountBook) {
		System.out.println("saveOneAccontBook: " + accountBook);
		String zh_dm = this.accUtil.generateUuid();
		String sql = "INSERT INTO zh_detail_info(zh_dm, zh_mc, khrmc, user_id, zhlx_dm, zhhm, ye, yxbz) "
				+ "VALUES(?,?,?,?,?,?,?,'Y')";
		
		this.jdbcTemplate.update(sql, new PreparedStatementSetter() {
			
			@Override
			public void setValues(PreparedStatement ps) throws SQLException {
				ps.setString(1, zh_dm);
				ps.setString(2, accountBook.getZh_mc());
				ps.setString(3, accountBook.getKhrmc());
				ps.setInt(4, accountBook.getUser_id());
				ps.setString(5, accountBook.getZhlx_dm());
				ps.setString(6, accountBook.getZhhm());
				ps.setFloat(7, accountBook.getYe());
			}
		});
	}

}
