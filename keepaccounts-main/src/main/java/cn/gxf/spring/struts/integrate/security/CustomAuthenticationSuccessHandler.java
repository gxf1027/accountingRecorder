package cn.gxf.spring.struts.integrate.security;

import java.io.IOException;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.security.web.savedrequest.DefaultSavedRequest;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.security.web.savedrequest.RequestCache;
import org.springframework.security.web.savedrequest.SavedRequest;
import org.springframework.util.StringUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.opensymphony.xwork2.ActionContext;

public class CustomAuthenticationSuccessHandler extends
				SimpleUrlAuthenticationSuccessHandler{
	
	protected final Log logger = LogFactory.getLog(this.getClass());

	public static final String MAGIC_ATTR = "MAGIC_NUM";
	public static final String MAGIC_NUM = "1027";
	
	private RequestCache requestCache = new HttpSessionRequestCache();
	private String defaultTargetUrl="/";
	
	@Override
	public void onAuthenticationSuccess(HttpServletRequest request,
			HttpServletResponse response, Authentication authentication)
			throws ServletException, IOException {
	    
		System.out.println("CustomAuthenticationSuccessHandler...");
		SavedRequest savedRequest = requestCache.getRequest(request, response);

		if (savedRequest == null) {
			
			/*使用super.ononAuthenticationSuccess无法正常工作
			 * application-security.xml: 在使用了authentication-success-handler-ref以后
			 * form-login节点的 default-target-url元素无效
			*/
			//super.onAuthenticationSuccess(request, response, authentication);
			// AbstractAuthenticationTargetUrlRequestHandler.handle
			RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();
			if (response.isCommitted()) {
				logger.debug("Response has already been committed. Unable to redirect to "
						+ defaultTargetUrl);
				return;
			}

			redirectStrategy.sendRedirect(request, response, defaultTargetUrl);

			return;
		}
		
		String targetUrlParameter = getTargetUrlParameter();
		if (isAlwaysUseDefaultTargetUrl()
				|| (targetUrlParameter != null && StringUtils.hasText(request
						.getParameter(targetUrlParameter)))) {
			requestCache.removeRequest(request, response);
			super.onAuthenticationSuccess(request, response, authentication);

			return;
		}

		clearAuthenticationAttributes(request);

		if (testSpecialConditions(savedRequest)){
			System.out.println("add magic_num...");
			HttpSession session = request.getSession();
			session.setAttribute(MAGIC_ATTR, MAGIC_NUM);
		}
		
		// Use the DefaultSavedRequest URL
		String targetUrl = savedRequest.getRedirectUrl();
		logger.debug("Redirecting to DefaultSavedRequest Url: " + targetUrl);
		getRedirectStrategy().sendRedirect(request, response, targetUrl);
	}
	
	public void setRequestCache(RequestCache requestCache) {
		this.requestCache = requestCache;
	}
	
	public void setDefaultTargetUrl(String defaultTargetUrl) {
		this.defaultTargetUrl = defaultTargetUrl;
	}
	
	
	private boolean testSpecialConditions(SavedRequest savedRequest){
		DefaultSavedRequest defaultSavedRequest = (DefaultSavedRequest)savedRequest;
		String servletPath = defaultSavedRequest.getServletPath();
		return !(servletPath.indexOf("incomeDetail")<0 && servletPath.indexOf("paymentDetail")<0 && servletPath.indexOf("transferDetail")<0);
	}
}
