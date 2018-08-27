package cn.gxf.spring.motan.filter;



import java.util.Date;
import java.util.UUID;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.context.ContextLoader;

import com.weibo.api.motan.common.MotanConstants;
import com.weibo.api.motan.core.extension.Activation;
import com.weibo.api.motan.core.extension.SpiMeta;
import com.weibo.api.motan.filter.Filter;
import com.weibo.api.motan.rpc.Caller;
import com.weibo.api.motan.rpc.Request;
import com.weibo.api.motan.rpc.Response;
import com.weibo.api.motan.rpc.RpcContext;
import com.weibo.api.motan.util.MotanFrameworkUtil;

import cn.gxf.spring.motan.control.MotanController;
import cn.gxf.spring.motan.model.RpcRequestInfo;
import cn.gxf.spring.struts.integrate.cache.PseudoRedisTopic;

@SpiMeta(name="requestInfoLoggingFilter")
@Activation(sequence=3, key=MotanConstants.NODE_TYPE_SERVICE)
public class RequestInfoLoggingFilter implements Filter{
	
	private Logger logger = LogManager.getLogger();
	
	@Override
	public Response filter(Caller<?> caller, Request request) {

		long start = System.currentTimeMillis();
		
		ApplicationContext springCtx = ContextLoader.getCurrentWebApplicationContext();
		MotanController controller = (MotanController) springCtx.getBean("motanController");
		
		if ( 1 == controller.isRpcInfoPersisted() ){
		
			//RpcContext ctx = RpcContext.getContext();
			
			RedisTemplate redisTemplate = (RedisTemplate) springCtx.getBean("redisTemplate");
			
			RpcRequestInfo reqInfo = new RpcRequestInfo();
			reqInfo.setUuid(UUID.randomUUID().toString().replace("-", "").toLowerCase());
			reqInfo.setHost(request.getAttachments().get("host"));
			reqInfo.setUserName(request.getAttachments().get(FilterConstants.ACCESS_USERNAME));
			reqInfo.setInterfaceName(request.getInterfaceName());
			reqInfo.setMethodName(request.getMethodName());
			reqInfo.setRequestTime(new Date());
			reqInfo.setDeniedFlag("N");
			
			redisTemplate.opsForList().leftPush(FilterConstants.RPC_REQUEST_LIST, reqInfo);
			
			long size = redisTemplate.opsForList().size(FilterConstants.RPC_REQUEST_LIST);
			if (size > FilterConstants.REDIS_LOG_LIST_MAX_SIZE){
				// 向rpc.stat这个topic推送消息
				PseudoRedisTopic topic =  (PseudoRedisTopic) springCtx.getBean("redisTopic4Motan");
				System.out.println("\n\n\n\n topci:" + topic.getTopicName());
				redisTemplate.convertAndSend(topic.getTopicName(), size);
			}
		
		}
		
		System.out.println("filter cost:" + (System.currentTimeMillis() - start));
		
		return caller.call(request); 
	}
}
