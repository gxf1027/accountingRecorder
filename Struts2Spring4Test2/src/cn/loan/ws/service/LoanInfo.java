
package cn.loan.ws.service;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>loanInfo complex type�� Java �ࡣ
 * 
 * <p>����ģʽƬ��ָ�������ڴ����е�Ԥ�����ݡ�
 * 
 * <pre>
 * &lt;complexType name="loanInfo">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="hkrq" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/>
 *         &lt;element name="je" type="{http://www.w3.org/2001/XMLSchema}float"/>
 *         &lt;element name="loan_type" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="loaner_name" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="loaner_zjhm" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="ssny" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="uuid" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "loanInfo", propOrder = {
    "hkrq",
    "je",
    "loanType",
    "loanerName",
    "loanerZjhm",
    "ssny",
    "uuid"
})
public class LoanInfo {

    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar hkrq;
    protected float je;
    @XmlElement(name = "loan_type")
    protected String loanType;
    @XmlElement(name = "loaner_name")
    protected String loanerName;
    @XmlElement(name = "loaner_zjhm")
    protected String loanerZjhm;
    protected String ssny;
    protected String uuid;

    /**
     * ��ȡhkrq���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getHkrq() {
        return hkrq;
    }

    /**
     * ����hkrq���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setHkrq(XMLGregorianCalendar value) {
        this.hkrq = value;
    }

    /**
     * ��ȡje���Ե�ֵ��
     * 
     */
    public float getJe() {
        return je;
    }

    /**
     * ����je���Ե�ֵ��
     * 
     */
    public void setJe(float value) {
        this.je = value;
    }

    /**
     * ��ȡloanType���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLoanType() {
        return loanType;
    }

    /**
     * ����loanType���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLoanType(String value) {
        this.loanType = value;
    }

    /**
     * ��ȡloanerName���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLoanerName() {
        return loanerName;
    }

    /**
     * ����loanerName���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLoanerName(String value) {
        this.loanerName = value;
    }

    /**
     * ��ȡloanerZjhm���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLoanerZjhm() {
        return loanerZjhm;
    }

    /**
     * ����loanerZjhm���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLoanerZjhm(String value) {
        this.loanerZjhm = value;
    }

    /**
     * ��ȡssny���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSsny() {
        return ssny;
    }

    /**
     * ����ssny���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSsny(String value) {
        this.ssny = value;
    }

    /**
     * ��ȡuuid���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getUuid() {
        return uuid;
    }

    /**
     * ����uuid���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setUuid(String value) {
        this.uuid = value;
    }

}
