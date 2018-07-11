
package cn.loan.ws.service;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>loanInfo complex type的 Java 类。
 * 
 * <p>以下模式片段指定包含在此类中的预期内容。
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
     * 获取hkrq属性的值。
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
     * 设置hkrq属性的值。
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
     * 获取je属性的值。
     * 
     */
    public float getJe() {
        return je;
    }

    /**
     * 设置je属性的值。
     * 
     */
    public void setJe(float value) {
        this.je = value;
    }

    /**
     * 获取loanType属性的值。
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
     * 设置loanType属性的值。
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
     * 获取loanerName属性的值。
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
     * 设置loanerName属性的值。
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
     * 获取loanerZjhm属性的值。
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
     * 设置loanerZjhm属性的值。
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
     * 获取ssny属性的值。
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
     * 设置ssny属性的值。
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
     * 获取uuid属性的值。
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
     * 设置uuid属性的值。
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
