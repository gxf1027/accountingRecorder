package cn.gxf.spring.struts.integrate.actions;

import java.util.Calendar;
import java.util.List;
import java.util.Map;

import org.apache.struts2.interceptor.RequestAware;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;

import com.opensymphony.xwork2.ActionSupport;

import cn.gxf.spring.struts.integrate.security.UserLogin;
import cn.gxf.spring.struts.integrate.util.AuxiliaryTools;
import cn.gxf.spring.struts2.integrate.model.StatByCategory;
import cn.gxf.spring.struts2.integrate.model.StatByMonth;
import cn.gxf.spring.struts2.integrate.service.FrontStatisticsService;
import cn.gxf.spring.struts2.integrate.service.KeepAccountingDatesService;

public class FrontStatisticsAction extends ActionSupport implements RequestAware{

	private static final long serialVersionUID = 7002679539389318440L;
	
	private String nd;
	private Map<String, Object> myrequest;
	
	@Autowired
	private FrontStatisticsService frontStatService;
	
	@Autowired
	private KeepAccountingDatesService keepAccountingDatesService;
	
//	@Autowired
//	private AccTools accTools; 
	
	private StatByMonth calcAggregationProp(List<StatByMonth> list){
		// 合计数
		StatByMonth statSum = new StatByMonth();
		float incomesum = 0.f;
		float salarysum = 0.f;
		float finproductsum = 0.f;
		float paysum = 0.f;
		for (StatByMonth stat : list){
			incomesum += stat.getIncomesum();
			salarysum += stat.getIncomesalary();
			finproductsum += stat.getIncomefinproduct();
			paysum += stat.getPaysum();
		}
		
		statSum.setIncomesum(incomesum);
		statSum.setIncomesalary(salarysum);
		statSum.setIncomefinproduct(finproductsum);
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
		
		int keepdates = keepAccountingDatesService.getKeepAccountingDates(user.getId());
		myrequest.put("keepdates", keepdates);
		System.out.println("keepdates: " + keepdates);
		
		return "inputOk";
	}
		
	public String reProcStat(){
		UserLogin user = (UserLogin) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		
		// 调用存储过程，生产数据（根据年度和根据类别的统计）
		frontStatService.reProcStat(this.nd, user.getId());
		
		List<StatByMonth> list = frontStatService.getStatByYear(this.nd, user.getId());
		myrequest.put("statistics", list);
		
		StatByMonth statSum = calcAggregationProp(list);
		myrequest.put("statsum", statSum);
		
		// 获得生成的根据类别的统计
		List<StatByCategory> incomeStatLb = frontStatService.getIncomeStatByLb(nd, user.getId());
		List<StatByCategory> paymentStatDl = frontStatService.getPaymentStatByDl(nd, user.getId());
		
		myrequest.put("incomeStatLb", incomeStatLb);
		myrequest.put("paymentStatDl", paymentStatDl);
		
		AuxiliaryTools.delay(AuxiliaryTools.millisec_wait_mysql_sync*10);
		
		return "inputOk";
	}
	
	public String reProcStatThisMonth(){
		
		// 获得当前年度
		Calendar date =  Calendar.getInstance();
		String thisyear = String.valueOf(date.get(Calendar.YEAR));
		
		UserLogin user = (UserLogin) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		
		// 调用存储过程，中心生产数据
		frontStatService.reProcStatThisMonth(user.getId());
		
		List<StatByMonth> list = frontStatService.getStatByYear(thisyear, user.getId());
		myrequest.put("statistics", list);
		
		StatByMonth statSum = calcAggregationProp(list);
		myrequest.put("statsum", statSum);
		
		List<StatByCategory> incomeStatLb = frontStatService.getIncomeStatByLb(thisyear, user.getId());
		List<StatByCategory> paymentStatDl = frontStatService.getPaymentStatByDl(thisyear, user.getId());
		
		myrequest.put("incomeStatLb", incomeStatLb);
		myrequest.put("paymentStatDl", paymentStatDl);
		
		AuxiliaryTools.delay(AuxiliaryTools.millisec_wait_mysql_sync*10);
		
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
