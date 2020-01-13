package cn.gxf.spring.struts2.integrate.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.gxf.spring.struts.mybatis.dao.MqMsgLogDao;
import cn.gxf.spring.struts2.integrate.model.MsgLog;

@Service
public class MsgLogServiceImpl implements MsgLogService {

	@Autowired
	private MqMsgLogDao msgLogDao;
	
	@Override
	public void insertMsgLog(MsgLog msgLog) {
		
		msgLogDao.addMsg(msgLog);
	}

	@Override
	public MsgLog getMsgLogById(String msgId) {
		
		return msgLogDao.getMsgLogById(msgId);
	}

	@Override
	public void updateStatus(String msgId, int status) {
		msgLogDao.updateStatus(msgId, status);
	}

}
