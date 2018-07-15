package cn.gxf.spring.struts.integrate.util;

import java.util.Calendar;
import java.util.Date;

public class AuxiliaryTools {
	
	static public Date getMonthFirstDate(Date date){
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
	
	static public Date getMonthLastDate(Date date){
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
}
