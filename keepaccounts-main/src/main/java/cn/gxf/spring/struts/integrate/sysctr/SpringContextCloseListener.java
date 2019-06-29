package cn.gxf.spring.struts.integrate.sysctr;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextClosedEvent;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.connection.StringRedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import com.weibo.api.motan.common.MotanConstants;
import com.weibo.api.motan.util.MotanSwitcherUtil;

import cn.gxf.spring.cxf.CXFWebServiceController;
import cn.gxf.spring.motan.control.MotanController;
import cn.gxf.spring.motan.filter.FilterConstants;
import cn.gxf.spring.motan.mbdao.RpcRequestLogDao;
import cn.gxf.spring.motan.model.RpcRequestInfo;
import cn.gxf.spring.struts.integrate.cache.RedisKeysContants;
import cn.gxf.spring.struts.integrate.util.AuxiliaryTools;

@Service
public class SpringContextCloseListener implements ApplicationListener<ContextClosedEvent>{

	@Autowired
	private MotanController motanController;
	
	@Autowired
	private CXFWebServiceController cxfController;
	
	@Autowired
	private RedisTemplate redisTemplate;
	
	@Autowired
	private RpcRequestLogDao rpcRequestLogDao;
	
	@SuppressWarnings("unchecked")
	@Override
	public void onApplicationEvent(ContextClosedEvent event) {
		
        if (event.getApplicationContext().getParent() == null) {
        	
        	cxfController.setBlocked(1); // ��ֹcxf WS����
        	
        	motanController.setBlocked(1); // ��ֹmotan�ӿڵĵ���
        	AuxiliaryTools.delay(500);
        	
        	// server������������Ҫ��ʽ�����������أ�ע�ᵽconsul
        	MotanSwitcherUtil.setSwitcherValue(MotanConstants.REGISTRY_HEARTBEAT_SWITCHER, false);
    		System.out.println("SpringContextCloseListener.....�����رգ���ע�ᵽconsul.");
    		
    		// ɾ����������ͳ�� 
    		redisTemplate.delete(RedisKeysContants.ONLINE_USERS_KEY);
    		
    		// �����Ҫ�־û�redis�б����rpc��������
    		long sz = redisTemplate.opsForList().size(FilterConstants.RPC_REQUEST_LIST);
    		if ( 1 == motanController.isRpcInfoPersisted() && sz > 0 ){
    			System.out.println("SpringContextCloseListener.....�����رգ��־û�rpc������Ϣ.");
    			List<RpcRequestInfo> rpcReqList = new ArrayList<RpcRequestInfo>();
    			
    			/*RpcRequestInfo rpcReq =  (RpcRequestInfo) redisTemplate.opsForList().rightPop(FilterConstants.RPC_REQUEST_LIST);
    			while (rpcReq != null){
    				rpcReqList.add(rpcReq);
    				rpcReq =  (RpcRequestInfo) redisTemplate.opsForList().rightPop(FilterConstants.RPC_REQUEST_LIST);
    			}*/
    			
    			byte[] rawKey = redisTemplate.getKeySerializer().serialize(FilterConstants.RPC_REQUEST_LIST);
    			rpcReqList = redisTemplate.executePipelined(
    					new RedisCallback<RpcRequestInfo>() {

							@Override
							public RpcRequestInfo doInRedis(RedisConnection connection) throws DataAccessException {
								
								//StringRedisConnection stringRedisConn = (StringRedisConnection)connection;
								for(int i=0; i< sz; i++) {
									connection.rPop(rawKey); //  org.springframework.data.redis.connection.jedis ���ʹ��pipeline�򷵻�null
							      }
								
								return null;
							}
    						
				});
    			
    			if (rpcReqList.size() > 0){
    				rpcRequestLogDao.saveRequestsInfo(rpcReqList);
    			}
    			
    		}
    		
    		// persist cxf blocked ip to db
    		//cxfController.persistBlockedIp();
        }
	}
	
	
	
	
}
