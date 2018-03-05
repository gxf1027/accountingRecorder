package cn.gxf.spring.quartz.job;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import cn.gxf.spring.quartz.job.dao.CreditCardBillDao;
import cn.gxf.spring.quartz.job.model.CreditCardTransRecord;

/*	
 * 
 * 
 * */

public class CreditCardsBillJob implements Job{
	

	
	@Override
	public void execute(JobExecutionContext jetc) throws JobExecutionException {
		
		/*NamedParameterJdbcTemplate namedJdbcTemplate = (NamedParameterJdbcTemplate) jetc.getJobDetail().getJobDataMap().get("namedJdbcTemplate");
		//System.out.println(namedJdbcTemplate);
		
		CreditCardBillDao creditCardBillDao = (CreditCardBillDao) jetc.getJobDetail().getJobDataMap().get("creditCardBillDao");
		//System.out.println(creditCardBillDao);
		 */		
		CreditCardsBillProcessor ccbp = (CreditCardsBillProcessor) jetc.getJobDetail().getJobDataMap().get("ccbProcessor");
		// test
		/*StatNdYfMBDao dao = (StatNdYfMBDao) jetc.getJobDetail().getJobDataMap().get("mbdao");
		System.out.println(dao.getIncomeStatOnSrlb("2018", 5));*/
		
		//CreditCardsBillProcessor ccbp = new CreditCardsBillProcessor();
		
		try {
			ccbp.processCreditCardBill();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	

}
