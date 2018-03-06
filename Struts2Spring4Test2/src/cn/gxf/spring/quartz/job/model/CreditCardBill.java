package cn.gxf.spring.quartz.job.model;

import java.io.Serializable;
import java.util.List;

public class CreditCardBill implements Serializable{
	
	private static final long serialVersionUID = -3175017787487738311L;
	private String username;
	private int user_id;
	private String ssqq;
	private String ssqz;
	private float yhkje; // 本期需还款金额
	private List<CreditCardTransRecord> cctrList;
	
	
	public String getUsername() {
		return username;
	}


	public void setUsername(String username) {
		this.username = username;
	}

	public int getUser_id() {
		return user_id;
	}
	
	public void setUser_id(int user_id) {
		this.user_id = user_id;
	}

	public String getSsqq() {
		return ssqq;
	}


	public void setSsqq(String ssqq) {
		this.ssqq = ssqq;
	}


	public String getSsqz() {
		return ssqz;
	}


	public void setSsqz(String ssqz) {
		this.ssqz = ssqz;
	}


	public float getYhkje() {
		return yhkje;
	}


	public void setYhkje(float yhkje) {
		this.yhkje = yhkje;
	}


	public List<CreditCardTransRecord> getCctrList() {
		return cctrList;
	}


	public void setCctrList(List<CreditCardTransRecord> cctrList) {
		this.cctrList = cctrList;
	}


	@Override
	public String toString() {
		return "CreditCardBill [username=" + username + ", ssqq=" + ssqq + ", ssqz=" + ssqz + ", yhkje=" + yhkje
				+ ", cctrList=" + cctrList + "]";
	}
	
}
