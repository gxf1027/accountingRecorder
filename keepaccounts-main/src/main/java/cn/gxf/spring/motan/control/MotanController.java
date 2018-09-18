package cn.gxf.spring.motan.control;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

/*
 * 
 * 可以通过JMX在线设置
 * */
public class MotanController {

	
	private Logger logger = LogManager.getLogger();
	// 是否持久化rpc调用信息
	private int rpcInfoPersisted = 1;
	// 是否阻止所有的rpc访问（容器关闭时自动触发设置为1）
	private int blocked = 0;
	
	// 监听redis reqeust list的监听器个数
	private int requestListenerNum = 2; 
	
	private int reqListenerLock;
	
	public void init(){
		logger.info("MotanController initializing.");
		this.reqListenerLock = this.requestListenerNum;
	}
	
	public int isRpcInfoPersisted() {
		return rpcInfoPersisted;
	}

	public void setRpcInfoPersisted(int rpcInfoPersisted) {
		
		this.rpcInfoPersisted = rpcInfoPersisted;
		logger.info("MotanController: set 'RpcInfoPersisted' to " + rpcInfoPersisted);
	}

	public int getBlocked() {
		return blocked;
	}

	public void setBlocked(int blocked) {
		this.blocked = blocked;
		logger.info("MotanController: set 'blocked' to " + blocked);
	}
	
	public int getRequestListenerNum() {
		return requestListenerNum;
	}
	
	public void setRequestListenerNum(int requestListenerNum) {
		this.requestListenerNum = requestListenerNum;
	}
	
	public int getReqListenerLock() {
		return reqListenerLock;
	}
	
	public void setReqListenerLock(int reqListenerLock) {
		this.reqListenerLock = reqListenerLock;
	}
	
	public void resetReqListenerLock(){
		this.reqListenerLock = this.requestListenerNum;
	}
	
	public void increReqListenerLock(){
		this.reqListenerLock++;
	}
	
	public boolean isReqListenerLocked(){
		return this.reqListenerLock >= this.requestListenerNum ? false : true;
	}
}
