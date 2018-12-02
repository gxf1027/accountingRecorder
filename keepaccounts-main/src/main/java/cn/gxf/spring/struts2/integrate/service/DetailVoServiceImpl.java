package cn.gxf.spring.struts2.integrate.service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import cn.gxf.spring.struts.mybatis.dao.AccountVoMBDao;
import cn.gxf.spring.struts2.integrate.model.IncomeDetailVO;
import cn.gxf.spring.struts2.integrate.model.PaymentDetailVO;
import cn.gxf.spring.struts2.integrate.model.TransferDetailVO;

@Service
public class DetailVoServiceImpl implements DetailVoService{

	@Autowired
	private AccountVoMBDao accountVoMBDao;
	
	@Cacheable(value="redisCacheStat",
			key="T(cn.gxf.spring.struts.integrate.cache.StatDetailKeyGenerator).generateKey(T(cn.gxf.spring.struts.integrate.cache.StatDetailKeyGenerator).detailPaymentVoPrefix, #user_id, #date_from, #date_to)")
	@Override
	public List<PaymentDetailVO> getPaymentVo(int user_id, Date date_from, Date date_to) {
		
		if (null == date_from || null == date_to){
			return null;
		}
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Map<String, Object> paramMap = new HashMap<>();
		paramMap.put("date_from", date_from == null ? null : sdf.format(date_from));
		paramMap.put("date_to", date_to == null ? null : sdf.format(date_to));
		paramMap.put("user_id", user_id);
		
		List<PaymentDetailVO> listPayment = accountVoMBDao.getPaymentVo(paramMap);
	
		return listPayment;
	}

	@Cacheable(value="redisCacheStat",
			key="T(cn.gxf.spring.struts.integrate.cache.StatDetailKeyGenerator).generateKey(T(cn.gxf.spring.struts.integrate.cache.StatDetailKeyGenerator).detailIncomeVoPrefix, #user_id, #date_from, #date_to)")
	@Override
	public List<IncomeDetailVO> getIncomeVo(int user_id, Date date_from, Date date_to) {
		
		if (null == date_from || null == date_to){
			return null;
		}
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Map<String, Object> paramMap = new HashMap<>();
		paramMap.put("date_from", date_from == null ? null : sdf.format(date_from));
		paramMap.put("date_to", date_to == null ? null : sdf.format(date_to));
		paramMap.put("user_id", user_id);
		
		List<IncomeDetailVO> listIncome = accountVoMBDao.getIncomeVo(paramMap);
		
		return listIncome;
	}

	@Cacheable(value="redisCacheStat",
			key="T(cn.gxf.spring.struts.integrate.cache.StatDetailKeyGenerator).generateKey(T(cn.gxf.spring.struts.integrate.cache.StatDetailKeyGenerator).detailTransferVoPrefix, #user_id, #date_from, #date_to)")
	@Override
	public List<TransferDetailVO> getTransferVo(int user_id, Date date_from, Date date_to) {
		
		if (null == date_from || null == date_to){
			return null;
		}
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Map<String, Object> paramMap = new HashMap<>();
		paramMap.put("date_from", date_from == null ? null : sdf.format(date_from));
		paramMap.put("date_to", date_to == null ? null : sdf.format(date_to));
		paramMap.put("user_id", user_id);
		
		List<TransferDetailVO> listTransfer = accountVoMBDao.getTransferVo(paramMap);
		
		return listTransfer;
	}

}
