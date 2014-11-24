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
import static io.geobit.common.statics.Log.error;
import io.geobit.chain.providers.balance.BalanceCheckRunnable;
import io.geobit.chain.providers.balance.BalanceFutureCallback;
import io.geobit.chain.providers.balance.BalanceProviders;
import io.geobit.chain.providers.balance.BalanceRunnable;
import io.geobit.chain.providers.received.ReceivedCheckRunnable;
import io.geobit.chain.providers.received.ReceivedFutureCallback;
import io.geobit.chain.providers.received.ReceivedProviders;
import io.geobit.chain.providers.received.ReceivedRunnable;
import io.geobit.common.providers.BalanceProvider;
import io.geobit.common.providers.ReceivedProvider;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;
import com.google.common.util.concurrent.ListeningExecutorService;
import com.google.common.util.concurrent.MoreExecutors;
import com.google.common.util.concurrent.SettableFuture;

public class BalanceAndReceivedDispatcher 
implements BalanceProvider, ReceivedProvider {

	private ExecutorService              executor     = Executors.newFixedThreadPool(10);
	private ListeningExecutorService moreExecutor     = MoreExecutors.listeningDecorator(executor);

	private BalanceProviders     balanceProviders     = new BalanceProviders();
	private ReceivedProviders    receivedProviders    = new ReceivedProviders();
	private LoadingCache<String, Long> cache;
	private LoadingCache<String, Long> recentCache;

	private static BalanceAndReceivedDispatcher me;

	public static BalanceAndReceivedDispatcher getInstance() {
		if(me==null) {
			me=new BalanceAndReceivedDispatcher();
		}	
		return me;
	}

	private BalanceAndReceivedDispatcher() {
		initializeCache();
	}

	private void initializeCache() {
		recentCache = CacheBuilder.newBuilder()
				.maximumSize(10000) 
				.expireAfterWrite(10, TimeUnit.SECONDS)
				.build(
						new CacheLoader<String, Long>() {
							public Long load(String cacheAddress) {  /* never been called */
								log("BalanceAndReceivedDispatchers cache LOADING key " + cacheAddress);
								if( cacheAddress.startsWith("b/") )
									return getBalance(cacheAddress.substring(2) );
								else if( cacheAddress.startsWith("r/") )
									return getReceived(cacheAddress.substring(2) );
								else
									throw new RuntimeException("BalanceAndReceivedDispatchers cache address invalid");			
							}
						}
						);
		
		cache = CacheBuilder.newBuilder()
				.maximumSize(1000000) /* keep in memory 1M balance and received */
				.build(
						new CacheLoader<String, Long>() {
							public Long load(String cacheAddress) {  /* never been called */
								log("BalanceAndReceivedDispatchers cache LOADING key " + cacheAddress);
								if( cacheAddress.startsWith("b/") )
									return getBalance(cacheAddress.substring(2) );
								else if( cacheAddress.startsWith("r/") )
									return getReceived(cacheAddress.substring(2) );
								else
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
	public Long getBalance(String address) {
		return getBalance(address,0);
	}

	public Long getBalance(String address, int cont) {
		if(cont>5)
			return null;
		Long valCache = recentCache.getIfPresent("b/"+address);
		if(valCache!=null)
			return valCache;
		valCache = cache.getIfPresent("b/"+address);
		BalanceProvider bal1 = balanceProviders.take();
		BalanceProvider bal2 = balanceProviders.takeDifferent(bal1);
		log("bal1=" + bal1 + " bal2="+bal2);
		Callable<Long> runner1    = new BalanceRunnable(bal1, address);
		Callable<Long> runner2   = new BalanceRunnable(bal2, address);
		final Long start=System.currentTimeMillis();
		ListenableFuture<Long> listenableFuture1 = moreExecutor.submit(runner1);
		ListenableFuture<Long> listenableFuture2 = moreExecutor.submit(runner2);
		SettableFuture<Long> returned = SettableFuture.create();
		Futures.addCallback(listenableFuture1,new BalanceFutureCallback(start,  bal1, returned, balanceProviders ));
		Futures.addCallback(listenableFuture2,new BalanceFutureCallback(start,  bal2, returned, balanceProviders ));
		Runnable checker = new BalanceCheckRunnable(address,listenableFuture1, bal1, listenableFuture2, bal2, balanceProviders, cache); 
		moreExecutor.execute(checker);
		try {
			Long valRet = returned.get();
			if(valCache!=null && valCache.equals( valRet ) ) {
				recentCache.put("b/" + address, valRet);
				return valRet;
			}
			
			Long first  = listenableFuture1.get();
			Long second = listenableFuture2.get();
			if(first!=null && first.equals( second ) ) {
				cache.put("b/" + address, first);
				recentCache.put("b/" + address, first);
				return first;
			}
		} catch (Exception e) {		
			error("BalanceAndReceivedDispatcher getReceived " + e.getMessage() );
		}
		/*waiting?*/
		return getBalance(address,cont+1);
	}

	@Override
	public Long getReceived(String address) {
		return getReceived(address,0);
	}
	public Long getReceived(String address, int cont) {
		if(cont>5)
			return null;
		Long valCache = recentCache.getIfPresent("r/"+address);
		if(valCache!=null)
			return valCache;
		valCache = cache.getIfPresent("r/"+address);
		ReceivedProvider rec1 = receivedProviders.take();
		ReceivedProvider rec2 = receivedProviders.takeDifferent(rec1);
		log("rec1=" + rec1 + " rec2="+rec2);
		
		Callable<Long> runner1 = new ReceivedRunnable(rec1, address);
		Callable<Long> runner2 = new ReceivedRunnable(rec2, address);
		final Long start=System.currentTimeMillis();
		ListenableFuture<Long> listenableFuture1 = moreExecutor.submit( runner1 );
		ListenableFuture<Long> listenableFuture2 = moreExecutor.submit(runner2);
		SettableFuture<Long> returned = SettableFuture.create();
		Futures.addCallback(listenableFuture1,new ReceivedFutureCallback(start,  rec1, returned, receivedProviders ));
		Futures.addCallback(listenableFuture2,new ReceivedFutureCallback(start,  rec2, returned, receivedProviders ));
		Runnable checker = new ReceivedCheckRunnable(address,listenableFuture1, rec1, listenableFuture2, rec2, receivedProviders, cache); 
		moreExecutor.execute(checker);
		
		Long valRet;
		try {
			valRet = returned.get();  /* return the faster */
			if(valCache!=null && valCache.equals( valRet ) ) {
				recentCache.put("r/" + address, valRet);
				return valRet;
			}
			
			Long first  = listenableFuture1.get();
			Long second = listenableFuture2.get();
			if(first!=null && first.equals( second ) ) {
				cache.put("r/" + address, first);
				recentCache.put("r/" + address, first);
				return first;
			}
			if(first != null)	    cache.put("r/" + address, first);
			else if(second!= null)	cache.put("r/" + address, second);
			
		} catch (Exception e) {
			error("BalanceAndReceivedDispatcher getReceived " + e.getMessage() );
		}
		return getBalance(address,cont+1);
	}



	public BalanceProviders getBalanceProviders() {
		return balanceProviders;
	}

	public ReceivedProviders getReceivedProviders() {
		return receivedProviders;
	}

	public LoadingCache<String, Long> getCache() {
		return cache;
	}

	
	
}
