package cn.gxf.spring.quartz.job;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

public class FinancialProductsNoticeJob implements Job{

	@Override
	public void execute(JobExecutionContext jetc) throws JobExecutionException {
		
		FinancialProductsNoticeProcessor finanProdProc = (FinancialProductsNoticeProcessor) jetc.getJobDetail().getJobDataMap().get("financialProdProcessor");
		
		try {
			finanProdProc.processFinanProductsNotice();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}
