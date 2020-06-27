package cn.gxf.spring.cxf.service;

import java.util.Date;

import javax.jws.WebService;

import org.apache.cxf.interceptor.Fault;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.gxf.spring.struts.integrate.sysctr.audit.AuditInfo;
import cn.gxf.spring.struts.integrate.sysctr.audit.AuditMsgSerivce;
import cn.gxf.spring.struts2.integrate.model.AccountObject;
import cn.gxf.spring.struts2.integrate.model.PaymentDetail;
import cn.gxf.spring.struts2.integrate.service.DetailAccountUnivServiceImpl;

@Service
@WebService(endpointInterface="cn.gxf.spring.cxf.service.PaymentDetailService")
public class PaymentDetailServiceImpl implements PaymentDetailService {

	@Autowired
	private DetailAccountUnivServiceImpl<PaymentDetail> detailAccountUnivServiceImpl;
	
	@Autowired
	private AuditMsgSerivce auditMsgService;
	
	private boolean checkUserId(AccountObject account){
		return account.getUser_id() > 0 ? true : false;
	}
	
	@Override
	public int save(PaymentDetail paymentDetail) {
		
		if (null == paymentDetail || false == checkUserId(paymentDetail)){
			return 0;
		}
		
		try {
			detailAccountUnivServiceImpl.saveOne(paymentDetail);
		} catch (Exception e) {
			throw new Fault(new RuntimeException(e));
		}
		
		// 记录审计日志
		this.auditMsgService.sendAuditMsg(AuditInfo.PAY_ADD, "支出-新增,accuuid:"+paymentDetail.getAccuuid(), paymentDetail.getUser_id(), new Date());
		
		return 1;
	}

	@Override
	public int update(PaymentDetail paymentDetailNew) {

		if (null == paymentDetailNew || false == checkUserId(paymentDetailNew)){
			return 0;
		}
		
		try {
			detailAccountUnivServiceImpl.updateOne(paymentDetailNew);
		} catch (Exception e) {
			throw new Fault(new RuntimeException(e));
		}
		
		// 记录审计日志
		this.auditMsgService.sendAuditMsg(AuditInfo.PAY_UPDATE, "支出-更新,accuuid:"+paymentDetailNew.getAccuuid(), paymentDetailNew.getUser_id(), new Date());
		
		return 1;
	}

	@Override
	public int delete(String mxuuid) {
		
		if (null == mxuuid){
			return 0;
		}
		
		PaymentDetail paymentDetail = null;
		try {
			paymentDetail = detailAccountUnivServiceImpl.getPaymentDetailByMxuuid(mxuuid);
			detailAccountUnivServiceImpl.deleteOne(paymentDetail);
		} catch (Exception e) {
			throw new Fault(new RuntimeException(e));
		}
		
		if (null != paymentDetail){
			this.auditMsgService.sendAuditMsg(AuditInfo.PAY_DEL, "支出-删除,accuuid:"+mxuuid, paymentDetail.getUser_id(), new Date());
		}
		
		return 0;
	}

}
