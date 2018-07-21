package cn.gxf.spring.quartz.job;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

public class FinancialProductsNoticeJob implements Job{

	@Override
	public void execute(JobExecutionContext jetc) throws JobExecutionException {
		
		//FinancialProductsNoticeProcessor finanProdProc = (FinancialProductsNoticeProcessor) jetc.getJobDetail().getJobDataMap().get("financialProdProcessor");
		String processorName =  (String) jetc.getJobDetail().getJobDataMap().get("processorName");
		ProcessorDispatcher dispatcher = (ProcessorDispatcher) jetc.getJobDetail().getJobDataMap().get("dispatcher");
		
		try {
			//finanProdProc.process();
			dispatcher.execute(processorName);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}
