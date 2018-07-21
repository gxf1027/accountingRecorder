package cn.gxf.spring.quartz.job;


import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import cn.gxf.spring.quartz.job.service.AccountStatisticsService;

public class StatBillJob implements Job{

	@Override
	public void execute(JobExecutionContext jetc) throws JobExecutionException {

		//AccountStatProcessor statProcessor = (AccountStatProcessor) jetc.getJobDetail().getJobDataMap().get("statProcessor");
		String processorName =  (String) jetc.getJobDetail().getJobDataMap().get("processorName");
		ProcessorDispatcher dispatcher = (ProcessorDispatcher) jetc.getJobDetail().getJobDataMap().get("dispatcher");

		try {
			//statProcessor.process();
			dispatcher.execute(processorName);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	

}
