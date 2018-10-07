package cn.gxf.spring.cxf.service;

import javax.jws.WebService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.gxf.spring.struts2.integrate.model.PaymentDetail;
import cn.gxf.spring.struts2.integrate.service.DetailAccountUnivServiceImpl;

@Service
@WebService(endpointInterface="cn.gxf.spring.cxf.service.PaymentDetailService")
public class PaymentDetailServiceImpl implements PaymentDetailService {

	@Autowired
	private DetailAccountUnivServiceImpl<PaymentDetail> detailAccountUnivServiceImpl;
	
	@Override
	public int save(PaymentDetail paymentDetail) {
		
		if (null == paymentDetail){
			return 0;
		}
		
		try {
			detailAccountUnivServiceImpl.saveOne(paymentDetail);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
		return 1;
	}

	@Override
	public int update(PaymentDetail paymentDetailNew) {

		if (null == paymentDetailNew){
			return 0;
		}
		
		try {
			detailAccountUnivServiceImpl.updateOne(paymentDetailNew);
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
			PaymentDetail paymentDetail = detailAccountUnivServiceImpl.getPaymentDetailByMxuuid(mxuuid);
			detailAccountUnivServiceImpl.deleteOne(paymentDetail);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return 0;
	}

}
