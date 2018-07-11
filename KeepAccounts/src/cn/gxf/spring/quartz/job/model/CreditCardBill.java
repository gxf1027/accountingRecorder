package cn.gxf.spring.quartz.job.model;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class CreditCardBill implements Serializable{
	
	private static final long serialVersionUID = -3175017787487738311L;
	private String pch;
	private String username;
	private int user_id;
	private String email;
	private String zh_dm;
	private String zh_mc;
	private String ssqq;
	private String ssqz;
	private float yhkje; // 本期需还款金额
	private List<CreditCardRecordSimplified> cctrList;
	
	
	
	public CreditCardBill() {
		super();
	}
	
	public void init(CreditCardTransRecord cctr, Date jyqq, Date jyqz){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd"); 
		this.user_id = cctr.getUser_id().intValue();
		this.zh_dm = cctr.getZh_dm();
		this.zh_mc = cctr.getZh_mc();
		this.ssqq = sdf.format(jyqq);
		this.ssqz = sdf.format(jyqz);
		this.yhkje = cctr.getJe();
		this.pch = generatePzh();
		this.cctrList = new ArrayList<CreditCardRecordSimplified>();
		this.cctrList.add(new CreditCardRecordSimplified(cctr));
	}
	
	public void add(CreditCardTransRecord cctr){
		this.cctrList.add(new CreditCardRecordSimplified(cctr));
		this.yhkje += cctr.getJe();
	}

	public String getPch() {
		return pch;
	}
	
	public void setPch(String pch) {
		this.pch = pch;
	}

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
	
	public String getEmail() {
		return email;
	}
	
	public void setEmail(String email) {
		this.email = email;
	}
	

	public String getSsqq() {
		return ssqq;
	}

	public String getZh_dm() {
		return zh_dm;
	}


	public void setZh_dm(String zh_dm) {
		this.zh_dm = zh_dm;
	}
	
	public String getZh_mc() {
		return zh_mc;
	}
	
	public void setZh_mc(String zh_mc) {
		this.zh_mc = zh_mc;
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

	public List<CreditCardRecordSimplified> getCctrList() {
		return cctrList;
	}


	public void setCctrList(List<CreditCardRecordSimplified> cctrList) {
		this.cctrList = cctrList;
	}
	
	public List<String> getMxUuidList(){
		
		List<String> uuidList = new ArrayList<>();
		for (CreditCardRecordSimplified item : this.cctrList){
			uuidList.add(item.getBilluuid());
		}
		
		return uuidList;
	}
	
	// 获取zh_detail_ccbill的主键
	private String generatePzh(){
		
		return this.zh_dm +"-"+this.ssqq.replaceAll("-", "")+this.ssqz.replaceAll("-", "")+"-"+String.valueOf(System.currentTimeMillis());
	}

	@Override
	public String toString() {
		return "CreditCardBill [pch=" + pch + ", username=" + username + ", user_id=" + user_id + ", email=" + email
				+ ", zh_dm=" + zh_dm + ", zh_mc=" + zh_mc + ", ssqq=" + ssqq + ", ssqz=" + ssqz + ", yhkje=" + yhkje
				+ ", cctrList=" + cctrList + "]";
	}

}
