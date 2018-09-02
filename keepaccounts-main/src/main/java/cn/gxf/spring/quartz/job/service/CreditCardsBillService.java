package cn.gxf.spring.quartz.job.service;

import java.util.Date;
import java.util.List;

import com.weibo.api.motan.transport.async.MotanAsync;

import cn.gxf.spring.quartz.job.model.CreditCard;

@MotanAsync
public interface CreditCardsBillService {
	public int processBill(List<String> zzdmList, Date jyqq, Date jyqz);
	public int processBillManually(int user_id, Date jyqq, Date jyqz);
	public List<CreditCard> getCreditCardInZDR(int zdr);
}
