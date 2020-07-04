package cn.gxf.spring.struts.integrate.util;

import java.util.Calendar;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class AuxiliaryTools {
	
    private static Logger logger = LogManager.getLogger();

	public static final long millisec_wait_mysql_sync = 20;

	static public Date getMonthFirstDate(Date date) {
		Calendar cale = null;
		cale = Calendar.getInstance();
		cale.setTime(date);

		cale.add(Calendar.MONTH, 0);
		cale.set(Calendar.DAY_OF_MONTH, 1);
		cale.set(Calendar.HOUR_OF_DAY, 0);
		cale.set(Calendar.MINUTE, 0);
		cale.set(Calendar.SECOND, 0);

		return cale.getTime();
	}

	static public Date getMonthLastDate(Date date) {
		Calendar cale = null;
		cale = Calendar.getInstance();
		cale.setTime(date);

		cale.add(Calendar.MONTH, 1);
		cale.set(Calendar.DAY_OF_MONTH, 0);
		cale.set(Calendar.HOUR_OF_DAY, 0);
		cale.set(Calendar.MINUTE, 0);
		cale.set(Calendar.SECOND, 0);
		return cale.getTime();
	}

	static public void delay(long milliseconds) {
//		Timer timer = new Timer();// 实例化Timer类
//		timer.schedule(new TimerTask() {
//			public void run() {
//				// System.out.println("退出");
//				this.cancel();
//			}
//		}, milliseconds);// 五百毫秒
		
		try {
			Thread.currentThread().sleep(milliseconds); //毫秒
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			logger.error(e.getMessage());
			Thread.currentThread().interrupt(); // "InterruptedException" should not be ignored
		}
	}
	
	public static void main(String[] args) {
		
		long start  = System.currentTimeMillis();
		AuxiliaryTools.delay(20);
		
		System.out.println(System.currentTimeMillis() - start);
	}
}
