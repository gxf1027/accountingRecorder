
package cn.loan.ws.service;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the cn.loan.ws.service package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {

    private final static QName _GetLoanInfo_QNAME = new QName("http://service.ws.loan.cn/", "getLoanInfo");
    private final static QName _GetLoanInfoResponse_QNAME = new QName("http://service.ws.loan.cn/", "getLoanInfoResponse");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: cn.loan.ws.service
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link GetLoanInfo }
     * 
     */
    public GetLoanInfo createGetLoanInfo() {
        return new GetLoanInfo();
    }

    /**
     * Create an instance of {@link GetLoanInfoResponse }
     * 
     */
    public GetLoanInfoResponse createGetLoanInfoResponse() {
        return new GetLoanInfoResponse();
    }

    /**
     * Create an instance of {@link LoanInfo }
     * 
     */
    public LoanInfo createLoanInfo() {
        return new LoanInfo();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetLoanInfo }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.ws.loan.cn/", name = "getLoanInfo")
    public JAXBElement<GetLoanInfo> createGetLoanInfo(GetLoanInfo value) {
        return new JAXBElement<GetLoanInfo>(_GetLoanInfo_QNAME, GetLoanInfo.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetLoanInfoResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.ws.loan.cn/", name = "getLoanInfoResponse")
    public JAXBElement<GetLoanInfoResponse> createGetLoanInfoResponse(GetLoanInfoResponse value) {
        return new JAXBElement<GetLoanInfoResponse>(_GetLoanInfoResponse_QNAME, GetLoanInfoResponse.class, null, value);
    }

}
