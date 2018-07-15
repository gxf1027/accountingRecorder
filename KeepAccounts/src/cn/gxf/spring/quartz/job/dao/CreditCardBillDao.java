package cn.gxf.spring.quartz.job.dao;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.mybatis.spring.annotation.MapperScan;

import cn.gxf.spring.quartz.job.model.CreditCard;
import cn.gxf.spring.quartz.job.model.CreditCardBill;
import cn.gxf.spring.quartz.job.model.CreditCardTransRecord;

@MapperScan
public interface CreditCardBillDao {
	//public List<String> getCreditCardInZDR(@Param("zdr") int zdr);
	public List<CreditCard> getCreditCardInZDR(@Param("zdr") int zdr);
	public List<CreditCard> getCreditCardbyUserId(@Param("user_id") int user_id);
	
	public List<CreditCardTransRecord> getCreditCardTranscationRecordInZDQ(Map<String, Object> params);
	// ������ϸ��
	public int saveTranscationRecordInZDQ(List<CreditCardTransRecord> cctrList);
	// ��������
	public int saveCreditCardBill(List<CreditCardBill> ccbList);
	// ��д��ϸ���е����κ�(pch)
	public int setTranscationRecordPch(@Param("uuidList") List<String> uuidList, @Param("pch") String pch);
	public void deleteInvalidRecord(Map<String, Object> params);
	
	public void setMailed(List<String> uuidList);
	public void setMailedTimestamp(@Param("pchList") List<String> pchList, @Param("mailedTmstp") Date mailedTmstp);
}
