package cn.gxf.spring.struts.integrate.cache;

import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import cn.gxf.spring.motan.filter.FilterConstants;
import cn.gxf.spring.motan.mbdao.RpcRequestLogDao;
import cn.gxf.spring.motan.model.RpcRequestInfo;
import cn.gxf.spring.struts.mybatis.dao.AccountVoMBDao;


public class RedisMessageListener implements MessageListener{

	private Logger logger = LogManager.getLogger();
	
	private RedisTemplate<String, Object> redisTemplate;  
	private RpcRequestLogDao rpcRequestLogDao;
	
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
			 
			popnum--;
		}
		
		logger.info("RedisMessageListener pop out " + (commond_num - popnum) +" RpcRequestInfo objects. \n Consume " + (System.currentTimeMillis()-start) + " millis");
		
		// 持久化到mysql
		if (rpcReqList.size() > 0){
			try {
				rpcRequestLogDao.saveRequestsInfo(rpcReqList);
				logger.info("RedisMessageListener persistent RpcRequestInfo Objects.");
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
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
	
	
	
}
