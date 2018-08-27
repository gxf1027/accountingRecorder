package cn.gxf.spring.motan.model;

import java.io.Serializable;
import java.util.Date;

public class RpcRequestInfo implements Serializable {

	private static final long serialVersionUID = -6037014437332042893L;
	private String uuid;
	private String host;
	private String userName;
	private String interfaceName;
	private String methodName;
	private Date requestTime;
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

	public Date getRequestTime() {
		return requestTime;
	}

	public void setRequestTime(Date requestTime) {
		this.requestTime = requestTime;
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
				+ interfaceName + ", methodName=" + methodName + ", requestTime=" + requestTime + ", deniedFlag="
				+ deniedFlag + "]";
	}

	
}
