package cn.gxf.spring.quartz.job.service;

import java.util.List;
import java.util.Map;

public interface BillService {
	public void SetBillMailed(List<String> uuidList);
}
