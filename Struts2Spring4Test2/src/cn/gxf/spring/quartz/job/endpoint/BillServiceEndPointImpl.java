package cn.gxf.spring.quartz.job.endpoint;

import java.util.List;

import javax.jws.WebService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import cn.gxf.spring.quartz.job.service.BillService;

@Component
@WebService(serviceName="BillService", endpointInterface="cn.gxf.spring.quartz.job.endpoint.BillServiceEndpoint")
public class BillServiceEndPointImpl implements BillServiceEndpoint {

	@Autowired
	private BillService billService; 
	
	@Override
	public void SetBillMailed(List<String> uuidList) {
		
		billService.SetBillMailed(uuidList);
	}

}
