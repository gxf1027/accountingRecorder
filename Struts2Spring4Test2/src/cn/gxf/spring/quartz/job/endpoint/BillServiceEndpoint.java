package cn.gxf.spring.quartz.job.endpoint;

import java.util.List;

import javax.jws.WebMethod;
import javax.jws.WebService;

@WebService
public interface BillServiceEndpoint {
	
	@WebMethod
	public void SetBillMailed(List<String> uuidList);
}
