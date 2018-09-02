package cn.gxf.spring.quartz.job.service;

import cn.gxf.spring.struts2.integrate.model.FinancialProductDetail;
import com.weibo.api.motan.rpc.ResponseFuture;
import java.util.Date;
import java.util.List;

public interface FinancialProductsNoticeServiceAsync extends FinancialProductsNoticeService {
  ResponseFuture processNoticeAsync(List<FinancialProductDetail> finanProdList, Date ssqq,
      Date ssqz);

  ResponseFuture queryFinancialProductDetailByEndDateAsync(Date date_from, Date date_to);
}
