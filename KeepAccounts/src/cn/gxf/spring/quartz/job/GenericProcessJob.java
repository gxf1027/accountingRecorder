package cn.gxf.spring.quartz.job;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

public class GenericProcessJob  implements Job{

	@Override
	public void execute(JobExecutionContext jetc) throws JobExecutionException {
		
		// 如果使用jdbc持久化quartz, jobDataMap中的属性对象必须可以被序列化
		String processorName =  (String) jetc.getJobDetail().getJobDataMap().get("processorName");
		// 获得掉队对象
		ProcessorDispatcher dispatcher = (ProcessorDispatcher) jetc.getJobDetail().getJobDataMap().get("dispatcher");
		
		try {
			// 执行job
			dispatcher.execute(processorName);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
