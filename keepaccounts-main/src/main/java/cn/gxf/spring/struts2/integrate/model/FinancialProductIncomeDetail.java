package cn.gxf.spring.struts2.integrate.model;

import java.io.Serializable;
import java.util.Date;

public class FinancialProductIncomeDetail implements Serializable{

	private static final long serialVersionUID = -3349928892791744350L;
	
	private String uuid;
	private String fpuuid;
	private String incomeuuid;
	private float je;
	private String is_redeem;
	private Date lrrq;
	private Date xgrq;
	private String yxbz;
	
	public void init(String uuid, String fpuuid, String incomeuuid, float je, String is_redeem, Date lrrq, Date xgrq, String yxbz)
	{
		this.uuid = uuid;
		this.fpuuid = fpuuid;
		this.incomeuuid = incomeuuid;
		this.je = je;
		this.is_redeem = is_redeem;
		this.lrrq = lrrq;
		this.xgrq = xgrq;
		this.yxbz = yxbz;
	}
	
	public String getUuid() {
		return uuid;
	}
	public void setUuid(String uuid) {
		this.uuid = uuid;
	}
	public String getFpuuid() {
		return fpuuid;
	}
	public void setFpuuid(String fpuuid) {
		this.fpuuid = fpuuid;
	}
	public String getIncomeuuid() {
		return incomeuuid;
	}
	public void setIncomeuuid(String incomeuuid) {
		this.incomeuuid = incomeuuid;
	}
	public float getJe() {
		return je;
	}
	public void setJe(float je) {
		this.je = je;
	}
	public String getIs_redeem() {
		return is_redeem;
	}
	public void setIs_redeem(String is_redeem) {
		this.is_redeem = is_redeem;
	}
	public Date getLrrq() {
		return lrrq;
	}
	public void setLrrq(Date lrrq) {
		this.lrrq = lrrq;
	}
	public Date getXgrq() {
		return xgrq;
	}
	public void setXgrq(Date xgrq) {
		this.xgrq = xgrq;
	}
	public String getYxbz() {
		return yxbz;
	}
	public void setYxbz(String yxbz) {
		this.yxbz = yxbz;
	}
	@Override
	public String toString() {
		return "FinancialProductIncomeDetail [uuid=" + uuid + ", fpuuid=" + fpuuid + ", incomeuuid=" + incomeuuid
				+ ", je=" + je + ", is_redeem=" + is_redeem + ", lrrq=" + lrrq + ", xgrq=" + xgrq + ", yxbz=" + yxbz
				+ "]";
	}
	
}
