package cn.gxf.spring.struts.integrate.actions;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.interceptor.RequestAware;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;

import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.Preparable;

import cn.gxf.spring.struts.integrate.security.UserLogin;
import cn.gxf.spring.struts.mybatis.dao.TransferDetailMBDao;
import cn.gxf.spring.struts2.integrate.dao.DmUtilDaoImplJdbc;
import cn.gxf.spring.struts2.integrate.model.AccDateStat;
import cn.gxf.spring.struts2.integrate.model.AccountingDetailVO;
import cn.gxf.spring.struts2.integrate.model.TransferDetail;
import cn.gxf.spring.struts2.integrate.service.AccountStatService;
import cn.gxf.spring.struts2.integrate.service.DetailAccountService;

public class ListDetailAction extends ActionSupport implements RequestAware, Preparable{

	private static final long serialVersionUID = -5051655297302818200L;
	private Map<String, Object> myrequest = null;
	private Date date_from;
	private Date date_to;
	
	@Autowired
	private AccountStatService accountStatService;
	
	@Autowired
	private DmUtilDaoImplJdbc daoImplJdbc;
	
	private Logger logger = LogManager.getLogger();
	
	@Override
	public void prepare() throws Exception {
		// TODO Auto-generated method stub
		System.out.println("AccountStatService::prepare..." + ServletActionContext.getActionMapping());
		
		// 如果是从listDetail!listPaymentByMonth访问，而不是从查询按钮提交,
		// date_from , date_to都为null, 默认需要显示本月数据
		if(this.date_from == null || this.date_to == null){	
			Date now = new Date();
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
			String firstday, lastday;
			Calendar cale = null;
			// 获取前月的第一天
	        cale = Calendar.getInstance();
	        cale.add(Calendar.MONTH, 0);
	        cale.set(Calendar.DAY_OF_MONTH, 1);
	        firstday = format.format(cale.getTime());
	        
	        // 获取前月的最后一天
	        cale = Calendar.getInstance();
	        cale.add(Calendar.MONTH, 1);
	        cale.set(Calendar.DAY_OF_MONTH, 0);
	        lastday = format.format(cale.getTime());
	        
	        this.date_from = format.parse(firstday);
	        this.date_to = format.parse(lastday);
	        
	        System.out.println("本月第一天和最后一天分别是 ： " + this.date_from + " and " + this.date_to);
		}
	}
	
	
	public String listByMonth(){
		
		UserLogin user = (UserLogin) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		//this.myrequest.put("ZH_INFO", daoImplJdbc.getZhInfo(user.getId()));
		
		//List<AccDateStat> list = detailAccountService.getDateStat(5, "2017", "10");
		//List<AccDateStat> list = accountStatService.getDateStatMB(5, "2017", "10");
		List<AccDateStat> list = accountStatService.getDateStatMB(user.getId(), this.date_from, this.date_to);
		System.out.println("listByMonth: " + list.hashCode());
		this.myrequest.put("detailList", list);
		System.out.println(list.size());
		//System.out.println(list);
		
		// 用于时间回显
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		this.myrequest.put("date_from", sdf.format(this.date_from));
		this.myrequest.put("date_to", sdf.format(this.date_to));
		this.myrequest.put("listType", "listAll");
		
		logger.debug("listByMonth username:" + user.getUsername() + " " + this.date_from + " " + this.date_to);
		
		return "listOk";
	}
	
	
	public String listIncomeByMonth(){
		
		UserLogin user = (UserLogin) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		//List<AccDateStat> list = accountStatService.getDateStatIncome(5, "2017", "10");
		List<AccDateStat> list = accountStatService.getDateStatIncome(user.getId(), this.date_from, this.date_to);
		System.out.println("listIncomeByMonth: " + list.hashCode());
		this.myrequest.put("detailList", list);
		
		// 用于时间回显
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		this.myrequest.put("date_from", sdf.format(this.date_from));
		this.myrequest.put("date_to", sdf.format(this.date_to));
		this.myrequest.put("listType", "listIncome");
		
		logger.debug("listIncomeByMonth username:" + user.getUsername() + " " + this.date_from + " " + this.date_to);
		
		return "listOk";
	}
	
	public String listPaymentByMonth(){
		
		UserLogin user = (UserLogin) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		//List<AccDateStat> list = accountStatService.getDateStatPayment(5, "2017", "10");
		List<AccDateStat> list = accountStatService.getDateStatPayment(user.getId(), this.date_from, this.date_to);
		System.out.println("listPaymentByMonth: " + list.hashCode());
		this.myrequest.put("detailList", list);
		
		
		// 用于时间回显
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		this.myrequest.put("date_from", sdf.format(this.date_from));
		this.myrequest.put("date_to", sdf.format(this.date_to));
		this.myrequest.put("listType", "listPayment");
		
		logger.debug("listPaymentByMonth username:" + user.getUsername() + " " + this.date_from + " " + this.date_to);
		return "listOk";
	}
	
	
	public String listTransferByMonth(){
		
		UserLogin user = (UserLogin) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		//List<AccDateStat> list = accountStatService.getDateStatTransfer(5, "2017", "10");
		List<AccDateStat> list = accountStatService.getDateStatTransfer(user.getId(), this.date_from, this.date_to);
		System.out.println("listTransferByMonth: " + list.hashCode());
		this.myrequest.put("detailList", list);
		
		// 用于时间回显
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		this.myrequest.put("date_from", sdf.format(this.date_from));
		this.myrequest.put("date_to", sdf.format(this.date_to));
		this.myrequest.put("listType", "listTransfer");
		
		logger.debug("listTransferByMonth username:" + user.getUsername() + " " + this.date_from + " " + this.date_to);
		return "listTransferOk";
	}
	
	@Override
	public void setRequest(Map<String, Object> request) {
		// TODO Auto-generated method stub
		this.myrequest = request;
	}

	public Map<String, Object> getMyrequest() {
		return myrequest;
	}

	public void setMyrequest(Map<String, Object> myrequest) {
		this.myrequest = myrequest;
	}

	public Date getDate_from() {
		return date_from;
	}

	public void setDate_from(Date date_from) {
		this.date_from = date_from;
	}

	public Date getDate_to() {
		return date_to;
	}

	public void setDate_to(Date date_to) {
		this.date_to = date_to;
	}
}
