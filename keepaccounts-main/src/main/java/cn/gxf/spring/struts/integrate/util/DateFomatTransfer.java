package cn.gxf.spring.struts.integrate.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

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
}
