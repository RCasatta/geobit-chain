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

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class StaticLists {
	public static final List<Character> levels = Arrays.asList('1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'i', 'J');
	public static final List<Character> ns = Arrays.asList('N', 'S');
	public static final List<Character> ew = Arrays.asList('E', 'W');
	
	public static final List<String> geobitPlaceHolders            = Arrays.asList("_TITLE_","_GEOID_","_LAT_","_LNG_","_LEVEL_","_GEOBITADDRESS_","_GEOBITADDRESSELLIPS_",
																			"_BALANCE_","_LIST_", "_COLOR_","_PAGE_URL_","_GEOBIT_DNS_",
																			"_GEOBIT_HTTP_","_YOUARE_","_ADDRESS_","_ADDRESSHR_","_HEADER_","_FOOTER_",
																			"_DEFAULTADDRESSELLIPS_","_DEFAULTADDRESS_");
	
	public static final List<String> transPlaceHolders             = Arrays.asList("_TITLE_","_TRANSACTIONFROM_","_TRANSACTIONFROMADDRESS_",
																				"_TRANSID_","_GEOBITADDRESS_","_PASSWORD_PROTECTION_",
																				"_CAPTCHA_","_GEOBIT_URL_","_MESSAGE_");
	
	public static final List<String> geobitPrintablePlaceHolders   = Arrays.asList("_TITLE_","_HEAD_TITLE_","_GEOID_","_LEVEL_","_GEOBITADDRESS_",
																					"_BALANCE_","_SUMMARY_", "_COLOR_","_PAGE_URL_ENCODED_",
																					"_PAGE_URL_","_NOW_");
	
	public static final List<String> nearestPlaceHoldersPatterns   = Arrays.asList("_MYPOSITION_","_TABLE_","_HEADER_","_FOOTER_");
	
	public static final List<String> greatestPlaceHoldersPatterns  = Arrays.asList("_MYPOSITION_","_TABLE_","_HEADER_","_FOOTER_");
	
	public static final List<String> errorPlaceHoldersPatterns      = Arrays.asList("_TITLE_","_MESSAGE_","_HEADER_","_FOOTER_");
	
	public static final List<String> topPlaceHoldersPatterns        = Arrays.asList("_TABLE_TOTAL_","_TABLE_COLLECTION_","_HEADER_","_FOOTER_","_TABLE_MONTHS_","_TABLE_USERS_","_TABLE_LINKS_");
	
	public static final List<String> homePlaceHoldersPatterns       = Arrays.asList("_HEADER_","_FOOTER_","_TOTAL_AVAILABLE_","_TOTAL_COLLECTED_","_TOTAL_SENT_","_FB_APP_ID_","_GOOGLE_APP_ID_");
	
	public static final List<String> myPositionPlaceHoldersPatterns = Arrays.asList("_HEADER_","_FOOTER_","_MYPOSITION_","_TABLE_NEAREST_","_TABLE_GREATEST_","_SERVICE_TIME_");

	public static final List<String> mapPlaceHoldersPatterns        = Arrays.asList("_HEADER_","_FOOTER_");
	
	public static final List<String> congratulationsPlaceHoldersPatterns        = Arrays.asList("_HEADER_", "_VALUE_","_GEOBITURL_","_SPONSOR_","_DESTADDRESS_", "_SHARETEXT_","_FOOTER_");
	
	public static final List<String> notCollectedPlaceHoldersPatterns        = Arrays.asList("_HEADER_", "_GEOBITURL_","_MESSAGE_","_FOOTER_");
	
	public static final List<String> loginPlaceHoldersPatterns        = Arrays.asList("_HEADER_","_FOOTER_","_FB_APP_ID_","_GOOGLE_APP_ID_","_WALLETS_LIST_","_LOGINOUT_BUTTONS_","_LOGIN_MESSAGE_","_MAILINGLIST_MESSAGE_","_AIRDROPS_LIST_","_MYLINKS_LIST_","_USERNAME_");
	
	public static final List<String> airdropPlaceHoldersPatterns        = Arrays.asList("_HEADER_","_FOOTER_","_CURRENT_PAGE_","_AIRDROP_ID_","_WHERE_LABEL_", "_BTCADDRESS_", "_HOWMANY_LABEL_" , "_WHO_LABEL_" , "_MESSAGE_LABEL_","_PASSWORD_LABEL_","_MAP_IMAGE_","_BTCADDRESSELLIPS_","_MINIMAL_AMOUNT_","_AIRDROP_HELP_","_EXECUTED_LIST_","_CURRENT_BALANCE_","_FAVOURITE_LABEL_");
	
//	public static final List<String> levels                      = Arrays.asList();
	
	public static final List<String> stringsToLoad = Arrays.asList("index.html","map.html","nearest.html","greatest.html","top.html",
			"googleee1d3e49970553f6.html","presentation.html","congratulations.html","notcollected.html","geobit.js","g/geobit.html","g/geobit2.html","geobit.css",
			"t/transaction.html", "p/print.html", "carousel.css","error-template.html","header.html","footer.html",
			"login/facebook.html","login/google.html","me.html","addwallet.html","login-buttons.html","logo.svg","link.html","airdrop.html","airdropcreate.html","news.html" , "faqs.html");
	
	public static final List<String> bytesToLoad   = Arrays.asList("favicon.ico","images/logo32.png","images/logo16.png","images/logo24.png","images/logo64.png",
			"images/logo.png","images/here.png","images/ajax-loader.gif","images/ajax-loader-trans.gif","images/plus1.png","images/follow.png",
			"images/like.png","images/sfondogeobit2.jpg","images/getbitcoins2.jpg","images/sendbitcoins2.jpg","images/bannercashclamber.jpg",
			"images/sfondo.jpg","images/world.jpg","images/geobit-video-start.jpg");
	
	public static final List<Long> times = Arrays.asList(
			TimeUnit.DAYS.toMillis(365),
			TimeUnit.DAYS.toMillis(30),
			TimeUnit.DAYS.toMillis(1),
			TimeUnit.HOURS.toMillis(1),
			TimeUnit.MINUTES.toMillis(1),
			TimeUnit.SECONDS.toMillis(1) );
	public static final List<String> timesString = Arrays.asList("year","month","day","hour","minute","second");
	
	
	

}
