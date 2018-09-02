package cn.gxf.spring.struts2.integrate.service;

import java.util.Date;

public interface WaitingForSyncService {
	public int queryWaiting4Save(String accuuid);
	public int queryWaiting4Del(String accuuid);
	public int queryWaiting4Update(String accuuid, Date date);
}
