package cn.gxf.spring.struts2.integrate.service;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import cn.gxf.spring.struts.mybatis.dao.CustomTailorQueryDao;
import cn.gxf.spring.struts2.integrate.model.IncomeDetailVO;
import cn.gxf.spring.struts2.integrate.model.PaymentDetailVO;
import cn.gxf.spring.struts2.integrate.model.TransferDetailVO;


/*
 *  根据制定的查询条件查询明细数据
 * 
 * 
 * */

@Service
public class CustomTailorQueryServiceImpl implements CustomTailorQueryService {

	@Autowired
	private CustomTailorQueryDao customTailorQueryDao;
	
	@Override
	public List<PaymentDetailVO> queryPayment(Map<String, Object> params, int pageNum, int pageSize) {
		// 获取第1页，10条内容，默认查询总数count
		PageHelper.startPage(pageNum, pageSize);
		List<PaymentDetailVO> paymentList = customTailorQueryDao.getPaymentDetailVo(params);
		
		//用PageInfo对结果进行包装
		PageInfo<PaymentDetailVO> page = new PageInfo<>(paymentList);
		System.out.println("total: " + page.getTotal());
		
		// 排序
		Collections.sort(paymentList);
		
		return paymentList;
	}

	@Override
	public List<IncomeDetailVO> queryIncome(Map<String, Object> params, int pageNum, int pageSize) {
		PageHelper.startPage(pageNum, pageSize);
		List<IncomeDetailVO> incomeList = customTailorQueryDao.getIncomeDetailVo(params);
		
		PageInfo<IncomeDetailVO> page = new PageInfo<>(incomeList);
		System.out.println("total: " + page.getTotal());
		
		// 排序
		Collections.sort(incomeList);
		return incomeList;
	}

	@Override
	public List<TransferDetailVO> queryTransfer(Map<String, Object> params, int pageNum, int pageSize) {
		PageHelper.startPage(pageNum, pageSize);
		List<TransferDetailVO> transferList = customTailorQueryDao.getTransferDetailVo(params);
		
		PageInfo<TransferDetailVO> page = new PageInfo<>(transferList);
		System.out.println("total: " + page.getTotal());
		
		// 排序
		Collections.sort(transferList);
		
		return transferList;
	}

}
