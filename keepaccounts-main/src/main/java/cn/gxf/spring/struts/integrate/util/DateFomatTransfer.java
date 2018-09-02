package cn.gxf.spring.struts.integrate.util;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateFomatTransfer {
	private final static String PATTERN_DEFAULT = "yyyy-MM-dd HH:mm:ss";
	private final static String PATTERN_B = "yyyyMMdd";
	
	private static SimpleDateFormat sdf_default = new SimpleDateFormat(DateFomatTransfer.PATTERN_DEFAULT);
	private static SimpleDateFormat sdf_compact = new SimpleDateFormat(DateFomatTransfer.PATTERN_B);
	
	public static String date2String(Date date){
		return sdf_default.format(date);
	}
	
	public static String date2CompactString(Date date){
		return sdf_compact.format(date);
	}
	
	public static String date2String(Date date, String pattern){
		if (null == pattern || pattern.equals("")){
			return date2String(date);
		}
		
		SimpleDateFormat sdf = new SimpleDateFormat(pattern);
		return sdf.format(date);
	}
}
