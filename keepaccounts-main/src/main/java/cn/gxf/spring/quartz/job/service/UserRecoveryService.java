package cn.gxf.spring.quartz.job.service;

public interface UserRecoveryService {
	public void recoverUsers(int attemptLimit);
}
