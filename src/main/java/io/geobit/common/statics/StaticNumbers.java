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

import java.util.concurrent.TimeUnit;

public class StaticNumbers {
	public static final int ADDRESS_QUEUE=10;
	public static final int LEVELS = 19;
	public static final long BTC_NETWORK_FEE = 10000;  /* in satoshi*/
	public static final int SCALA = 100000;
	public static final double MAX_SPEED = 13.8888;  /* 13.88 m/s = 50 Km/h */
	
	public static final int SECONDS_IN_A_WEEK = (int) TimeUnit.DAYS.toSeconds(7);
	
	public static final long SATOSHIS = 100000000;

	public static final int CONNECT_TIMEOUT = 15000;
	public static final int READ_TIMEOUT = 15000;
	
	public static final int PROXY_PORT = 8080;
	public static final int HTTP_THREADS = 20;
	public static final int BALANCE_PROVIDERS_QUEUE = 10;
	
	public static final int OF_ELAPSED_TO_CONSIDER = 3;
	public static final int OF_ELAPSED_TO_RECORD    = 1000;
//	public static final Long PENALTY_ON_NULL = 3000L;
	
	public static final int BALANCE_PROVIDER_FOR_HOST = 4;
	public static final int BLOCK_PROVIDER_FOR_HOST = 3;
	public static final double PERCENTUAL_OF_CHECK = 0.05;
	public static final int PENALTY_ROUND = 20;
}
