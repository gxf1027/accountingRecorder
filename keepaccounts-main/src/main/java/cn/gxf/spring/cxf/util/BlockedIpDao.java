package cn.gxf.spring.cxf.util;

public interface BlockedIpDao {
	public int isIpBlocked(String ip);
	public int persistBlockedIpFromRedis();
}
