package cn.gxf.spring.quartz.job.service;

import java.util.Map;

public interface RpcService {
	public void SetFinanProductsNoticeMailed(String uuid); // 以批次号和用户id作为联合主键的表
}
