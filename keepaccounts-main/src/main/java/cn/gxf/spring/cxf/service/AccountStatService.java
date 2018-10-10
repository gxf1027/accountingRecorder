package cn.gxf.spring.cxf.service;

import java.util.Date;
import java.util.List;

import javax.jws.WebMethod;
import javax.jws.WebService;

import cn.gxf.spring.struts2.integrate.model.AccDateStat;

@WebService
public interface AccountStatService {
	@WebMethod
	public List<AccDateStat> getDateStat(int user_id, Date date_from , Date date_to);
	
	@WebMethod
	public List<AccDateStat> getDateStatIncome(int user_id, Date date_from, Date date_to);
	
	@WebMethod
	public List<AccDateStat> getDateStatPayment(int user_id, Date date_from, Date date_to);
	
	@WebMethod
	public List<AccDateStat> getDateStatTransfer(int user_id, Date date_from, Date date_to);
}
