package cn.gxf.spring.struts2.integrate.dao;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import cn.gxf.spring.struts2.integrate.model.AccountingDetail;

@Repository("accountingDetailDao")
public class AccountingDetailDaoImplJdbc implements AccountingDetailDao{

	@Autowired
	private NamedParameterJdbcTemplate namedParameterJdbcTemplate;
	
	@Autowired
	private AccUtil accUtil;
	
	@Override
	public String addOne(AccountingDetail accountingDetail) {
		// TODO Auto-generated method stub
		// TODO Auto-generated method stub
		String sql = "INSERT INTO account_detail(accuuid, rec_dm, user_id, je,  shijian, yxbz, xgrq)   "
				+ "VALUES( :accuuid, :rec_dm, :user_id, :je, :shijian, 'Y', :xgrq)";
		Map<String, Object> paramMap = new HashMap<>();
		//paramMap.put("mxuuid", accUtil.generateUuid());
		//paramMap.put("accuuid", accUtil.generateUuid());
		
		paramMap.put("accuuid", accountingDetail.getAccuuid());
		paramMap.put("rec_dm", accountingDetail.getRec_dm());
		paramMap.put("user_id", accountingDetail.getUser_id());
		paramMap.put("je", accountingDetail.getJe());
		paramMap.put("shijian", accountingDetail.getShijian());
		paramMap.put("xgrq", new Date());
		
		namedParameterJdbcTemplate.update(sql, paramMap);
		
		return accountingDetail.getAccuuid();
	}

}
