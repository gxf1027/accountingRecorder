package cn.gxf.spring.quartz.job.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class CreditCard implements Serializable{

	private static final long serialVersionUID = 9133401427210838031L;
	private String zh_dm;
	private String zh_mc;
	private String zdr;
	private String user_id;
	private float ye;
	
	public static List<String> getZhdmList(List<CreditCard> cards){
		if (null == cards || cards.size() == 0){
			return null;
		}
		List<String> zhdms = new ArrayList<>();
		for (CreditCard c : cards){
			zhdms.add(c.getZh_dm());
		}
		return zhdms;
	}
	
	public String getZh_dm() {
		return zh_dm;
	}
	public void setZh_dm(String zh_dm) {
		this.zh_dm = zh_dm;
	}
	public String getZh_mc() {
		return zh_mc;
	}
	public void setZh_mc(String zh_mc) {
		this.zh_mc = zh_mc;
	}
	public String getZdr() {
		return zdr;
	}
	public void setZdr(String zdr) {
		this.zdr = zdr;
	}
	public String getUser_id() {
		return user_id;
	}
	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}
	public float getYe() {
		return ye;
	}
	public void setYe(float ye) {
		this.ye = ye;
	}
	@Override
	public String toString() {
		return "CreditCard [zh_dm=" + zh_dm + ", zh_mc=" + zh_mc + ", zdr=" + zdr + ", user_id=" + user_id + ", ye="
				+ ye + "]";
	}
	
	
}
