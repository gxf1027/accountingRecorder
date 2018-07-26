package cn.gxf.spring.motan.filter;

import com.weibo.api.motan.common.MotanConstants;
import com.weibo.api.motan.core.extension.Activation;
import com.weibo.api.motan.core.extension.SpiMeta;
import com.weibo.api.motan.filter.Filter;
import com.weibo.api.motan.rpc.Caller;
import com.weibo.api.motan.rpc.Request;
import com.weibo.api.motan.rpc.Response;

@SpiMeta(name="accessAuthFilter")
@Activation(sequence=1, key = MotanConstants.NODE_TYPE_SERVICE)
public class AccessAuthFilter implements Filter{
	@Override
	public Response filter(Caller<?> caller, Request request) {

		System.out.println("in filter: caller " + caller + " request: " + request);
		return caller.call(request); 
	}
}
