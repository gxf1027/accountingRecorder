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
import org.springframework.transaction.annotation.Transactional;


import cn.gxf.spring.struts.integrate.test.AccountVO;
import cn.gxf.spring.struts2.integrate.model.AccountInfo;
import cn.gxf.spring.struts2.integrate.model.ExpInfo;

@Repository("accountDao")
public class AccountDaoImplJdbc implements AccountDao {

	@Autowired
	private NamedParameterJdbcTemplate namedTemplate;
	
	@Autowired
	private AccUtil accUtil;
	
	@Override
	public void insertOneRecord(AccountVO accountVO) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateOneRecord(AccountVO accountVO) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public AccountInfo queryTest() {
		String sql = "select a.accuuid, a.ssny, a.income, a.salary, a.bonus, a.expenditure, a.netincome, "
				+ "b.expenditure as exp2, b.shopping, b.tax, b.others "
				+ "from test.account_info a , test.exp_info b where a.accuuid = b.accuuid "
				+ "and a.accuuid = 'c8d39a1e-97b6-11e7-89c8-4ccc6a5d2c43'";
		
		List<AccountInfo> list = namedTemplate.query(sql, new RowMapper<AccountInfo>() {

			@Override
			public AccountInfo mapRow(ResultSet rs, int rowNum) throws SQLException {
				// TODO Auto-generated method stub
				AccountInfo accInfo = new AccountInfo();
				
				accInfo.setAccuuid(rs.getString("accuuid"));
				accInfo.setSsny(rs.getString("ssny"));
				accInfo.setIncome(rs.getFloat("income"));
				accInfo.setSalary(rs.getFloat("salary"));
				accInfo.setBonus(rs.getFloat("bonus"));
				accInfo.setExpenditure(rs.getFloat("expenditure"));
				accInfo.setNetincome(rs.getFloat("netincome"));
				accInfo.getExpInfo().setExpenditure(rs.getFloat("exp2"));
				accInfo.getExpInfo().setShopping(rs.getFloat("shopping"));
				accInfo.getExpInfo().setTax(rs.getFloat("tax"));
				accInfo.getExpInfo().setOthers(rs.getFloat("others"));
				return accInfo;
			}
		});
		
		
		return list.get(0);
	}

	@Transactional
	@Override
	public int persistOne(AccountInfo accontInfo) {
		// TODO Auto-generated method stub
		
		Date xgrq = new Date();
		
		String accuuid = accUtil.generateUuid();
		String sql = "INSERT INTO account_info(accuuid, ssny, income, salary, bonus, expenditure, netincome, xgrq) "
				+ "VALUES(:accuuid, :ssny, :income, :salary, :bonus, :expenditure, :netincome, :xgrq)";
		
		Map<String, Object> paramMap = new HashMap<>();
		paramMap.put("accuuid", accuuid);
		paramMap.put("ssny", accontInfo.getSsny());
		paramMap.put("income", accontInfo.getIncome());
		paramMap.put("salary", accontInfo.getSalary());
		paramMap.put("bonus", accontInfo.getBonus());
		paramMap.put("expenditure", accontInfo.getExpenditure());
		paramMap.put("netincome", accontInfo.getNetincome());
		paramMap.put("xgrq", xgrq);
		namedTemplate.update(sql, paramMap);
		
		ExpInfo expInfo = accontInfo.getExpInfo();
		String expuuid = accUtil.generateUuid();
		sql = "INSERT INTO exp_info(expuuid, accuuid, expenditure, shopping, tax, loan, others, xgrq) "
				+ "VALUES(:expuuid, :accuuid, :expenditure, :shopping, :tax, :loan, :others, :xgrq)";
		Map<String, Object> expMap = new HashMap<>();
		expMap.put("expuuid", expuuid);
		expMap.put("accuuid", accuuid);
		expMap.put("expenditure", expInfo.getExpenditure());
		expMap.put("shopping", expInfo.getShopping());
		expMap.put("tax", expInfo.getTax());
		expMap.put("loan", expInfo.getLoan());
		expMap.put("others", expInfo.getOthers());
		expMap.put("xgrq", xgrq);
		namedTemplate.update(sql, expMap);
		
		return 0;
	}

	@Override
	public List<AccountInfo> getByNdYf(String nd, String yf) {
		// TODO Auto-generated method stub
		
		if (nd==null && yf==null){
			return getAll();
		}
		
		if (nd.length()!=4 ){
			System.out.println("getByNdYf£∫ ∏Ò Ω¥ÌŒÛ");
			return null;
		}
		
		try {
			Integer.parseInt(nd);
			if (yf!=null){
					Integer.parseInt(yf);
				}
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println(e);
			return null;
		}
		
		String sql = "select a.accuuid, a.ssny, a.income, a.salary, a.bonus, a.expenditure, a.netincome, "
					+ " b.expuuid, b.expenditure as exp2, b.shopping, b.tax, b.loan, b.others "
					+ " from test.account_info a , test.exp_info b where a.accuuid = b.accuuid";
		String ssny;
		if (yf==null){
			ssny = nd;
			sql = sql + " and substring(a.ssny,1,4)= :ssny";
		}else{
			ssny = nd+yf;
			sql = sql + " and a.ssny= :ssny";	
		}
		
		
		Map<String, Object> paramsMap = new HashMap<>();
		paramsMap.put("ssny", ssny);
		
		List<AccountInfo> list = namedTemplate.query(sql, paramsMap, new RowMapper<AccountInfo>() {

			@Override
			public AccountInfo mapRow(ResultSet rs, int rowNum) throws SQLException {
				// TODO Auto-generated method stub
				AccountInfo accInfo = new AccountInfo();
				
				accInfo.setAccuuid(rs.getString("accuuid"));
				accInfo.setSsny(rs.getString("ssny"));
				accInfo.setIncome(rs.getFloat("income"));
				accInfo.setSalary(rs.getFloat("salary"));
				accInfo.setBonus(rs.getFloat("bonus"));
				accInfo.setExpenditure(rs.getFloat("expenditure"));
				accInfo.setNetincome(rs.getFloat("netincome"));
				accInfo.getExpInfo().setExpuuid(rs.getString("expuuid"));
				accInfo.getExpInfo().setAccuuid(rs.getString("accuuid"));
				accInfo.getExpInfo().setExpenditure(rs.getFloat("exp2"));
				accInfo.getExpInfo().setShopping(rs.getFloat("shopping"));
				accInfo.getExpInfo().setTax(rs.getFloat("tax"));
				accInfo.getExpInfo().setLoan(rs.getFloat("loan"));
				accInfo.getExpInfo().setOthers(rs.getFloat("others"));
				return accInfo;
			}
		});
		
		return list;
	}

	@Override
	public List<AccountInfo> getAll() {
		// TODO Auto-generated method stub
		
		String sql = "select a.accuuid, a.ssny, a.income, a.salary, a.bonus, a.expenditure, a.netincome, "
				+ "b.expuuid, b.expenditure as exp2, b.shopping, b.tax, b.loan, b.others "
				+ "from test.account_info a , test.exp_info b where a.accuuid = b.accuuid ";
		
		
		List<AccountInfo> list = namedTemplate.query(sql,  new RowMapper<AccountInfo>() {

			@Override
			public AccountInfo mapRow(ResultSet rs, int rowNum) throws SQLException {
				// TODO Auto-generated method stub
				AccountInfo accInfo = new AccountInfo();
				
				accInfo.setAccuuid(rs.getString("accuuid"));
				accInfo.setSsny(rs.getString("ssny"));
				accInfo.setIncome(rs.getFloat("income"));
				accInfo.setSalary(rs.getFloat("salary"));
				accInfo.setBonus(rs.getFloat("bonus"));
				accInfo.setExpenditure(rs.getFloat("expenditure"));
				accInfo.setNetincome(rs.getFloat("netincome"));
				accInfo.getExpInfo().setExpuuid(rs.getString("expuuid"));
				accInfo.getExpInfo().setAccuuid(rs.getString("accuuid"));
				accInfo.getExpInfo().setExpenditure(rs.getFloat("exp2"));
				accInfo.getExpInfo().setShopping(rs.getFloat("shopping"));
				accInfo.getExpInfo().setTax(rs.getFloat("tax"));
				accInfo.getExpInfo().setLoan(rs.getFloat("loan"));
				accInfo.getExpInfo().setOthers(rs.getFloat("others"));
				return accInfo;
			}
		});
		
		return list;
	}

	@Override
	public void delAccountInfo(String accuuid) {
		// TODO Auto-generated method stub
		String sql = "DELETE FROM test.account_info WHERE accuuid = :accuuid";
		Map<String, Object> paramsMap = new HashMap<>();
		paramsMap.put("accuuid", accuuid);
		namedTemplate.update(sql, paramsMap);
	}

	@Override
	public void delExpInfo(String accuuid) {
		// TODO Auto-generated method stub
		String sql = "DELETE FROM test.exp_info WHERE accuuid = :accuuid";
		Map<String, Object> paramsMap = new HashMap<>();
		paramsMap.put("accuuid", accuuid);
		namedTemplate.update(sql, paramsMap);
	}
	
	public AccountInfo getAccExpByAccuuid(String accuuid){
		
		
		String sql = "SELECT a.accuuid, a.ssny, a.income, a.salary, a.bonus, a.expenditure, a.netincome, "
				+ "b.expuuid,  b.expenditure as exp2, b.shopping, b.tax, b.loan, b.others "
				+ "FROM test.account_info a , test.exp_info b WHERE a.accuuid = b.accuuid "
				+ "AND a.accuuid = :accuuid";
		
		Map<String, Object> paramsMap = new HashMap<>();
		paramsMap.put("accuuid", accuuid);
		
		List<AccountInfo> list = namedTemplate.query(sql, paramsMap, new RowMapper<AccountInfo>() {

			@Override
			public AccountInfo mapRow(ResultSet rs, int rowNum) throws SQLException {
				// TODO Auto-generated method stub
				AccountInfo accInfo = new AccountInfo();
				
				accInfo.setAccuuid(rs.getString("accuuid"));
				accInfo.setSsny(rs.getString("ssny"));
				accInfo.setIncome(rs.getFloat("income"));
				accInfo.setSalary(rs.getFloat("salary"));
				accInfo.setBonus(rs.getFloat("bonus"));
				accInfo.setExpenditure(rs.getFloat("expenditure"));
				accInfo.setNetincome(rs.getFloat("netincome"));
				accInfo.getExpInfo().setExpuuid(rs.getString("expuuid"));
				accInfo.getExpInfo().setAccuuid(rs.getString("accuuid"));
				accInfo.getExpInfo().setExpenditure(rs.getFloat("exp2"));
				accInfo.getExpInfo().setShopping(rs.getFloat("shopping"));
				accInfo.getExpInfo().setTax(rs.getFloat("tax"));
				accInfo.getExpInfo().setLoan(rs.getFloat("loan"));
				accInfo.getExpInfo().setOthers(rs.getFloat("others"));
				return accInfo;
			}
		});
		
		return list.get(0);
	}

	@Override
	public void updateAccountInfo(AccountInfo accountInfo) {
		
		String sql = "UPDATE test.account_info "
				+ " SET income=:income, salary=:salary, bonus=:bonus, expenditure=:expenditure, netincome=:netincome, xgrq=:xgrq "
				+ " WHERE accuuid =:accuuid";
		Map<String,Object> paramMap = new HashMap<>();
		paramMap.put("income", accountInfo.getIncome());
		paramMap.put("salary", accountInfo.getSalary());
		paramMap.put("bonus", accountInfo.getBonus());
		paramMap.put("expenditure", accountInfo.getExpenditure());
		paramMap.put("netincome", accountInfo.getNetincome());
		paramMap.put("xgrq", new Date());
		paramMap.put("accuuid", accountInfo.getAccuuid());
		int rv = namedTemplate.update(sql, paramMap);
		System.out.println("updateAccountInfo rv: " + rv);
	}

	@Override
	public void updateExpInfo(ExpInfo expInfo) {
		String sql = "UPDATE test.exp_info "
				+ " SET expenditure=:expenditure, shopping=:shopping, tax=:tax, loan=:loan, others=:others, xgrq=:xgrq "
				+ " WHERE expuuid=:expuuid";
		Map<String,Object> paramMap = new HashMap<>();
		
		paramMap.put("expenditure", expInfo.getExpenditure());
		paramMap.put("shopping", expInfo.getShopping());
		paramMap.put("tax", expInfo.getTax());
		paramMap.put("loan", expInfo.getLoan());
		paramMap.put("others", expInfo.getOthers());
		paramMap.put("xgrq", new Date());
		paramMap.put("expuuid", expInfo.getExpuuid());
		int rv = namedTemplate.update(sql, paramMap);
		
		System.out.println("updateExpInfo rv: " + rv);
	}
	
}
