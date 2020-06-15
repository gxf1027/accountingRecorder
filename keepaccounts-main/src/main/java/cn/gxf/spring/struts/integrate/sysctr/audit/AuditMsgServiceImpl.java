package cn.gxf.spring.struts.integrate.sysctr.audit;

import java.util.Date;
import java.util.UUID;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;

@Service
public class AuditMsgServiceImpl implements AuditMsgSerivce {

	@Autowired
	private ThreadPoolTaskExecutor taskExecutor;
	
	@Autowired
	private RabbitTemplate rabbitTemplate;
	
	@Override
	public int sendAuditMsg(int type, String event_info, int user_id, Date proc_time) {
		
		AuditInfo auditInfo = new AuditInfo(UUID.randomUUID().toString(), 
				type, event_info, user_id, proc_time);
		taskExecutor.execute(new AuditProcess(this.rabbitTemplate, auditInfo));
		
		return 1;
	}

}
