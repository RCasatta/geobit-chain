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

package io.geobit.chain.dispatchers;

import static io.geobit.common.statics.Log.log;
import io.geobit.chain.providers.addresstransactions.AddressTransactionsCheckRunnable;
import io.geobit.chain.providers.addresstransactions.AddressTransactionsFutureCallback;
import io.geobit.chain.providers.addresstransactions.AddressTransactionsProvider;
import io.geobit.chain.providers.addresstransactions.AddressTransactionsProviders;
import io.geobit.chain.providers.addresstransactions.AddressTransactionsRunnable;
import io.geobit.common.entity.AddressTransactions;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;
import com.google.common.util.concurrent.ListeningExecutorService;
import com.google.common.util.concurrent.MoreExecutors;
import com.google.common.util.concurrent.SettableFuture;

public class AddressTransactionsDispatcher implements AddressTransactionsProvider {

	private ExecutorService              executor         = Executors.newFixedThreadPool(10);
	private ListeningExecutorService moreExecutor         = MoreExecutors.listeningDecorator(executor);

	private AddressTransactionsProviders  addTxsProviders = new AddressTransactionsProviders();
	private LoadingCache<String, AddressTransactions> cache;

	private static AddressTransactionsDispatcher me;

	public static AddressTransactionsDispatcher getInstance() {
		if(me==null) {
			me=new AddressTransactionsDispatcher();
		}	
		return me;
	}

	public AddressTransactionsDispatcher() {
		super();
		cache = CacheBuilder.newBuilder()
				.maximumSize(1000000) /* keep in memory 1M balance and received */
				.build(
						new CacheLoader<String, AddressTransactions>() {
							public AddressTransactions load(String cacheAddress) {  /* never been called */
								log("BalanceAndReceivedDispatchers cache LOADING key " + cacheAddress);
								throw new RuntimeException("BalanceAndReceivedDispatchers cache address invalid");			
							}
						}
						);
	}

	@Override
	public String getPrefix() {
		return "mixed";
	}

	@Override
	public AddressTransactions getAddressTransactions(String address) {
		return getAddressTransactions(address,0);
	}

	private AddressTransactions getAddressTransactions(String address, int cont) {
		if(cont>5)
			return null;
		AddressTransactions valCache = cache.getIfPresent(address);
		AddressTransactionsProvider atp1 = addTxsProviders.take();
		AddressTransactionsProvider atp2 = addTxsProviders.takeDifferent(atp1);
		log("atp1=" + atp1 + " atp2="+atp2);
		Callable<AddressTransactions> runner1   = new AddressTransactionsRunnable(atp1, address);
		Callable<AddressTransactions> runner2   = new AddressTransactionsRunnable(atp2, address);
		final Long start=System.currentTimeMillis();
		ListenableFuture<AddressTransactions> listenableFuture1 = moreExecutor.submit(runner1);
		ListenableFuture<AddressTransactions> listenableFuture2 = moreExecutor.submit(runner2);
		SettableFuture<AddressTransactions> returned = SettableFuture.create();
		Futures.addCallback(listenableFuture1,new AddressTransactionsFutureCallback(start,  atp1, returned, addTxsProviders ));
		Futures.addCallback(listenableFuture2,new AddressTransactionsFutureCallback(start,  atp2, returned, addTxsProviders ));
		Runnable checker = new AddressTransactionsCheckRunnable(address,listenableFuture1, atp1, listenableFuture2, atp2, addTxsProviders, cache); 
		moreExecutor.execute(checker);
		try {
			AddressTransactions valRet = returned.get();
			if(valCache!=null && valCache.equals( valRet ) )
				return valRet;
			
			AddressTransactions first  = listenableFuture1.get();
			AddressTransactions second = listenableFuture2.get();
			if(first!=null && first.equals( second ) ) {
				cache.put(address, first);
				return first;
			}
		} catch (Exception e) {		
		}
		return getAddressTransactions(address,cont+1);
	}

	public AddressTransactionsProviders getAddTxsProviders() {
		return addTxsProviders;
	}

	public LoadingCache<String, AddressTransactions> getCache() {
		return cache;
	}


	
}
