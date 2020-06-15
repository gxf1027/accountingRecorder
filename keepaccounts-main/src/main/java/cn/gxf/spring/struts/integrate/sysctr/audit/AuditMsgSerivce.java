package cn.gxf.spring.struts.integrate.sysctr.audit;

import java.util.Date;

public interface AuditMsgSerivce {
	public int sendAuditMsg(int type, String event_info, int user_id, Date proc_time);
}
