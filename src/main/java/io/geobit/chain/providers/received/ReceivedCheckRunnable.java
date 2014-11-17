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

package io.geobit.chain.providers.received;

import java.util.concurrent.Future;

import com.google.common.base.Objects;
import com.google.common.cache.LoadingCache;
import static io.geobit.common.statics.Log.*;
public class ReceivedCheckRunnable implements Runnable {
	private String address;
	private Future<Long> firstFuture;
	private ReceivedProvider firstProvider;
	private Future<Long> secondFuture;
	private ReceivedProvider secondProvider;
	private ReceivedProviders providers;
	private LoadingCache<String, Long> receivedCache;



	public ReceivedCheckRunnable(String address, Future<Long> firstFuture,
			ReceivedProvider firstProvider, Future<Long> secondFuture,
			ReceivedProvider secondProvider, 
			ReceivedProviders providers, LoadingCache<String, Long> receivedCache) {
		super();
		this.address        = address;
		this.firstFuture    = firstFuture;
		this.firstProvider  = firstProvider;
		this.secondFuture   = secondFuture;
		this.secondProvider = secondProvider;
		this.providers      = providers;
		this.receivedCache   = receivedCache;
	}

	@Override
	public void run() {
		try {
			log("started check received");
			Long firstResult  = firstFuture.get();
			Long secondResult = secondFuture.get();
			String format = String.format("balance(%s)=%s from %s "
					+ "secondResult=%s from %s ", 
					address, 
					firstResult, firstProvider,
					secondResult, secondProvider );
			log(format);
			if(!Objects.equal( firstResult , secondResult))  {
				
				/* HANDLING DIFFERENCE */
				ReceivedProvider thirdProvider=providers.takeDifferent(firstProvider,secondProvider);
				
				Long thirdResult=thirdProvider.getReceived(address);
				if( Objects.equal( firstResult , thirdResult) ) {
					error(secondProvider + " gave bad result " + secondResult +". correct received(" + address + ") is " + firstResult + " from " + firstProvider  + " and " + thirdProvider  );
					receivedCache.put(address, firstResult);
					providers.record(secondProvider, null);
				} else if (Objects.equal( secondResult , thirdResult)) {
					error(firstProvider  + " gave bad result " + firstResult +". correct received(" + address + ") is " + secondResult + " from " + secondProvider + " and " + thirdProvider  );
					receivedCache.put(address, secondResult);
					providers.record(firstProvider, null);
				} else {
					error("THREE DIFFERENT RESULT!!!! for " + address + " are " + firstResult + " " + secondResult + " " + thirdResult);
				}
				
			}
			
		} catch (Exception e) {
			error("CheckReceivedRunnable exception " + e.getMessage() );
		}
	}


}
