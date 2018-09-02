package cn.gxf.spring.quartz.job.service;

import com.weibo.api.motan.rpc.ResponseFuture;
import java.lang.String;
import java.util.Date;
import java.util.List;

public interface CreditCardsBillServiceAsync extends CreditCardsBillService {
  ResponseFuture processBillAsync(List<String> zzdmList, Date jyqq, Date jyqz);

  ResponseFuture processBillManuallyAsync(int user_id, Date jyqq, Date jyqz);

  ResponseFuture getCreditCardInZDRAsync(int zdr);
}
