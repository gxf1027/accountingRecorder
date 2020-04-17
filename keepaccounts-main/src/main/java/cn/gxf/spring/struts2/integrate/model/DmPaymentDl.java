package cn.gxf.spring.struts2.integrate.model;

import java.io.Serializable;

public class DmPaymentDl implements Comparable<DmPaymentDl>, Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 3761670436115465074L;
	
	private String dl_dm;
	private String dl_mc;
	private String yxbz;
	private String xybz;
	public String getDl_dm() {
		return dl_dm;
	}
	public void setDl_dm(String dl_dm) {
		this.dl_dm = dl_dm;
	}
	public String getDl_mc() {
		return dl_mc;
	}
	public void setDl_mc(String dl_mc) {
		this.dl_mc = dl_mc;
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
		return "DmPaymentDl [dl_dm=" + dl_dm + ", dl_mc=" + dl_mc + ", yxbz=" + yxbz + ", xybz=" + xybz + "]";
	}
	@Override
	public int compareTo(DmPaymentDl o) {
		
		if (o.dl_dm == null || this.dl_dm == null){
			return -1;
		}
		
		return this.getDl_dm().compareTo(o.getDl_dm());
	}

	
}
