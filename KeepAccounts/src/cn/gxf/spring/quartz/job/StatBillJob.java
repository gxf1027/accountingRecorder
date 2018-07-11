package cn.gxf.spring.quartz.job;


import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import cn.gxf.spring.quartz.job.service.AccountStatisticsService;

public class StatBillJob implements Job{

	@Override
	public void execute(JobExecutionContext jetc) throws JobExecutionException {

		AccountStatProcessor statProcessor = (AccountStatProcessor) jetc.getJobDetail().getJobDataMap().get("statProcessor");

		try {
			statProcessor.updateStatThisMonth();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	

}
