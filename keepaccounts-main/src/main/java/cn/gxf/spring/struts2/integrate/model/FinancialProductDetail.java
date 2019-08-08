package cn.gxf.spring.struts2.integrate.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "FinancialProductDetail")
@XmlType
public class FinancialProductDetail implements Serializable {

	private static final long serialVersionUID = -3685279529329841186L;

	private String uuid;
	private String transferUuid;
	private String redeemUuid;
	private String returnUuid;
	private Integer user_id;
	private String productName;
	private String productType;
	private String yh_dm;
	private String yh_mc;
	private int dateCount;
	private Date startDate;
	private Date endDate;
	private float expectedReturnRate;
	private float netvPurchase;
	private float netvSelling;
	private float je;
	private float realReturn;
	private String is_redeem;
	private Date lrrq;
	private Date xgrq;
	private String yxbz;

	public FinancialProductDetail() {
		this.is_redeem = "N"; // Ĭ�ϣ���ʼ����ʱ��ر�־λN
	}

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

	public String getRedeemUuid() {
		return redeemUuid;
	}

	public void setRedeemUuid(String redeemUuid) {
		this.redeemUuid = redeemUuid;
	}

	public String getReturnUuid() {
		return returnUuid;
	}

	public void setReturnUuid(String returnUuid) {
		this.returnUuid = returnUuid;
	}

	public Integer getUser_id() {
		return user_id;
	}

	public void setUser_id(Integer user_id) {
		this.user_id = user_id;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public String getProductType() {
		return productType;
	}

	public void setProductType(String productType) {
		this.productType = productType;
	}

	public String getYh_dm() {
		return yh_dm;
	}

	public void setYh_dm(String yh_dm) {
		this.yh_dm = yh_dm;
	}

	public String getYh_mc() {
		return yh_mc;
	}

	public void setYh_mc(String yh_mc) {
		this.yh_mc = yh_mc;
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

	/* �����ڻ���ʱ��input type="date"����ʾ */
	public String getStartDateToShow() {
		return dateToShow(this.startDate);
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	/* �����ڻ���ʱ��input type="date"����ʾ */
	public String getEndDateToShow() {
		return dateToShow(this.endDate);
	}

	public float getExpectedReturnRate() {
		return expectedReturnRate;
	}

	public void setExpectedReturnRate(float expectedReturnRate) {
		this.expectedReturnRate = expectedReturnRate;
	}

	public float getNetvPurchase() {
		return netvPurchase;
	}

	public void setNetvPurchase(float netvPurchase) {
		this.netvPurchase = netvPurchase;
	}

	public float getNetvSelling() {
		return netvSelling;
	}

	public void setNetvSelling(float netvSelling) {
		this.netvSelling = netvSelling;
	}
	
	/*
	 * ���ڽ����ϣ�name=financialProductDetail.netvalused��Ԫ�صĻ���,
	 * �������1����ôcheckbox�ڻ���ʱ���Զ���ѡ
	 * */
	public int getNetvalused(){
		return this.netvPurchase > 0.f ? 1 : 0;
	}
	
	/*
	 * ��������ϣ�name=financialProductDetail.netvalused��checkboxԪ��δ��ѡ�У�
	 * ��ôused����Ϊ0�������洫��ġ����뾻ֵ���͡�������ֵ��ǿ������Ϊ0
	 * */
	public void setNetvalused(int used){
		if (used == 0){
			this.netvPurchase = 0.f;
			this.netvSelling = 0.f;
		}
	}

	public float getJe() {
		return je;
	}

	public void setJe(float je) {
		this.je = je;
	}

	public float getExpectedReturn() {
		float expectedReturn = 1.0f * dateCount / 365 * expectedReturnRate * je;
		int scale = 2;// ����λ��
		int roundingMode = 4;// ��ʾ�������룬����ѡ��������ֵ��ʽ������ȥβ���ȵ�.
		BigDecimal bd = BigDecimal.valueOf((double) expectedReturn);
		bd = bd.setScale(scale, roundingMode);
		return bd.floatValue();
	}

	public float getRealReturn() {
		return realReturn;
	}

	public void setRealReturn(float realReturn) {
		this.realReturn = realReturn;
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
		return "FinancialProductDetail [uuid=" + uuid + ", transferUuid=" + transferUuid + ", redeemUuid=" + redeemUuid
				+ ", returnUuid=" + returnUuid + ", user_id=" + user_id + ", productName=" + productName
				+ ", productType=" + productType + ", yh_dm=" + yh_dm + ", yh_mc=" + yh_mc + ", dateCount=" + dateCount
				+ ", startDate=" + startDate + ", endDate=" + endDate + ", expectedReturnRate=" + expectedReturnRate
				+ ", netvPurchase=" + netvPurchase + ", netvSelling=" + netvSelling + ", je=" + je + ", realReturn="
				+ realReturn + ", is_redeem=" + is_redeem + ", lrrq=" + lrrq + ", xgrq=" + xgrq + ", yxbz=" + yxbz
				+ "]";
	}

	private String dateToShow(Date date) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		try {
			if (date != null) {
				return sdf.format(date);
			}
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println(this.getClass() + "dateToShow error");
		}
		return null;
	}

}
