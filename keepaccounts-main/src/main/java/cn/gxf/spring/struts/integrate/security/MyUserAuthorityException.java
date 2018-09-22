package cn.gxf.spring.struts.integrate.security;

import org.springframework.security.core.AuthenticationException;

public class MyUserAuthorityException extends AuthenticationException{


	private static final long serialVersionUID = 20042034258425400L;

	public MyUserAuthorityException(String msg) {
		super(msg);
		// TODO Auto-generated constructor stub
	}

	public MyUserAuthorityException(String msg, Throwable t) {
        super(msg, t);
    }
}
