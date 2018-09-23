package cn.gxf.spring.quartz.job.service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.gxf.spring.quartz.job.dao.UserRecoveryDao;

@Service
public class UserRecoveryServiceImpl implements UserRecoveryService {

	@Autowired
	private UserRecoveryDao userRecoveryDao;
	
	private Logger logger = LogManager.getLogger();
	
	@Override
	public void recoverUsers(int attemptLimit) {
		if (attemptLimit < 1){
			throw new RuntimeException("recoverUsers user improper parameter");
		}

		int recoveryUserNum = userRecoveryDao.recoverUsers(attemptLimit);
		
		logger.info(recoveryUserNum + " users is recovered.");
	}

}
