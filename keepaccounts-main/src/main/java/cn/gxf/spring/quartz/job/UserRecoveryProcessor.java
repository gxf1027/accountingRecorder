package cn.gxf.spring.quartz.job;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import cn.gxf.spring.quartz.job.service.UserRecoveryService;
import cn.gxf.spring.struts.integrate.security.MyUserDetailService;


@Service
public class UserRecoveryProcessor implements JobProcessor{

	
	@Autowired
	private UserRecoveryService userRecoveryService;
	
	@Override
	public int process() {
		
		userRecoveryService.recoverUsers(MyUserDetailService.attemptsLimit);
		
		return 1;
	}
	
	
	
}
