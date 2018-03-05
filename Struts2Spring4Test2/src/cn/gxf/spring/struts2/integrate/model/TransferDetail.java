package cn.gxf.spring.struts2.integrate.model;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class TransferDetail extends AccountObject implements Serializable{
	private static final long serialVersionUID = 3519049834018168814L;
	private String mxuuid;
	private String accuuid;
	private String user_name;
	private int user_id;
	private float je;
	private String srcZh_dm;
	private String tgtZh_dm;
	private Date shijian;
	private String zzlx_dm;
	private String bz;
	private String yxbz;
	private Date xgrq;

	public String getMxuuid() {
		return mxuuid;
	}

	public void setMxuuid(String mxuuid) {
		this.mxuuid = mxuuid;
	}
	
	public String getAccuuid() {
		return accuuid;
	}

	public void setAccuuid(String accuuid) {
		this.accuuid = accuuid;
	}

	public String getUser_name() {
		return user_name;
	}

	public void setUser_name(String user_name) {
		this.user_name = user_name;
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

	public String getSrcZh_dm() {
		return srcZh_dm;
	}

	public void setSrcZh_dm(String srcZh_dm) {
		this.srcZh_dm = srcZh_dm;
	}

	public String getTgtZh_dm() {
		return tgtZh_dm;
	}

	public void setTgtZh_dm(String tgtZh_dm) {
		this.tgtZh_dm = tgtZh_dm;
	}

	public Date getShijian() {
		return shijian;
	}

	public void setShijian(Date shijian) {
		this.shijian = shijian;
	}
	
	public void setFsrq(String fsrq) throws ParseException{
		// 参数fsrq格式： 2017-10-21T16:36，要把中间的“T”去掉，否则sdf.parse(fsrq)会抛出异常
		// System.out.println("setFsrq: " + fsrq);
		fsrq = fsrq.replace("T", " ");
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		this.shijian = sdf.parse(fsrq);
		//System.out.println("setFsrq: " + fsrq);
		
	}
	
	public String getFsrq(){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		try {
			if (this.shijian != null){
				return sdf.format(this.shijian);
			}
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println(this.getClass() + "getFsrq error");
			e.printStackTrace();
		}
		return null;
	}
	
	/* 用于在回显时，input type="datetime-local"中显示  */
	public String getFsrqToShow(){
		SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
		SimpleDateFormat sdf2 = new SimpleDateFormat("HH:mm");
		try {
			if (this.shijian != null){
				return sdf1.format(this.shijian)+"T"+sdf2.format(this.shijian);
			}
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println(this.getClass() + "getFsrq error");
			e.printStackTrace();
		}
		return null;
	}
	
	public String getZzlx_dm() {
		return zzlx_dm;
	}
	
	public void setZzlx_dm(String zzlx_dm) {
		this.zzlx_dm = zzlx_dm;
	}

	public String getBz() {
		return bz;
	}

	public void setBz(String bz) {
		this.bz = bz;
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
	
	public int getType(){
		return 3;
	}

	@Override
	public String toString() {
		return "TransferDetail [mxuuid=" + mxuuid + ", user_name=" + user_name + ", user_id=" + user_id + ", je=" + je
				+ ", srcZh_dm=" + srcZh_dm + ", tgtZh_dm=" + tgtZh_dm + ", shijian=" + shijian + ", bz=" + bz
				+ ", yxbz=" + yxbz + ", xgrq=" + xgrq + "]";
	}
}
