package cn.gxf.spring.cxf;

import java.util.Map;

public interface CxfAuthenInfoDao {
	public Map<String, String> getAuthenInfo(String username);
	public Map<String, String> getAuthenInfo();
	
	public int accessValidating(String username, String interfaceName, String methodName);
}
