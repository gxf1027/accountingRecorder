package cn.gxf.spring.cxf.util;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;


public class CxfUtil {
	
	private static final Logger logger = LogManager.getLogger();
	//private static final Logger logger = Logger.getLogger(CxfUtil.class);
	
	public static String getUsrIPAddr(HttpServletRequest request) {        
		String ip = "";        
		//1.���ȿ����з����������������д���ͨ����x-forwarded-for����ȡ��ʵip��ַ        
		ip = request.getHeader("x-forwarded-for");        
		//2.���squid.conf�������ļ�forwarded_for��Ĭ����off����X-Forwarded-For��unknown��������Proxy-Client-IP��WL-Proxy-Client-IP��ȡ        
		if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {            
			ip = request.getHeader("Proxy-Client-IP");        }        
		if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {            
			ip = request.getHeader("WL-Proxy-Client-IP");        }        
		//3.�����û�д���������ֱ����request.getRemoteAddr()��ȡip        
		if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {            
			ip = request.getRemoteAddr();        }        
		//4.���ͨ���༶�����������ܲ������ip�����е�һ����unknown��IPΪ�ͻ�����ʵIP��IP����','�ָ        
		if(ip != null && ip.split(",").length > 1){            
			ip = (ip.split(","))[0];        }                
		//5.����Ƿ��������ط��ʣ���Ҫ����������ȡ������ʵip        
		if("127.0.0.1".equals(ip)) {            
			try {                
				ip = InetAddress.getLocalHost().getHostAddress();            
				} catch (UnknownHostException e) {               
					logger.error(e.getMessage(),e);//��ȡ������(����)ip��Ϣʧ��               
					return "";            
				}        
			}
		
		// 6.У��ip�ĺϷ��ԣ����Ϸ�����""        
		if(!isValidIp(ip)) {            
			return "The ip is invalid.";        
		}else {            
			return ip;        
		}
		//        return ip;    
	}
	
	public static boolean isValidIp(String ipAddress) {
		boolean retVal = false;
		try {
			if (ipAddress != null && !"".equals(ipAddress)) {
				Pattern pattern = Pattern.compile(
						"([1-9]|[1-9]\\d|1\\d{2}|2[0-4]\\d|25[0-5])(\\.(\\d|[1-9]\\d|1\\d{2}|2[0-4]\\d|25[0-5])){3}");
				retVal = pattern.matcher(ipAddress).matches();
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return retVal;
	}
	
	public static boolean isValidIpWithStar(String ipAddress) {
		boolean retVal = false;
		try {
			if (ipAddress != null && !"".equals(ipAddress)) {
//				Pattern pattern = Pattern.compile(
//						"([1-9]|[1-9]\\d|1\\d{2}|2[0-4]\\d|25[0-5])(\\.(\\d|[1-9]\\d|1\\d{2}|2[0-4]\\d|25[0-5])){3}");
				Pattern pattern = Pattern.compile(
				"^(1\\d{2}|2[0-4]\\d|25[0-5]|[1-9]\\d|[1-9]|\\*{1})\\."
				+"(1\\d{2}|2[0-4]\\d|25[0-5]|[1-9]\\d|\\d|\\*{1})\\."
				+"(1\\d{2}|2[0-4]\\d|25[0-5]|[1-9]\\d|\\d|\\*{1})\\."
				+"(1\\d{2}|2[0-4]\\d|25[0-5]|[1-9]\\d|\\d|\\*{1})$");
				retVal = pattern.matcher(ipAddress).matches();
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return retVal;
	}
	
	public static void main(String[] args) {
		System.out.println(isValidIpWithStar("*.*.*.*"));
		System.out.println(isValidIpWithStar("*.*.**.*"));
		System.out.println(isValidIpWithStar("192.*.*.*"));
		System.out.println(isValidIpWithStar("192.168.0.77"));
		System.out.println(isValidIpWithStar("192.168.0"));
		System.out.println("");
		System.out.println(isValidIp("*.*.*.*"));
		System.out.println(isValidIp("*.*.**.*"));
		System.out.println(isValidIp("192.*.*.*"));
		System.out.println(isValidIp("192.168.0.77"));
		System.out.println(isValidIp("192.168.0"));
	}
}
