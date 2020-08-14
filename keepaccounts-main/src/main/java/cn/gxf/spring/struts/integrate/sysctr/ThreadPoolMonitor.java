package cn.gxf.spring.struts.integrate.sysctr;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Component;


@Component
public class ThreadPoolMonitor {

	private Logger logger = LogManager.getLogger();
	
	@Autowired
	private ThreadPoolTaskExecutor taskExecutor;
	
	@Scheduled(fixedDelay=60000)
	public void threadPoolStatusRecording(){
		
		//System.out.println("thread pool activeCount{[]}, poolSize{[]} " + taskExecutor.getActiveCount() + " " + taskExecutor.getPoolSize() + " " + taskExecutor.getThreadPoolExecutor().getQueue().size());
		logger.info("thread pool activeCount[{}], poolSize[{}], queueSize[{}], taskCount[{}] ", taskExecutor.getActiveCount(), taskExecutor.getPoolSize(), taskExecutor.getThreadPoolExecutor().getQueue().size(), taskExecutor.getThreadPoolExecutor().getTaskCount());
	}
}
