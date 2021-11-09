package cn.gxf.spring.struts2.integrate.model;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "IncomeDetail")
@XmlType
public class IncomeDetail extends AccountObject implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 853505961922326813L;

	/*@XmlElement(required=true)*/
	private String mxuuid;
	/*@XmlElement(required=true)*/
	private String accuuid;
	/*@XmlElement(nillable=true)*/
	private String finprodUuid; // 与“收入”关联的“理财产品”，当收入类型是“理财收益”时有效
	private String is_redeem; // 与“收入”关联的“理财产品”，当收入类型是“理财收益”时有效
	private String user_name;
	private int user_id;
	private float je;
	private String lb_dm;
	private String fkfmc;
	private String zh_dm;
	private Date shijian;
	/*@XmlElement(nillable=true)*/
	private String bz;
	private String yxbz;
	/*@XmlElement(nillable=true)*/
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
	
	public String getFinprodUuid() {
		return finprodUuid;
	}
	
	public void setFinprodUuid(String finprodUuid) {
		this.finprodUuid = finprodUuid;
	}

	public String getIs_redeem() {
		return is_redeem;
	}
	
	public void setIs_redeem(String is_redeem) {
		this.is_redeem = is_redeem;
	}
	
	public int getUser_id() {
		return user_id;
	}

	public void setUser_id(int user_id) {
		this.user_id = user_id;
	}

	public String getUser_name() {
		return user_name;
	}

	public void setUser_name(String user_name) {
		this.user_name = user_name;
	}

	public float getJe() {
		return je;
	}

	public void setJe(float je) {
		this.je = je;
	}

	public String getLb_dm() {
		return lb_dm;
	}

	public void setLb_dm(String lb_dm) {
		this.lb_dm = lb_dm;
	}

	public String getFkfmc() {
		return fkfmc;
	}

	public void setFkfmc(String fkfmc) {
		this.fkfmc = fkfmc;
	}

	public String getZh_dm() {
		return zh_dm;
	}

	public void setZh_dm(String zh_dm) {
		this.zh_dm = zh_dm;
	}

	public Date getShijian() {
		return shijian;
	}

	public void setShijian(Date shijian) {
		this.shijian = shijian;
	}

	public String getBz() {
		return bz;
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
			System.out.println(this.getClass() + "getFsrq error");
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
		}
		return null;
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
		return 1;
	}

	@Override
	public String toString() {
		return "IncomeDetail [mxuuid=" + mxuuid + ", accuuid=" + accuuid + ", finprodUuid=" + finprodUuid
				+ ", user_name=" + user_name + ", user_id=" + user_id + ", je=" + je + ", lb_dm=" + lb_dm + ", fkfmc="
				+ fkfmc + ", zh_dm=" + zh_dm + ", shijian=" + shijian + ", bz=" + bz + ", yxbz=" + yxbz + ", xgrq="
				+ xgrq + "]";
	}

}
