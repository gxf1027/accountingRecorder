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
import org.apache.log4j.Logger;
import org.w3c.dom.Element;
import org.w3c.dom.Node;


public class AuthorizingInterceptorJMSTransport extends AbstractPhaseInterceptor<SoapMessage> {

	private static final Logger logger = Logger.getLogger(AuthorizingInterceptorJMSTransport.class);
	private CXFWebServiceController controller;
	private static final String AUTHORIZING_NAME="OrderCredentials";
	private String userName;
	private String password;
	
	public AuthorizingInterceptorJMSTransport(){
		super(Phase.PRE_INVOKE); 
	}

	@Override
	public void handleMessage(SoapMessage message) throws Fault {
		
		// �ж��Ƿ�blocked����
		if (1 == controller.getBlocked()){
			logger.info("All requests via web service are blocked.");
			throw new Fault(new SOAPException("All requests are blocked."));  
		}
		
		// ��ȡ���õĺ���
		Exchange exchange = message.getExchange();
		BindingOperationInfo bop = exchange.get(BindingOperationInfo.class);
		MethodDispatcher md = (MethodDispatcher) exchange.get(Service.class).get(MethodDispatcher.class.getName());
		String methodName = md.getMethod(bop).getName(); 
		
		
		//
		JMSMessageHeadersType headerstype = (JMSMessageHeadersType) message.get("org.apache.cxf.jms.server.request.headers");
		ActiveMQTextMessage mqMessage = (ActiveMQTextMessage) message.get("org.apache.cxf.jms.request.message");
		BindingMessageInfo bmi = (BindingMessageInfo) message.get("org.apache.cxf.service.model.BindingMessageInfo");
		Destination dest = (Destination) message.get("org.apache.cxf.transport.Destination");
		try {
			String mqtext = mqMessage.getText();
		} catch (JMSException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		for(String key : message.keySet()){
			System.out.println("key: " + key +" value: " + message.get(key));
		}
		
	
		
		logger.info("==================SoapMessage =" + message); 
		System.out.println("==================SoapMessage =" + message);
		// ��ȡSOAP��Ϣ��ȫ��ͷ  
        List<Header> headers = message.getHeaders(); 
        System.out.println("==================SoapHeaders =" + headers);
        
        if (null == headers || headers.size() < 1) {  
            throw new Fault(new SOAPException("SOAP��Ϣͷ��ʽ����ȷ��"));  
        }  
        
        // ��Header��ȡ�û���������Ϣ
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
        
        
        // ����methodName��servicePath��֤�Ƿ���accessȨ��
//        int isAccessible = cxfAuthenInfoDao.accessValidating(userName, servicePath, methodName);
//        if (0 == isAccessible){
//        	throw new Fault(new SOAPException("�޷�����"));  
//        }
	}
	
	public CXFWebServiceController getController() {
		return controller;
	}
	
	public void setController(CXFWebServiceController controller) {
		this.controller = controller;
	}
	
}
