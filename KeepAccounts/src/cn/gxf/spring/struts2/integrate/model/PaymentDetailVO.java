package cn.gxf.spring.struts2.integrate.model;

import java.util.Date;

public class PaymentDetailVO extends AccountingDetailVO {
	private String mxuuid;
	private String lbmc;
	private String xlmc; // –°¿‡
	private String category_mc;
	private String seller;

	public String getMxuuid() {
		return mxuuid;
	}
	
	public void setMxuuid(String mxuuid) {
		this.mxuuid = mxuuid;
	}
	
	public String getLbmc() {
		return lbmc;
	}

	public void setLbmc(String lbmc) {
		this.lbmc = lbmc;
	}

	public String getXlmc() {
		return xlmc;
	}

	public void setXlmc(String xlmc) {
		this.xlmc = xlmc;
	}
	
	public String getCategory_mc() {
		return category_mc;
	}
	
	public void setCategory_mc(String category_mc) {
		this.category_mc = category_mc;
	}

	public String getSeller() {
		return seller;
	}

	public void setSeller(String seller) {
		this.seller = seller;
	}

	@Override
	public String toString() {
		return "PaymentDetailVO [mxuuid=" + mxuuid + ", lbmc=" + lbmc + ", xlmc=" + xlmc + ", category_mc="
				+ category_mc + ", seller=" + seller + "]";
	}

	@Override
	public int compareTo(AccountingDetailVO o) {
		return this.getFsrq().compareTo(o.getFsrq());
	}
}
