package cn.gxf.spring.struts.integrate.security;

import org.springframework.security.core.AuthenticationException;

public class MyRoleNotFound extends AuthenticationException{

	/**
	 * 
	 */
	private static final long serialVersionUID = -2396788335121202040L;

	public MyRoleNotFound(String msg) {
		super(msg);
		// TODO Auto-generated constructor stub
	}
	
	public MyRoleNotFound(String msg, Throwable t) {
        super(msg, t);
    }
}
