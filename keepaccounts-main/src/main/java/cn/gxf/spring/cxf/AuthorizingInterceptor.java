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
		
		// ��ȡClient IP��ַ
		HttpServletRequest request = (HttpServletRequest) message.get(AbstractHTTPDestination.HTTP_REQUEST);
		String ipAddress = CxfUtil.getUsrIPAddr(request);//��ȡ�ͻ��˵�IP��ַ
		String servicePath = request.getPathInfo(); // ����"/billService"
		
		// �ж����IP�Ƿ���ֹ
		if (controller.isIPBlocked(ipAddress)){
			logger.info("IP " + ipAddress + " is blocked.");
			throw new Fault(new SOAPException("IP is blocked."));  
		}
		
		logger.info("==================SoapMessage =" + message); 
		//System.out.println("==================SoapMessage =" + message);
		// ��ȡSOAP��Ϣ��ȫ��ͷ  
        List<Header> headers = message.getHeaders(); 
        System.out.println("==================SoapHeaders =" + headers);
        
        if (null == headers || headers.size() < 1) {  
        	logger.error(String.format("Request from %s with wrong headers.", ipAddress));
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
        int isAccessible = cxfAuthenInfoDao.accessValidating(userName, servicePath, methodName);
        if (0 == isAccessible){
        	logger.error(String.format("user %s cann't access to %s %s", userName, servicePath, methodName));
        	throw new Fault(new SOAPException("�޷�����"));  
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
