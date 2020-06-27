package cn.gxf.spring.keepacc.mqproc.sysctr;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.listener.exception.ListenerExecutionFailedException;
import org.springframework.util.ErrorHandler;

public class MyMsgErrorHandler implements ErrorHandler{

	private Logger logger = LogManager.getLogger();
	
	@Override
	public void handleError(Throwable t) {
		
		// https://pinocc.cn/2019/09/30/rabbitmq-%E5%BC%82%E5%B8%B8%E5%A4%84%E7%90%86%E6%BA%90%E7%A0%81/
		// ªÒ»°message
		
		if (t instanceof ListenerExecutionFailedException ){
			
			Message failed = ((ListenerExecutionFailedException) t).getFailedMessage();
		    
		    if (failed != null) {
		    	
		    	System.out.println(failed);
		    	
		    	// logging
		    	if (this.logger.isWarnEnabled()) {
					this.logger.warn(
							"Fatal message conversion error; message rejected; "
							+ "it will be dropped or routed to a dead letter exchange, if so configured: "
							+ ((ListenerExecutionFailedException) t).getFailedMessage());
				}
		    }
			
		}
		    
	}
	
}
