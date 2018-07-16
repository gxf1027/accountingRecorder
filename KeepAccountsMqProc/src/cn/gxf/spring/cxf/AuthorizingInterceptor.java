package cn.gxf.spring.cxf;

import javax.xml.namespace.QName;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.cxf.binding.soap.SoapMessage;
import org.apache.cxf.binding.soap.interceptor.SoapPreProtocolOutInterceptor;
import org.apache.cxf.headers.Header;
import org.apache.cxf.interceptor.Fault;
import org.apache.cxf.phase.AbstractPhaseInterceptor;
import org.apache.cxf.phase.Phase;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class AuthorizingInterceptor extends AbstractPhaseInterceptor<SoapMessage> {

	private static final String AUTHORIZING_NAME = "OrderCredentials";
	private String userName;
	private String password;

	public AuthorizingInterceptor() {
		super(Phase.WRITE);
		addAfter(SoapPreProtocolOutInterceptor.class.getName());
	}

	@Override
	public void handleMessage(SoapMessage message) throws Fault {
		System.out.println("OrderProcessClientHandler handleMessage invoked");

		DocumentBuilder builder = null;
		try {
			builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		}
		Document doc = builder.newDocument();
		Element elementCredentials = doc.createElement(AUTHORIZING_NAME);
		Element elementUser = doc.createElement("username");
		elementUser.setTextContent(getUserName());
		Element elementPassword = doc.createElement("password");
		elementPassword.setTextContent(getPassword());
		elementCredentials.appendChild(elementUser);
		elementCredentials.appendChild(elementPassword);
		// Create Header object
		QName qnameCredentials = new QName(AUTHORIZING_NAME);
		Header header = new Header(qnameCredentials, elementCredentials);
		message.getHeaders().add(header);
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}
}
