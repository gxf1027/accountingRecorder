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
import org.apache.cxf.service.model.BindingMessageInfo;
import org.apache.cxf.service.model.BindingOperationInfo;
import org.apache.cxf.transport.Destination;
import org.apache.cxf.transport.http.AbstractHTTPDestination;
import org.apache.cxf.transport.jms.JMSMessageHeadersType;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
//import org.apache.log4j.Logger;
import org.w3c.dom.Element;
import org.w3c.dom.Node;


public class AuthorizingInterceptorJMSTransport extends AbstractPhaseInterceptor<SoapMessage> {

	private Logger logger = LogManager.getLogger();
	//private static final Logger logger = Logger.getLogger(AuthorizingInterceptorJMSTransport.class);
	private CXFWebServiceController controller;
	private static final String AUTHORIZING_NAME="OrderCredentials";
	private String userName;
	private String password;
	
	public AuthorizingInterceptorJMSTransport(){
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
		Service service = exchange.getService();
//		for (String key : service.keySet()){
//			System.out.println("key: " + key+ " | " + service.get(key));
//		}
		String serviceInfaceName = service.get("endpoint.class").toString().substring(10);
//		System.out.println("serviceInfaceName: " + serviceInfaceName);
//		for(QName key : service.getEndpoints().keySet()){
//			System.out.println("QName: " + key.toString() + " " + service.getEndpoints().get(key).toString());
//		}
		
		MethodDispatcher md = (MethodDispatcher) exchange.get(Service.class).get(MethodDispatcher.class.getName());
		String methodName = md.getMethod(bop).getName(); 
		
		//
//		JMSMessageHeadersType headerstype = (JMSMessageHeadersType) message.get("org.apache.cxf.jms.server.request.headers");
//		ActiveMQTextMessage mqMessage = (ActiveMQTextMessage) message.get("org.apache.cxf.jms.request.message");
//		BindingMessageInfo bmi = (BindingMessageInfo) message.get("org.apache.cxf.service.model.BindingMessageInfo");
//		Destination dest = (Destination) message.get("org.apache.cxf.transport.Destination");
//		try {
//			String mqtext = mqMessage.getText();
//		} catch (JMSException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		for(String key : message.keySet()){
//			System.out.println("key: " + key +" value: " + message.get(key));
//		}
		
	
		
		logger.info("==================SoapMessage =" + message); 
		System.out.println("==================SoapMessage =" + message);
		// 获取SOAP消息的全部头  
        List<Header> headers = message.getHeaders(); 
        System.out.println("==================SoapHeaders =" + headers);
        
        if (null == headers || headers.size() < 1) {  
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
        
	}
	
	public CXFWebServiceController getController() {
		return controller;
	}
	
	public void setController(CXFWebServiceController controller) {
		this.controller = controller;
	}
	
}
