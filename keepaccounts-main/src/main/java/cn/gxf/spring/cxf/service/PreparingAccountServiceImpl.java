package cn.gxf.spring.cxf.service;

import java.util.List;

import javax.jws.WebService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.gxf.spring.struts2.integrate.model.FinancialProductDetail;
import cn.gxf.spring.struts2.integrate.service.FinanicalProductService;

@Service
@WebService(endpointInterface="cn.gxf.spring.cxf.service.PreparingAccountService")
public class PreparingAccountServiceImpl implements PreparingAccountService {

	@Autowired
	private FinanicalProductService finanicalProductService;
	
	@Override
	public List<FinancialProductDetail> getFinancialProducts(int user_id, int type) {
		
		switch (type){
			case UNREDEEM_PRODUCT:
				
				break;
			case UNRETURN_PRODUCT:
				
				break;
			default:
					return null;
		}
		
		return null;
	}

}
