package cn.gxf.spring.quartz.job;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.context.ApplicationContext;

public class GenericProcessJob  implements Job{

    private Logger logger = LogManager.getLogger();

	private static final String APPLICATION_CONTEXT_KEY = "applicationContextKey";
	
	@Override
	public void execute(JobExecutionContext jetc) throws JobExecutionException {
		
		// ���ʹ��jdbc�־û�quartz, jobDataMap�е����Զ��������Ա����л�
		String processorName =  (String) jetc.getJobDetail().getJobDataMap().get("processorName");
		// ��õ��Ӷ���
		ProcessorDispatcher dispatcher = (ProcessorDispatcher) jetc.getJobDetail().getJobDataMap().get("dispatcher");
		
		
		try {
			// ���applicationContext
			ApplicationContext ctx = this.getApplicationContext(jetc);
			// ִ��job
			dispatcher.execute(processorName, ctx);
			
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
	}
	
	
	
	private ApplicationContext getApplicationContext(JobExecutionContext context) throws Exception {
			ApplicationContext appCtx = null;
			appCtx = (ApplicationContext) context.getScheduler().getContext().get(APPLICATION_CONTEXT_KEY);
			if (appCtx == null) {
				throw new JobExecutionException("No application context available in scheduler context for key \"" + APPLICATION_CONTEXT_KEY + "\"");
			}
			return appCtx;
	}

}
