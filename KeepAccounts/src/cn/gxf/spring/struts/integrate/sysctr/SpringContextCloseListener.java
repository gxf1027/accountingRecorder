package cn.gxf.spring.struts.integrate.sysctr;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextClosedEvent;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import com.weibo.api.motan.common.MotanConstants;
import com.weibo.api.motan.util.MotanSwitcherUtil;

import cn.gxf.spring.motan.control.MotanController;
import cn.gxf.spring.motan.filter.FilterConstants;
import cn.gxf.spring.motan.mbdao.RpcRequestLogDao;
import cn.gxf.spring.motan.model.RpcRequestInfo;

@Service
public class SpringContextCloseListener implements ApplicationListener<ContextClosedEvent>{

	@Autowired
	private MotanController motanController;
	
	@Autowired
	private RedisTemplate redisTemplate;
	
	@Autowired
	private RpcRequestLogDao rpcRequestLogDao;
	
	@Override
	public void onApplicationEvent(ContextClosedEvent event) {
		
        if (event.getApplicationContext().getParent() == null) {
        	// server������������Ҫ��ʽ�����������أ�ע�ᵽconsul
        	MotanSwitcherUtil.setSwitcherValue(MotanConstants.REGISTRY_HEARTBEAT_SWITCHER, false);
    		System.out.println("SpringContextCloseListener.....�����رգ���ע�ᵽconsul.");
    		
    		// �����Ҫ�־û�redis�б����rpc��������
    		long sz = redisTemplate.opsForList().size(FilterConstants.RPC_REQUEST_LIST);
    		if ( 1 == motanController.isRpcInfoPersisted() && sz > 0 ){
    			System.out.println("SpringContextCloseListener.....�����رգ��־û�rpc������Ϣ.");
    			List<RpcRequestInfo> rpcReqList = new ArrayList<RpcRequestInfo>();
    			for (long i=0; i<sz; i++){
    				RpcRequestInfo rpcReq =  (RpcRequestInfo) redisTemplate.opsForList().rightPop(FilterConstants.RPC_REQUEST_LIST);
    				rpcReqList.add(rpcReq);
    			}
    			rpcRequestLogDao.saveRequestsInfo(rpcReqList);
    		}
        }
	}
	
	
	
	
}
