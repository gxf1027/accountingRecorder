package cn.gxf.spring.struts2.integrate.model;

import java.util.Date;

public class TransferDetailVO extends AccountingDetailVO {
	/*
	 * private int user_id; private Date fsrq; private float je; private String
	 * bz; private int type;
	 */
	private String mxuuid;
	private String srcZhmc;
	private String tgtZhmc;
	
	public String getMxuuid() {
		return mxuuid;
	}
	
	public void setMxuuid(String mxuuid) {
		this.mxuuid = mxuuid;
	}

	public String getSrcZhmc() {
		return srcZhmc;
	}

	public void setSrcZhmc(String srcZhmc) {
		this.srcZhmc = srcZhmc;
	}

	public String getTgtZhmc() {
		return tgtZhmc;
	}

	public void setTgtZhmc(String tgtZhmc) {
		this.tgtZhmc = tgtZhmc;
	}

	@Override
	public String toString() {
		return "TransferDetailVO [srcZhmc=" + srcZhmc + ", tgtZhmc=" + tgtZhmc + ", toString()=" + super.toString()
				+ "]";
	}

	@Override
	public int compareTo(AccountingDetailVO o) {
		return this.getFsrq().compareTo(o.getFsrq());
	}

}
