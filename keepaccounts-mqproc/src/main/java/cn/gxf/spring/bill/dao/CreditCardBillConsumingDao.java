package cn.gxf.spring.bill.dao;

import cn.gxf.spring.bill.model.CreditCardBillConsumingInfo;

public interface CreditCardBillConsumingDao {
	public int isPchExists(String pch);
	public void saveConsumingInfo(CreditCardBillConsumingInfo ccbci);
}
