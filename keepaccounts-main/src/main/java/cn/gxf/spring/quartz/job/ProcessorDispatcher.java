package cn.gxf.spring.quartz.job;


import java.io.Serializable;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;
import org.springframework.web.context.ContextLoader;

/*
 * ProcessorDispatcher�Ķ���processorDispatcher��ΪjobDataMapע��Job��
 * ����ɱ����л�����˲���ʹ��MyBatis Dao��
 * �����ٷ�װһ�㣬ʹ��ContextLoader.getCurrentWebApplicationContext
 * ����ȡ�������ڴ����JobProcessorʵ����
 * 
 */
@Service
public class ProcessorDispatcher implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1068867817750858030L;
	
    private Logger logger = LogManager.getLogger();

	
	/**
	 * @param processorName
	 * @param ctx �ӵ�����ע��context
	 */
	public void execute(String processorName, ApplicationContext ctx){
		
		try {
			//ApplicationContext ctx = ContextLoader.getCurrentWebApplicationContext();
			JobProcessor processor = (JobProcessor)ctx.getBean(processorName);
			processor.process();
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		
	}

}
