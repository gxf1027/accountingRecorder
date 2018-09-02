package cn.gxf.spring.cxf;

import java.util.List;

import javax.xml.namespace.QName;
import javax.xml.soap.SOAPException;

import org.apache.cxf.binding.soap.SoapMessage;
import org.apache.cxf.headers.Header;
import org.apache.cxf.interceptor.Fault;
import org.apache.cxf.phase.AbstractPhaseInterceptor;
import org.apache.cxf.phase.Phase;
import org.apache.log4j.Logger;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

public class AuthorizingInterceptor extends AbstractPhaseInterceptor<SoapMessage> {

	private static final Logger logger = Logger.getLogger(AuthorizingInterceptor.class); 
	private static final String AUTHORIZING_NAME="OrderCredentials";
	private String userName;
	private String password;
	
	public AuthorizingInterceptor(){
		super(Phase.PRE_INVOKE); 
	}

	@Override
	public void handleMessage(SoapMessage message) throws Fault {
		logger.info("==================SoapMessage =" + message); 
		System.out.println("==================SoapMessage =" + message);
		// 获取SOAP消息的全部头  
        List<Header> headers = message.getHeaders(); 
        System.out.println("==================SoapHeaders =" + headers);
        
        if (null == headers || headers.size() < 1) {  
            throw new Fault(new SOAPException("SOAP消息头格式不对哦！"));  
        }  
        
        
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
	}
}
