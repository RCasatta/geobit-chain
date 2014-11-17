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

import java.util.Collections;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import com.google.common.base.Joiner;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.google.common.collect.EvictingQueue;

public class Log {
	private static final int NUM_ENTRIES=100;
	private static EvictingQueue<String> errors=EvictingQueue.create(NUM_ENTRIES);
	private static LoadingCache<String,String> messagesCache;
	private static final String link="<a href=\"/btc/v1/logs/%s\">%s</a>";
	
	static {
		messagesCache = CacheBuilder.newBuilder()
	       .maximumSize(NUM_ENTRIES)
	       .build(
	           new CacheLoader<String, String>() {
	             public String load(String key) throws Exception {
	               return null;
	             }
	           });
	}
	
	public static void log(String message) {
		System.out.println(message);
	}
	
	public static void error(String message) {
		System.out.println(message);
		if(message!=null) {
			String messageOneLine=message.replaceAll("\n", " ");
			String t=SimpleDateFormats.logTime.format(new Date());
			if(message.length()>120) {
				String shortMessage=messageOneLine.substring(0, 100);
				errors.add(String.format(link, t, t ) + " " + shortMessage + "..." );
				messagesCache.put(t, message);
			} else {
				errors.add(t+ " " + messageOneLine);		
			}		
		}
	}
	
	public static String getErrors() {
		List<String> lista=new LinkedList<String>();
		
		for(String current : errors) {
			lista.add(current);
		}
		Collections.reverse(lista);
		return Joiner.on("\n").join(lista);
	}
	
	public static String getError(String t) {
		return messagesCache.getIfPresent(t);
	}
}
