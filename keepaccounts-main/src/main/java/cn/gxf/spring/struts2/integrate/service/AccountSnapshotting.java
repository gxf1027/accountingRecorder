package cn.gxf.spring.struts2.integrate.service;

import cn.gxf.spring.struts2.integrate.model.AccountSnapshot;
import cn.gxf.spring.struts2.integrate.model.AccountingDetail;

public interface AccountSnapshotting {
	public static final String ADD="I";
	public static final String UPDATE="U";
	public static final String DELETE="D";
	
	public AccountSnapshot shotting(String zh_dm, String accuuid, String type, int user_id, float bdje);
	public AccountSnapshot shotting(String zh_dm,  String type, AccountingDetail detail);
	public AccountSnapshot shottingAfterBookUpdated(String zh_dm, String accuuid, String type, int user_id, float bdje);
}
