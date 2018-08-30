package cn.gxf.spring.motan.control;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

/*
 * 
 * 可以通过JMX在线设置
 * */
@Service
public class MotanController {

	
	private Logger logger = LogManager.getLogger();
	// 是否持久化rpc调用信息
	private int rpcInfoPersisted = 1;
	// 是否阻止所有的rpc访问（容器关闭时自动触发设置为1）
	private int blocked = 0;
	
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
	
	
}
