package cn.gxf.spring.struts.integrate.cache;

import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.data.redis.core.RedisTemplate;

import cn.gxf.spring.motan.control.MotanController;
import cn.gxf.spring.motan.filter.FilterConstants;
import cn.gxf.spring.motan.mbdao.RpcRequestLogDao;
import cn.gxf.spring.motan.model.RpcRequestInfo;


public class RedisMessageListener implements MessageListener{

	private Logger logger = LogManager.getLogger();
	
	private String listenerName;
	private RedisTemplate<String, Object> redisTemplate;  
	private RpcRequestLogDao rpcRequestLogDao;
	private MotanController motanController;
	
	@Override
	public void onMessage(Message message, byte[] pattern) {
		
		List<RpcRequestInfo> rpcReqList = new ArrayList<RpcRequestInfo>();
		byte[] body = message.getBody();//请使用valueSerializer  
	
		long start = System.currentTimeMillis();
		
		long popnum = (long) redisTemplate.getValueSerializer().deserialize(body); 
//		try {
//			Thread.sleep(10000); // 阻塞test
//		} catch (InterruptedException e1) {
//			// TODO Auto-generated catch block
//			e1.printStackTrace();
//		}
//		
		//System.out.println("\n\n\n in redis's onMessage..." + this.hashCode());
		long commond_num = popnum;
		while(popnum > 0){
			
			// 以防止在循环中出现异常导致listener挂掉
			try {
				if (redisTemplate.opsForList().size(FilterConstants.RPC_REQUEST_LIST) == 0){
					break;
				}
				
				// 这里直接就反序列化好了
				RpcRequestInfo rpcReq =  (RpcRequestInfo) redisTemplate.opsForList().rightPop(FilterConstants.RPC_REQUEST_LIST);
				// 多个订阅客户端情况下需要考虑,可能先被其他客户端pop完
				if (null == rpcReq){
					break;
				}
				rpcReqList.add(rpcReq);
				
			} catch (Exception e) {
				e.printStackTrace();
				logger.info("RedisMessageListener exception in loop: " + e.getMessage());
			}
			
			popnum--; // 即使抛出异常也要--, 否则可能死循环
			
		}
		
		logger.info(String.format("RedisMessageListener '%s' has %s RpcRequestInfo objects need to pop out", this.listenerName, commond_num));
		logger.info(String.format("RedisMessageListener '%s' pops out %s RpcRequestInfo objects.  Consume %s millis.", this.listenerName, (commond_num - popnum), (System.currentTimeMillis()-start)));		
		// 持久化到mysql
		if (rpcReqList.size() > 0){
			try {
				rpcRequestLogDao.saveRequestsInfo(rpcReqList);
				logger.info(String.format("RedisMessageListener '%s' persistent RpcRequestInfo Objects", this.listenerName));
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		motanController.increReqListenerLock();
		
	}
	
	public String getListenerName() {
		return listenerName;
	}
	
	public void setListenerName(String listenerName) {
		this.listenerName = listenerName;
	}

	public RedisTemplate<String, Object> getRedisTemplate() {
		return redisTemplate;
	}

	public void setRedisTemplate(RedisTemplate<String, Object> redisTemplate) {
		this.redisTemplate = redisTemplate;
	}

	public RpcRequestLogDao getRpcRequestLogDao() {
		return rpcRequestLogDao;
	}

	public void setRpcRequestLogDao(RpcRequestLogDao rpcRequestLogDao) {
		this.rpcRequestLogDao = rpcRequestLogDao;
	}
	
	public void setMotanController(MotanController motanController) {
		this.motanController = motanController;
	}
	
}
