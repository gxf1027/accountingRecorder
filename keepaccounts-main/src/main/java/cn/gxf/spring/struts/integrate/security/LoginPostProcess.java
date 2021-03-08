package cn.gxf.spring.struts.integrate.security;

import java.util.Date;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import cn.gxf.spring.struts.integrate.sysctr.audit.AuditInfo;
import cn.gxf.spring.struts.integrate.sysctr.audit.AuditMsgSerivce;
import cn.gxf.spring.struts2.integrate.dao.UserDao;

public class LoginPostProcess implements Runnable{
	
    private Logger logger = LogManager.getLogger();

	private UserDao userDao;
	private AuditMsgSerivce auditService;
	private UserLogin user;
	private String ip;
	
	public LoginPostProcess(UserDao userDao, AuditMsgSerivce auditService, UserLogin user, String ip) {
		super();
		this.userDao = userDao;
		this.auditService = auditService;
		this.user = user;
		this.ip = ip;
	}

	@Override
	public void run() {
		try {
			Date proc_time = new Date();
			userDao.resetUserAttemptLimit(user.getUsername());
	    	userDao.recordUserLoginInfo(user.getId(), proc_time, ip);
	    	if (null != auditService){
	    		System.out.println("login auditmsg...");
	    		auditService.sendAuditMsg(AuditInfo.LOGIN_SUCCESS, "µÇÂ¼³É¹¦,("+user.getId()+", "+user.getUsername()+")", user.getId(), proc_time);
	    	}
		} catch (Exception e) {
			logger.warn("recording login processing has exceptions with user :[{}], ip [{}], eception:[{}]", user.getId(), ip, e.getMessage());
		}
		
	}
	
	
}
