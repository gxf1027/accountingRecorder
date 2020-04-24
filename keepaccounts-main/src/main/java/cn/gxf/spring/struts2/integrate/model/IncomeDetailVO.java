package cn.gxf.spring.struts2.integrate.model;


public class IncomeDetailVO extends AccountingDetailVO {
	/**
	 * 
	 */
	private static final long serialVersionUID = -1173065464477163457L;
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
	public int compareTo(AccountingDetailVO o) {

		return this.getFsrq().compareTo(o.getFsrq());
	}

}
