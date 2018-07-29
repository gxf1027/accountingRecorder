package cn.gxf.spring.motan.filter;

import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;
import org.springframework.web.context.ContextLoader;

import com.weibo.api.motan.common.MotanConstants;
import com.weibo.api.motan.core.extension.Activation;
import com.weibo.api.motan.core.extension.SpiMeta;
import com.weibo.api.motan.exception.MotanErrorMsgConstant;
import com.weibo.api.motan.exception.MotanServiceException;
import com.weibo.api.motan.filter.Filter;
import com.weibo.api.motan.rpc.Caller;
import com.weibo.api.motan.rpc.Provider;
import com.weibo.api.motan.rpc.Request;
import com.weibo.api.motan.rpc.Response;
import com.weibo.api.motan.rpc.RpcContext;
import com.weibo.api.motan.util.MotanFrameworkUtil;

import cn.gxf.spring.motan.dao.RpcAuthenInfoDao;

@SpiMeta(name="accessAuthFilter")
@Activation(sequence=1, key = MotanConstants.NODE_TYPE_SERVICE)
public class AccessAuthFilter implements Filter{
	
	private static final String ACCESS_USERNAME = "rpc_username";
	private static final String ACCESS_PASSWORD = "rpc_password";
	private Logger logger = LogManager.getLogger();
	
	//private RpcAuthenInfoDao rpcAuthenInfoDao;
	
	@Override
	public Response filter(Caller<?> caller, Request request) {

		logger.info("RequestId: " + request.getRequestId() + " Host: " + request.getAttachments().get("host") + " call: " + MotanFrameworkUtil.getFullMethodString(request));
		logger.info("Arguments: " + request.getArguments().toString());
		logger.info("Caller url: " + caller.getUrl());
		
		RpcContext ctx = RpcContext.getContext();
		ApplicationContext springCtx = ContextLoader.getCurrentWebApplicationContext();
		System.out.println("springCtx: " + springCtx);
		
		System.out.println("RpcContext: " + ctx);
		if (caller instanceof Provider) { // server end
			System.out.println("in filter: caller " + caller + " request: " + request);
			
			String username = request.getAttachments().get(ACCESS_USERNAME);
			String confidentialCode = request.getAttachments().get(ACCESS_PASSWORD);
			logger.info("RequestId: " + request.getRequestId() + "RequestId: " + request.getRequestId() + " Request username: " + username + " Request confidential code: " + confidentialCode);
			//System.out.println("Request username: " + username + " Request confidential code: " + confidentialCode);
			// 只能用这种方式过去Spring中的bean
			RpcAuthenInfoDao rpcAuthenInfoDao = (RpcAuthenInfoDao) springCtx.getBean("rpcAuthenInfoDao");
			// 验证客户端
			Map<String, String> authInfo = rpcAuthenInfoDao.getAuthenInfo(username);
			if (authInfo.size() == 0){
				logger.error("User name " + username + " not exists.");
				throw new MotanServiceException(String.format("User (%s) not exists.", username), MotanErrorMsgConstant.SERVICE_REJECT);
			}else if (!authInfo.get(username).equals(confidentialCode)){
				logger.error("confidential code is wrong.");
				throw new MotanServiceException("confidential code is wrong.", MotanErrorMsgConstant.SERVICE_REJECT);
			}
			
			Integer validAccess = rpcAuthenInfoDao.accessValidating(username, request.getInterfaceName(), request.getMethodName());
			if (0 == validAccess){
				logger.error("username " + username +" has no valid access to " + MotanFrameworkUtil.getFullMethodString(request));
				throw new MotanServiceException(String.format("Username (%s) has no valid access to '%S' ", username, MotanFrameworkUtil.getFullMethodString(request)), MotanErrorMsgConstant.SERVICE_REJECT);
			}
			// request.getInterfaceName() + "." + request.getMethodName()
			//System.out.println(rpcAuthenInfoDao.getAuthenInfo());
			//System.out.println("Host " + request.getAttachments().get("host") + "call " + MotanFrameworkUtil.getFullMethodString(request));
			
		}
		
		return caller.call(request); 
	}
}
