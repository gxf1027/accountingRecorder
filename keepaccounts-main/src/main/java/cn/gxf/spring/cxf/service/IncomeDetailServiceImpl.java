package cn.gxf.spring.cxf.service;


import java.io.StringReader;
import java.util.Date;

import javax.jws.WebService;
import javax.xml.soap.SOAPException;
import javax.xml.transform.stream.StreamSource;

import org.apache.cxf.interceptor.Fault;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import org.springframework.stereotype.Service;

import cn.gxf.spring.cxf.util.OxmMapper;
import cn.gxf.spring.struts.integrate.sysctr.audit.AuditInfo;
import cn.gxf.spring.struts.integrate.sysctr.audit.AuditMsgSerivce;
import cn.gxf.spring.struts2.integrate.model.AccountObject;
import cn.gxf.spring.struts2.integrate.model.IncomeDetail;
import cn.gxf.spring.struts2.integrate.service.DetailAccountUnivServiceImpl;

@Service
@WebService(endpointInterface="cn.gxf.spring.cxf.service.IncomeDetailService")
public class IncomeDetailServiceImpl implements IncomeDetailService {

	@Autowired
	private OxmMapper oxmMapper;
	 
	@Autowired
	private DetailAccountUnivServiceImpl<IncomeDetail> detailAccountUnivServiceImpl;
	
	@Autowired
	private AuditMsgSerivce auditMsgService;
	
	@Value("${cn.gxf.keepacc.audit}")
	private String audit;
	
	private boolean checkUserId(AccountObject account){
		return account.getUser_id() > 0 ? true : false;
	}
	
	@Override
	public int save2(IncomeDetail incomeDetail) {
		if (null == incomeDetail || false == checkUserId(incomeDetail)){
			return 0;
		}
		
		try {
			detailAccountUnivServiceImpl.saveOne(incomeDetail);
		} catch (Exception e) {
			throw new Fault(new RuntimeException(e));  
		}
		
		// 记录审计日志
		if (isAuditOpen()){
			this.auditMsgService.sendAuditMsg(AuditInfo.INCOME_ADD, "收入-新增,accuuid:"+incomeDetail.getAccuuid(), incomeDetail.getUser_id(), new Date());
		}
		
		return 1;
	}
	
	
//	@Override
//	public String save(String incomeDetailXml) {
//		
//		IncomeDetail incomeDetail = (IncomeDetail) oxmMapper.unmarshal(incomeDetailXml);
//		String accuuid = null;
//		try {
//			accuuid = detailAccountUnivServiceImpl.saveOne(incomeDetail);
//		} catch (Exception e) {
//			e.printStackTrace();
//			throw new Fault(new RuntimeException(e));  
//		}
//		
//		return accuuid;
//	}

//	@Override
//	public int update(IncomeDetail incomeDetailNew) {
//		
//		if (null == incomeDetailNew){
//			return 0;
//		}
//		
//		try {
//			detailAccountUnivServiceImpl.updateOne(incomeDetailNew);
//		} catch (Exception e) {
//			e.printStackTrace();
//			throw new Fault(new RuntimeException(e));
//		}
//		
//		return 1;
//	}
	
	@Override
	public int update(String incomeDetailNewXml) {
		
		IncomeDetail incomeDetailNew = (IncomeDetail) oxmMapper.unmarshal(incomeDetailNewXml);
		if (null == incomeDetailNew){
			return 0;
		}
		
		try {
			detailAccountUnivServiceImpl.updateOne(incomeDetailNew);
		} catch (Exception e) {
			throw new Fault(new RuntimeException(e));
		}
		
		return 1;
	}

	@Override
	public int delete(String mxuuid) {
		
		if (null == mxuuid){
			return 0;
		}
		
		IncomeDetail incomeDetail = null;
		try {
			incomeDetail = detailAccountUnivServiceImpl.getIncomeDetailByMxuuid(mxuuid);
			detailAccountUnivServiceImpl.deleteOne(incomeDetail);
		} catch (Exception e) {
			throw new Fault(new RuntimeException(e));
		}
		
		if (isAuditOpen()){
			if (null != incomeDetail){
				this.auditMsgService.sendAuditMsg(AuditInfo.INCOME_DEL, "收入-删除,accuuid:"+mxuuid, incomeDetail.getUser_id(), new Date());
			}
		}
		
		return 0;
	}
	
	private boolean isAuditOpen(){
		return this.audit.equals("enabled");
	}

}
