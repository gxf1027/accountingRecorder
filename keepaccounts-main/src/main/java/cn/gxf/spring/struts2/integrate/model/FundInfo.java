package cn.gxf.spring.struts2.integrate.model;

import java.io.Serializable;
import java.util.Date;

/*
 * 基金接口对应的JavaBean
 * https://www.showapi.com/api/view/902
 * */
public class FundInfo implements Serializable {

	private static final long serialVersionUID = -3324041635830960984L;

	String fundCode;
	String tgCom; // "中国建设银行"
	String fundLink; // "http://jingzhi.funds.hexun.com/519185.shtml",
	String fundManager; // "莫海波",
	String redeemStatus; // "开放",
	String redeemRate; // "0.50%",
	String latestScale; // "60571775.94",
	Date setupDate; // "2009-05-18",
	String firstScale; // "1640074657.51",
	String simpleName; // "万家精选",
	String investType; // "成长型",
	Date dtUp; // "2016-11-10 01:37:22",
	String investStyle; // "混合型",
	String commonName; // "万家精选",
	String buyStatus; // "开放",
	String buyRate; // "1.50%",
	String manageCom; // "万家基金管理有限公司",
	int evLevel; // 4

	public String getFundCode() {
		return fundCode;
	}

	public void setFundCode(String fundCode) {
		this.fundCode = fundCode;
	}

	public String getTgCom() {
		return tgCom;
	}

	public void setTgCom(String tgCom) {
		this.tgCom = tgCom;
	}

	public String getFundLink() {
		return fundLink;
	}

	public void setFundLink(String fundLink) {
		this.fundLink = fundLink;
	}

	public String getFundManager() {
		return fundManager;
	}

	public void setFundManager(String fundManager) {
		this.fundManager = fundManager;
	}

	public String getRedeemStatus() {
		return redeemStatus;
	}

	public void setRedeemStatus(String redeemStatus) {
		this.redeemStatus = redeemStatus;
	}

	public String getRedeemRate() {
		return redeemRate;
	}

	public void setRedeemRate(String redeemRate) {
		this.redeemRate = redeemRate;
	}

	public String getLatestScale() {
		return latestScale;
	}

	public void setLatestScale(String latestScale) {
		this.latestScale = latestScale;
	}

	public Date getSetupDate() {
		return setupDate;
	}

	public void setSetupDate(Date setupDate) {
		this.setupDate = setupDate;
	}

	public String getFirstScale() {
		return firstScale;
	}

	public void setFirstScale(String firstScale) {
		this.firstScale = firstScale;
	}

	public String getSimpleName() {
		return simpleName;
	}

	public void setSimpleName(String simpleName) {
		this.simpleName = simpleName;
	}

	public String getInvestType() {
		return investType;
	}

	public void setInvestType(String investType) {
		this.investType = investType;
	}

	public Date getDtUp() {
		return dtUp;
	}

	public void setDtUp(Date dtUp) {
		this.dtUp = dtUp;
	}

	public String getInvestStyle() {
		return investStyle;
	}

	public void setInvestStyle(String investStyle) {
		this.investStyle = investStyle;
	}

	public String getCommonName() {
		return commonName;
	}

	public void setCommonName(String commonName) {
		this.commonName = commonName;
	}

	public String getBuyStatus() {
		return buyStatus;
	}

	public void setBuyStatus(String buyStatus) {
		this.buyStatus = buyStatus;
	}

	public String getBuyRate() {
		return buyRate;
	}

	public void setBuyRate(String buyRate) {
		this.buyRate = buyRate;
	}

	public String getManageCom() {
		return manageCom;
	}

	public void setManageCom(String manageCom) {
		this.manageCom = manageCom;
	}

	public int getEvLevel() {
		return evLevel;
	}

	public void setEvLevel(int evLevel) {
		this.evLevel = evLevel;
	}

}
