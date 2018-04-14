package cn.gxf.spring.struts2.integrate.model;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

public class FinancialProductDetail implements Serializable{

	private static final long serialVersionUID = -3685279529329841186L;
	private String uuid;
	private String transferUuid;
	private String productName;
	private String yh_dm;
	private int dateCount;
	private Date startDate;
	private Date endDate;
	private float expectedReturnRate;
	private float je;
	private float realReturn;
	private Date lrrq;
	private Date xgrq;
	private String yxbz;
	public String getUuid() {
		return uuid;
	}
	public void setUuid(String uuid) {
		this.uuid = uuid;
	}
	public String getTransferUuid() {
		return transferUuid;
	}
	public void setTransferUuid(String transferUuid) {
		this.transferUuid = transferUuid;
	}
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	public String getYh_dm() {
		return yh_dm;
	}
	public void setYh_dm(String yh_dm) {
		this.yh_dm = yh_dm;
	}
	public int getDateCount() {
		return dateCount;
	}
	public void setDateCount(int dateCount) {
		this.dateCount = dateCount;
	}
	public Date getStartDate() {
		return startDate;
	}
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}
	/* 用于在回显时，input type="date"中显示  */
	public String getStartDateToShow(){
		return dateToShow(this.startDate);
	}
	public Date getEndDate() {
		return endDate;
	}
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	/* 用于在回显时，input type="date"中显示  */
	public String getEndDateToShow(){
		return dateToShow(this.endDate);
	}
	public float getExpectedReturnRate() {
		return expectedReturnRate;
	}
	public void setExpectedReturnRate(float expectedReturnRate) {
		this.expectedReturnRate = expectedReturnRate;
	}
	public float getJe() {
		return je;
	}
	public void setJe(float je) {
		this.je = je;
	}
	public float getRealReturn() {
		return realReturn;
	}
	public void setRealReturn(float realReturn) {
		this.realReturn = realReturn;
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
		return "FinancialProductDetail [uuid=" + uuid + ", transferUuid=" + transferUuid + ", productName="
				+ productName + ", yh_dm=" + yh_dm + ", dateCount=" + dateCount + ", startDate=" + startDate
				+ ", endDate=" + endDate + ", expectedReturnRate=" + expectedReturnRate + ", je=" + je + ", realReturn="
				+ realReturn + ", lrrq=" + lrrq + ", xgrq=" + xgrq + ", yxbz=" + yxbz + "]";
	}
	
	private String dateToShow(Date date){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		try {
			if (date != null){
				return sdf.format(date);
			}
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println(this.getClass() + "dateToShow error");
			e.printStackTrace();
		}
		return null;
	}
	
}
