package cn.gxf.spring.quartz.job;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

public class GenericProcessJob  implements Job{

	@Override
	public void execute(JobExecutionContext jetc) throws JobExecutionException {
		
		// ���ʹ��jdbc�־û�quartz, jobDataMap�е����Զ��������Ա����л�
		String processorName =  (String) jetc.getJobDetail().getJobDataMap().get("processorName");
		// ��õ��Ӷ���
		ProcessorDispatcher dispatcher = (ProcessorDispatcher) jetc.getJobDetail().getJobDataMap().get("dispatcher");
		
		try {
			// ִ��job
			dispatcher.execute(processorName);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
