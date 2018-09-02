package cn.gxf.spring.struts.integrate.security;

import org.springframework.security.core.AuthenticationException;

public class MyUsernameNotFoundException extends AuthenticationException{

	/**
	 * 
	 */
	private static final long serialVersionUID = 2185081955865256296L;

	public MyUsernameNotFoundException(String msg) {
		super(msg);
		// TODO Auto-generated constructor stub
	}

	public MyUsernameNotFoundException(String msg, Throwable t) {
        super(msg, t);
    }
}
