package cn.gxf.spring.struts2.integrate.service;

import cn.gxf.spring.struts2.integrate.model.MsgLog;

public interface MsgLogService {
	public void insertMsgLog(MsgLog msgLog);
	public MsgLog getMsgLogById(String msgId);
	public void updateStatus(String msgId, int status);
}
