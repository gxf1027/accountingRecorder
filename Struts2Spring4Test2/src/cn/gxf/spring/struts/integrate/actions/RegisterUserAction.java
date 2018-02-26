package cn.gxf.spring.struts.integrate.actions;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;
import org.springframework.beans.factory.annotation.Autowired;

import com.opensymphony.xwork2.ActionSupport;

import cn.gxf.spring.struts.integrate.security.UserLogin;
import cn.gxf.spring.struts2.integrate.service.RegisterUserService;

public class RegisterUserAction extends ActionSupport {


	private static final long serialVersionUID = 125191163443156807L;
	
	private String username;
	private String pwd;
	private String confirm_pwd;
	private String msg;
	
	@Autowired
	private RegisterUserService registerUserService;
	
	public String registerUser(){
		
		
		System.out.println(username+ " " + pwd + " "+ confirm_pwd);
		if (username == null || pwd == null || confirm_pwd == null ||
			 username.equals("") || pwd.equals("") || confirm_pwd.equals("") ||
			 !pwd.equals(confirm_pwd)){
			return "notProperyUerPwd";
		}
		
		UserLogin userLogin = new UserLogin();
		userLogin.setUsername(username);
		userLogin.setPassword(pwd);
		int rv = registerUserService.registerUser(userLogin);
		if (-1 == rv){
			System.out.println("用户名已存在");
			return "notProperyUerPwd";
		}
		
		
		System.out.println("注册成功");
		return "registerOk";
	}
	
	public String checkUserName() throws IOException{
		
		System.out.println("checkUserName...");
		
		if (username == null || username.equals("") ){
			return null;
		}
		
		
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html);charset=utf-8");
		PrintWriter write = response.getWriter();
		
		int exists = registerUserService.isUserNameExists(username);
		if (exists == 1){
			this.msg = "<font color='red'>用户名已存在</font>";
		}else{
			this.msg ="<font color='blue'>此用户名可以使用</font>";
		}
		write.print(msg);
		write.flush();
		write.close();
		return null;
	}
	
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPwd() {
		return pwd;
	}
	public void setPwd(String pwd) {
		this.pwd = pwd;
	}
	public String getConfirm_pwd() {
		return confirm_pwd;
	}
	public void setConfirm_pwd(String confirm_pwd) {
		this.confirm_pwd = confirm_pwd;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	
}
