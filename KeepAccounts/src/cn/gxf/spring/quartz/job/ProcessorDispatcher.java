package cn.gxf.spring.quartz.job;


import java.io.Serializable;

import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;
import org.springframework.web.context.ContextLoader;

/*
 * ProcessorDispatcher的对象processorDispatcher作为jobDataMap注入Job，
 * 必须可被序列化。因此不能使用MyBatis Dao。
 * 所以再封装一层，使用ContextLoader.getCurrentWebApplicationContext
 * 来获取真正用于处理的JobProcessor实现类
 * 
 */
@Service
public class ProcessorDispatcher implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1068867817750858030L;
	
	/**
	 * @param processorName
	 * @param ctx 从调用者注入context
	 */
	public void execute(String processorName, ApplicationContext ctx){
		
		try {
			//ApplicationContext ctx = ContextLoader.getCurrentWebApplicationContext();
			JobProcessor processor = (JobProcessor)ctx.getBean(processorName);
			processor.process();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

}
