package cn.gxf.spring.struts.mybatis.dao;



import java.util.List;
import java.util.Map;

import org.mybatis.spring.annotation.MapperScan;

import cn.gxf.spring.struts2.integrate.model.IncomeDetailVO;
import cn.gxf.spring.struts2.integrate.model.PaymentDetailVO;
import cn.gxf.spring.struts2.integrate.model.TransferDetailVO;

@MapperScan
public interface AccountVoMBDao {
	public List<PaymentDetailVO> getPaymentVo(Map<String, Object> map);
	public List<IncomeDetailVO> getIncomeVo(Map<String, Object> map);
	public List<TransferDetailVO> getTransferVo(Map<String, Object> map);
}
