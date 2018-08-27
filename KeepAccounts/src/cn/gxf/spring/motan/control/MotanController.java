package cn.gxf.spring.motan.control;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

@Service
public class MotanController {

	
	private Logger logger = LogManager.getLogger();
	private int rpcInfoPersisted = 1;
	
	public int isRpcInfoPersisted() {
		return rpcInfoPersisted;
	}

	public void setRpcInfoPersisted(int rpcInfoPersisted) {
		
		this.rpcInfoPersisted = rpcInfoPersisted;
		logger.info("MotanController: set 'RpcInfoPersisted' to " + rpcInfoPersisted);
	}
}
