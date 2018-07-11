package cn.gxf.spring.struts.mybatis.dao;

import java.util.List;
import java.util.Map;

import org.mybatis.spring.annotation.MapperScan;

import cn.gxf.spring.struts2.integrate.model.IncomeDetail;
import cn.gxf.spring.struts2.integrate.model.PaymentDetail;

@MapperScan
public interface PaymentDetailMBDao {
	public List<PaymentDetail> getPaymentDetailByUserId(int user_id);
	public List<PaymentDetail> getDetailByPatchUuid(List<String> mxuuidList);
	public List<PaymentDetail> getDetailByPatchAccuuid(List<String> accuuidList);
	public PaymentDetail getPaymentDetailByUuid(String mxuuid);
	public void addOne(PaymentDetail paymentDetail);
	public void updateOne(PaymentDetail paymentDetail);
	public void deleteOne(Map<String, Object> paramMap);
	public void deletePatch(List<String> mxuuidList);
}
