package cn.gxf.spring.struts2.integrate.model;

import java.io.Serializable;

public class ExpInfo implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -4791734126605922010L;
	private String expuuid;
	private String accuuid;
	private float expenditure;
	private float shopping;
	private float tax;
	private float loan;
	private float others;

	public float getExpenditure() {
		return expenditure;
	}

	public void setExpenditure(float expenditure) {
		this.expenditure = expenditure;
	}

	public float getShopping() {
		return shopping;
	}

	public void setShopping(float shopping) {
		this.shopping = shopping;
	}

	public float getTax() {
		return tax;
	}

	public void setTax(float tax) {
		this.tax = tax;
	}

	public float getOthers() {
		return others;
	}

	public void setOthers(float others) {
		this.others = others;
	}

	public String getExpuuid() {
		return expuuid;
	}

	public void setExpuuid(String expuuid) {
		this.expuuid = expuuid;
	}

	public String getAccuuid() {
		return accuuid;
	}

	public void setAccuuid(String accuuid) {
		this.accuuid = accuuid;
	}

	public float getLoan() {
		return loan;
	}

	public void setLoan(float loan) {
		this.loan = loan;
	}

	public float updateExpenditure(){
		expenditure = shopping + tax + loan + others;
		return expenditure;
	}
	
	@Override
	public String toString() {
		return "ExpInfo [expuuid=" + expuuid + ", accuuid=" + accuuid + ", expenditure=" + expenditure + ", shopping="
				+ shopping + ", tax=" + tax + ", loan=" + loan + ", others=" + others + "]";
	}

}
