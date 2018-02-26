package cn.gxf.spring.struts2.integrate.model;

import java.io.Serializable;
import java.util.Date;

public class AccountingDetail implements Serializable {
	private static final long serialVersionUID = 7705697704787989490L;
	private String accuuid;
	private int user_id;
	private float je;
	private Date shijian;
	private String yxbz;
	private Date xgrq;
	private int rec_dm;

	public String getAccuuid() {
		return accuuid;
	}

	public void setAccuuid(String accuuid) {
		this.accuuid = accuuid;
	}

	public int getUser_id() {
		return user_id;
	}

	public void setUser_id(int user_id) {
		this.user_id = user_id;
	}

	public float getJe() {
		return je;
	}

	public void setJe(float je) {
		this.je = je;
	}

	public Date getShijian() {
		return shijian;
	}

	public void setShijian(Date shijian) {
		this.shijian = shijian;
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

	public void setRec_dm(int record_dm) {
		this.rec_dm = record_dm;
	}
	
	public int getRec_dm() {
		return rec_dm;
	}
}
