package cn.gxf.spring.struts.integrate.test;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.interceptor.TokenInterceptor;
import org.apache.struts2.util.TokenHelper;

import com.opensymphony.xwork2.ActionInvocation;

import cn.gxf.spring.struts.integrate.security.CustomAuthenticationSuccessHandler;
import cn.gxf.spring.struts.integrate.sysctr.SpringSessionConfiguration;

public class MyTokenInterceptor extends TokenInterceptor{

	/**
	 * 
	 */
	private static final long serialVersionUID = 6415507340808607748L;

	
	@Override
    protected String doIntercept(ActionInvocation invocation) throws Exception {
        if (log.isDebugEnabled()) {
            log.debug("MyTokenInterceptor: Intercepting invocation to check for valid transaction token.");
        }
        
        HttpServletRequest request = ServletActionContext.getRequest();
        
        if (magicNumExists(request)){
        	System.out.println("MyTokenInterceptor: trriger magics.");
        	// to delete magic number(use only once)
        	HttpSession session = request.getSession();
        	session.removeAttribute(CustomAuthenticationSuccessHandler.MAGIC_ATTR);
        	return handleValidToken(invocation);
        }
        
        return handleToken(invocation);
    }
	
	private boolean magicNumExists(HttpServletRequest request){
		//System.out.println("testing magic");
		HttpSession session = request.getSession();
		if (session == null){
			return false;
		}
		
		String magic_num = (String) session.getAttribute(CustomAuthenticationSuccessHandler.MAGIC_ATTR);
		return magic_num !=null && magic_num.equals(CustomAuthenticationSuccessHandler.MAGIC_NUM);
	}
}
