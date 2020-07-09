package cn.gxf.spring.cxf.service;

import java.util.Date;

import javax.jws.WebService;

import org.apache.cxf.interceptor.Fault;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import cn.gxf.spring.struts.integrate.sysctr.audit.AuditInfo;
import cn.gxf.spring.struts.integrate.sysctr.audit.AuditMsgSerivce;
import cn.gxf.spring.struts2.integrate.model.AccountObject;
import cn.gxf.spring.struts2.integrate.model.TransferDetail;
import cn.gxf.spring.struts2.integrate.service.DetailAccountUnivServiceImpl;

@Service
@WebService(endpointInterface="cn.gxf.spring.cxf.service.TransferDetailService")
public class TransferDetailServiceImpl implements TransferDetailService{

	
	@Autowired
	private DetailAccountUnivServiceImpl<TransferDetail> detailAccountUnivServiceImpl;
	
	@Autowired
	private AuditMsgSerivce auditMsgService;
	
	@Value("${cn.gxf.keepacc.audit}")
	private String audit;
	
	private boolean checkUserId(AccountObject account){
		return account.getUser_id() > 0 ? true : false;
	}
	
	@Override
	public int save(TransferDetail transferDetail) {
		
		if (null == transferDetail || false == checkUserId(transferDetail)){
			return 0;
		}
		
		try {
			detailAccountUnivServiceImpl.saveOne(transferDetail);
		} catch (Exception e) {
			throw new Fault(new RuntimeException(e));
		}
		
		// 记录审计日志
		if (isAuditOpen()){
			this.auditMsgService.sendAuditMsg(AuditInfo.TRANS_ADD, "转账-新增,accuuid:"+transferDetail.getAccuuid(), transferDetail.getUser_id(), new Date());
		}
				
		return 1;
	}

	@Override
	public int update(TransferDetail transferDetailNew) {
		
		if (null == transferDetailNew || false == checkUserId(transferDetailNew)){
			return 0;
		}
		
		try {
			detailAccountUnivServiceImpl.updateOne(transferDetailNew);
		} catch (Exception e) {
			throw new Fault(new RuntimeException(e));
		}
		
		// 记录审计日志
		if (isAuditOpen()){
			this.auditMsgService.sendAuditMsg(AuditInfo.TRANS_UPDATE, "转账-更新,accuuid:"+transferDetailNew.getAccuuid(), transferDetailNew.getUser_id(), new Date());
		}
		
		return 1;
	}

	@Override
	public int delete(String mxuuid) {
		
		if (null == mxuuid){
			return 0;
		}
		
		TransferDetail transferDetail = null;
		try {
			transferDetail = detailAccountUnivServiceImpl.getTransferDetailByMxuuid(mxuuid);
			detailAccountUnivServiceImpl.deleteOne(transferDetail);
		} catch (Exception e) {
			throw new Fault(new RuntimeException(e));
		}
		
		if (isAuditOpen()){
			if (null != transferDetail){
				this.auditMsgService.sendAuditMsg(AuditInfo.TRANS_DEL, "转账-删除,accuuid:"+mxuuid, transferDetail.getUser_id(), new Date());
			}
		}
		
		return 0;
	}

	private boolean isAuditOpen(){
		return this.audit.equals("enabled");
	}
}
