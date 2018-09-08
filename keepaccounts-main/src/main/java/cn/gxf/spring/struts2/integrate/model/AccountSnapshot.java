package cn.gxf.spring.struts2.integrate.model;

import java.io.Serializable;
import java.util.Date;

public class AccountSnapshot implements Serializable {

	private static final long serialVersionUID = 8920701717635699678L;
	private String uuid;
	private String accuuid;
	private String type;
	private int user_id;
	private String zh_dm;
	private float bdje; // �䶯���
	private float fshje; // ��������
	private Date lrrq;
	

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

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

	public String getZh_dm() {
		return zh_dm;
	}

	public void setZh_dm(String zh_dm) {
		this.zh_dm = zh_dm;
	}

	public float getBdje() {
		return bdje;
	}

	public void setBdje(float bdje) {
		this.bdje = bdje;
	}

	public float getFshje() {
		return fshje;
	}

	public void setFshje(float fshje) {
		this.fshje = fshje;
	}

	public Date getLrrq() {
		return lrrq;
	}

	public void setLrrq(Date lrrq) {
		this.lrrq = lrrq;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	@Override
	public String toString() {
		return "AccountSnapshot [uuid=" + uuid + ", accuuid=" + accuuid + ", type=" + type + ", user_id=" + user_id
				+ ", zh_dm=" + zh_dm + ", bdje=" + bdje + ", fshje=" + fshje + ", lrrq=" + lrrq + "]";
	}

}
