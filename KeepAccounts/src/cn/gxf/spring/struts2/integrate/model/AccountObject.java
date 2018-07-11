package cn.gxf.spring.struts2.integrate.model;

import java.util.Date;

public abstract class AccountObject {
	public abstract void setJe(float je);
	public abstract float getJe();
	public abstract Date getShijian();
	public abstract int getUser_id();
	public abstract Date getXgrq();
	public abstract String getMxuuid();
	public abstract void setMxuuid(String mxuuid);
	public abstract String getAccuuid();
	public abstract void setAccuuid(String accuuid);
	public abstract int getType();
}
