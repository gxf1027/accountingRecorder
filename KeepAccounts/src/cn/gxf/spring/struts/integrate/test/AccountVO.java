package cn.gxf.spring.struts.integrate.test;

import java.io.Serializable;

public class AccountVO implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 7266008056640090719L;
	private float income;
	private float salary;
	private float bonus;
	private float expenditure;
	private float netincome;

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

	@Override
	public String toString() {
		return "AccountVO [income=" + income + ", salary=" + salary + ", bonus=" + bonus + ", expenditure="
				+ expenditure + ", netincome=" + netincome + "]";
	}
	
	
}
