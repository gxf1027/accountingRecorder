package cn.gxf.spring.struts2.integrate.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import cn.gxf.spring.struts2.integrate.model.AccDateStat;

@Repository("accountStatDao")
public class AccountStatDaoImplJdbc implements AccountStatDao{

	@Autowired
	private NamedParameterJdbcTemplate namedParameterJdbcTemplate;
	
	@Override
	public List<AccDateStat> getDateStat(int user_id, String nd, String yf) {
		// TODO Auto-generated method stub
		
		String dd = nd+"-"+yf+"-01";
		
		String sql = "SELECT "
				+ "DATE_FORMAT(t.shijian, '%Y-%m-%d') FSRQ, "
				+ "t.je je, "
				+ "t.lb_dm"
				+ "FROM "
				+ "	test.account_income_detail t "
				+ "WHERE "
				+ "	t.shijian  between DATE_FORMAT(:dd,'%Y-%m-%d') and last_day(DATE_FORMAT(:dd,'%Y-%m-%d'))";
		
		
		
		return null;
	}

}
