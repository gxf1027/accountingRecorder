package cn.gxf.spring.struts2.integrate.model;

import java.io.Serializable;
import java.util.Date;

public class FundDetail implements Serializable{

	private static final long serialVersionUID = 4429213054083358932L;
	private String transferUuid;
	private String fundCode;
	private String fundName;
	private float unitNet; // 单位净值
	private float extraFee; // 手续费
	private float confirmedSum; // 确认金额 
	private Date lrrq;
	private Date xgrq;
	
	
	public String getTransferUuid() {
		return transferUuid;
	}
	public void setTransferUuid(String transferUuid) {
		this.transferUuid = transferUuid;
	}
	public String getFundCode() {
		return fundCode;
	}
	public void setFundCode(String fundCode) {
		this.fundCode = fundCode;
	}
	public String getFundName() {
		return fundName;
	}
	public void setFundName(String fundName) {
		this.fundName = fundName;
	}
	public float getUnitNet() {
		return unitNet;
	}
	public void setUnitNet(float unitNet) {
		this.unitNet = unitNet;
	}
	public float getExtraFee() {
		return extraFee;
	}
	public void setExtraFee(float extraFee) {
		this.extraFee = extraFee;
	}
	public float getConfirmedSum() {
		return confirmedSum;
	}
	public void setConfirmedSum(float confirmedSum) {
		this.confirmedSum = confirmedSum;
	}
	public Date getLrrq() {
		return lrrq;
	}
	public void setLrrq(Date lrrq) {
		this.lrrq = lrrq;
	}
	public Date getXgrq() {
		return xgrq;
	}
	public void setXgrq(Date xgrq) {
		this.xgrq = xgrq;
	}
	@Override
	public String toString() {
		return "FundDetail [transferUuid=" + transferUuid + ", fundCode=" + fundCode + ", fundName=" + fundName
				+ ", unitNet=" + unitNet + ", extraFee=" + extraFee + ", confirmedSum=" + confirmedSum + ", lrrq="
				+ lrrq + ", xgrq=" + xgrq + "]";
	}
	
	
}
