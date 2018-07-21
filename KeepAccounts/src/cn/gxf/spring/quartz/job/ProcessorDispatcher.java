package cn.gxf.spring.quartz.job;


import java.io.Serializable;

import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;
import org.springframework.web.context.ContextLoader;

@Service
public class ProcessorDispatcher implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1068867817750858030L;
	
	public void execute(String processorName){
		
		try {
			ApplicationContext ctx = ContextLoader.getCurrentWebApplicationContext();
			JobProcessor processor = (JobProcessor)ctx.getBean(processorName);
			processor.process();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

}
