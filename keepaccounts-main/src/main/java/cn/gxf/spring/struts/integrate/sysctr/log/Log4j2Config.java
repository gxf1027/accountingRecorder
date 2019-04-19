package cn.gxf.spring.struts.integrate.sysctr.log;

import java.nio.charset.Charset;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.Appender;
import org.apache.logging.log4j.core.Filter;
import org.apache.logging.log4j.core.Filter.Result;
import org.apache.logging.log4j.core.LoggerContext;
import org.apache.logging.log4j.core.appender.FailoverAppender;
import org.apache.logging.log4j.core.appender.RollingFileAppender;
import org.apache.logging.log4j.core.appender.rolling.TimeBasedTriggeringPolicy;
import org.apache.logging.log4j.core.appender.rolling.TriggeringPolicy;
import org.apache.logging.log4j.core.filter.ThreadContextMapFilter;
import org.apache.logging.log4j.core.layout.PatternLayout;
import org.apache.logging.log4j.core.util.KeyValuePair;

public class Log4j2Config {
	
	static public void config(){
		final LoggerContext ctx = (LoggerContext) LogManager.getContext(false);
	    final org.apache.logging.log4j.core.config.Configuration config = ctx.getConfiguration();
	    
	    Appender failoverAppender = FailoverAppender.createAppender("FailoverAppender", 
	    			"kafkaLog", new String[]{"failoverKafkaLog"}, "600", config, null, "false");
	    
	   
	    failoverAppender.start();
	    config.addAppender(failoverAppender);
	    //final KeyValuePair[] pairs = {KeyValuePair.newBuilder().setKey("domainId").setValue("XXX").build()};
	    //final Filter filter = ThreadContextMapFilter.createFilter(pairs, null, Result.ACCEPT, Result.DENY);
	    config.getLoggerConfig("cn.gxf.spring.struts.mybatis.dao").addAppender(failoverAppender, Level.DEBUG, null);
	    ctx.updateLoggers(config);
	
	}
}
