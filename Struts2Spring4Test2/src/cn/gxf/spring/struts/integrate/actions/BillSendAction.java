package cn.gxf.spring.struts.integrate.actions;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;

import com.opensymphony.xwork2.ActionSupport;

import cn.gxf.spring.quartz.job.BillProcessor;
import cn.gxf.spring.struts.integrate.security.UserLogin;

public class BillSendAction extends ActionSupport {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -4298087426612179674L;
	@Autowired
	private BillProcessor billProcessor;
	
	private String msg;
	
	public String execute(){
		
		UserLogin user = (UserLogin) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		billProcessor.processCreditCardBillManually(user.getId());
		
		return SUCCESS;
	}
	
	public String ajaxrequest() throws IOException{
		System.out.println("ajaxrequest...");
		UserLogin user = (UserLogin) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		System.out.println("ajaxrequest..."+user);
		// 在security中排除了这个url，所以没有security信息，TODO！！
		billProcessor.processCreditCardBillManually(user.getId());
		
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html);charset=utf-8");
		PrintWriter write = response.getWriter();
		
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
