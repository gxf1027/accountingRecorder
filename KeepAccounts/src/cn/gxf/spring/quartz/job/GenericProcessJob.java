package cn.gxf.spring.quartz.job;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.context.ApplicationContext;

public class GenericProcessJob  implements Job{

	private static final String APPLICATION_CONTEXT_KEY = "applicationContextKey";
	
	@Override
	public void execute(JobExecutionContext jetc) throws JobExecutionException {
		
		// 如果使用jdbc持久化quartz, jobDataMap中的属性对象必须可以被序列化
		String processorName =  (String) jetc.getJobDetail().getJobDataMap().get("processorName");
		// 获得掉队对象
		ProcessorDispatcher dispatcher = (ProcessorDispatcher) jetc.getJobDetail().getJobDataMap().get("dispatcher");
		
		
		try {
			// 获得applicationContext
			ApplicationContext ctx = this.getApplicationContext(jetc);
			// 执行job
			dispatcher.execute(processorName, ctx);
			
		} catch (Exception e) {
			e.printStackTrace();
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
