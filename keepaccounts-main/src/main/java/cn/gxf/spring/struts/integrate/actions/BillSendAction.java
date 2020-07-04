package cn.gxf.spring.struts.integrate.actions;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.struts2.ServletActionContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;

import com.opensymphony.xwork2.ActionSupport;

import cn.gxf.spring.quartz.job.CreditCardsBillProcessor;
import cn.gxf.spring.struts.integrate.security.UserLogin;

public class BillSendAction extends ActionSupport {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -4298087426612179674L;
	@Autowired
	private CreditCardsBillProcessor billProcessor;
	
	private String msg;
	
	private Logger logger = LogManager.getLogger();
	
	public String execute(){
		
		UserLogin user = (UserLogin) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		billProcessor.processCreditCardBillManually(user.getId());
		
		return SUCCESS;
	}
	
	public String ajaxrequest() throws IOException{
		
		UserLogin user = (UserLogin) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html);charset=utf-8");
		PrintWriter write = response.getWriter();
		
		try {
			billProcessor.processCreditCardBillManually(user.getId());
		} catch (Exception e) {
			logger.warn("ajaxrequest processing has exceptions with user :[{}], eception:[{}]", user.getId(), e.getMessage());

			this.msg = e.getMessage();
			write.print(msg);
			write.flush();
			write.close();
			return null;
		}
	
		
		this.msg = "success";
		write.print(msg);
		write.flush();
		write.close();
		
		return null;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}
	
	
}
