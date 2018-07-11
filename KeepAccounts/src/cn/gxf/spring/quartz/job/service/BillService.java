package cn.gxf.spring.quartz.job.service;

import java.util.List;

public interface BillService {
	public void SetBillMailed(List<String> uuidList);
}
