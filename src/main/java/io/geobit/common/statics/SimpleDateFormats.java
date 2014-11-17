/*
 * The MIT License (MIT)
 * 
 * Copyright (c) 2014 geobit.io 
 * 
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * 
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 * 
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */

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
	public static SimpleDateFormat logTime = new SimpleDateFormat("yyyyMMdd HH:mm:ss.SSS");
	
	public static SimpleDateFormat dateTimeSpaceFormat= new SimpleDateFormat("yyyy MM dd HHmmss");
	public static SimpleDateFormat modifiedFormat = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss zzz");
	public static DateFormat m_ISO8601Local = new SimpleDateFormat ("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.ENGLISH);
	//													  "2014-03-28T13:54:00Z"

	
	
}
