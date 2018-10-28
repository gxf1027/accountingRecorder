package cn.gxf.spring.cxf.util;

public interface IpControlService {
	public boolean isIPBlocked(String ip);
	public int addIPBlocked(String ip);
	public void removeIPBlocked(String ip);
}
