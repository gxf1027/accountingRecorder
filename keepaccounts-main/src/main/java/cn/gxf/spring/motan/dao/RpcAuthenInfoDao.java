package cn.gxf.spring.motan.dao;

import java.util.Map;

public interface RpcAuthenInfoDao {
	public Map<String, String> getAuthenInfo(String username);
	public Map<String, String> getAuthenInfo();
	
	public int accessValidating(String username, String interfaceName, String methodName);
}
