package cn.gxf.spring.cxf.util;

import java.util.List;
import java.util.Map;

public interface BlockedIpDao {
	public long listCount();
	public Map<String, String> getIpBlocked();
	public int isIpBlocked(String ip);
	public void addIpBlocked(String ip);
	public void removeIpBlocked(String ip);
	//public int persistBlockedIpFromRedis();
}
