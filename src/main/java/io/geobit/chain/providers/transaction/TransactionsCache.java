package io.geobit.chain.providers.transaction;

import static io.geobit.common.statics.Log.log;

import java.util.concurrent.TimeUnit;

import io.geobit.chain.providers.GetPut;
import io.geobit.common.entity.Transaction;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;

public class TransactionsCache implements GetPut<String,Transaction> {
	private LoadingCache<String,Transaction>  allSpentCache;
	private LoadingCache<String,Transaction>  expiringCache;
	
	public TransactionsCache() {
		super();
		
		allSpentCache = CacheBuilder.newBuilder()
				.maximumSize(100000) 
				.build(
						new CacheLoader<String, Transaction>() {
							public Transaction load(String txHash) {  /* never been called */
								log("txCache  " + txHash);
								throw new RuntimeException("txCache");			
							}
						}
						);
		
		expiringCache = CacheBuilder.newBuilder()
				.maximumSize(100000) 
				.expireAfterWrite(1, TimeUnit.MINUTES)
				.build(
						new CacheLoader<String, Transaction>() {
							public Transaction load(String txHash) {  /* never been called */
								log("txCache  " + txHash);
								throw new RuntimeException("txCache");			
							}
						}
						);
		
	}

	@Override
	public Transaction get(String k) {
		Transaction t= allSpentCache.getIfPresent(k);
		if(t!=null)
			return t;
		t=expiringCache.getIfPresent(k);
		if(t!=null)
			return t;
		return null;
	}

	@Override
	public void put(String k, Transaction v) {
		expiringCache.put(k, v);
		if(v.areAllSpent())
			allSpentCache.put(k, v);		
	}

	@Override
	public String toString() {
		return "TransactionsCache [allSpentCache.size()=" + allSpentCache.size()
				+ ", expiringCache.size()=" + expiringCache.size() + "]";
	}
	
	
	
	
	

}
