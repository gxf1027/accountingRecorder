package cn.gxf.spring.quartz.job.endpoint;

import java.util.List;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;

@WebService
public interface BillServiceEndpoint {
	
	@WebMethod
	public void SetBillMailed(@WebParam(name="uuid") List<String> uuidList);
}
