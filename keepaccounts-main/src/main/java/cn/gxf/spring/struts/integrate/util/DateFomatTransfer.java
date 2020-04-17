package cn.gxf.spring.struts.integrate.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.joda.time.DateTime;

public class DateFomatTransfer {
	private final static String PATTERN_DEFAULT = "yyyy-MM-dd HH:mm:ss";
	private final static String PATTERN_B = "yyyyMMdd";
	
	private static ThreadLocal<DateFormat> sdf_default = new ThreadLocal<DateFormat>() {
	    @Override
	    protected DateFormat initialValue() {
	        return new SimpleDateFormat(DateFomatTransfer.PATTERN_DEFAULT);
	    }
	};
	
	private static ThreadLocal<DateFormat> sdf_compact = new ThreadLocal<DateFormat>() {
	    @Override
	    protected DateFormat initialValue() {
	        return new SimpleDateFormat(DateFomatTransfer.PATTERN_B);
	    }
	};
	
	public static String date2String(Date date){
		return sdf_default.get().format(date);
	}
	
	public static String date2CompactString(Date date){
		return sdf_compact.get().format(date);
	}
	
	public static String date2String(Date date, String pattern){
		if (null == pattern || pattern.equals("")){
			return date2String(date);
		}
		
		SimpleDateFormat sdf = new SimpleDateFormat(pattern);
		return sdf.format(date);
	}
	
	public static Date plusMinutes(Date date, int minutes) {
        if (null == date) {
            return null;
        }

        DateTime dateTime = new DateTime(date);
        dateTime = dateTime.plusMinutes(minutes);

        return dateTime.toDate();
    }
	
	
	public static Date minusMinutes(Date date, int minutes) {
        if (null == date) {
            return null;
        }

        DateTime dateTime = new DateTime(date);
        dateTime = dateTime.minusMinutes(minutes);
        
        return dateTime.toDate();
    }
	
	public static String pareTime(long millis){
        try {
            Calendar calendar = Calendar.getInstance();
            calendar.setTimeInMillis(millis);

            //SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss.SSS");
            return sdf_default.get().format(calendar.getTime());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
