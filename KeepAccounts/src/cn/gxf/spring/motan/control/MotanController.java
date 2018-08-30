package cn.gxf.spring.motan.control;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

/*
 * 
 * ����ͨ��JMX��������
 * */
@Service
public class MotanController {

	
	private Logger logger = LogManager.getLogger();
	// �Ƿ�־û�rpc������Ϣ
	private int rpcInfoPersisted = 1;
	// �Ƿ���ֹ���е�rpc���ʣ������ر�ʱ�Զ���������Ϊ1��
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
