package cn.gxf.spring.struts2.integrate.dao;

import java.util.Date;
import java.util.List;

import cn.gxf.spring.struts2.integrate.model.PaymentDetail;

public interface PaymentDetailDao {
	public List<PaymentDetail> getPaymentDetailByUserId(int user_id);
	public List<PaymentDetail> getPaymentDetailByUserIdAndNy(int user_id, Date ssqq, Date ssqz);
	public void saveOne(PaymentDetail paymentDetail);
	public void updateOne(PaymentDetail paymentDetail);
	public void deleteOne(String uuid);
}
