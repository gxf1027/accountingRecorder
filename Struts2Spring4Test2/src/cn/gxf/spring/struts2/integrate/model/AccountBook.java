package cn.gxf.spring.struts2.integrate.model;

public class AccountBook {
	private String zh_dm;
	private String zh_mc;
	private String khrmc;
	private int user_id;
	private String zhhm; // �˻�����
	private String yxbz;
	private String zhlx_dm; // �˻�����
	private float ye;

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

	public String getKhrmc() {
		return khrmc;
	}

	public void setKhrmc(String khrmc) {
		this.khrmc = khrmc;
	}

	
	public String getZhhm() {
		return zhhm;
	}

	public void setZhhm(String zhhm) {
		this.zhhm = zhhm;
	}

	public String getYxbz() {
		return yxbz;
	}

	public void setYxbz(String yxbz) {
		this.yxbz = yxbz;
	}
	

	public String getZhlx_dm() {
		return zhlx_dm;
	}

	public void setZhlx_dm(String zhlx_dm) {
		this.zhlx_dm = zhlx_dm;
	}

	public int getUser_id() {
		return user_id;
	}

	public void setUser_id(int user_id) {
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
		return "AccountDetialInfo [zh_dm=" + zh_dm + ", zh_mc=" + zh_mc + ", khrmc=" + khrmc + ", user_id=" + user_id
				+ ", zhhm=" + zhhm + ", yxbz=" + yxbz + ", zhlx_dm=" + zhlx_dm + ", ye=" + ye + "]";
	}
}
