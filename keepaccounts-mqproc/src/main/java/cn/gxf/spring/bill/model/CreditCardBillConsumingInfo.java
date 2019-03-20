package cn.gxf.spring.bill.model;

import java.io.Serializable;
import java.util.Date;

public class CreditCardBillConsumingInfo implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -8202293108407100289L;
	
	private String pch;
	private String threadId;
	private Date revTime;
	private Date sendTime;
	private String yxbz;
	public String getPch() {
		return pch;
	}
	public void setPch(String pch) {
		this.pch = pch;
	}
	public String getThreadId() {
		return threadId;
	}
	public void setThreadId(String threadId) {
		this.threadId = threadId;
	}
	public Date getRevTime() {
		return revTime;
	}
	public void setRevTime(Date revTime) {
		this.revTime = revTime;
	}
	public Date getSendTime() {
		return sendTime;
	}
	public void setSendTime(Date sendTime) {
		this.sendTime = sendTime;
	}
	public String getYxbz() {
		return yxbz;
	}
	public void setYxbz(String yxbz) {
		this.yxbz = yxbz;
	}
	@Override
	public String toString() {
		return "CreditCardBillConsumingInfo [pch=" + pch + ", threadId=" + threadId + ", revTime=" + revTime
				+ ", sendTime=" + sendTime + ", yxbz=" + yxbz + "]";
	}
	
}
