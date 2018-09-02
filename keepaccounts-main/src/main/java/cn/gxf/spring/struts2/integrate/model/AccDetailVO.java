package cn.gxf.spring.struts2.integrate.model;

import java.util.Date;

public class AccDetailVO {
	private int user_id;
	private Date fsrq;
	private float je;
	private String lbmc;
	private String seller;
	private String bz;
	private int type;
	
	public int getUser_id() {
		return user_id;
	}
	
	public void setUser_id(int user_id) {
		this.user_id = user_id;
	}
	
	public Date getFsrq() {
		return fsrq;
	}
	public void setFsrq(Date fsrq) {
		this.fsrq = fsrq;
	}
	public float getJe() {
		return je;
	}
	public void setJe(float je) {
		this.je = je;
	}
	public String getLbmc() {
		return lbmc;
	}
	public void setLbmc(String lbmc) {
		this.lbmc = lbmc;
	}
	public String getSeller() {
		return seller;
	}
	public void setSeller(String seller) {
		this.seller = seller;
	}
	public String getBz() {
		return bz;
	}
	public void setBz(String bz) {
		this.bz = bz;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}

	@Override
	public String toString() {
		return "AccDetailVO [user_id=" + user_id + ", fsrq=" + fsrq + ", je=" + je + ", lbmc=" + lbmc + ", seller="
				+ seller + ", bz=" + bz + ", type=" + type + "]";
	}
	
	
}
