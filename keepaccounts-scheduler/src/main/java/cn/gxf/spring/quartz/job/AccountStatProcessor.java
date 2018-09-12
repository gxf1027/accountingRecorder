package cn.gxf.spring.quartz.job;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.weibo.api.motan.rpc.Future;
import com.weibo.api.motan.rpc.FutureListener;
import com.weibo.api.motan.rpc.ResponseFuture;

import cn.gxf.spring.quartz.job.service.AccountStatisticsServiceAsync;


@Service
public class AccountStatProcessor implements JobProcessor{
	
	@Autowired
	@Qualifier("statServiceAsync")
	private AccountStatisticsServiceAsync statisticsServiceAsync;
	
	@Override
	public int process() {
		
		// 获得所有用户
		Map<String, String> users = statisticsServiceAsync.getUsersIdNames();
		
		Map<ResponseFuture, String> rvMap = new HashMap<ResponseFuture, String>();
		// async with listener
	    FutureListener listener = new FutureListener() {
	        @Override
	        public void operationComplete(Future future) throws Exception {
	            System.out.println("async call, user " +
	            		rvMap.get(future) + ": "+ (future.isSuccess() ? "sucess! value:" + future.getValue() : "fail! exception:"
	                            + future.getException().getMessage()));
	        }
	    };
	    
		for (String userid : users.keySet()){
			
			//statisticsService.updateStatThisMonthByUserid(userid, users.get(userid));
			// 异步方法
			ResponseFuture future =  statisticsServiceAsync.updateStatThisMonthByUseridAsync(userid, users.get(userid));
			rvMap.put(future, users.get(userid));
			future.addListener(listener);
		}
		
		return 1;
	}
	
	
	
}
