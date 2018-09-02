package cn.gxf.spring.struts.integrate.test;

import java.util.Map;

import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;

public class SessionInterceptor extends AbstractInterceptor {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5748564540880890793L;

	@Override
	public String intercept(ActionInvocation arg0) throws Exception {
		// TODO Auto-generated method stub
		System.out.println("SessionInterceptor::intercept 1111");
		
		Map<String, Object> ss = ActionContext.getContext().getSession();
		System.out.println("SessionInterceptor::intercept:" + ss );
		
		
		// ��֤Session�Ƿ����
		if (!ServletActionContext.getRequest().isRequestedSessionIdValid()) {
			// session����,��ת����ʾҳ��
			System.out.println("session ���ڣ�");
			
			return "session_invalid";
		}
		
		System.out.println("SessionInterceptor::intercept 2222");
		
		return arg0.invoke();
	}

}
