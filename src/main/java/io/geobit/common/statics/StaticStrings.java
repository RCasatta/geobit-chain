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

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;

public class StaticStrings {

	public static final String USER_AGENT="Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/536.11 (KHTML, like Gecko) Chrome/20.0.1132.57 Safari/536.11";


	public static final String BTC_ADDRESS    = "btc-address";
	public static final String GEOBIT_ID      = "geobit-id";
	public static final String PRIVATE_KEY    = "private-key"; 

	public static final String ADDR_MESSAGE        = "addrmessage";
	public static final String ADDR_PSWD           = "addrpswd";
	public static final String ADDR_ALIAS          = "addralias";
//	public static final String ADDR_SIGNED_MESSAGE = "addr-signed-message";
//	public static final String ADDR_SENDER         = "addr-sender";
	
	public static final String MAILING_LIST        = "mailing-list";
	public static final String COOKIE_MY_POS       = "myposition";
	public static final String COOKIE_MY_ADDR      = "getaddress";

	public static final String Q_ADDRESSBALANCE = "/q/addressbalance/";
	public static final String Q_GETRECEIVED    = "/q/getreceivedbyaddress/";

	public static final String API_PROTOCOL        = "http://";
	public static final String SECURE_API_PROTOCOL = "https://";

	public static final String AREA_4  = "[street_address]";
	public static final String AREA_3  = "[administrative_area_level_3, political]";
	public static final String AREA_2  = "[administrative_area_level_2, political]";
	public static final String AREA_1  = "[administrative_area_level_1, political]";
	public static final String AREA_0  = "[country, political]";
	public static final String AREA_0B = "[locality, political]";

	public static final String PROXY_PASSWORD = "Olanda14";
	public static final String PROXY_USER = "itzrcca";

	public static final String PROXY_IP = "10.243.241.41";
	public static final String MYADDRESS = "1DiWBDy3eejMtUUPAvVLqWmPVsecbqbrSU";
	public static final String COOKIE_ACCESS_TOKEN = "access-token";


	public static final Object UNDER_CONSTRUCTION_JSON = "{\"state\":\"under development\"}";
	
	public static final String[] MONTH_NAMES = {"January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"};

	
	public static String encode(String toEncode) {
		try {
			return URLEncoder.encode(toEncode, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return "";

	}

	
	public static String decode(String toDecode) {
		try {
			return URLDecoder.decode(toDecode, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return "";

	}

	public static String breakka(String toBreak) {
		return toBreak.replaceAll(" ","&nbsp;").replaceAll("<br>"," ");
	}

	public static String convertSatoshisToHtml(Long satoshis) {
		return convertSatoshisToHtml(satoshis, "class=\"text-muted\"");
	}

	public static String convertSatoshisToHtml(Long satoshis, String classDecimal) {
		String[] humanArray = convertSatoshisToHumanArray(satoshis);
		String unit="";
		if("u".equals(humanArray[2]))
			unit=" <small>&nbsp;&micro;</small><i class=\"fa fa-bitcoin baseline\"></i>";
		else if ("".equals(humanArray[2]))
			unit=" <small>&nbsp;&nbsp;&nbsp;</small><i class=\"fa fa-bitcoin baseline\"></i>";
		else if ("m".equals(humanArray[2]))
			unit=" <small>m</small><i class=\"fa fa-bitcoin baseline\"></i>";

		return humanArray[0] + "<small " + classDecimal+  ">." +humanArray[1]
				+ "</small> " + unit;
	}

	public static String convertSatoshisToText(Long satoshis) {
		String[] humanArray = convertSatoshisToHumanArray(satoshis);
		return humanArray[0] + "." +humanArray[1] + " " + humanArray[2] + "BTC";
	}

	public static String[] convertSatoshisToHumanArray(long satoshis) {

		long microbit=satoshis/100;

		if(microbit<1000) {
			long microbitDecimal=satoshis%100;
			String[] val = { Long.toString(microbit),
					(microbitDecimal<10 ? "0" + microbitDecimal : Long.toString(microbitDecimal) ) ,
			"u" };
			return  val;
		}
		long millibit=microbit/1000;

		if(millibit<1000) {
			long millibitDecimal=(microbit%1000)/10;
			String[] val = { Long.toString(millibit),
					(millibitDecimal<10 ? "0" + millibitDecimal : Long.toString(millibitDecimal) ) ,
			"m" };
			return  val;
		}
		long btc=millibit/1000;
		long btcDecimal=(millibit%1000)/10;
		String[] val = { Long.toString(btc),
				(btcDecimal<10 ? "0" + btcDecimal : Long.toString(btcDecimal) ) ,
		"" };
		return  val;}


	public static String buildHtmlEntityCode(String input) {
		StringBuffer output = new StringBuffer(input.length() * 2);

		int len = input.length();
		int code,code1,code2,code3,code4;
		char ch;

		for( int i=0; i<len; ) {
			code1 = input.codePointAt(i);
			if( code1 >> 3 == 30 ){
				code2 = input.codePointAt(i + 1);
				code3 = input.codePointAt(i + 2);
				code4 = input.codePointAt(i + 3);
				code = ((code1 & 7) << 18) | ((code2 & 63) << 12) | ((code3 & 63) << 6) | ( code4 & 63 );
				i += 4;
				output.append("&#" + code + ";");
			}
			else if( code1 >> 4 == 14 ){
				code2 = input.codePointAt(i + 1);
				code3 = input.codePointAt(i + 2);
				code = ((code1 & 15) << 12) | ((code2 & 63) << 6) | ( code3 & 63 );
				i += 3;
				output.append("&#" + code + ";");
			}
			else if( code1 >> 5 == 6 ){
				code2 = input.codePointAt(i + 1);
				code = ((code1 & 31) << 6) | ( code2 & 63 );
				i += 2;
				output.append("&#" + code + ";");
			}
			else {
				code = code1;
				i += 1;

				ch = (char)code;
				if (ch >= 'a' && ch <= 'z' || ch >= 'A' && ch <= 'Z' || ch >= '0' && ch <= '9') {
					output.append(ch);
				}
				else {
					output.append("&#" + code + ";");
				}
			}
		}

		return output.toString();
	}

	public static String convertSatoshisToHtml(String val) {

		return convertSatoshisToHtml(Long.valueOf(val));
	}


	public static String unescape(String s) {
		int i=0, len=s.length();
		char c;
		StringBuffer sb = new StringBuffer(len);
		while (i < len) {
			c = s.charAt(i++);
			if (c == '\\') {
				if (i < len) {
					c = s.charAt(i++);
					if (c == 'u') {
						// TODO: check that 4 more chars exist and are all hex digits
						c = (char) Integer.parseInt(s.substring(i, i+4), 16);
						i += 4;
					} // add other cases here as desired...
				}
			} // fall through: \ escapes itself, quotes any character but u
			sb.append(c);
		}
		return sb.toString();
	}
	public static String ellipsizeTextHtml(String add) {
		return ellipsizeTextHtml(add,null);
	}
	public static String ellipsizeTextHtml(String addr, String string) {
		int i=0;
		StringBuffer addrBreak = new StringBuffer();
		int length = addr.length();
		while(i < length) {
			int j = i+5;
			addrBreak.append(addr.substring(i, Math.min(j, length )));
			if( j<length)
				addrBreak.append("&shy;");
			i=j;
		}

		StringBuffer result=new StringBuffer();
		result.append("<div class=\"container-lword\"><div class=\"content-lword\">" );
		if(string!=null && !"".equals(string))
			result.append(string);
		result.append(addr);
        result.append("</div><div class=\"spacer-lword\">");
        result.append(addrBreak);
        result.append("</div><span>&nbsp;</span></div>");
    
		return result.toString();
	}  


	
}
