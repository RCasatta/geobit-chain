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

package io.geobit.chain.providers;

import io.geobit.chain.util.Performance;
import io.geobit.common.providers.Provider;

import java.util.LinkedList;
import java.util.Random;

public abstract class Providers<T extends Provider> {

	protected Performance<T> perf = new Performance<T>();
	protected LinkedList<T>  list = new LinkedList<T>();
	protected Integer       total = 0;
	private   Random       random = new Random();
	

	public synchronized T take() {
		T t = list.get(random.nextInt(total));
		
		if( perf.isBlacklisted(t) ) {
			return take();
		}
		return t;
	}
	
	public synchronized T takeDifferent(T other) {
		T t = take();
		
		if(other==t) {
			return takeDifferent(other);
		}
		return t;
	}
	
	public synchronized T takeDifferent(T other , T other2) {
		T t = takeDifferent(other);
		
		if(t==other2) {
			return takeDifferent(other,other2);
		}
		return t;
	}

	public synchronized void add(T provider) {
		list.add(provider);
		perf.add(provider);
		total++;
	}

	public void record(T provider, Long millis) {
		perf.record(provider, millis);	
		

	}

	public String getElapsedOf(T provider) {
		return perf.getElapsedOf(provider);
	}


	public Performance<T> getTimers() {
		return perf;
	}

	@Override
	public String toString() {
		return "Provider [timers=" + perf + ", list=" + list.size() + " " + list + "]";
	}



}
