package cn.gxf.spring.quartz.job.endpoint;

import java.util.List;

import javax.jws.WebService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import cn.gxf.spring.quartz.job.service.BillService;

@Component
@WebService(endpointInterface="cn.gxf.spring.quartz.job.endpoint.BillServiceEndpoint")
public class BillServiceEndPointImpl implements BillServiceEndpoint {

	@Autowired
	private BillService billService; 
	
	@Override
	public void SetBillMailed(List<String> uuidList) {
		System.out.println("SetBillMailed param: " + uuidList);
		billService.SetBillMailed(uuidList);
	}

}
