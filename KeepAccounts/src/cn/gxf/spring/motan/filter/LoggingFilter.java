package cn.gxf.spring.motan.filter;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import com.weibo.api.motan.common.MotanConstants;
import com.weibo.api.motan.core.extension.Activation;
import com.weibo.api.motan.core.extension.SpiMeta;
import com.weibo.api.motan.filter.Filter;
import com.weibo.api.motan.rpc.Caller;
import com.weibo.api.motan.rpc.Request;
import com.weibo.api.motan.rpc.Response;
import com.weibo.api.motan.rpc.RpcContext;
import com.weibo.api.motan.util.MotanFrameworkUtil;

@SpiMeta(name="loggingFilter")
@Activation(sequence=1, key = MotanConstants.NODE_TYPE_SERVICE)
public class LoggingFilter implements Filter{
	
	private Logger logger = LogManager.getLogger();

	
	@Override
	public Response filter(Caller<?> caller, Request request) {

		logger.info("RequestId: " + request.getRequestId() + " Host: " + request.getAttachments().get("host") + " call: " + MotanFrameworkUtil.getFullMethodString(request));
		logger.info("Arguments: " + request.getArguments().toString());
		logger.info("Caller url: " + caller.getUrl());
		
		//RpcContext ctx = RpcContext.getContext();
		
		return caller.call(request); 
	}
}
