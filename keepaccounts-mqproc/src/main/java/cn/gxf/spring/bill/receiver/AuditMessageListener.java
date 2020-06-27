package cn.gxf.spring.bill.receiver;


import java.util.HashMap;
import java.util.Map;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.core.ChannelAwareMessageListener;
import org.springframework.messaging.converter.MessageConversionException;
import org.springframework.amqp.support.converter.SimpleMessageConverter;
import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.fastjson.JSONObject;
import com.rabbitmq.client.Channel;

import cn.gxf.spring.bill.dao.AuditMsgDao;



public class AuditMessageListener implements ChannelAwareMessageListener{


	private SimpleMessageConverter simpleMsgConvt;
	
	@Autowired
	private AuditMsgDao auditMsgDao;
	
	@Override
	public void onMessage(Message message, Channel channel) throws Exception {
		
		// message json格式
		Object objMsg = simpleMsgConvt.fromMessage(message);
		String jsonAuditMsg = (String)objMsg;
		JSONObject jobjAuditMsg;
		// json解析
		try {
			jobjAuditMsg = JSONObject.parseObject(jsonAuditMsg);
		} catch (Exception e) {
			System.out.println("JSON PARSE ERROR " + e);
			
			/*
			 * 
			 * org.springframework.amqp.rabbit.listener.ConditionalRejectingErrorHandler
			 * private boolean isCauseFatal(Throwable cause) {
			return cause instanceof MessageConversionException
					|| cause instanceof org.springframework.messaging.converter.MessageConversionException
					|| cause instanceof MethodArgumentResolutionException
					|| cause instanceof NoSuchMethodException
					|| cause instanceof ClassCastException
					|| isUserCauseFatal(cause);
				}
			 * 
			 * 
			 */
			throw new MessageConversionException("消息消费失败，移出消息队列，不再试错"); // fatal error rabbitmq会丢弃msg不重试
		}
		
		
//		String uuid = jobjAuditMsg.getString("uuid");
//		int event_type = jobjAuditMsg.getIntValue("event_type");
//		int user_id = jobjAuditMsg.getIntValue("user_id");
//		String event_info = jobjAuditMsg.getString("event_info");
//		Date proc_time = jobjAuditMsg.getDate("proc_time");
		
		Map<String, Object> mapMsg = parseJson(jobjAuditMsg);
		
		System.out.println("===================================");
		System.out.println("msg: " + jsonAuditMsg);
		// 异步发送email
		//taskExecutor.execute(new SendingEmailProcess(sender, objMsg));
		
		auditMsgDao.save(mapMsg);
		
		channel.basicAck(message.getMessageProperties().getDeliveryTag(),false);
	}
	
	
	public void setSimpleMsgConvt(SimpleMessageConverter simpleMsgConvt) {
		this.simpleMsgConvt = simpleMsgConvt;
	}
	
	private Map<String, Object> parseJson(JSONObject jobjAuditMsg){
		
		Map<String, Object> mapAuditMsg = new HashMap<String, Object>();
		mapAuditMsg.put("uuid", jobjAuditMsg.getString("uuid"));
		mapAuditMsg.put("event_type", jobjAuditMsg.getIntValue("event_type"));
		mapAuditMsg.put("user_id", jobjAuditMsg.getIntValue("user_id"));
		mapAuditMsg.put("event_info", jobjAuditMsg.getString("event_info"));
		mapAuditMsg.put("proc_time", jobjAuditMsg.getDate("proc_time"));
		
		return mapAuditMsg;
	}
}
