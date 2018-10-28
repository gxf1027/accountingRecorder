package cn.gxf.spring.cxf.util;

import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class IpControlServiceImpl implements IpControlService{

	private Logger logger = LogManager.getLogger();
	
	@Autowired
	private BlockedIpDao blockedIpDao; 
	
	@Override
	public boolean isIPBlocked(String ip) {
		
		if (false == CxfUtil.isValidIpWithStar(ip)){
			logger.info("IpControlService: not valid ip.");
			return false;
		}
		
		Map<String, String> ipBlocked = blockedIpDao.getIpBlocked();
		if (ipBlocked.size() == 0){
			logger.info("IpControlService: blocked ip table is empty.");
			return false;
		}
		
		String[] aa = ip.split("\\.");
		String testIp = "*.*.*.*";
		
		for (int i=0; i<4; i++){
			testIp = testIp.replaceFirst("\\*", aa[i]);
			if (ipBlocked.get(testIp) != null){
				logger.info("CXFWebServiceController: ip " + ip + " matches " + testIp);
				return true;
			}
		}
		
		return false;
		
	}

	@Override
	public int addIPBlocked(String ip) {
		
		if (false == CxfUtil.isValidIpWithStar(ip)){
			logger.info("IpControlService: not valid ip.");
			return 0;
		}
		
		// 是否已经存在
		if (true == isIPBlocked(ip)){
			return 0;
		}
		
		// 没有匹配上
		blockedIpDao.addIpBlocked(ip);
		logger.info("IpControlService: ip " + ip + " is added to blocked list");
		return 1;
	}

	@Override
	public void removeIPBlocked(String ip) {
		
		if (false == CxfUtil.isValidIpWithStar(ip)){
			logger.info("IpControlService: not valid ip.");
			return;
		}
		
		blockedIpDao.removeIpBlocked(ip);
	}

}
