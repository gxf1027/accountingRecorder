package cn.gxf.spring.quartz.job;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import cn.gxf.spring.quartz.job.service.UserRecoveryService;



@Service
public class UserRecoveryProcessor implements JobProcessor{

	
	@Autowired
	private UserRecoveryService userRecoveryService;
	
	@Override
	public int process() {
		
		userRecoveryService.recoverUsers(3);
		
		return 1;
	}
	
	
	
}
