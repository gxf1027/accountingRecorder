package cn.gxf.spring.quartz.job.model;

import java.io.Serializable;
import java.util.Date;

public class CreditCardTransRecord implements Serializable{
	private static final long serialVersionUID = -631767788340849680L;
	private String billuuid;
	private Integer user_id; 
	private Date bill_ssqq;
	private Date bill_ssqz;
	private String zh_dm;
	private String zh_mc;
	private Date jysj;
	private String jylx;
	private String jyf;
	private float je;
	private String bz;
	private String yxbz;
	private Date lrrq;
	private Date xgrq;
	private String isMailed;
	
	public String getBilluuid() {
		return billuuid;
	}
	public void setBilluuid(String billuuid) {
		this.billuuid = billuuid;
	}
	public Date getBill_ssqq() {
		return bill_ssqq;
	}
	public Integer getUser_id() {
		return user_id;
	}
	public void setUser_id(Integer user_id) {
		this.user_id = user_id;
	}
	public void setBill_ssqq(Date bill_ssqq) {
		this.bill_ssqq = bill_ssqq;
	}
	public Date getBill_ssqz() {
		return bill_ssqz;
	}
	public void setBill_ssqz(Date bill_ssqz) {
		this.bill_ssqz = bill_ssqz;
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
	public Date getJysj() {
		return jysj;
	}
	public void setJysj(Date jysj) {
		this.jysj = jysj;
	}
	public String getJylx() {
		return jylx;
	}
	public void setJylx(String jylx) {
		this.jylx = jylx;
	}
	public String getJyf() {
		return jyf;
	}
	public void setJyf(String jyf) {
		this.jyf = jyf;
	}
	public float getJe() {
		return je;
	}
	public void setJe(float je) {
		this.je = je;
	}
	public String getBz() {
		return bz;
	}
	public void setBz(String bz) {
		this.bz = bz;
	}
	public String getYxbz() {
		return yxbz;
	}
	public void setYxbz(String yxbz) {
		this.yxbz = yxbz;
	}
	public Date getXgrq() {
		return xgrq;
	}
	public void setXgrq(Date xgrq) {
		this.xgrq = xgrq;
	}
	public Date getLrrq() {
		return lrrq;
	}
	public void setLrrq(Date lrrq) {
		this.lrrq = lrrq;
	}
	public void setIsMailed(String isMailed) {
		this.isMailed = isMailed;
	}
	public String getIsMailed() {
		return isMailed;
	}
	@Override
	public String toString() {
		return "CreditCardTransRecord [billuuid=" + billuuid + ", user_id=" + user_id + ", bill_ssqq=" + bill_ssqq
				+ ", bill_ssqz=" + bill_ssqz + ", zh_dm=" + zh_dm + ", zh_mc=" + zh_mc + ", jysj=" + jysj + ", jylx="
				+ jylx + ", jyf=" + jyf + ", je=" + je + ", bz=" + bz + ", yxbz=" + yxbz + ", lrrq=" + lrrq + ", xgrq="
				+ xgrq + ", isMailed=" + isMailed + "]";
	}
	
}
