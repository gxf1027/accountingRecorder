package cn.gxf.spring.cxf;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.data.redis.core.StringRedisTemplate;

import cn.gxf.spring.cxf.util.BlockedIpDao;
import cn.gxf.spring.cxf.util.CxfUtil;
import cn.gxf.spring.cxf.util.IpControlService;


public class CXFWebServiceController {

	private Logger logger = LogManager.getLogger();
	
	public static final String CXF_BLOCKED_IP_SET = "cxf_blocked_ip_set";
	// 是否阻止所有的ws访问（容器关闭时自动触发设置为1）
	private int blocked = 0;
	
	private IpControlService ipController;
//	private StringRedisTemplate redisTemplate;
//	
//	private BlockedIpDao blockedIpDao;
	
	public int getBlocked() {
		return blocked;
	}

	public void setBlocked(int blocked) {
		this.blocked = blocked;
		logger.info("CXFWebServiceController: set 'blocked' to " + blocked);
	}
	
	public boolean isIPBlocked(String ip){
		return ipController.isIPBlocked(ip);
	}
	
	public void addIPBlocked(String ip){
		
		int rv = ipController.addIPBlocked(ip);
		if (1 == rv){
			logger.info("CXFWebServiceController: set " + ip +" to the blocked list. ");
		}else{
			logger.info("CXFWebServiceController: " + ip +" has existed in the blocked list. ");
		}
	}
	
	public void removeIPBlocked(String ip){
		ipController.removeIPBlocked(ip);
	}
	
	
	public IpControlService getIpController() {
		return ipController;
	}
	
	public void setIpController(IpControlService ipController) {
		this.ipController = ipController;
	}
	
//	public void addIPBlocked(String ip){
//		
//		if (false == CxfUtil.isValidIpWithStar(ip)){
//			logger.info("CXFWebServiceController: not valid ip.");
//			return;
//		}
//		
//		// 是否已经存在
//		if (true == isIPBlocked(ip)){
//			return;
//		}
//		
//		// 没有匹配上
//		redisTemplate.opsForSet().add(CXF_BLOCKED_IP_SET, ip);
//		logger.info("CXFWebServiceController: ip " + ip + " blocked");
//		
//		persistBlockedIp();
//	}
//	
//	public void removeIPBlocked(String ip){
//		redisTemplate.opsForSet().remove(CXF_BLOCKED_IP_SET, ip);
//		logger.info("CXFWebServiceController: ip " + ip + " unblocked");
//	}
//	
//	public boolean isIPBlocked(String ip){
//		
//		long count = redisTemplate.opsForSet().size(CXF_BLOCKED_IP_SET);
//		if (0 == count){
//			return false;
//		}
//		
//		if (false == CxfUtil.isValidIpWithStar(ip)){
//			logger.info("CXFWebServiceController: not valid ip.");
//			return false;
//		}
//		
//		String[] aa = ip.split("\\.");
//		
//		String testIp = "*.*.*.*";
//		for (int i=0; i<4; i++){
//			testIp = testIp.replaceFirst("\\*", aa[i]);
//			if (redisTemplate.opsForSet().isMember(CXF_BLOCKED_IP_SET, testIp)){
//				logger.info("CXFWebServiceController: ip " + ip + " matches " + testIp);
//				return true;
//			}
//		}
//		
//		return false;
//	}
	
//	public void setRedisTemplate(StringRedisTemplate redisTemplate) {
//		this.redisTemplate = redisTemplate;
//	}
	
//	public void persistBlockedIp(){
//		blockedIpDao.persistBlockedIpFromRedis();
//	}
	
//	public void setBlockedIpDao(BlockedIpDao blockedIpDao) {
//		this.blockedIpDao = blockedIpDao;
//	}
	
}
