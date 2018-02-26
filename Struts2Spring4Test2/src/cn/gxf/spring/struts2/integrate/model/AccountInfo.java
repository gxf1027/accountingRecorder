package cn.gxf.spring.struts2.integrate.model;

import java.io.Serializable;
import java.util.List;


public class AccountInfo implements Serializable, Comparable<AccountInfo> {
	/**
	 * 
	 */
	private static final long serialVersionUID = -1507294034015675100L;
	private String accuuid;
	private String ssny;
	private float income;
	private float salary;
	private float bonus;
	private float expenditure;
	private ExpInfo expInfo;
	private float netincome;
	
	public static AccountInfo aggregate(List<AccountInfo> acc){
		AccountInfo accountInfo = new AccountInfo();
		accountInfo.setZeros();
		for(AccountInfo a : acc){
			accountInfo.setSalary(accountInfo.getSalary()+a.getSalary());
			accountInfo.setBonus(accountInfo.getBonus()+a.getBonus());
			accountInfo.setExpenditure(accountInfo.getExpenditure()+a.getExpenditure());
			accountInfo.setIncome(accountInfo.getIncome() + a.getIncome());
			accountInfo.setNetincome(accountInfo.getNetincome() + a.getNetincome());
			
			if( accountInfo.getExpInfo() != null && a.getExpInfo() != null){
				accountInfo.getExpInfo().setShopping(accountInfo.getExpInfo().getShopping() + a.getExpInfo().getShopping());
				accountInfo.getExpInfo().setTax(accountInfo.getExpInfo().getTax() + a.getExpInfo().getTax());
				accountInfo.getExpInfo().setLoan(accountInfo.getExpInfo().getLoan() + a.getExpInfo().getLoan());
				accountInfo.getExpInfo().setOthers(accountInfo.getExpInfo().getOthers() + a.getExpInfo().getOthers());
				accountInfo.getExpInfo().setExpenditure(accountInfo.getExpInfo().getExpenditure() + a.getExpInfo().getExpenditure());
			}
		}
		return accountInfo;
	}
	
	public AccountInfo() {
		this.expInfo = new ExpInfo();
	}
	
	private void setZeros(){
		this.income = 0;
		this.salary = 0;
		this.bonus = 0;
		this.expenditure = 0;
		this.netincome = 0;
		this.ssny="000000";
		if (this.expInfo != null){
			this.expInfo.setExpenditure(0);
			this.expInfo.setShopping(0);
			this.expInfo.setTax(0);
			this.expInfo.setLoan(0);
			this.expInfo.setOthers(0);
		}
	}

	public String getSsny() {
		return ssny;
	}

	public void setSsny(String ssny) {
		this.ssny = ssny;
	}

	public float getIncome() {
		return income;
	}

	public void setIncome(float income) {
		this.income = income;
	}

	public float getSalary() {
		return salary;
	}

	public void setSalary(float salary) {
		this.salary = salary;
	}

	public float getBonus() {
		return bonus;
	}

	public void setBonus(float bonus) {
		this.bonus = bonus;
	}

	public float getExpenditure() {
		return expenditure;
	}

	public void setExpenditure(float expenditure) {
		this.expenditure = expenditure;
	}

	public float getNetincome() {
		return netincome;
	}

	public void setNetincome(float netincome) {
		this.netincome = netincome;
	}

	public ExpInfo getExpInfo() {
		return expInfo;
	}

	public void setExpInfo(ExpInfo expInfo) {
		this.expInfo = expInfo;
	}

	public String getAccuuid() {
		return accuuid;
	}

	public void setAccuuid(String accuuid) {
		this.accuuid = accuuid;
	}
	
	public String getSsnd(){
		return ssny == null ? null : ssny.substring(0, 4);
	}
	
	public String getSsyf(){
		return ssny == null ? null : ssny.substring(4, 6);
	}

	@Override
	public String toString() {
		return "AccountInfo [accuuid=" + accuuid + ", ssny=" + ssny + ", income=" + income + ", salary=" + salary
				+ ", bonus=" + bonus + ", expenditure=" + expenditure + ", expInfo=" + expInfo + ", netincome="
				+ netincome + "]";
	}

	@Override
	public int compareTo(AccountInfo o) {
		
		if (this.ssny == null){
			return -1;
		}
		
		return this.getSsny().compareTo(o.getSsny()==null ? "200101" : o.getSsny());
	}

}
