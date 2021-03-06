package cn.gxf.spring.quartz.job.service;

import com.weibo.api.motan.rpc.ResponseFuture;
import java.lang.Integer;
import java.lang.String;

public interface AccountStatisticsServiceAsync extends AccountStatisticsService {
  ResponseFuture updateStatThisMonthByUseridAsync(String userid, String username);

  ResponseFuture updateStatThisMonthForAllUsersAsync();

  ResponseFuture getAllUsersIdNamePairToProcessAsync();

  ResponseFuture getUsersIdNamePairToProcessByLimitAsync(Integer start, Integer limit);

  ResponseFuture getUsersNumToProcessingAsync();
}
