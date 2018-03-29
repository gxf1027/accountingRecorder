package cn.gxf.spring.quartz.job.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.mybatis.spring.annotation.MapperScan;

import cn.gxf.spring.quartz.job.model.CreditCard;
import cn.gxf.spring.quartz.job.model.CreditCardBill;
import cn.gxf.spring.quartz.job.model.CreditCardTransRecord;
import cn.gxf.spring.struts2.integrate.model.IncomeDetail;

@MapperScan
public interface CreditCardBillDao {
	public List<String> getCreditCardInZDR(@Param("zdr") int zdr);
	public List<CreditCard> getCreditCardbyUserId(@Param("user_id") int user_id);
	
	public List<CreditCardTransRecord> getCreditCardTranscationRecordInZDQ(Map<String, Object> params);
	// 保存明细表
	public int saveTranscationRecordInZDQ(List<CreditCardTransRecord> cctrList);
	// 保存主表
	public int saveCreditCardBill(List<CreditCardBill> ccbList);
	// 回写明细表中的批次号(pch)
	public int setTranscationRecordPch(@Param("uuidList") List<String> uuidList, @Param("pch") String pch);
	public void deleteInvalidRecord(Map<String, Object> params);
	public void setMailed(List<String> uuidList);
}
