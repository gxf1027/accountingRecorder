package cn.gxf.spring.motan;


import java.util.Map;

import com.weibo.api.motan.common.MotanConstants;
import com.weibo.api.motan.core.extension.Activation;
import com.weibo.api.motan.core.extension.SpiMeta;
import com.weibo.api.motan.filter.Filter;
import com.weibo.api.motan.rpc.Caller;
import com.weibo.api.motan.rpc.Provider;
import com.weibo.api.motan.rpc.Request;
import com.weibo.api.motan.rpc.Response;
import com.weibo.api.motan.rpc.RpcContext;
import com.weibo.api.motan.util.MotanFrameworkUtil;

@SpiMeta(name="attachAuthInfoFilter")
@Activation(sequence=1, key = MotanConstants.NODE_TYPE_REFERER)
public class AttachAuthenticationInfoFilter implements Filter{
	
	@Override
	public Response filter(Caller<?> caller, Request request) {

		RpcContext ctx = RpcContext.getContext();
		
		System.out.println("RpcContext: " + ctx);
		
		System.out.println("in filter: caller " + caller + " request: " + request);
		
		Map<String, String> reqMap = request.getAttachments();
		reqMap.put(FilterConstants.ACCESS_USERNAME, "keepacc_client");
		reqMap.put(FilterConstants.ACCESS_PASSWORD, "B053806C5AF39581722FF396656CEC99");
		
		System.out.println("Host " + request.getAttachments().get("host") + " call " + MotanFrameworkUtil.getFullMethodString(request));
		
	
		
		return caller.call(request); 
	}
}
