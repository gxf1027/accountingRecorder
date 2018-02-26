package cn.gxf.spring.struts.integrate.actions;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.struts2.interceptor.RequestAware;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;

import com.opensymphony.xwork2.ActionSupport;

import cn.gxf.spring.struts.integrate.security.UserLogin;
import cn.gxf.spring.struts2.integrate.model.StatByCategory;
import cn.gxf.spring.struts2.integrate.model.StatByMonth;
import cn.gxf.spring.struts2.integrate.service.FrontStatisticsService;

public class FrontStatisticsAction extends ActionSupport implements RequestAware{

	private static final long serialVersionUID = 7002679539389318440L;
	
	private String nd;
	private Map<String, Object> myrequest;
	
	@Autowired
	private FrontStatisticsService frontStatService;
	
	private StatByMonth calcAggregationProp(List<StatByMonth> list){
		// 合计数
		StatByMonth statSum = new StatByMonth();
		float incomesum = 0.f;
		float paysum = 0.f;
		for (StatByMonth stat : list){
			incomesum += stat.getIncomesum();
			paysum += stat.getPaysum();
		}
		
		statSum.setIncomesum(incomesum);
		statSum.setPaysum(paysum);
		return statSum;
	}
	
	public String inputFront(){
		// 获得当前年度
		if (this.nd == null || this.nd.equals("")){
			Calendar date =  Calendar.getInstance();
			this.nd = String.valueOf(date.get(Calendar.YEAR));
		}
		UserLogin user = (UserLogin) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		List<StatByMonth> list = frontStatService.getStatByYear(nd, user.getId());
		myrequest.put("statistics", list);
		
		StatByMonth statSum = calcAggregationProp(list);
		myrequest.put("statsum", statSum);
		
		List<StatByCategory> incomeStatLb = frontStatService.getIncomeStatByLb(nd, user.getId());
		List<StatByCategory> paymentStatDl = frontStatService.getPaymentStatByDl(nd, user.getId());
		
		myrequest.put("incomeStatLb", incomeStatLb);
		myrequest.put("paymentStatDl", paymentStatDl);
		
		return "inputOk";
	}
		
	public String reProcStat(){
		UserLogin user = (UserLogin) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		
		// 调用存储过程，中心生产数据
		frontStatService.reProcStat(this.nd, user.getId());
		
		List<StatByMonth> list = frontStatService.getStatByYear(this.nd, user.getId());
		myrequest.put("statistics", list);
		
		StatByMonth statSum = calcAggregationProp(list);
		myrequest.put("statsum", statSum);
		
		List<StatByCategory> incomeStatLb = frontStatService.getIncomeStatByLb(nd, user.getId());
		List<StatByCategory> paymentStatDl = frontStatService.getPaymentStatByDl(nd, user.getId());
		
		myrequest.put("incomeStatLb", incomeStatLb);
		myrequest.put("paymentStatDl", paymentStatDl);
		
		return "inputOk";
	}

	@Override
	public void setRequest(Map<String, Object> request) {
		
		this.myrequest = request;
	}
	public Map<String, Object> getMyrequest() {
		return myrequest;
	}
	
	public void setNd(String nd) {
		this.nd = nd;
	}
	public String getNd() {
		return nd;
	}

}
