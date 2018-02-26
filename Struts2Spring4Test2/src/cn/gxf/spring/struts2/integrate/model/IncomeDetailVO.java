package cn.gxf.spring.struts2.integrate.model;

import java.util.Date;

public class IncomeDetailVO extends AccountingDetailVO {
	/*
	 * private int user_id; private Date fsrq; private float je; private String
	 * bz; private int type;
	 */
	private String mxuuid;
	private String lbmc;
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

	public String getSeller() {
		return seller;
	}

	public void setSeller(String seller) {
		this.seller = seller;
	}

	@Override
	public String toString() {
		return "IncomeDetailVO [lbmc=" + lbmc + ", seller=" + seller + ", toString()=" + super.toString() + "]";
	}

	@Override
	public int compareTo(AccountingDetailVO o) {

		return this.getFsrq().compareTo(o.getFsrq());
	}

}
