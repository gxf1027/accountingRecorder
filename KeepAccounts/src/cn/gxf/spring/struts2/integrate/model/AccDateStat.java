package cn.gxf.spring.struts2.integrate.model;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class AccDateStat implements Serializable, Comparable<AccDateStat>{
	private Date date;
	float incomesum;
	float paymentsum;
	float transfersum;
	
	/*private List<AccDetailVO> detail_list;*/
	private List<AccountingDetailVO> detail_list;
	
	public AccDateStat(){
		date = null;
		incomesum = 0.f;
		paymentsum = 0.f;
		detail_list = new ArrayList<>();
	}

	public String getDateToShow(){
		
		SimpleDateFormat sdf = new SimpleDateFormat("MM-dd EEEE");
		
		return sdf.format(date);
	}
	
	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public float getIncomesum() {
		return incomesum;
	}

	public void setIncomesum(float incomesum) {
		this.incomesum = incomesum;
	}

	public float getPaymentsum() {
		return paymentsum;
	}

	public void setPaymentsum(float paymentsum) {
		this.paymentsum = paymentsum;
	}

	public List<AccountingDetailVO> getDetail_list() {
		return detail_list;
	}

	public void setDetail_list(List<AccountingDetailVO> detail_list) {
		this.detail_list = detail_list;
	}
	
	public float getTransfersum() {
		return transfersum;
	}
	
	public void setTransfersum(float transfersum) {
		this.transfersum = transfersum;
	}

	@Override
	public String toString() {
		return "AccDateStat [date=" + date + ", incomesum=" + incomesum + ", paymentsum=" + paymentsum
				+ ", detail_list=" + detail_list + "]";
	}

	@Override
	public int compareTo(AccDateStat o) {
		return this.date.compareTo(o.date);
	}
}	
