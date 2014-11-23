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

package io.geobit.chain.providers.addresstransactions;

import io.geobit.common.entity.AddressTransactions;
import io.geobit.common.providers.AddressTransactionsProvider;

import java.util.concurrent.Future;

import com.google.common.base.Objects;
import com.google.common.cache.LoadingCache;

import static io.geobit.common.statics.Log.*;

public class AddressTransactionsCheckRunnable implements Runnable {
	private String address;
	private Future<AddressTransactions> firstFuture;
	private AddressTransactionsProvider firstProvider;
	private Future<AddressTransactions> secondFuture;
	private AddressTransactionsProvider secondProvider;
	private AddressTransactionsProviders providers;
	private LoadingCache<String, AddressTransactions> addressTransactionsCache;



	public AddressTransactionsCheckRunnable(String address, Future<AddressTransactions> firstFuture,
			AddressTransactionsProvider firstProvider, Future<AddressTransactions> secondFuture,
			AddressTransactionsProvider secondProvider, 
			AddressTransactionsProviders providers, LoadingCache<String, AddressTransactions> addressTransactionsCache) {
		super();
		this.address        = address;
		this.firstFuture    = firstFuture;
		this.firstProvider  = firstProvider;
		this.secondFuture   = secondFuture;
		this.secondProvider = secondProvider;
		this.providers      = providers;
		this.addressTransactionsCache   = addressTransactionsCache;
	}

	@Override
	public void run() {
		AddressTransactionsProvider thirdProvider=null;
		try {
			log("started check received");
			AddressTransactions firstResult  = firstFuture.get();
			AddressTransactions secondResult = secondFuture.get();
			String format = String.format("balance(%s)=%s from %s "
					+ "secondResult=%s from %s ", 
					address, 
					firstResult, firstProvider,
					secondResult, secondProvider );
			log(format);
			if(!Objects.equal( firstResult , secondResult))  {
				
				/* HANDLING DIFFERENCE */
				AddressTransactionsProvider current=null;
				do { /* need to take a provider different from the first and the second */
					current=providers.takeDifferent(firstProvider);
				} while( secondProvider==current );
				thirdProvider=current;
				String aCapo="\n%s\n";
				AddressTransactions thirdResult=thirdProvider.getAddressTransactions(address);
				if( Objects.equal( firstResult , thirdResult) ) {
					error(secondProvider + " gave bad result " +  String.format(aCapo, secondResult) +". correct addTxs(" + address + ") is " + "\n" + String.format(aCapo,firstResult) + " from " + firstProvider  + " and " + thirdProvider  );
					addressTransactionsCache.put(address, firstResult);
					providers.record(secondProvider, null);
				} else if (Objects.equal( secondResult , thirdResult)) {
					error(firstProvider  + " gave bad result " + "\n" + String.format(aCapo,firstResult) +". correct addTxs(" + address + ") is " + "\n" + String.format(aCapo,secondResult) + " from " + secondProvider + " and " + thirdProvider  );
					addressTransactionsCache.put(address, secondResult);
					providers.record(firstProvider, null);
				} else {
					error("THREE DIFFERENT RESULT!!!! for " + address + " are " + "\n" + String.format(aCapo,firstResult) + " " + "\n" + String.format(aCapo,secondResult) + " " + "\n" + String.format(aCapo,thirdResult));
				}
				
			}
			
		} catch (Exception e) {
			error("CheckAddressTransactionsRunnable exception " + e.getMessage() );
		}
	}


}
