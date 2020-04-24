package cn.gxf.spring.struts2.integrate.model;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.lang3.builder.ToStringBuilder;

public abstract class AccountingDetailVO implements Serializable, Comparable<AccountingDetailVO>{
	
	private static final long serialVersionUID = 8496050198477700624L;
	private String accuuid;
	private int user_id;
	private Date fsrq;
	private float je;
	private String bz;
	private int type;
	
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

	public String getFsrqToShow() {
		SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");  // 小时：分钟
		if (fsrq != null){
			return sdf.format(fsrq);
		}
		return null;
	}
	
	public String getFsrqToShowFull() {
		if (fsrq != null){
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");  // 小时：分钟
			return sdf.format(fsrq);
		}
		return null;
	}

	public Date getFsrq() {
		return fsrq;
	}

	public void setFsrq(Date fsrq) {
		this.fsrq = fsrq;
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

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}

}
