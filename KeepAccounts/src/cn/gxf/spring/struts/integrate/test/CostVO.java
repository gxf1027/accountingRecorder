package cn.gxf.spring.struts.integrate.test;

import java.io.Serializable;

import javax.servlet.http.HttpSessionActivationListener;
import javax.servlet.http.HttpSessionEvent;

import com.opensymphony.xwork2.validator.annotations.DoubleRangeFieldValidator;
import com.opensymphony.xwork2.validator.annotations.RequiredFieldValidator;
import com.opensymphony.xwork2.validator.annotations.Validations;
import com.opensymphony.xwork2.validator.annotations.ValidatorType;


public class CostVO implements Serializable, HttpSessionActivationListener {
	/**
	 * 
	 */
	private static final long serialVersionUID = -6607144233115209057L;
	private float expenditure;
	private float shopping;
	private float tax;

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

	@Override
	public String toString() {
		return "CostVO [expenditure=" + expenditure + ", shopping=" + shopping + ", tax=" + tax + "]";
	}

	@Override
	public void sessionDidActivate(HttpSessionEvent arg0) {
		// TODO Auto-generated method stub
		System.out.println("CostVO::sessionDidActivate..." + arg0);
	}

	@Override
	public void sessionWillPassivate(HttpSessionEvent arg0) {
		// TODO Auto-generated method stub
		System.out.println("CostVO::sessionWillPassivate..." + arg0);
	}

}
