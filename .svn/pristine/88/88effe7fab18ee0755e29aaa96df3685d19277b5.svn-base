package io.geobit.chain.util;
import java.util.Random;


public class Token {
	
	private static final String AB    = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
	private static final Random rnd   = new Random();
	private static int length = AB.length();

	public static String randomString( int len ) 
	{
		
	   StringBuilder sb = new StringBuilder( len );
	   for( int i = 0; i < len; i++ ) {
		
		sb.append( AB.charAt( rnd.nextInt(length) ) );
	}
	   return sb.toString();
	}
	
	public static String getStandardToken() {
		return randomString(32);
	}
	
	public static String getStandardUid() {
		return randomString(8);
	}
	
	
	private static final String consonant = "bcdfghjklmnprstvwxyz";
	private static final int consonantLength=consonant.length();
	private static final String vowels ="aeiou";
	private static final int vowelsLength=vowels.length();
	
	public static String getRandomPronounceableWord() {
		StringBuffer sb=new StringBuffer();
		sb.append( Character.toUpperCase( consonant.charAt(rnd.nextInt(consonantLength) ))  );
		for(int i=1 ;i<8;i++) {
			if ((i%2)==0) {
				sb.append(consonant.charAt(rnd.nextInt(consonantLength)));
			} else {
				sb.append(vowels.charAt(rnd.nextInt(vowelsLength)));
			}
			
			if(i>5 && rnd.nextDouble()>0.8)
				break;
		}
		
		return sb.toString();
		
	}

}
