package cn.gxf.spring.struts.integrate.util;

import java.util.Calendar;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

public class AuxiliaryTools {

	public static final long millisec_wait_mysql_sync = 100;

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
		Timer timer = new Timer();// 实例化Timer类
		timer.schedule(new TimerTask() {
			public void run() {
				// System.out.println("退出");
				this.cancel();
			}
		}, milliseconds);// 五百毫秒
	}
}
