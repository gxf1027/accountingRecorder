package cn.gxf.spring.struts2.integrate.service;

public interface SimpleRateLimitService {
	public boolean isActionAllowed(String userId, String actionKey, int period, int maxCount);
}
