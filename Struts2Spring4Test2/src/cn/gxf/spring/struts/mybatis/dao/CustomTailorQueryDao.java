package cn.gxf.spring.struts.mybatis.dao;

import java.util.List;
import java.util.Map;

import org.mybatis.spring.annotation.MapperScan;

import cn.gxf.spring.struts2.integrate.model.IncomeDetailVO;
import cn.gxf.spring.struts2.integrate.model.PaymentDetailVO;
import cn.gxf.spring.struts2.integrate.model.TransferDetailVO;

@MapperScan
public interface CustomTailorQueryDao {
	public List<IncomeDetailVO> getIncomeDetailVo(Map<String, Object> params);
	public List<PaymentDetailVO> getPaymentDetailVo(Map<String, Object> params);
	public List<TransferDetailVO> getTransferDetailVo(Map<String, Object> params);
}
