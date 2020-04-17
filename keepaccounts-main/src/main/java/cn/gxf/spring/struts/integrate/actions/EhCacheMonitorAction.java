package cn.gxf.spring.struts.integrate.actions;

import java.util.List;
import java.util.Map;

import org.apache.struts2.interceptor.RequestAware;
import org.springframework.beans.factory.annotation.Autowired;

import com.opensymphony.xwork2.ActionSupport;

import cn.gxf.spring.struts.integrate.cache.EhCacheInfo;
import cn.gxf.spring.struts.integrate.cache.EhCacheUtils;

public class EhCacheMonitorAction extends ActionSupport implements RequestAware{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1321531537427788104L;
	
	private String cacheName = "dmCache";
	
	private Map<String, Object> myrequest = null;
	
	@Autowired
	private EhCacheUtils ehCacheUtils;

	
	public String monitor() {
		
		ehCacheUtils.printCacheInfo(this.cacheName);
		
		List<EhCacheInfo> list = ehCacheUtils.getCacheInfo(this.cacheName);
		myrequest.put("EhCacheInfo", list);
		
		return "MONITOR_OK";
	}
	
	@Override
	public void setRequest(Map<String, Object> request) {
		// TODO Auto-generated method stub
		myrequest = request;
	}
	
	public String getCacheName() {
		return cacheName;
	}
	
	public void setCacheName(String cacheName) {
		this.cacheName = cacheName;
	}
	
}
