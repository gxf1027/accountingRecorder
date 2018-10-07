package cn.gxf.spring.cxf.service;


import javax.jws.WebService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.gxf.spring.struts2.integrate.model.IncomeDetail;
import cn.gxf.spring.struts2.integrate.service.DetailAccountUnivServiceImpl;

@Service
@WebService(endpointInterface="cn.gxf.spring.cxf.service.IncomeDetailService")
public class IncomeDetailServiceImpl implements IncomeDetailService {

	 
	@Autowired
	private DetailAccountUnivServiceImpl<IncomeDetail> detailAccountUnivServiceImpl;
	
	@Override
	public int save(IncomeDetail incomeDetail) {
		if (null == incomeDetail){
			return 0;
		}
		
		try {
			detailAccountUnivServiceImpl.saveOne(incomeDetail);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
		return 1;
	}

	@Override
	public int update(IncomeDetail incomeDetailNew) {
		
		if (null == incomeDetailNew){
			return 0;
		}
		
		try {
			detailAccountUnivServiceImpl.updateOne(incomeDetailNew);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return 1;
	}

	@Override
	public int delete(String mxuuid) {
		
		if (null == mxuuid){
			return 0;
		}
		
		try {
			IncomeDetail incomeDetail = detailAccountUnivServiceImpl.getIncomeDetailByMxuuid(mxuuid);
			detailAccountUnivServiceImpl.deleteOne(incomeDetail);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return 0;
	}

}
