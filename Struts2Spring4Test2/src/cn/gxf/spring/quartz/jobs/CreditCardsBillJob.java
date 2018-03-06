package cn.gxf.spring.quartz.jobs;

import java.util.Map;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

@Repository
public class CreditCardsBillJob implements Job{

	@Autowired
	@Qualifier(value="namedParameterJdbcTemplate")
	private NamedParameterJdbcTemplate namedTemplate;
	
	@Override
	public void execute(JobExecutionContext jbec) throws JobExecutionException {
		System.out.println("CreditCardsBillJob job ... ");
		System.out.println(this.namedTemplate);
		
		Map dataMap = jbec.getJobDetail().getJobDataMap();
		JdbcTemplate jdbcTemplate = (JdbcTemplate) dataMap.get("jdbcTemplate");
		System.out.println(jdbcTemplate);
		
		try {
			Thread.sleep(100000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		System.out.println("continue...");
	}

}
