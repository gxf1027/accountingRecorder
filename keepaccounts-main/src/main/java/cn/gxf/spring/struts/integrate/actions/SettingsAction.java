package cn.gxf.spring.struts.integrate.actions;

import java.util.Map;

import org.apache.struts2.interceptor.RequestAware;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;

import com.opensymphony.xwork2.ActionSupport;

import cn.gxf.spring.struts.integrate.security.UserLogin;
import cn.gxf.spring.struts2.integrate.service.DmService;

public class SettingsAction extends ActionSupport implements RequestAware{

	/**
	 * 
	 */
	private static final long serialVersionUID = -75837660321748829L;
	
	private Map<String, Object> myrequest = null;
	
	@Autowired
	private DmService dmService;
	
	public String showSettings(){
		
		return "SETTINGS_PAGE";
	}
	
	
	public String showAccountBookSettings(){
		
		UserLogin user = (UserLogin) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		this.myrequest.put("ZH_INFO", dmService.getZhInfoVO(user.getId()));
		//this.myrequest.put("ZH_INFO_MAP", dmService.getZhInfoMap(user.getId()));
		
		return "SETTINGS_ACCOUNT_BOOK";
	}


	@Override
	public void setRequest(Map<String, Object> request) {
		myrequest = request;
	}
}
