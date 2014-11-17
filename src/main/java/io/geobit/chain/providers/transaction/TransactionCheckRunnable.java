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

package io.geobit.chain.providers.transaction;

import io.geobit.common.entity.Transaction;

import java.util.concurrent.Future;

import com.google.common.base.Objects;
import com.google.common.cache.LoadingCache;

import static io.geobit.common.statics.Log.*;

public class TransactionCheckRunnable implements Runnable {
	private String txHash;
	private Future<Transaction>  firstFuture;
	private TransactionProvider  firstProvider;
	private Future<Transaction>  secondFuture;
	private TransactionProvider  secondProvider;
	private Future<Transaction>  thirdFuture;
	private TransactionProvider  thirdProvider;
	private TransactionProviders providers;
	private LoadingCache<String,Transaction> cache;

	public TransactionCheckRunnable(String txHash, Future<Transaction> firstFuture,
			TransactionProvider firstProvider, Future<Transaction> secondFuture,
			TransactionProvider secondProvider, 
			Future<Transaction> thirdFuture, TransactionProvider thirdProvider, 
			TransactionProviders providers, LoadingCache<String,Transaction> cache) {
		super();
		this.txHash         = txHash;
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
			Transaction firstResult  = firstFuture.get();
			Transaction secondResult = secondFuture.get();
			Transaction thirdResult  = thirdFuture.get();
			String format = String.format("transaction(%s)=%s from %s "
					+ "secondResult=%s from %s " 
					+ "thirdResult=%s from %s ",
					txHash, 
					firstResult, firstProvider,
					secondResult, secondProvider,
					thirdResult, thirdProvider );
			log(format);
			String aCapo="\n%s\n";
			if(firstResult!=null && Objects.equal( firstResult , secondResult) && !Objects.equal( firstResult , thirdResult))  {
				error(thirdProvider + " gave bad result " + String.format(aCapo, thirdResult) +". correct tx(" + txHash + ") is " 
						+ String.format(aCapo,firstResult)  + " from " + firstProvider + " and " + secondProvider  );
				cache.put(txHash, firstResult);
				providers.record(thirdProvider, null);  // give penalty to provider giving bad result
			} else if(firstResult!=null && Objects.equal( firstResult , thirdResult) && !Objects.equal( firstResult , secondResult))  {
				error(secondProvider + " gave bad result " + String.format(aCapo,secondResult) +". correct tx(" + txHash + ") is " 
						+ String.format(aCapo,firstResult)  + " from " + firstProvider + " and " + thirdProvider  );
				cache.put(txHash, firstResult);
				providers.record(secondProvider, null);  // give penalty to provider giving bad result
			} else if(secondResult!=null && Objects.equal( secondResult , thirdResult) && !Objects.equal( firstResult , thirdResult))  {
				error(firstProvider + " gave bad result " + String.format(aCapo,firstResult) +". correct tx(" + txHash + ") is " 
						+ String.format(aCapo,secondResult)  + " from " + secondProvider + " and " + thirdProvider  );
				cache.put(txHash, firstResult);
				providers.record(firstProvider, null);  // give penalty to provider giving bad result
			}

		} catch (Exception e) {
			error("CheckBlockRunnable exception " + e.getMessage() );
		}
	}

}
