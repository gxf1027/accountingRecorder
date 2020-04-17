package cn.gxf.spring.struts2.integrate.model;

import java.io.Serializable;

public class DmPaymentXl implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -4926691898376038851L;
	
	private String xl_dm;
	private String xl_mc;
	private String dl_dm;
	private String yxbz;
	private String xybz;
	
	
	
	public String getXl_dm() {
		return xl_dm;
	}



	public void setXl_dm(String xl_dm) {
		this.xl_dm = xl_dm;
	}



	public String getXl_mc() {
		return xl_mc;
	}



	public void setXl_mc(String xl_mc) {
		this.xl_mc = xl_mc;
	}



	public String getDl_dm() {
		return dl_dm;
	}



	public void setDl_dm(String dl_dm) {
		this.dl_dm = dl_dm;
	}



	public String getYxbz() {
		return yxbz;
	}



	public void setYxbz(String yxbz) {
		this.yxbz = yxbz;
	}



	public String getXybz() {
		return xybz;
	}



	public void setXybz(String xybz) {
		this.xybz = xybz;
	}



	@Override
	public String toString() {
		return "DmPaymentXl [xl_dm=" + xl_dm + ", xl_mc=" + xl_mc + ", dl_dm=" + dl_dm + ", yxbz=" + yxbz + ", xybz="
				+ xybz + "]";
	}
	
	
}
