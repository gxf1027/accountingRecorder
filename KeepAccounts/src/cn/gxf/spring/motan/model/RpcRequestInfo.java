package cn.gxf.spring.motan.model;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;

public class RpcRequestInfo implements Serializable {

	private static final long serialVersionUID = -6037014437332042893L;
	private String uuid;
	private String host;
	private String userName;
	private String interfaceName;
	private String methodName;
	private String params;
	private Timestamp requestTime;
	private Timestamp responseTime;
	private String deniedFlag;

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getInterfaceName() {
		return interfaceName;
	}

	public void setInterfaceName(String interfaceName) {
		this.interfaceName = interfaceName;
	}

	public String getMethodName() {
		return methodName;
	}

	public void setMethodName(String methodName) {
		this.methodName = methodName;
	}

	public String getParams() {
		return params;
	}
	
	public void setParams(String params) {
		this.params = params;
	}
	
	public Timestamp getRequestTime() {
		return requestTime;
	}

	public void setRequestTime(Timestamp requestTime) {
		this.requestTime = requestTime;
	}

	public Timestamp getResponseTime() {
		return responseTime;
	}

	public void setResponseTime(Timestamp responseTime) {
		this.responseTime = responseTime;
	}

	public String getDeniedFlag() {
		return deniedFlag;
	}

	public void setDeniedFlag(String deniedFlag) {
		this.deniedFlag = deniedFlag;
	}

	@Override
	public String toString() {
		return "RpcRequestInfo [uuid=" + uuid + ", host=" + host + ", userName=" + userName + ", interfaceName="
				+ interfaceName + ", methodName=" + methodName + ", params=" + params + ", requestTime=" + requestTime
				+ ", responseTime=" + responseTime + ", deniedFlag=" + deniedFlag + "]";
	}
	
}
