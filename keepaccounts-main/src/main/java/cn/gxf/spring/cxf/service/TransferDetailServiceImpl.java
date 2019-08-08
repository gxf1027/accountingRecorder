package cn.gxf.spring.cxf.service;

import javax.jws.WebService;

import org.apache.cxf.interceptor.Fault;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.gxf.spring.struts2.integrate.model.TransferDetail;
import cn.gxf.spring.struts2.integrate.service.DetailAccountUnivServiceImpl;

@Service
@WebService(endpointInterface="cn.gxf.spring.cxf.service.TransferDetailService")
public class TransferDetailServiceImpl implements TransferDetailService{

	
	@Autowired
	private DetailAccountUnivServiceImpl<TransferDetail> detailAccountUnivServiceImpl;
	
	@Override
	public int save(TransferDetail transferDetail) {
		
		if (null == transferDetail){
			return 0;
		}
		
		try {
			detailAccountUnivServiceImpl.saveOne(transferDetail);
		} catch (Exception e) {
			throw new Fault(new RuntimeException(e));
		}
		
		return 1;
	}

	@Override
	public int update(TransferDetail transferDetailNew) {
		
		if (null == transferDetailNew){
			return 0;
		}
		
		try {
			detailAccountUnivServiceImpl.updateOne(transferDetailNew);
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
		
		try {
			TransferDetail transferDetail = detailAccountUnivServiceImpl.getTransferDetailByMxuuid(mxuuid);
			detailAccountUnivServiceImpl.deleteOne(transferDetail);
		} catch (Exception e) {
			throw new Fault(new RuntimeException(e));
		}
		return 0;
	}

}
