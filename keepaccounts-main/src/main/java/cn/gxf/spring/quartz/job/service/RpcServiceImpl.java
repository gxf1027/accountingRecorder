package cn.gxf.spring.quartz.job.service;

import org.springframework.beans.factory.annotation.Autowired;

import cn.gxf.spring.quartz.job.dao.FinanProductsNoticeDao;

public class RpcServiceImpl implements RpcService{

	@Autowired
	private FinanProductsNoticeDao noticeDao;
	
	@Override
	public void setFinanProductsNoticeMailed(String uuid) {
		System.out.println("SetFinanProductsNoticeMailed..." + uuid);
		noticeDao.setMailed(uuid);
	}

}
