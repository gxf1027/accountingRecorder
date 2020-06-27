package cn.gxf.spring.cxf;


import java.util.List;

import javax.jms.JMSException;
import javax.servlet.http.HttpServletRequest;
import javax.xml.namespace.QName;
import javax.xml.soap.SOAPException;

import org.apache.activemq.command.ActiveMQTextMessage;
import org.apache.cxf.binding.soap.SoapMessage;
import org.apache.cxf.headers.Header;
import org.apache.cxf.interceptor.Fault;
import org.apache.cxf.message.Exchange;
import org.apache.cxf.phase.AbstractPhaseInterceptor;
import org.apache.cxf.phase.Phase;
import org.apache.cxf.service.Service;
import org.apache.cxf.service.invoker.MethodDispatcher;
import org.apache.cxf.service.model.BindingOperationInfo;
import org.apache.cxf.transport.http.AbstractHTTPDestination;
//import org.apache.log4j.Logger;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

import cn.gxf.spring.cxf.util.BlockedIpDao;
import cn.gxf.spring.cxf.util.CxfUtil;

public class AuthorizingInterceptor extends AbstractPhaseInterceptor<SoapMessage> {

	//private static final Logger logger = Logger.getLogger(AuthorizingInterceptor.class);
	private Logger logger = LogManager.getLogger();
	private CXFWebServiceController controller;
	private CxfAuthenInfoDao cxfAuthenInfoDao;
	private static final String AUTHORIZING_NAME="OrderCredentials";
	private String userName;
	private String password;
	
	public AuthorizingInterceptor(){
		super(Phase.PRE_INVOKE); 
	}

	@Override
	public void handleMessage(SoapMessage message) throws Fault {
		
		// 判断是否blocked所有
		if (1 == controller.getBlocked()){
			logger.info("All requests via web service are blocked.");
			throw new Fault(new SOAPException("All requests are blocked."));  
		}
		
		// 获取调用的函数
		Exchange exchange = message.getExchange();
		BindingOperationInfo bop = exchange.get(BindingOperationInfo.class);
		MethodDispatcher md = (MethodDispatcher) exchange.get(Service.class).get(MethodDispatcher.class.getName());
		String methodName = md.getMethod(bop).getName(); 
		
		// 获取Client IP地址
		HttpServletRequest request = (HttpServletRequest) message.get(AbstractHTTPDestination.HTTP_REQUEST);
		String ipAddress = CxfUtil.getUsrIPAddr(request);//获取客户端的IP地址
		String servicePath = request.getPathInfo(); // 比如"/billService"
		
		// 判断这个IP是否被阻止
		if (controller.isIPBlocked(ipAddress)){
			logger.info("IP " + ipAddress + " is blocked.");
			throw new Fault(new SOAPException("IP is blocked."));  
		}
		
		logger.info("==================SoapMessage =" + message); 
		//System.out.println("==================SoapMessage =" + message);
		// 获取SOAP消息的全部头  
        List<Header> headers = message.getHeaders(); 
        System.out.println("==================SoapHeaders =" + headers);
        
        if (null == headers || headers.size() < 1) {  
        	logger.error(String.format("Request from %s with wrong headers.", ipAddress));
            throw new Fault(new SOAPException("SOAP消息头格式不正确！"));  
        }  
        
        // 从Header获取用户和密码信息
        QName qnameCredentials = new QName(AUTHORIZING_NAME);
        // Get header based on QNAME
        if (message.hasHeader(qnameCredentials)) {
        	Header header = message.getHeader(qnameCredentials);
        	Element elementOrderCredential= (Element) header.getObject();
            Node nodeUser = elementOrderCredential.getFirstChild();
            Node nodePassword = elementOrderCredential.getLastChild();
            
            userName = nodeUser.getTextContent();
            password = nodePassword.getTextContent();
            
        }
        System.out.println("userName reterived from SOAP Header is "  + userName);
        System.out.println("password reterived from SOAP Header is " + password);
        
        /*if ("John".equalsIgnoreCase(userName) && "password".
	      equalsIgnoreCase(password)) {
	         System.out.println("Authentication successful for John");
	      } else {
	         throw new RuntimeException("Invalid user");
	      }*/
        
        
        // 根据methodName和servicePath验证是否有access权限
        int isAccessible = cxfAuthenInfoDao.accessValidating(userName, servicePath, methodName);
        if (0 == isAccessible){
        	logger.error(String.format("user %s cann't access to %s %s", userName, servicePath, methodName));
        	throw new Fault(new SOAPException("无法访问"));  
        }
	}
	
	public CXFWebServiceController getController() {
		return controller;
	}
	
	public void setController(CXFWebServiceController controller) {
		this.controller = controller;
	}
	
	public CxfAuthenInfoDao getCxfAuthenInfoDao() {
		return cxfAuthenInfoDao;
	}
	
	public void setCxfAuthenInfoDao(CxfAuthenInfoDao cxfAuthenInfoDao) {
		this.cxfAuthenInfoDao = cxfAuthenInfoDao;
	}

}
