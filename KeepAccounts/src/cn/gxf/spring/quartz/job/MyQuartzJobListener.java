package cn.gxf.spring.quartz.job;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.JobListener;

public class MyQuartzJobListener implements JobListener{

	public static final String LISTENER_NAME = "keepAccountsJobListener";
	
	@Override
	public String getName() {
		
		return LISTENER_NAME;
	}

	@Override
	public void jobExecutionVetoed(JobExecutionContext context) {
		String jobName = context.getJobDetail().getKey().toString();
		System.out.println("jobExecutionVetoed");
		System.out.println("Job : " + jobName + " execution vetoed");
	}

	@Override
	public void jobToBeExecuted(JobExecutionContext context) {
		
		String jobName = context.getJobDetail().getKey().toString();
		System.out.println("jobToBeExecuted");
		System.out.println("Job : " + jobName + " is going to start...");
		
	}

	@Override
	public void jobWasExecuted(JobExecutionContext context, JobExecutionException jobException) {
		System.out.println("jobWasExecuted");
		String jobName = context.getJobDetail().getKey().toString();
		System.out.println("Job : " + jobName + " is finished...");
		
		if (!jobException.getMessage().equals("")) {
			System.out.println("Exception thrown by: " + jobName
				+ " Exception: " + jobException.getMessage());
		}
	}

}
