package cn.gxf.spring.quartz.job;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;

import cn.gxf.spring.struts.integrate.sysctr.mq.Constant;
import cn.gxf.spring.struts.integrate.util.DateFomatTransfer;
import cn.gxf.spring.struts.mybatis.dao.MqMsgLogDao;
import cn.gxf.spring.struts2.integrate.model.MsgLog;

@Component
public class ResendScheduler {
	
	@Autowired
	public MqMsgLogDao msgLogDao;
	
	@Autowired
	private RabbitSender rabbitSender;
	
	@Scheduled(cron="0 */10 * * * ?")
	public void resend(){
		
		try {
			System.out.println("resend scheduler starts to work...");
			List<MsgLog> msgLogList = msgLogDao.getTimeoutMsg();
			System.out.println("timeout size: " + msgLogList.size());
			
			for (MsgLog msgLog:msgLogList){
				int tryCount = msgLog.getTryCount();
				if (rabbitSender.exceedMaxResendTimes(tryCount)){
					// 超过最大重试次数，设置消息状态为失败
					msgLogDao.updateStatus(msgLog.getMsgId(), Constant.MsgLogStatus.DELIVER_FAIL);
				}else{
					tryCount += 1;
					Date nextTryTime = DateFomatTransfer.plusMinutes(msgLog.getNextTryTime(), 10);
					// 重试次数+1,并且更新重试时间
					msgLogDao.updateTryCountNextTryTime(msgLog.getMsgId(), nextTryTime);
					// 重新发送
					Object msgObject = JSON.parse(msgLog.getMsg());
					if (msgObject instanceof Serializable){
						System.out.println(msgObject.getClass()+": "+msgObject.getClass().getName());
						
						rabbitSender.convertAndSend((Serializable)msgObject);
					}else{
						System.out.println("msgObject not instanceof Serializable.");
					}
				}
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		
	}
}
