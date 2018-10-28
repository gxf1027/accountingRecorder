package cn.gxf.spring.cxf;





import javax.servlet.http.HttpServletRequest;
import javax.xml.soap.SOAPException;

import org.apache.cxf.binding.soap.SoapMessage;
import org.apache.cxf.interceptor.Fault;
import org.apache.cxf.message.Exchange;
import org.apache.cxf.phase.AbstractPhaseInterceptor;
import org.apache.cxf.phase.Phase;
import org.apache.cxf.service.Service;
import org.apache.cxf.service.invoker.MethodDispatcher;
import org.apache.cxf.service.model.BindingOperationInfo;
import org.apache.cxf.transport.http.AbstractHTTPDestination;
//import org.apache.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import cn.gxf.spring.cxf.util.CxfUtil;
import cn.gxf.spring.struts2.integrate.service.SimpleRateLimitService;

public class AccessRateLimitInterceptor extends AbstractPhaseInterceptor<SoapMessage> {

	//private static final Logger logger = Logger.getLogger(AccessRateLimitInterceptor.class);
	private Logger logger = LogManager.getLogger();
	private SimpleRateLimitService rateLimitService;
	
	public AccessRateLimitInterceptor(){
		super(Phase.PRE_INVOKE); 
	}

	@Override
	public void handleMessage(SoapMessage message) throws Fault {
		
		// 获取调用的函数
		Exchange exchange = message.getExchange();
		BindingOperationInfo bop = exchange.get(BindingOperationInfo.class);
		MethodDispatcher md = (MethodDispatcher) exchange.get(Service.class).get(MethodDispatcher.class.getName());
		String methodName = md.getMethod(bop).getName(); 
		
		// 获取Client IP地址
		HttpServletRequest request = (HttpServletRequest) message.get(AbstractHTTPDestination.HTTP_REQUEST);
		String ipAddress = CxfUtil.getUsrIPAddr(request); //获取客户端的IP地址
		String servicePath = request.getPathInfo().substring(1); // 比如"billService"
		
		String limitKey = String.format("ip-%s-servicePath-%s-method-%s", ipAddress, servicePath, methodName);
		boolean limitAllowed = rateLimitService.isActionAllowed("cxfclient"/*userId*/, limitKey/*actionKey*/, 60, 3);
		if (false == limitAllowed){
			throw new Fault(new SOAPException("流量控制"));  
		}
	}

	
	public SimpleRateLimitService getRateLimitService() {
		return rateLimitService;
	}
	
	public void setRateLimitService(SimpleRateLimitService rateLimitService) {
		this.rateLimitService = rateLimitService;
	}

}
