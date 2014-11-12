package io.geobit.common.statics;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Locale;

public class SimpleDateFormats {
	public static SimpleDateFormat dateTimeFormat= new SimpleDateFormat("MMM d yyyy HH:mm");
	public static SimpleDateFormat dateTimeSecondFormat= new SimpleDateFormat("MMM d yyyy HH:mm:ss");
	public static SimpleDateFormat dateFormat= new SimpleDateFormat("MMM d yyyy");
	public static SimpleDateFormat dateSlashFormat= new SimpleDateFormat("yyyy/MM/dd");
	public static SimpleDateFormat timeFormat= new SimpleDateFormat("HHmmss");
	public static SimpleDateFormat hourMinuteFormat= new SimpleDateFormat("HHmm");
	public static SimpleDateFormat hourFormat= new SimpleDateFormat("yyyyMM/ddHH");
	public static SimpleDateFormat dayFormat= new SimpleDateFormat("dd");
	public static SimpleDateFormat readableTimestampFormat= new SimpleDateFormat("yyyyMMddHHmmssSSS");
	public static SimpleDateFormat yyyyMMddFormat= new SimpleDateFormat("yyyyMMdd");
	public static SimpleDateFormat monthNameFormat= new SimpleDateFormat("MMM");
	
	
	public static SimpleDateFormat dateTimeSpaceFormat= new SimpleDateFormat("yyyy MM dd HHmmss");
	public static SimpleDateFormat modifiedFormat = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss zzz");
	public static DateFormat m_ISO8601Local = new SimpleDateFormat ("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.ENGLISH);
	//													  "2014-03-28T13:54:00Z"

	
	
}
