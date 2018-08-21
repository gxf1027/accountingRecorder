package cn.gxf.spring.quartz.job.service;

import java.util.Date;
import java.util.List;

import cn.gxf.spring.struts2.integrate.model.FinancialProductDetail;

public interface FinancialProductsNoticeService {
	public int processNotice(List<FinancialProductDetail> finanProdList, Date ssqq, Date ssqz);
	
	public List<FinancialProductDetail> queryFinancialProductDetailByEndDate(Date date_from, Date date_to);
}
