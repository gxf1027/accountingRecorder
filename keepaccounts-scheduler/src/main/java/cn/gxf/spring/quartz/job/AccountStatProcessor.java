package cn.gxf.spring.quartz.job;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.weibo.api.motan.rpc.Future;
import com.weibo.api.motan.rpc.FutureListener;
import com.weibo.api.motan.rpc.ResponseFuture;

import cn.gxf.spring.quartz.job.service.AccountStatisticsServiceAsync;


@Service
public class AccountStatProcessor implements JobProcessor{
	
	private Logger logger = LogManager.getLogger();
	
	@Autowired
	@Qualifier("statServiceAsync")
	private AccountStatisticsServiceAsync statisticsServiceAsync;
	
	private final int patchSize = 200;
	
	@Override
	public int process() {
		
		int usersToProcessNum = statisticsServiceAsync.getUsersNumToProcessing();
		logger.info("number of user to process: " + usersToProcessNum);
		
		if (0 == usersToProcessNum){
			return 0;
		}
		
		int start = 0;
		while (true){
			
			// 获取待处理的用户（并非全部用户）
			Map<String, String> users = statisticsServiceAsync.getUsersIdNamePairToProcessByLimit(start, patchSize);
			if (users.size() == 0){
				break;
			}
			
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
				
				try {
					Thread.sleep(200);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			
			start += patchSize;
			
			if (start >= usersToProcessNum){
				break;
			}
			
		}
		
		return 1;
	}
	
}
