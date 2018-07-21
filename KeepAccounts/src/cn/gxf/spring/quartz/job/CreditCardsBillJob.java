package cn.gxf.spring.quartz.job;


import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

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
		//BillProcessor ccbp = (BillProcessor) jetc.getJobDetail().getJobDataMap().get("ccbProcessor");
		String processorName =  (String) jetc.getJobDetail().getJobDataMap().get("processorName");
		ProcessorDispatcher dispatcher = (ProcessorDispatcher) jetc.getJobDetail().getJobDataMap().get("dispatcher");
		// test
		/*StatNdYfMBDao dao = (StatNdYfMBDao) jetc.getJobDetail().getJobDataMap().get("mbdao");
		System.out.println(dao.getIncomeStatOnSrlb("2018", 5));*/
		
		//CreditCardsBillProcessor ccbp = new CreditCardsBillProcessor();
		
		try {
			// ccbp.processCreditCardBill();
			dispatcher.execute(processorName);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	

}
