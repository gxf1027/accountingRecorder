package cn.gxf.spring.quartz.job.dao;

import org.mybatis.spring.annotation.MapperScan;

@MapperScan
public interface UserRecoveryDao {
	public int recoverUsers(int attemptsLimit);
}
