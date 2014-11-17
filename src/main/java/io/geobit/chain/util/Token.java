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
