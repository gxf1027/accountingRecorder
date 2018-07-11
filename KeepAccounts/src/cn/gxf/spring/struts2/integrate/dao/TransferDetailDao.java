package cn.gxf.spring.struts2.integrate.dao;

import java.util.Date;
import java.util.List;

import cn.gxf.spring.struts2.integrate.model.TransferDetail;



public interface TransferDetailDao {
	public List<TransferDetail> getPaymentDetailByUserId(int user_id);
	public List<TransferDetail> getPaymentDetailByUserIdAndNy(int user_id, Date ssqq, Date ssqz);
	public void saveOne(TransferDetail transferDetail);
	public void updateOne(TransferDetail transferDetail);
	public void deleteOne(String uuid);
}
