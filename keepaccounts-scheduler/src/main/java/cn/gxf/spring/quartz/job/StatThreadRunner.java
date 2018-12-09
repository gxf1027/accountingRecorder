package cn.gxf.spring.quartz.job;

import java.util.Map;

import com.weibo.api.motan.rpc.FutureListener;
import com.weibo.api.motan.rpc.ResponseFuture;

import cn.gxf.spring.quartz.job.service.AccountStatisticsServiceAsync;

public class StatThreadRunner implements Runnable{

	private AccountStatisticsServiceAsync statisticsServiceAsync;
	private String userId;
	private String userName;
	private FutureListener listener;
	private Map<ResponseFuture, String> rvMap;
	
	public StatThreadRunner(AccountStatisticsServiceAsync statisticsServiceAsync, 
							String userId, String userName, 
							FutureListener listener, Map<ResponseFuture, String> rvMap){
		this.statisticsServiceAsync = statisticsServiceAsync;
		this.userId = userId;
		this.userName = userName;
		this.listener = listener;
		this.rvMap = rvMap;
	}
	
	@Override
	public void run() {
		ResponseFuture future =  statisticsServiceAsync.updateStatThisMonthByUseridAsync(userId, userName);
		rvMap.put(future, userName);
		future.addListener(listener);
	}

}
