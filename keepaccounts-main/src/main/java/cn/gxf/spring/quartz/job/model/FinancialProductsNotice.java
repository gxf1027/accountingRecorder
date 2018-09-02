package cn.gxf.spring.quartz.job.model;

import java.io.Serializable;
import java.util.List;

import cn.gxf.spring.struts2.integrate.model.FinancialProductDetail;

public class FinancialProductsNotice implements Serializable{

	private static final long serialVersionUID = -7698015969668613928L;
	private String uuid;
	private String pch;
	private String username;
	private int user_id;
	private String email;
	private String ssqq;
	private String ssqz;
	private List<FinancialProductDetail> finanProducts;
	
	public String getUuid() {
		return uuid;
	}
	
	public void setUuid(String uuid) {
		this.uuid = uuid;
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
	public void setSsqq(String ssqq) {
		this.ssqq = ssqq;
	}
	public String getSsqz() {
		return ssqz;
	}
	public void setSsqz(String ssqz) {
		this.ssqz = ssqz;
	}
	public List<FinancialProductDetail> getFinanProducts() {
		return finanProducts;
	}
	public void setFinanProducts(List<FinancialProductDetail> finanProducts) {
		this.finanProducts = finanProducts;
	}
	
	
}
