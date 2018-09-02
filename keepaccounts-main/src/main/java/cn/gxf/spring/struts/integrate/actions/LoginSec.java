package cn.gxf.spring.struts.integrate.actions;

import com.opensymphony.xwork2.ActionSupport;

public class LoginSec extends ActionSupport{

	/**
	 * 
	 */
	private static final long serialVersionUID = -5737111091178760418L;

	
	public String loginOut(){
		return "Out_OK";
	}
}
