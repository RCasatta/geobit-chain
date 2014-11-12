package io.geobit.chain.dispatchers;

import static io.geobit.common.statics.Log.log;
import io.geobit.chain.providers.balance.BalanceCheckRunnable;
import io.geobit.chain.providers.balance.BalanceProvider;
import io.geobit.chain.providers.balance.BalanceProviders;
import io.geobit.chain.providers.balance.BalanceRunnable;
import io.geobit.chain.providers.balance.BalanceFutureCallback;
import io.geobit.chain.providers.received.ReceivedFutureCallback;
import io.geobit.chain.providers.received.ReceivedProvider;
import io.geobit.chain.providers.received.ReceivedProviders;
import io.geobit.chain.providers.received.ReceivedRunnable;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
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

public class BalanceAndReceivedDispatchers 
implements BalanceProvider, ReceivedProvider {

	private ExecutorService              executor     = Executors.newFixedThreadPool(10);
	private ListeningExecutorService moreExecutor     = MoreExecutors.listeningDecorator(executor);

	private BalanceProviders     balanceProviders     = new BalanceProviders();
	private ReceivedProviders    receivedProviders    = new ReceivedProviders();
	private LoadingCache<String, Long> cache;

	private static BalanceAndReceivedDispatchers me;

	public static BalanceAndReceivedDispatchers getInstance() {
		if(me==null) {
			me=new BalanceAndReceivedDispatchers();
		}	
		return me;
	}

	private BalanceAndReceivedDispatchers() {
		initializeCache();
	}

	private void initializeCache() {
		cache = CacheBuilder.newBuilder()
				.maximumSize(100000)
				.recordStats()
				.build(
						new CacheLoader<String, Long>() {
							public Long load(String cacheAddress) {
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
	
	public Long getBalanceCache(String address) {
		Long ret=cache.getIfPresent("b/" + address);
		if(ret==null)
			ret=0L;
		return ret;
	}

	@Override
	public Long getBalance(String address) {
		return getBalance(address,0);
	}

	public Long getBalance(String address, int cont) {
		if(cont>3)
			return null;
		Long valCache = cache.getIfPresent("b/"+address);
		BalanceProvider bal1 = balanceProviders.take();
		BalanceProvider bal2 = balanceProviders.takeDifferent(bal1);
		System.out.println("bal1=" + bal1 + " bal2="+bal2);
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
			if(valCache!=null && valCache.equals( valRet ) )
				return valRet;
			
			Long first  = listenableFuture1.get();
			Long second = listenableFuture2.get();
			if(first!=null && first.equals( second ) ) {
				cache.put("b/" + address, first);
				return first;
			}
		} catch (Exception e) {		
		}
		return getBalance(address,cont+1);
	}

	@Override
	public Long getReceived(String address) {
		return getReceived(address,0);
	}
	public Long getReceived(String address, int cont) {
		if(cont>3)
			return null;
		Long valCache = cache.getIfPresent("r/"+address);
		ReceivedProvider rec1 = receivedProviders.take();
		ReceivedProvider rec2 = receivedProviders.takeDifferent(rec1);
		System.out.println("rec1=" + rec1 + " rec2="+rec2);
		
		Callable<Long> runner1 = new ReceivedRunnable(rec1, address);
		Callable<Long> runner2 = new ReceivedRunnable(rec2, address);
		final Long start=System.currentTimeMillis();
		ListenableFuture<Long> listenableFuture1 = moreExecutor.submit( runner1 );
		ListenableFuture<Long> listenableFuture2 = moreExecutor.submit(runner2);
		SettableFuture<Long> returned = SettableFuture.create();
		Futures.addCallback(listenableFuture1,new ReceivedFutureCallback(start,  rec1, returned, receivedProviders ));
		Futures.addCallback(listenableFuture2,new ReceivedFutureCallback(start,  rec2, returned, receivedProviders ));
		/* check balance runnable??? */
		/* how to spot providers returning wrong values? */
		
		Long valRet;
		try {
			valRet = returned.get();  /* return the faster */
			if(valCache!=null && valCache.equals( valRet ) )
				return valRet;
			
			Long first  = listenableFuture1.get();
			Long second = listenableFuture2.get();
			if(first!=null && first.equals( second ) ) {
				cache.put("r/" + address, first);
				return first;
			}
			if(first != null)	    cache.put("r/" + address, first);
			else if(second!= null)	cache.put("r/" + address, second);
			
		} catch (Exception e) {
			
		}
		return getBalance(address,cont+1);
	}

	public Long getReceivedCache(String address) {
		Long ret=cache.getIfPresent("r/" + address);
		if(ret==null)
			ret=0L;
		return ret;
	}

	public BalanceProviders getBalanceProviders() {
		return balanceProviders;
	}

	public ReceivedProviders getReceivedProviders() {
		return receivedProviders;
	}

}
