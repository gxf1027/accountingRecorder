package cn.gxf.spring.struts2.integrate.model;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "PaymentDetail")
@XmlType
public class PaymentDetail extends AccountObject implements Serializable {

	
	private static final long serialVersionUID = 2228658324437876254L;

	private String mxuuid;
	private String accuuid;
	private String user_name;
	private int user_id;
	private float je;
	private float yhje;
	private String dl_dm;
	private String xl_dm;
	private String seller;
	private String zh_dm;
	private String category_dm;
	private Date shijian;
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

	public float getJe() {
		return je;
	}

	public void setJe(float je) {
		this.je = je;
	}
	
	public float getYhje() {
		return yhje;
	}
	
	public void setYhje(float yhje) {
		this.yhje = yhje;
	}

	public String getDl_dm() {
		return dl_dm;
	}

	public void setDl_dm(String dl_dm) {
		this.dl_dm = dl_dm;
	}

	public String getXl_dm() {
		return xl_dm;
	}

	public void setXl_dm(String xl_dm) {
		this.xl_dm = xl_dm;
	}

	public String getSeller() {
		return seller;
	}

	public void setSeller(String seller) {
		this.seller = seller;
	}

	public String getZh_dm() {
		return zh_dm;
	}

	public void setZh_dm(String zh_dm) {
		this.zh_dm = zh_dm;
	}

	public String getCategory_dm() {
		return category_dm;
	}
	
	public void setCategory_dm(String category_dm) {
		this.category_dm = category_dm;
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
	
	public int getType(){
		return 2;
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

	public int getUser_id() {
		return user_id;
	}

	public void setUser_id(int user_id) {
		this.user_id = user_id;
	}

	@Override
	public String toString() {
		return "PaymentDetail [mxuuid=" + mxuuid + ", accuuid=" + accuuid + ", user_name=" + user_name + ", user_id="
				+ user_id + ", je=" + je + ", yhje=" + yhje + ", dl_dm=" + dl_dm + ", xl_dm=" + xl_dm + ", seller="
				+ seller + ", zh_dm=" + zh_dm + ", category_dm=" + category_dm + ", shijian=" + shijian + ", bz=" + bz
				+ ", yxbz=" + yxbz + ", xgrq=" + xgrq + "]";
	}

}
