package cn.gxf.spring.quartz.job.service;

import java.util.Date;
import java.util.List;

import cn.gxf.spring.quartz.job.model.CreditCard;

public interface CreditCardsBillService {
	public int processBill(List<String> zzdmList, Date jyqq, Date jyqz);
	public int processBillManually(int user_id, Date jyqq, Date jyqz);
	public List<CreditCard> getCreditCardInZDR(int zdr);
}
