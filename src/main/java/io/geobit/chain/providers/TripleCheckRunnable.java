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

import io.geobit.chain.providers.balance.BalanceProvider;
import io.geobit.chain.providers.balance.BalanceProviders;

import java.util.concurrent.Future;

import com.google.common.base.Objects;
import com.google.common.cache.LoadingCache;
import com.google.common.util.concurrent.ListenableFuture;
import static io.geobit.common.statics.Log.*;
public class TripleCheckRunnable<K,V> implements Runnable {
	private K chiave;
	private Future<V>  firstFuture;
	private Provider  firstProvider;
	private Future<V>  secondFuture;
	private Provider  secondProvider;
	private Future<V>  thirdFuture;
	private Provider  thirdProvider;
	private Providers<Provider> providers;
	private GetPut<K,V> cache;

	public TripleCheckRunnable(K chiave, 
			ListenableFuture<V> firstFuture,  Provider firstProvider, 
			ListenableFuture<V> secondFuture, Provider secondProvider, 
			ListenableFuture<V> thirdFuture,  Provider thirdProvider, 
			Providers<Provider> providers, GetPut<K,V> cache) {
		super();
		this.chiave         = chiave;
		this.firstFuture    = firstFuture;
		this.firstProvider  = firstProvider;
		this.secondFuture   = secondFuture;
		this.secondProvider = secondProvider;
		this.thirdFuture    = thirdFuture;
		this.thirdProvider  = thirdProvider;
		this.providers      = providers;
		this.cache          = cache;
	}




	@Override
	public void run() {
		try {
			log("started check block");
			V firstResult  = firstFuture.get();
			V secondResult = secondFuture.get();
			V thirdResult  = thirdFuture.get();
			String format = String.format(firstResult.getClass().getName() + "(%d)=%s from %s "
					+ "secondResult=%s from %s " 
					+ "thirdResult=%s from %s ",
					chiave, 
					firstResult, firstProvider,
					secondResult, secondProvider,
					thirdResult, thirdProvider );
			log(format);
			if(firstResult!=null && Objects.equal( firstResult , secondResult) && !Objects.equal( firstResult , thirdResult))  {
				error(thirdProvider + " gave bad result " + thirdResult +". correct (" + chiave + ") is " 
						+ firstResult  + " from " + firstProvider + " and " + secondProvider  );
				cache.put(chiave, firstResult); 
				providers.record(thirdProvider, null);  // give penalty to provider giving bad result
			} else if(firstResult!=null && Objects.equal( firstResult , thirdResult) && !Objects.equal( firstResult , secondResult))  {
				error(secondProvider + " gave bad result " + secondResult +". correct block(" + chiave + ") is " 
						+ firstResult  + " from " + firstProvider + " and " + thirdProvider  );
				cache.put(chiave, firstResult);
				providers.record(secondProvider, null);  // give penalty to provider giving bad result
			} else if(secondResult!=null && Objects.equal( secondResult , thirdResult) && !Objects.equal( firstResult , thirdResult))  {
				error(firstProvider + " gave bad result " + firstResult +". correct block(" + chiave + ") is " 
						+ secondResult  + " from " + secondProvider + " and " + thirdProvider  );
				cache.put(chiave, firstResult);
				providers.record(firstProvider, null);  // give penalty to provider giving bad result
			}

		} catch (Exception e) {
			error("CheckBlockRunnable exception " + e.getMessage() );
		}
	}

}
