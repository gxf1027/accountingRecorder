package cn.gxf.spring.quartz.job.model;

import java.io.Serializable;
import java.util.Date;

public class CreditCardRecordSimplified implements Serializable {

	private static final long serialVersionUID = -7363637009144834284L;
	private String billuuid;
	private Date jysj;
	private String jylx;
	private String jyf;
	private float je;
	private String bz;
	private String isMailed;

	public CreditCardRecordSimplified() {
		super();
	}

	public CreditCardRecordSimplified(CreditCardTransRecord cctr) {
		this.billuuid = cctr.getBilluuid();
		this.jysj = cctr.getJysj();
		this.jylx = cctr.getJylx();
		this.jyf = cctr.getJyf();
		this.je = cctr.getJe();
		this.bz = cctr.getBz();
		this.isMailed = cctr.getIsMailed();
	}

	public String getBilluuid() {
		return billuuid;
	}

	public void setBilluuid(String billuuid) {
		this.billuuid = billuuid;
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

	public String getIsMailed() {
		return isMailed;
	}

	public void setIsMailed(String isMailed) {
		this.isMailed = isMailed;
	}

	@Override
	public String toString() {
		return "CreditCardRecordSimplified [billuuid=" + billuuid + ", jysj=" + jysj + ", jylx=" + jylx + ", jyf=" + jyf
				+ ", je=" + je + ", bz=" + bz + ", isMailed=" + isMailed + "]";
	}

}
