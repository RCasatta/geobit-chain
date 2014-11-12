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

	public static final int CONNECT_TIMEOUT = 5000;
	public static final int READ_TIMEOUT = 5000;
	
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
