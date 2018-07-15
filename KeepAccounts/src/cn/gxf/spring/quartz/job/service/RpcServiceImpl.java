package cn.gxf.spring.quartz.job.service;

import cn.gxf.spring.quartz.job.dao.FinanProductsNoticeDao;

public class RpcServiceImpl implements RpcService{

	private FinanProductsNoticeDao noticeDao;
	
	@Override
	public void SetFinanProductsNoticeMailed(String uuid) {
		System.out.println("SetFinanProductsNoticeMailed..." + uuid);
		//noticeDao.setMailed(uuid);
	}

}
