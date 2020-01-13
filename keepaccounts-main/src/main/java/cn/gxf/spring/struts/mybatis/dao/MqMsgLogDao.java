package cn.gxf.spring.struts.mybatis.dao;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.mybatis.spring.annotation.MapperScan;

import cn.gxf.spring.struts2.integrate.model.MsgLog;

@MapperScan
public interface MqMsgLogDao {
	public void addMsg(MsgLog msgLog);
	public MsgLog getMsgLogById(String msgId);
	public void updateStatus(@Param("msgId") String msgId, @Param("status") int status);
	public List<MsgLog> getTimeoutMsg();
	public void updateTryCountNextTryTime(@Param("msgId") String msgId, @Param("nextTryTime") Date nextTryTime);
}
