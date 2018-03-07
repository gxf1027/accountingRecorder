package cn.gxf.spring.quartz.job.dao;

import java.util.List;
import java.util.Map;

import org.mybatis.spring.annotation.MapperScan;

import cn.gxf.spring.quartz.job.model.CreditCardTransRecord;
import cn.gxf.spring.struts2.integrate.model.IncomeDetail;

@MapperScan
public interface CreditCardBillDao {
	public List<CreditCardTransRecord> getCreditCardTranscationRecordInZDQ(Map<String, Object> params);
	public int saveTranscationRecordInZDQ(List<CreditCardTransRecord> cctrList);
	public void deleteInvalidRecord(Map<String, Object> params);
}
