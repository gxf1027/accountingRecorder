package cn.gxf.spring.struts2.integrate.model;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/*
 *  用于统计按月的支出或者收入的数据，接收从数据库返回的结果
 * 
 * 
 * 
 * */

public class StatByMonth implements Serializable {
	
	private static final long serialVersionUID = 1813349739089842816L;
	
	//private String ndyf; // 2017-10
	//private float jesum;
	// 2018-01-19
	private String nd;
	private String yf;
	private float paysum;
	private float incomesum;
	private float incomesalary;
	private float incomefinproduct;
	
	
	public String getNdyf() {
		//return ndyf;
		return this.nd + "-" + this.yf;
	}
	public void setNdyf(String ndyf) {
		//this.ndyf = ndyf;
		System.out.println("param: " + ndyf);
		this.nd = ndyf.substring(0, 4);
		this.yf = ndyf.substring(5,6);
		System.out.println("nd: "+ this.nd + " yf: "+this.yf);
	}
	public float getJeNetSum(){
		return this.incomesum - this.paysum;
	}
	public String getJeNetSumStr2(){
		return String.format("%.2f", this.incomesum - this.paysum);
	}
	public float getJesum() {
		return 0;//jesum;
	}
	public void setJesum(float jesum) {
		//this.jesum = jesum;
	}
	public float getPaysum() {
		return paysum;
	}
	public String getPaysumStr2(){
		return String.format("%.2f", paysum);
	}
	public void setPaysum(float paysum) {
		this.paysum = paysum;
	}
	public float getIncomesum() {
		return incomesum;
	}
	public String getIncomesumStr2(){
		return String.format("%.2f", incomesum);
	}
	public void setIncomesum(float incomesum) {
		this.incomesum = incomesum;
	}
	public float getIncomesalary() {
		return incomesalary;
	}
	public String getIncomesalary2() {
		return String.format("%.2f", incomesalary);
	}
	public void setIncomesalary(float incomesalary) {
		this.incomesalary = incomesalary;
	}
	public float getIncomefinproduct() {
		return incomefinproduct;
	}
	public String getIncomefinproduct2() {
		return String.format("%.2f", incomefinproduct);
	}
	public void setIncomefinproduct(float incomefinproduct) {
		this.incomefinproduct = incomefinproduct;
	}
	
	public String getNd(){
		return this.nd;
	}
	
	public String getYf(){
		return this.yf;
	}
	
	// 获取属性中“年度月份”所在的月份第一天
	public String getYfFirstDate(){
		return this.getNdyf()+"-01";
	}
	
	// 获取属性中“年度月份”所在月份的最后一天
	public String getYfLastDate(){
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date date = null;
		try {
			date = dateFormat.parse(this.getYfFirstDate());
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}  
		Calendar calendar = Calendar.getInstance(); 
		calendar.setTime(date);
		calendar.add(Calendar.MONTH, 1);
		calendar.set(Calendar.DAY_OF_MONTH, 0);
		return dateFormat.format(calendar.getTime());
	}
	@Override
	public String toString() {
		return "StatByMonth [nd=" + nd + ", yf=" + yf + ", paysum=" + paysum + ", incomesum=" + incomesum
				+ ", incomesalary=" + incomesalary + ", incomefinproduct=" + incomefinproduct + "]";
	}
	
}
